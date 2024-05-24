package cybermart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    private FileChooser fileChooser;
    private File selectedFile;
    private HomepageController homepageController;

    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", ".png", ".jpg", "*.gif")
        );
    }

    public void setHomepageController(HomepageController homepageController) {
        this.homepageController = homepageController;
    }

    @FXML
    private void handleAddButton() {
        String mark = markField.getText();
        String model = modelField.getText();
        int price = Integer.parseInt(priceField.getText());
        String description = descriptionField.getText();
        String pictures = null;
        if (selectedFile != null) {
            pictures = selectedFile.getName();
            try {
                Path destination = Paths.get("C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads\\" + selectedFile.getName());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        String insertQuery = "INSERT INTO car (mark, model, price, description, pictures, abs, epc, gray_card, auto_gear_box, taxes, insurance, color, mileage, quantity, add_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setInt(16, 45);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new car was inserted successfully!");
                homepageController.loadFrontPage();
                closeStage();
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL insert: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSelectImage() {
        selectedFile = fileChooser.showOpenDialog(markField.getScene().getWindow());
        if (selectedFile != null) {
            picturesField.setText(selectedFile.getName());
        }
    }

    @FXML
    private void handleCancelButton() {
        homepageController.loadFrontPage();
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) markField.getScene().getWindow();
        stage.close();
    }
}
