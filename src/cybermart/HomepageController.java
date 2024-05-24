/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cybermart;

import com.jfoenix.controls.JFXButton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
//import java.time.Duration;
import java.util.ResourceBundle;
//import javafx.​collections.​transformation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author majid
 */
public class HomepageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private JFXButton exit;
 

    @FXML
    private AnchorPane slider;

    
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
    private JFXButton searchButton;
    @FXML
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String username;

    private int userId;
    private FrontPageController frontPageController;

    // ObservableList<productSearchModel>productSearchModelObservableList = FXCollections.observableArrayList();
    private void loadFrontPageController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("frontPage.fxml"));
            Parent root = fxmlLoader.load();
            frontPageController = fxmlLoader.getController();
            bordermainPane.setCenter(root);
        } catch (IOException e) {
            System.err.println("Error loading frontPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void loadFrontPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("frontPage.fxml"));
            Parent root = loader.load();
            bordermainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        DatabaseConnection connectnow = new DatabaseConnection();
        Connection connectDBMS = connectnow.getConnection();


        String insertMain = "Update search_info SET search_query='' WHERE s_id ='1';";
        fxmlLoaderHome objectFront = new fxmlLoaderHome();
        String frontPageFXMLPath = "src/cybermart/frontPage.fxml";

        Pane viewHome = null;
        loadFrontPageController();




        exit.setOnMouseClicked(event -> {
                    System.exit(0);
                }
        );
        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();
            slider.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });
        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-176);
            slide.play();
            slider.setTranslateX(0);
            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
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
                shoppingCartController.setUserId(userId); // Pass the user ID

                // Assuming bordermainPane is the main container in your application
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
                receiptController.setUserId(userId); // Pass the user ID

                bordermainPane.setCenter(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        searchButton.setOnMouseClicked(event -> {


            try {
                Statement statement = connectDBMS.createStatement();

                statement.executeUpdate(insertMain);

            } catch (SQLException e) {
                e.getCause();
            }
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("searchPage");
            bordermainPane.setCenter(view);

        });

        homeButton.setOnMouseClicked(event -> {
            if (frontPageController != null) {
                frontPageController.refreshData(); // Refresh the data in FrontPageController
            }
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("frontPage");
            bordermainPane.setCenter(view);
        });


        MenuBack.setOnMouseExited(event -> {
            MenuBack.setStyle("-fx-background-color: #00000000; ");

        });
        MenuBack.setOnMouseEntered(event -> {
            MenuBack.setStyle("-fx-background-color:  #00CED1; ");

        });
        Menu.setOnMouseExited(event -> {
            Menu.setStyle("-fx-background-color: #00000000; ");

        });
        Menu.setOnMouseEntered(event -> {
            Menu.setStyle("-fx-background-color:  #00CED1; ");

        });




    }


        public void setLabel(String text){
            login_sign.setText(text);
           System.out.println(text);
        }
    public void setUsername(String username) {
        this.username = username;
        // Update the UI to show logout button or user information
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
            // User is logged in
            login_sign.setText("Logout");
            // You can also display user information here
        } else {
            // User is not logged in
            login_sign.setText("Login/Signup");
        }
    }

    @FXML
    private void showAddCardForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCardForm.fxml"));
            Parent root = loader.load();
            AddCardFormController controller = loader.getController();
            controller.setHomepageController(this); // Pass reference to HomepageController
            bordermainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleLogoutClick() {
        // Perform logout actions
        username = null;
        updateUI();

        // Switch back to the login scene or show a message
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) bordermainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }



}
 
            

        
    

