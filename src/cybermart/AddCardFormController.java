package cybermart;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
public class AddCardFormController {

    @FXML
    private TextField markField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField picturesField;
    @FXML
    private CheckBox absCheckBox;
    @FXML
    private CheckBox epcCheckBox;
    @FXML
    private CheckBox grayCardCheckBox;
    @FXML
    private CheckBox autoGearBoxCheckBox;
    @FXML
    private CheckBox taxesCheckBox;
    @FXML
    private CheckBox insuranceCheckBox;
    @FXML
    private TextField colorField;
    @FXML
    private TextField mileageField;
    @FXML
    private TextField quantityField;

    @FXML
    private void handleAddButton() {
        String mark = markField.getText();
        String model = modelField.getText();
        int price = Integer.parseInt(priceField.getText());
        String description = descriptionField.getText();
        String pictures = picturesField.getText();
        boolean abs = absCheckBox.isSelected();
        boolean epc = epcCheckBox.isSelected();
        boolean grayCard = grayCardCheckBox.isSelected();
        boolean autoGearBox = autoGearBoxCheckBox.isSelected();
        boolean taxes = taxesCheckBox.isSelected();
        Timestamp currentTime = new Timestamp(new Date().getTime());
        boolean insurance = insuranceCheckBox.isSelected();
        String color = colorField.getText();
        int mileage = Integer.parseInt(mileageField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Modify the INSERT query to include user_id
        String insertQuery = "INSERT INTO car (mark, model, price, description, pictures, abs, epc, gray_card, auto_gear_box, taxes, insurance, color, mileage, quantity, add_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        try {
            PreparedStatement statement = connectDB.prepareStatement(insertQuery);
            statement.setString(1, mark);
            statement.setString(2, model);
            statement.setInt(3, price);
            statement.setString(4, description);
            statement.setString(5, pictures);
            statement.setBoolean(6, abs);
            statement.setBoolean(7, epc);
            statement.setBoolean(8, grayCard);
            statement.setBoolean(9, autoGearBox);
            statement.setBoolean(10, taxes);
            statement.setBoolean(11, insurance);
            statement.setString(12, color);
            statement.setInt(13, mileage);
            statement.setInt(14, quantity);
            statement.setTimestamp(15, currentTime);
            // Set user_id to 45
            statement.setInt(16, 45);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new car was inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL insert: " + e.getMessage());
            e.printStackTrace();
        }

        // Close the AddCardForm stage
        Stage stage = (Stage) markField.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void handleCancelButton() {
        // Close the AddCardForm stage
        Stage stage = (Stage) markField.getScene().getWindow();
        stage.close();
    }}