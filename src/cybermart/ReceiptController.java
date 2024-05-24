package cybermart;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptController {
    @FXML
    private VBox receiptItemsContainer;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadReceiptItems();
    }

    private void loadReceiptItems() {
        List<ReceiptModel> receiptItems = fetchReceiptItems(userId);
        receiptItemsContainer.getChildren().clear();
        for (ReceiptModel item : receiptItems) {
            // Create UI components for each receipt item
            ImageView itemImage = new ImageView();
            String imagePath = "C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads\\" + item.getPictures();
            Image image = new Image("file:///" + imagePath, 250, 150, false, false);
            itemImage.setImage(image);
            itemImage.getStyleClass().add("receipt-item-image");

            Label itemName = new Label("Model: " + item.getCarModel() + ", Mark: " + item.getCarMark());
            itemName.getStyleClass().add("receipt-item-name");
            Label itemPrice = new Label("Price: $" + item.getPrice());
            itemPrice.getStyleClass().add("receipt-item-price");
            Label purchaseDateLabel = new Label("Purchase Date: " + item.getPurchaseDate());
            purchaseDateLabel.getStyleClass().add("receipt-item-date");

            VBox itemDetails = new VBox(itemName, itemPrice, purchaseDateLabel);
            itemDetails.getStyleClass().add("receipt-item-details");

            HBox itemContainer = new HBox(itemImage, itemDetails);
            itemContainer.getStyleClass().add("receipt-item");
            itemContainer.setAlignment(Pos.CENTER_LEFT);
            itemContainer.setSpacing(10);

            receiptItemsContainer.getChildren().add(itemContainer);
        }
    }


    private List<ReceiptModel> fetchReceiptItems(int userId) {
        List<ReceiptModel> receiptItems = new ArrayList<>();
        String query = "SELECT r.purchase_date, c.price, car.model, car.mark, car.pictures " +
                "FROM Receipt r " +
                "JOIN Cart c ON r.cart_id = c.id " +
                "JOIN Car car ON c.car_id = car.id " +
                "WHERE c.user_id = ?";
        DatabaseConnection connectNow = new DatabaseConnection();

        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReceiptModel item = new ReceiptModel(
                        resultSet.getTimestamp("purchase_date").toLocalDateTime(),
                        resultSet.getDouble("price"),
                        resultSet.getString("model"),
                        resultSet.getString("mark")
                );
                item.setPictures(resultSet.getString("pictures")); // Set the pictures path
                receiptItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receiptItems;
    }
}
