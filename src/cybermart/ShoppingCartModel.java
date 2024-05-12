/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cybermart;

/**
 *
 * @author majid
 */
public class ShoppingCartModel {
    private int id;
    private double price;
    private int userId;
    private int carId;
    private boolean purchased;
    private String carModel;
    private String carMark;
    public ShoppingCartModel(int id, double price, int userId, int carId, boolean purchased) {
        this.id = id;
        this.price = price;
        this.userId = userId;
        this.carId = carId;
        this.purchased = purchased;
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getUserId() {
        return userId;
    }

    public int getCarId() {
        return carId;
    }

    public boolean isPurchased() {
        return purchased;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
    public String getCarModel() {
        return carModel;
    }

    public String getCarMark() {
        return carMark;
    }
}