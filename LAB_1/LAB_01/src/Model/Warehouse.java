package Model;

import java.util.Date;

public class Warehouse extends Product{

    protected Date createTime;

    public Warehouse(Date createTime, String code, String name, int quantity, String type, float price, Date manufacturingDate, Date expiryDate) {
        super(code, name, quantity, type, price, manufacturingDate, expiryDate);
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
