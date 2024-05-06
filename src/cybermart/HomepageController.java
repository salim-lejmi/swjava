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
    private JFXButton dashboardButton;
    @FXML
    private JFXButton vendorButton;
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

    
   // ObservableList<productSearchModel>productSearchModelObservableList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        DatabaseConnection connectnow = new DatabaseConnection();
        Connection connectDBMS = connectnow.getConnection();


        String insertApp = "Update search_info SET search_query='Apparels' WHERE s_id ='1';";
        String insertElec = "Update search_info SET search_query='Electronics' WHERE s_id ='1';";
        String insertAcc = "Update search_info SET search_query='Accesories' WHERE s_id ='1';";
        String insertKid = "Update search_info SET search_query='Kids' WHERE s_id ='1';";
        String insertMain = "Update search_info SET search_query='' WHERE s_id ='1';";
        fxmlLoaderHome objectFront = new fxmlLoaderHome();
        String frontPageFXMLPath = "src/cybermart/frontPage.fxml";

        Pane viewHome = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(frontPageFXMLPath).toURI().toURL());
            viewHome = fxmlLoader.load();
            bordermainPane.setCenter(viewHome);
        } catch (IOException e) {
            System.err.println("Error loading frontPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        bordermainPane.setCenter(viewHome);


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

            int cus_id = 0;
            String cus_name = null;
            try {
                Statement statement1 = connectDBMS.createStatement();
                String queryCID = "select customer_ID,customer_name FROM c_user WHERE c_ID=1;";
                ResultSet queryCR = statement1.executeQuery(queryCID);
                while (queryCR.next()) {
                    cus_id = queryCR.getInt("customer_ID");
                    cus_name = queryCR.getString("customer_name");
                }

            } catch (SQLException e) {
                e.getCause();
            }
            if (cus_id == 0 || cus_name.isEmpty()) {
                fxmlLoaderHome object = new fxmlLoaderHome();
                Pane view = object.getPage("pleaseLogin");
                bordermainPane.setCenter(view);

            } else {

                fxmlLoaderHome object = new fxmlLoaderHome();
                Pane view = object.getPage("shoppingCart");
                bordermainPane.setCenter(view);

            }

        });
        dashboardButton.setOnMouseClicked(event -> {

            int cus_id = 0;
            String cus_name = null;
            try {
                Statement statement1 = connectDBMS.createStatement();
                String queryCID = "select customer_ID,customer_name FROM c_user WHERE c_ID=1;";
                ResultSet queryCR = statement1.executeQuery(queryCID);
                while (queryCR.next()) {
                    cus_id = queryCR.getInt("customer_ID");
                    cus_name = queryCR.getString("customer_name");
                }

            } catch (SQLException e) {
                e.getCause();
            }
            if (cus_id == 0 || cus_name.isEmpty()) {
                fxmlLoaderHome object = new fxmlLoaderHome();
                Pane view = object.getPage("pleaseLogin");
                bordermainPane.setCenter(view);

            } else {

                fxmlLoaderHome object = new fxmlLoaderHome();
                Pane view = object.getPage("DashboardPage");
                bordermainPane.setCenter(view);

            }


        });
        vendorButton.setOnMouseClicked(e -> {
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("vendorPage");
            bordermainPane.setCenter(view);
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

            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("frontPage");
            bordermainPane.setCenter(view);

        });
        apparelsButton.setOnMouseClicked(event -> {

            try {
                Statement statement = connectDBMS.createStatement();
                statement.executeUpdate(insertApp);

            } catch (SQLException e) {
                e.getCause();

            }
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("searchPage");

            bordermainPane.setCenter(view);

        });
        electronicsButton.setOnMouseClicked(event -> {

            try {
                Statement statement = connectDBMS.createStatement();
                statement.executeUpdate(insertElec);

            } catch (SQLException e) {
                e.getCause();
            }
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("searchPage");
            bordermainPane.setCenter(view);

        });
        kidsButton.setOnMouseClicked(event -> {


            try {
                Statement statement = connectDBMS.createStatement();
                statement.executeUpdate(insertKid);
            } catch (SQLException e) {
                e.getCause();
            }
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("searchPage");
            bordermainPane.setCenter(view);

        });
        accesoriesButton.setOnMouseClicked(event -> {


            try {
                Statement statement = connectDBMS.createStatement();
                statement.executeUpdate(insertAcc);
            } catch (SQLException e) {
                e.getCause();
            }
            fxmlLoaderHome object = new fxmlLoaderHome();
            Pane view = object.getPage("searchPage");

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

//searchButton.setOnMouseEntered(event->{
//            searchButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        searchButton.setOnMouseExited(event->{
//            searchButton. setStyle("-fx-background-color: #00CED1; "); 
//        });
//        apparelsButton.setOnMouseEntered(event->{
//            apparelsButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        apparelsButton.setOnMouseExited(event->{
//            apparelsButton. setStyle("-fx-background-color: #00CED1; "); 
//        });
//        electronicsButton.setOnMouseExited(event->{
//            electronicsButton. setStyle("-fx-background-color: #00CED1; "); 
//        });
//        electronicsButton.setOnMouseEntered(event->{
//            electronicsButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//         kidsButton.setOnMouseExited(event->{
//             kidsButton. setStyle("-fx-background-color: #00CED1; "); 
//        });
//        kidsButton.setOnMouseEntered(event->{
//             kidsButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        accesoriesButton.setOnMouseExited(event->{
//            accesoriesButton. setStyle("-fx-background-color: #00CED1; "); 
//        });
//        accesoriesButton.setOnMouseEntered(event->{
//            accesoriesButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//    login_sign.setOnMouseEntered(event->{
//            login_sign. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        login_sign.setOnMouseExited(event->{
//            login_sign. setStyle("-fx-background-color: #40E0D0; "); 
//            
//        });
//        vendorButton.setOnMouseEntered(event->{
//            vendorButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        vendorButton.setOnMouseExited(event->{
//            vendorButton. setStyle("-fx-background-color: #40E0D0; "); 
//        });
//        dashboardButton.setOnMouseEntered(event->{
//            dashboardButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        dashboardButton.setOnMouseExited(event->{
//            dashboardButton. setStyle("-fx-background-color: #40E0D0; "); 
//        });
//        cartButton.setOnMouseEntered(event->{
//            cartButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        cartButton.setOnMouseExited(event->{
//            cartButton. setStyle("-fx-background-color: #40E0D0; "); 
//        });
//        homeButton.setOnMouseEntered(event->{
//            homeButton. setStyle("-fx-background-color: #F0FFFF; "); 
//        });
//        homeButton.setOnMouseExited(event->{
//            homeButton. setStyle("-fx-background-color: #00CED1; "); 
//        });


    }
          
        public void setLabel(String text){
            login_sign.setText(text);
           System.out.println(text);
        }
    public void setUsername(String username) {
        this.username = username;
        // Update the UI to show logout button or user information
        updateUI();
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
 
            

        
    

