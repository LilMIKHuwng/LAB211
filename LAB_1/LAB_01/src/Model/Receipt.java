
package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt extends Warehouse{
    private String codeReceipt;

    public Receipt(String codeReceipt, Date createTime, String code, String name, int quantity, String type, float price, Date manufacturingDate, Date expiryDate) {
        super(createTime, code, name, quantity, type, price, manufacturingDate, expiryDate);
        this.codeReceipt = codeReceipt;
    }

    public String getCodeReceipt() {
        return codeReceipt;
    }

    public void setCodeReceipt(String codeReceipt) {
        this.codeReceipt = codeReceipt;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("| %-11s | %-12s | %-15s | %-8d | %-9s | %-7.0f | %-18s | %-11s |", codeReceipt, dateFormat.format(createTime), 
                getName(), getQuantity(), getType(), price, dateFormat.format(getManufacturingDate()), dateFormat.format(getExpiryDate()));
    }
}
