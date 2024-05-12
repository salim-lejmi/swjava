/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cybermart;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;





/**
 * FXML Controller class
 *
 * @author majid
 */

public class LoginController implements Initializable {
    @FXML
    private JFXButton loginButton;

    @FXML
    private TextField passwordField;

    @FXML
    private JFXButton signupButton;

    @FXML
    private TextField usernameField;
    @FXML
    private AnchorPane LoginPane;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label LoginMsgLabel;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        signupButton.setOnMouseClicked(event->{
            try {
                root = FXMLLoader.load(getClass().getResource("signup.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Signup Page");
                stage.resizableProperty().setValue(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        loginButton.setOnMouseClicked(event-> {

            if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {

                validateLogin();

            } else {
                LoginMsgLabel.setText("Please enter Username & Password");
            }


        });

    }
    public void validateLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            LoginMsgLabel.setText("Please enter Username & Password");
            return;
        }

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password); // Consider hashing passwords for security

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Login successful
                LoginMsgLabel.setText("Welcome to Cyber Mart family");
                int userId = resultSet.getInt("id");

                // Load the homepage scene
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                Parent root = fxmlLoader.load();
                HomepageController homepageController = fxmlLoader.getController();

                // Update the existing scene with the homepage content
                Stage stage = (Stage) LoginPane.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Homepage");
                stage.resizableProperty().setValue(false);
                stage.show();

                // Pass the username to the HomepageController if needed
                homepageController.setUsername(username);
                homepageController.setUserId(userId); // Add this method in HomepageController

            } else {
                // Login failed
                LoginMsgLabel.setText("Invalid login. Please try again.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            LoginMsgLabel.setText("An error occurred. Please try again.");
        }
    }




}