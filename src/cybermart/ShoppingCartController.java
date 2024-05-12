/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cybermart;

import com.jfoenix.controls.JFXButton;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author majid
 */
public class ShoppingCartController {
    @FXML
    private VBox cartItemsContainer;
    @FXML
    private Button purchaseButton;

    @FXML
    private Label itemCountLabel;

    @FXML
    private Label totalPriceLabel;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadCartItems();
    }

    private void loadCartItems() {
        List<ShoppingCartModel> cartItems = fetchCartItems(userId);
        cartItemsContainer.getChildren().clear();
        double totalPrice = 0.0;
        for (ShoppingCartModel item : cartItems) {
            // Create UI components for each cart item
            ImageView itemImage = new ImageView();
            String imagePath = "C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads\\" + item.getPictures();
            Image image = new Image("file:" + imagePath, 100, 100, false, false);
            itemImage.setImage(image);

            Label itemName = new Label("Model: " + item.getCarModel() + ", Mark: " + item.getCarMark());
            Label itemPrice = new Label("Price: $" + item.getPrice());

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                deleteCartItem(item.getId());
                loadCartItems(); // Reload cart items after deletion
            });

            HBox itemContainer = new HBox(itemImage, new VBox(itemName, itemPrice), deleteButton);
            itemContainer.setSpacing(10);
            itemContainer.setStyle("-fx-padding: 10px; -fx-background-color: #f0f0f0; -fx-background-radius: 5px;");

            cartItemsContainer.getChildren().add(itemContainer);

            totalPrice += item.getPrice();
        }

        itemCountLabel.setText(cartItems.size() + " Items");
        totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
    }

    private void deleteCartItem(int itemId) {
        String query = "DELETE FROM cart WHERE id = ?";
        DatabaseConnection connectNow = new DatabaseConnection();

        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setInt(1, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void purchaseItems() {
        List<ShoppingCartModel> cartItems = fetchCartItems(userId);
        for (ShoppingCartModel item : cartItems) {
            // Mark the item as purchased
            updateCartItemAsPurchased(item.getId());

            // Insert a new record in the Receipt table
            insertReceipt(item.getId(), LocalDateTime.now());
        }

        // Reload the cart items
        loadCartItems();
    }

    private void updateCartItemAsPurchased(int itemId) {
        String query = "UPDATE cart SET purchased = true WHERE id = ?";
        DatabaseConnection connectNow = new DatabaseConnection();

        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setInt(1, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertReceipt(int cartId, LocalDateTime purchaseDate) {
        String query = "INSERT INTO Receipt (purchase_date, cart_id) VALUES (?, ?)";
        DatabaseConnection connectNow = new DatabaseConnection();
        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.valueOf(purchaseDate));
            statement.setInt(2, cartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<ShoppingCartModel> fetchCartItems(int userId) {
        List<ShoppingCartModel> cartItems = new ArrayList<>();
        String query = "SELECT c.*, car.model, car.mark, car.pictures FROM cart c JOIN car car ON c.car_id = car.id WHERE c.user_id = ? AND c.purchased = false";
        DatabaseConnection connectNow = new DatabaseConnection();

        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ShoppingCartModel item = new ShoppingCartModel(
                        resultSet.getInt("id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("car_id"),
                        resultSet.getBoolean("purchased")
                );
                item.setCarModel(resultSet.getString("model"));
                item.setCarMark(resultSet.getString("mark"));
                item.setPictures(resultSet.getString("pictures")); // Add this line
                cartItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}


