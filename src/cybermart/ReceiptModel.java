package cybermart;

import java.time.LocalDateTime;

public class ReceiptModel {
    private LocalDateTime purchaseDate;
    private double price;
    private String carModel;
    private String carMark;
    private String pictures; // New field to store the car picture path

    public ReceiptModel(LocalDateTime purchaseDate, double price, String carModel, String carMark) {
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.carModel = carModel;
        this.carMark = carMark;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarMark() {
        return carMark;
    }
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }
    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

}