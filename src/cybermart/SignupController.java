package cybermart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.mindrot.jbcrypt.BCrypt;

public class SignupController implements Initializable {
    public Label jobTitleLabel;
    public Label specialityLabel;

    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField bankAccountField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private TextField specialityField;
    @FXML
    private Label messageLabel;

    @FXML
    private Button clientSignupButton;
    @FXML
    private Button expertSignupButton;
    @FXML
    private Button pictureUploadButton;
    @FXML
    private Button jobDescriptionUploadButton;

    @FXML
    private Button backButton; // Declare the back button

    @FXML
    private void handleBack() {
        try {
            // Load the login page
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login Page");
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isClientSignup = true;
    private String picturePath = "";
    private String jobDescriptionPath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientSignupButton.setOnAction(event -> {
            isClientSignup = true;
            updateFields();
        });

        expertSignupButton.setOnAction(event -> {
            isClientSignup = false;
            updateFields();
        });
    }

    private void updateFields() {
        jobTitleField.setVisible(!isClientSignup);
        specialityField.setVisible(!isClientSignup);
        pictureUploadButton.setVisible(true); // Assuming picture upload is common for both
        jobDescriptionUploadButton.setVisible(!isClientSignup);
        jobTitleLabel.setVisible(!isClientSignup);
        specialityLabel.setVisible(!isClientSignup);

    }


    @FXML
    private void handlePictureUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String uploadDirectory = "C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads";
            File targetFile = new File(uploadDirectory, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                picturePath = targetFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleJobDescriptionUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Job Description");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String uploadDirectory = "C:\\Users\\21696\\Documents\\SwiftWheels\\backend\\uploads";
            File targetFile = new File(uploadDirectory, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                jobDescriptionPath = targetFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void handleSignup() {
        if (isClientSignup) {
            handleClientSignup();
        } else {
            handleExpertSignup();
        }
    }

    public void handleClientSignup() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String bank_account= bankAccountField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            try {
                String query = "INSERT INTO user (username, email, password, bank_account, roles, picture_url,bank_amount) VALUES (?, ?, ?, ?, ?, ?,?)";
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, hashedPassword);
                statement.setString(4, bank_account);
                statement.setString(5, "ROLE_CLIENT");
                statement.setString(6, picturePath);
                statement.setInt(7, 9515135);

                statement.executeUpdate();
                messageLabel.setText("Client registration successful!");
            } catch (SQLException e) {
                messageLabel.setText("Registration failed: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Passwords do not match!");
        }
    }

    public void handleExpertSignup() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String jobTitle = jobTitleField.getText();
        String speciality = specialityField.getText();
        String bank_account= bankAccountField.getText();

        if (password.equals(confirmPassword)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            try {
                String query = "INSERT INTO user (username, email, password, bank_account, job_title, speciality, roles, picture_url, job_description,bank_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, hashedPassword);
                statement.setString(4, bank_account);
                statement.setString(5, jobTitle);
                statement.setString(6, speciality);
                statement.setString(7, "ROLE_EXPERT");
                statement.setString(8, picturePath);
                statement.setString(9, jobDescriptionPath);
                statement.setInt(10, 9515135);

                statement.executeUpdate();
                messageLabel.setText("Expert registration successful!");
            } catch (SQLException e) {
                messageLabel.setText("Registration failed: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Passwords do not match!");
        }
    }

}