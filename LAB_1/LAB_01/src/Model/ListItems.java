
package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ListItems implements Serializable{
    private String codeWarehouse;
    private ArrayList<Warehouse> listItems;

    public ListItems(String codeWarehouse, ArrayList<Warehouse> listItems) {
        this.codeWarehouse = codeWarehouse;
        this.listItems = listItems;
    }

    public String getCodeWarehouse() {
        return codeWarehouse;
    }

    public void setCodeWarehouse(String codeWarehouse) {
        this.codeWarehouse = codeWarehouse;
    }

    public ArrayList<Warehouse> getListItems() {
        return listItems;
    }

    public void setListItems(ArrayList<Warehouse> listItems) {
        this.listItems = listItems;
    }
    
}
