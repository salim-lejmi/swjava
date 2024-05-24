package cybermart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import com.luciad.imageio.webp.WebPReadParam;
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

        Image tImage1;
        if (imagePath.endsWith(".webp")) {
            tImage1 = loadWebPImage(imagePath);
        } else {
            tImage1 = new Image("file:" + imagePath);
        }

        cardImage.setImage(tImage1);
        cardName.setText(cardC.getMark() + " " + cardC.getModel());
        cardPrice.setText("Price: " + String.valueOf(cardC.getPrice()));
        cardDescription.setText("Description: " + cardC.getDescription());
        cardColor.setText("Color: " + cardC.getColor());
        cardMileage.setText("Mileage: " + String.valueOf(cardC.getMileage()));
        cardQuantity.setText("Quantity: " + String.valueOf(cardC.getQuantity()));
    }


    private Image loadWebPImage(String imagePath) {
        try {
            File file = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(new FileImageInputStream(file));
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
}
