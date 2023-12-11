package Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product implements Serializable, Comparable<Product>{

    protected String code;
    protected String name;
    protected int quantity;
    protected String type;
    protected float price;
    protected Date manufacturingDate;
    protected Date expiryDate;

    public Product(String code, String name, int quantity, String type, float price, Date manufacturingDate, Date expiryDate) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    
    
    @Override
    public String toString() {
        //chỉ các Date về đúng format để in ra
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("| %-12s | %-15s | %-8d | %-9s | %-7.0f | %-18s | %-11s |", code, name, quantity, type, price, 
                dateFormat.format(manufacturingDate), dateFormat.format(expiryDate));
    }

    @Override
    public int compareTo(Product t) {
        return this.quantity > t.quantity ? 1 : this.quantity == t.quantity ? 0 : -1;
    }

}
