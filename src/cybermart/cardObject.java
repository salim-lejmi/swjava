package cybermart;

public class cardObject {
    private int id;
    private String mark;
    private String model;
    private int price;
    private String description;
    private String pictures;
    private boolean abs;
    private boolean epc;
    private boolean gray_card;
    private boolean auto_gear_box;
    private boolean taxes;
    private boolean insurance;
    private String color;
    private int mileage;
    private int quantity;

    // Constructor
    public cardObject(int id, String mark, String model, int price, String description, String pictures, boolean abs, boolean epc, boolean gray_card, boolean auto_gear_box, boolean taxes, boolean insurance, String color, int mileage, int quantity) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.price = price;
        this.description = description;
        this.pictures = pictures;
        this.abs = abs;
        this.epc = epc;
        this.gray_card = gray_card;
        this.auto_gear_box = auto_gear_box;
        this.taxes = taxes;
        this.insurance = insurance;
        this.color = color;
        this.mileage = mileage;
        this.quantity = quantity;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPictures() {
        return pictures;
    }

    public boolean isAbs() {
        return abs;
    }

    public boolean isEpc() {
        return epc;
    }

    public boolean isGrayCard() {
        return gray_card;
    }

    public boolean isAutoGearBox() {
        return auto_gear_box;
    }

    public boolean isTaxes() {
        return taxes;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public String getColor() {
        return color;
    }

    public int getMileage() {
        return mileage;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
    }

    public void setEpc(boolean epc) {
        this.epc = epc;
    }

    public void setGrayCard(boolean gray_card) {
        this.gray_card = gray_card;
    }

    public void setAutoGearBox(boolean auto_gear_box) {
        this.auto_gear_box = auto_gear_box;
    }

    public void setTaxes(boolean taxes) {
        this.taxes = taxes;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



}
