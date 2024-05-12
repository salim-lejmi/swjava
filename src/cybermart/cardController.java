package cybermart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class cardController {
    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardName, cardPrice, cardStock, cardDescription, cardColor, cardMileage, cardQuantity;
    private cardObject cardC;
    private MyListener myListener;
    @FXML
    private HBox pCard;
    private int userId;


    public void setData(cardObject cardC, MyListener myListener, int userId) {
        this.cardC = cardC;
        this.myListener = myListener;
        this.userId = userId;

        String imagePath = "C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads\\" + cardC.getPictures();
        Image tImage1 = new Image("file:" + imagePath, 169, 163, false, false);
        cardImage.setImage(tImage1);
        cardImage.setFitHeight(169);
        cardImage.setFitWidth(163);

        cardName.setText(cardC.getMark() + " " + cardC.getModel());
        cardPrice.setText("Price: " + String.valueOf(cardC.getPrice()));
        cardStock.setText("Stock: " + String.valueOf(cardC.getQuantity()));

        cardDescription.setText("Description: " + cardC.getDescription());
        cardColor.setText("Color: " + cardC.getColor());
        cardMileage.setText("Mileage: " + String.valueOf(cardC.getMileage()));
        cardQuantity.setText("Quantity: " + String.valueOf(cardC.getQuantity()));
    }


    @FXML
    private void click() {
        myListener.onClickListener(cardC);
    }
    public void buyClicked() {
        int userId = getUserId();
        System.out.println("User ID in card controller: " + userId);

        int carId = cardC.getId(); // Assuming cardObject has an ID
        DatabaseConnection connectNow = new DatabaseConnection();

        String insertQuery = "INSERT INTO cart (price, user_id, car_id, purchased) VALUES (?,?,?,?)";
        try (Connection connectDB = connectNow.getConnection();
             PreparedStatement statement = connectDB.prepareStatement(insertQuery)) {
            statement.setDouble(1, cardC.getPrice());
            statement.setInt(2, userId);
            statement.setInt(3, carId);
            statement.setBoolean(4, false); // Assuming 'purchased' is a boolean indicating if the item is purchased
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getUserId() {
        return userId;

    }
    // Additional methods for handling user interactions, such as clicks, can be added here
}
