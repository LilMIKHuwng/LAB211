package Model;

import java.io.Serializable;
import java.time.Year;

public class Vehicle implements Serializable {

    private String idVehicle;
    private String nameVehicle;
    private String colorVehicle;
    private double priceVehicle;
    private String brandVehicle;
    private String type;
    private Year productYear;

    public Vehicle(String idVehicle, String nameVehicle, String colorVehicle, double priceVehicle, String brandVehicle, String type, Year productYear) {
        this.idVehicle = idVehicle;
        this.nameVehicle = nameVehicle;
        this.colorVehicle = colorVehicle;
        this.priceVehicle = priceVehicle;
        this.brandVehicle = brandVehicle;
        this.type = type;
        this.productYear = productYear;
    }

    public String getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getNameVehicle() {
        return nameVehicle;
    }

    public void setNameVehicle(String nameVehicle) {
        this.nameVehicle = nameVehicle;
    }

    public String getColorVehicle() {
        return colorVehicle;
    }

    public void setColorVehicle(String colorVehicle) {
        this.colorVehicle = colorVehicle;
    }

    public double getPriceVehicle() {
        return priceVehicle;
    }

    public void setPriceVehicle(double priceVehicle) {
        this.priceVehicle = priceVehicle;
    }

    public String getBrandVehicle() {
        return brandVehicle;
    }

    public void setBrandVehicle(String brandVehicle) {
        this.brandVehicle = brandVehicle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Year getProductYear() {
        return productYear;
    }

    public void setProductYear(Year productYear) {
        this.productYear = productYear;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-15s | %-13s | %-13.0f | %-13s | %-10s | %-12s |", idVehicle, nameVehicle, colorVehicle,
                priceVehicle, brandVehicle, type, productYear.toString());
    }

}
