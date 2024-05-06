/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package cybermart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



/**
 *
 * @author majid
 */
public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDBMS = connectNow.getConnection();

        String queryUser = "UPDATE c_user SET customer_ID='0', customer_name='' WHERE c_ID='1';";
        String queryVen = "UPDATE current_vendor SET id_vendor='0', vendor_name='' WHERE v_ID='1';";

        try {
            Statement statementR = connectDBMS.createStatement();
            Statement statementR1 = connectDBMS.createStatement();
            statementR.executeUpdate(queryUser);
            statementR1.executeUpdate(queryVen);
        } catch (SQLException e) {
            e.getCause();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("SwiftWheels");
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
