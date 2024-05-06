package cybermart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class cardController {
    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardName, cardPrice, cardStock, cardDescription, cardColor, cardMileage, cardQuantity;
    private cardObject cardC;
    private MyListener myListener;
    @FXML
    private HBox pCard;

    public void setData(cardObject cardC, MyListener myListener) {
        this.cardC = cardC;
        this.myListener = myListener;

        // Set existing fields
        String imagePath = "C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads\\" + cardC.getPictures();
        Image tImage1 = new Image("file:" + imagePath, 169, 163, false, false);
        cardImage.setImage(tImage1);
        cardImage.setFitHeight(169);
        cardImage.setFitWidth(163);

        cardName.setText(cardC.getMark() + " " + cardC.getModel());
        cardPrice.setText(String.valueOf(cardC.getPrice()));
        cardStock.setText(String.valueOf(cardC.getQuantity()));

        // Set new fields
        cardDescription.setText(cardC.getDescription());
        cardColor.setText(cardC.getColor());
        cardMileage.setText(String.valueOf(cardC.getMileage()));
        cardQuantity.setText(String.valueOf(cardC.getQuantity()));
    }
    @FXML
    private void click() {
        // Add your logic here for handling the click event
        // For example, you could call the MyListener's onClickListener method
        myListener.onClickListener(cardC);
    }
    // Additional methods for handling user interactions, such as clicks, can be added here
}
