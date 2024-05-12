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

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadCartItems();
    }

    private void loadCartItems() {
        List<ShoppingCartModel> cartItems = fetchCartItems(userId);

        cartItemsContainer.getChildren().clear();
        for (ShoppingCartModel item : cartItems) {
            // Create UI components for each cart item
            Label itemName = new Label("Model: " + item.getCarModel() + ", Mark: " + item.getCarMark());
            Label itemPrice = new Label("Price: " + item.getPrice());
            VBox itemContainer = new VBox(itemName, itemPrice);
            cartItemsContainer.getChildren().add(itemContainer);
        }
    }

    @FXML
    private void purchaseItems() {
        // Logic to handle purchasing items
        System.out.println("Purchasing items...");
    }

    private List<ShoppingCartModel> fetchCartItems(int userId) {
        List<ShoppingCartModel> cartItems = new ArrayList<>();
        String query = "SELECT c.*, car.model, car.mark FROM cart c JOIN car car ON c.car_id = car.id WHERE c.user_id = ?";
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
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}


