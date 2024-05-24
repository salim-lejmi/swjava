/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cybermart;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author majid
 */
public class HomepageController implements Initializable {

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton apparelsButton;
    @FXML
    private JFXButton kidsButton;
    @FXML
    private JFXButton accesoriesButton;
    @FXML
    private JFXButton electronicsButton;
    @FXML
    private JFXButton cartButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton receiptButton;
    @FXML
    private JFXButton login_sign;
    @FXML
    private BorderPane bordermainPane;
    @FXML
    private JFXButton addCarButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String username;
    private int userId;
    private FrontPageController frontPageController;

    private void loadFrontPageController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("frontPage.fxml"));
            Parent root = loader.load();
            frontPageController = loader.getController();
            FrontPageController frontPageController = loader.getController();
            frontPageController.setUserId(this.userId);
            bordermainPane.setCenter(root);
        } catch (IOException e) {
            System.err.println("Error loading frontPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadFrontPage() {
        loadFrontPageController();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseConnection connectnow = new DatabaseConnection();
        Connection connectDBMS = connectnow.getConnection();

        String insertMain = "Update search_info SET search_query='' WHERE s_id ='1';";

        exit.setOnMouseClicked(event -> System.exit(0));

        loadFrontPage();

        homeButton.setOnMouseClicked(event -> loadFrontPage());

        login_sign.setOnMouseClicked(event -> {
            if (username == null) {
                try {
                    root = FXMLLoader.load(getClass().getResource("login.fxml"));
                    stage = new Stage();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Login Page");
                    stage.resizableProperty().setValue(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            } else {
                handleLogoutClick();
            }
        });

        cartButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shoppingCart.fxml"));
                Parent root = fxmlLoader.load();
                ShoppingCartController shoppingCartController = fxmlLoader.getController();
                shoppingCartController.setUserId(userId);
                bordermainPane.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        receiptButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receipt.fxml"));
                Parent root = fxmlLoader.load();
                ReceiptController receiptController = fxmlLoader.getController();
                receiptController.setUserId(userId);
                bordermainPane.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addCarButton.setOnMouseClicked(event -> showAddCardForm());
    }

    public void setLabel(String text) {
        login_sign.setText(text);
        System.out.println(text);
    }

    public void setUsername(String username) {
        this.username = username;
        updateUI();
        System.out.println("Username: " + username);
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User ID in home page controller: " + userId);
        if (frontPageController != null) {
            frontPageController.setUserId(userId);
        }
    }

    private void updateUI() {
        if (username != null) {
            login_sign.setText("Logout");
        } else {
            login_sign.setText("Login/Signup");
        }
    }

    @FXML
    private void showAddCardForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCardForm.fxml"));
            Parent root = loader.load();
            AddCardFormController controller = loader.getController();
            controller.setHomepageController(this);
            bordermainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutClick() {
        username = null;
        updateUI();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) bordermainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}