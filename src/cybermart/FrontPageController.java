package cybermart;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class FrontPageController implements Initializable {

    @FXML
    private GridPane recentLayout;


    private List<cardObject> cars;
    private MyListener myListener;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private void setChosenCard(cardObject cardC) {
        String queryRow = "Update row_table SET r_id='" + cardC.getId() + "',r_name='" + cardC.getMark() + "' WHERE r_pk ='1';";
        DatabaseConnection connectNow = new DatabaseConnection();
        java.sql.Connection connectDB = connectNow.getConnection();

        try {
            Statement statementR = connectDB.createStatement();
            statementR.executeUpdate(queryRow);
        } catch (SQLException e) {
            e.getCause();
        }

        try {
            root = FXMLLoader.load(getClass().getResource("ProductDetails.fxml"));
            stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Product details page");
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cars = new ArrayList<>(getAllCars());
        if (cars.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(cardObject cardC) {
                    setChosenCard(cardC);
                }
            };
        }

        try {
            int columnCount = 3; // Number of cards per row
            for (int i = 0; i < cars.size(); i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("productCard.fxml"));
                HBox cardBox = fxmlloader.load();
                cardController cardC = fxmlloader.getController();
                cardObject a = cars.get(i);
                cardC.setData(a, myListener);

                // Calculate row and column
                int row = i / columnCount;
                int col = i % columnCount;

                // Add the card to the GridPane
                recentLayout.add(cardBox, col, row);
                System.out.println("Added cardBox for: " + a.getMark() + " " + a.getModel());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<cardObject> getAllCars() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        List<cardObject> rAdd = new ArrayList<>();

        String query1 = "SELECT id, mark, model, price, description, pictures, abs, epc, gray_card, auto_gear_box, taxes, insurance, color, mileage, quantity, add_date FROM car";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryresult = statement.executeQuery(query1);

            System.out.println("getAllCars() method called");

            if (!queryresult.isBeforeFirst()) {
                System.out.println("No data found in the car table");
            } else {
                while (queryresult.next()) {
                    Integer queryId = queryresult.getInt("id");
                    String queryMark = queryresult.getString("mark");
                    String queryModel = queryresult.getString("model");
                    Integer queryPrice = queryresult.getInt("price");
                    String queryDescription = queryresult.getString("description");
                    String queryPictures = queryresult.getString("pictures");
                    Boolean queryAbs = queryresult.getBoolean("abs");
                    Boolean queryEpc = queryresult.getBoolean("epc");
                    Boolean queryGrayCard = queryresult.getBoolean("gray_card");
                    Boolean queryAutoGearBox = queryresult.getBoolean("auto_gear_box");
                    Boolean queryTaxes = queryresult.getBoolean("taxes");
                    Boolean queryInsurance = queryresult.getBoolean("insurance");
                    String queryColor = queryresult.getString("color");
                    Integer queryMileage = queryresult.getInt("mileage");
                    Integer queryQuantity = queryresult.getInt("quantity");

                    cardObject cObj = new cardObject(queryId, queryMark, queryModel, queryPrice, queryDescription, queryPictures, queryAbs, queryEpc, queryGrayCard, queryAutoGearBox, queryTaxes, queryInsurance, queryColor, queryMileage, queryQuantity);
                    rAdd.add(cObj);

                    System.out.println("Added cardObject: " + cObj.getMark() + " " + cObj.getModel());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("getAllCars() method returning " + rAdd.size() + " cardObjects");

        return rAdd;
    }
}
