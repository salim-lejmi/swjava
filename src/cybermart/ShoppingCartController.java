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
            Image image = new Image("file:///" + imagePath, 250, 150, false, false); // Increase width and height to 150
            itemImage.setImage(image);
            itemImage.getStyleClass().add("cart-item-image");

            Label itemName = new Label("Model: " + item.getCarModel() + ", Mark: " + item.getCarMark());
            itemName.getStyleClass().add("cart-item-name");
            Label itemPrice = new Label("Price: $" + item.getPrice());
            itemPrice.getStyleClass().add("cart-item-price");

            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setOnAction(event -> {
                deleteCartItem(item.getId());
                loadCartItems(); // Reload cart items after deletion
            });

            VBox itemDetails = new VBox(itemName, itemPrice);
            itemDetails.getStyleClass().add("cart-item-details");

            HBox itemContainer = new HBox(itemImage, itemDetails, deleteButton);
            itemContainer.getStyleClass().add("cart-item");
            itemContainer.setAlignment(Pos.CENTER_LEFT);
            itemContainer.setSpacing(10);

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

        if (cartItems.isEmpty()) {
            showAlert("Your cart is empty. Please add items to your cart before purchasing.");
            return;
        }

        double totalPrice = cartItems.stream().mapToDouble(ShoppingCartModel::getPrice).sum();
        double userBalance = fetchUserBalance(userId);

        if (userBalance < totalPrice) {
            showAlert("Insufficient balance to complete the purchase.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to purchase the items in your cart?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Deduct the total price from the user's bank account
            updateUserBalance(userId, userBalance - totalPrice);

            // Proceed with the purchase
            for (ShoppingCartModel item : cartItems) {
                // Mark the item as purchased
                updateCartItemAsPurchased(item.getId());

                // Insert a new record in the Receipt table
                insertReceipt(item.getId(), LocalDateTime.now());
            }

            // Reload the cart items
            loadCartItems();

            // Show a success alert
            showAlert("Purchase successful!");
        }
    }

    private double fetchUserBalance(int userId) {
        String query = "SELECT bank_amount FROM user WHERE id = ?";
        DatabaseConnection connectNow = new DatabaseConnection();

        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("bank_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void updateUserBalance(int userId, double newBalance) {
        String query = "UPDATE user SET bank_amount = ? WHERE id = ?";
        DatabaseConnection connectNow = new DatabaseConnection();

        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
