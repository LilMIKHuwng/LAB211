package Control;

import static Control.ListWarehouse.listItemsExport;
import static Control.ListWarehouse.listItemsImport;
import Model.ListItems;
import Model.Warehouse;
import Model.Product;
import Utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class ListProduct {

    public static ArrayList<Product> listProduct = new ArrayList<>();
    private String pathFile;

    public ListProduct(String pathFile) {
        this.pathFile = pathFile;
    }

    Scanner sc = new Scanner(System.in);
    
    //hàm add Daily Product
    public void addDailyProduct() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|              Add DaiLy Product              |");
        System.out.println("+---------------------------------------------+");
        String code, name, type;
        int quantity;
        float price;
        Date manufacturingDate, expiryDate;

        //kiểm tra xem add sản phẩm thành công 
        //tạo Product Code mới, nếu tồn tại thì nhập lại
        boolean check = true;
        do {
            code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                    "Code cannot be empty.", "Wrong code format.");
            if (checkCode(code)) {
                System.out.println("Same code.");
            } else {
                check = false;
            }
        } while (check);
        //tạo Product name
        name = Utils.getString("Enter Name: ", "Name cannot be empty.");
        //tạo Quantity = 0
        quantity = 0;
        //tạo type là Daily
        type = "Daily";
        //tạo Price >=1000
        price = Utils.getloat("Enter Price: ", 1000);
        //tạo Ngày sản xuất có cấu trúc dd/mm/yyyy
        manufacturingDate = Utils.getDate("Enter Manufacturing Date (dd/MM/yyyy): ");
        //vì là sản phẩm daily nên ngày hết hạn bằng ngày sản xuất
        expiryDate = manufacturingDate;

        // tạo và add mới sản phẩm vào List Product
        Product pro = new Product(code, name, quantity, type, price, manufacturingDate, expiryDate);
        listProduct.add(pro);

        System.out.println("+---------------------------------------------+");
        System.out.println("|           Add Product Successfully          |");
        System.out.println("+---------------------------------------------+");

        // duyệt xem user có muốn tt add
        System.out.println();
        System.out.print("Continue Add Daily Product (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            addDailyProduct();
        }
    }

    // hàm add mới sản phẩm 
    public void addLongProduct() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|                 Add Product                 |");
        System.out.println("+---------------------------------------------+");
        String code, name, type;
        int quantity;
        float price;
        Date manufacturingDate, expiryDate;

        //kiểm tra xem add sản phẩm thành công 
        //tạo Product Code mới, nếu tồn tại thì nhập lại
        boolean check = true;
        do {
            code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                    "Code cannot be empty.", "Wrong code format.");
            if (checkCode(code)) {
                System.out.println("Same code.");
            } else {
                check = false;
            }
        } while (check);
        //tạo Product name
        name = Utils.getString("Enter Name: ", "Name cannot be empty.");
        //tạo Quantity =0
        quantity = 0;
        //tạo type là long term
        type = "Long-term";
        //tạo Price >=1000
        price = Utils.getloat("Enter Price: ", 1000);
        //tạo Ngày sản xuất có cấu trúc dd/mm/yyyy
        manufacturingDate = Utils.getDate("Enter Manufacturing Date (dd/MM/yyyy): ");
        //tạo ngày hết hạn, nếu ngày hết hạn trước ngày sản xuất thì nhập lại có cấu trúc dd/mm/yyyy 
        check = true;
        do {
            expiryDate = Utils.getDate("Enter Expiry Date (dd/MM/yyyy): ");
            if (expiryDate.before(manufacturingDate)) {
                System.out.println("Expiry Date must be greater than Manufacturing Date");
            } else {
                check = false;
            }
        } while (check);

        // tạo và add mới sản phẩm vào List Product
        Product pro = new Product(code, name, quantity, type, price, manufacturingDate, expiryDate);
        listProduct.add(pro);

        System.out.println("+---------------------------------------------+");
        System.out.println("|           Add Product Successfully          |");
        System.out.println("+---------------------------------------------+");

        // duyệt xem user có muốn tt add
        System.out.println();
        System.out.print("Continue Add Long-term Product (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            addLongProduct();
        }
    }

    // hàm update 1 sản phẩm
    public void update() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|               Update Product                |");
        System.out.println("+---------------------------------------------+");

        //nhập Product Code để kiểm tra trong List Product
        String code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                "Code cannot be empty.", "Wrong code format.");
        
        //tạo các biến tượng trưng cho giá trị cũ và mới cho update
        String name, quantity, price;
        int newquantity;
        float newprice = 0;
        String manufacturingDate = "", expiryDate = "";
        Date newmanufacturingDate = new Date();
        Date newexpiryDate = new Date();
        Date currentDate = new Date();

        //duyệt kiểm tra update
        boolean check = true;
        for (Product pro : listProduct) {
            //Nếu Product Code có tồn tại trong List Product thì thực hiện Update 
            if (code.equalsIgnoreCase(pro.getCode())) {

                //Nhập New Name, nếu user press Enter thì skip qua và không đổi Name
                name = Utils.getUpdateString("Enter Name (press Enter to skip): ");
                if (!name.isEmpty()) {
                    pro.setName(name);
                }

                //Nhập New Quantity, nếu user press Enter thì skip qua và không đổi Quantity,
                //nếu nhập mới thì phải đúng là Number và lớn hơn hoặc bằng 0
                boolean checkQuantity = true;
                do {
                    System.out.print("Enter Quantity (press Enter to skip): ");
                    quantity = sc.nextLine();
                    if (quantity.isEmpty()) {
                        checkQuantity = false;
                    } else {
                        if (Utils.getUpdateInt(quantity, 0)) {
                            newquantity = Integer.parseInt(quantity);
                            pro.setQuantity(newquantity);
                            checkQuantity = false;
                        }
                    }
                } while (checkQuantity);

                //Nhập New Price, nếu user press Enter thì skip qua và không đổi Price,
                //nếu nhập mới thì phải đúng là Number và lớn hơn hoặc bằng 1000
                boolean checkPrice = true;
                do {
                    System.out.print("Enter Price (press Enter to skip): ");
                    price = sc.nextLine();
                    if (price.isEmpty()) {
                        checkPrice = false;
                    } else {
                        if (Utils.getUpdateFloat(price, 1000)) {
                            newprice = Float.parseFloat(price);
                            pro.setPrice(newprice);
                            checkPrice = false;
                        }
                    }
                } while (checkPrice);

                System.out.println();
                
                //thông báo cho người dùng biết đây là loại sản phẩm gì để cập nhật ngày cho hợp lí 
                if (pro.getType().equals("Daily")) {
                    System.out.println("This product is a daily use product.");
                    
                    //Nhập New Ngày sản xuất, nếu user press Enter thì skip qua và không đổi ngày sản xuất,
                    //nếu nhập mới thì phải đúng là dd/mm/yyyy và New ngày sản xuất phải sau ngày sản xuất cũ
                    boolean checkManufacturingDate = true;
                    do {
                        System.out.print("Enter Manufacturing Date (dd/MM/yyyy)(press Enter to skip): ");
                        manufacturingDate = sc.nextLine();
                        if (manufacturingDate.isEmpty()) {
                            checkManufacturingDate = false;
                        } else {
                            if (Utils.getUpdateDate(manufacturingDate)) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                dateFormat.setLenient(false);
                                try {
                                    newmanufacturingDate = dateFormat.parse(manufacturingDate);
                                    if (pro.getManufacturingDate().before(newmanufacturingDate)) {
                                        pro.setManufacturingDate(newmanufacturingDate);
                                        pro.setExpiryDate(newmanufacturingDate);
                                        checkManufacturingDate = false;
                                    } else {
                                        System.out.println("New manufacturing date must be after the old manufacturing date");
                                    }
                                } catch (ParseException ex) {
                                }
                            }
                        }
                    } while (checkManufacturingDate);
                
                
                } else if (pro.getType().equals("Long-term")) {
                    System.out.println("This product is a long-term product.");
                    
                    //Nhập New Ngày sản xuất, nếu user press Enter thì skip qua và không đổi ngày sản xuất,
                    //nếu nhập mới thì phải đúng là dd/mm/yyyy 
                    boolean checkManufacturingDate = true;
                    do {
                        System.out.print("Enter Manufacturing Date (dd/MM/yyyy)(press Enter to skip): ");
                        manufacturingDate = sc.nextLine();
                        if (manufacturingDate.isEmpty()) {
                            checkManufacturingDate = false;
                        } else {
                            if (Utils.getUpdateDate(manufacturingDate)) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                dateFormat.setLenient(false);
                                try {
                                    newmanufacturingDate = dateFormat.parse(manufacturingDate);
                                    pro.setManufacturingDate(newmanufacturingDate);
                                    checkManufacturingDate = false;
                                } catch (ParseException ex) {
                                }
                            }
                        }
                    } while (checkManufacturingDate);

                    //Nhập New Ngày hết hạn, nếu user press Enter thì skip qua và không đổi ngày hết hạn,
                    //nếu nhập mới thì phải đúng là dd/mm/yyyy và (sau ngày sản xuất và sau ngày hiện tại)
                    boolean checkExpiryDate = true;
                    do {
                        System.out.print("Enter Expiry Date (dd/MM/yyyy)(press Enter to skip): ");
                        expiryDate = sc.nextLine();
                        if (expiryDate.isEmpty()) {
                            checkExpiryDate = false;
                        } else {
                            if (Utils.getUpdateDate(expiryDate)) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                dateFormat.setLenient(false);
                                try {
                                    newexpiryDate = dateFormat.parse(expiryDate);
                                    if (newexpiryDate.before(currentDate)) {
                                        System.out.println("Expiry Date must be after the current date now.");
                                    } else if (newexpiryDate.before(pro.getManufacturingDate())) {
                                        System.out.println("Expiry Date must be greater than Manufacturing Date");
                                    } else {
                                        pro.setExpiryDate(newexpiryDate);
                                        checkExpiryDate = false;
                                    }
                                } catch (ParseException ex) {
                                }
                            }
                        }
                    } while (checkExpiryDate);
                }

                //Nếu user skip qua tất cả thì thông báo không có j update, còn không thì in ra sản phẩm vừa được update kèm thông báo
                if (name.isEmpty() && quantity.isEmpty() && price.isEmpty() && manufacturingDate.isEmpty() && expiryDate.isEmpty()) {
                    System.out.println("+---------------------------------------------+");
                    System.out.println("|   Product has no changes after the update   |");
                    System.out.println("+---------------------------------------------+");
                } else {
                    System.out.println("+---------------------------------------------+");
                    System.out.println("|    Product has been successfully updated    |");
                    System.out.println("+---------------------------------------------+");
                    System.out.println("+----------------------------------------------------------------------------------------------------+");
                    System.out.println("| Product Code | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
                    System.out.println("+----------------------------------------------------------------------------------------------------+");
                    System.out.println(pro);
                    System.out.println("+----------------------------------------------------------------------------------------------------+");
                }

                //check trả về false nếu sản phẩm tổn tại
                check = false;

                //Thay đổi import Receipt theo Update (Bao gồm Name, Price)
                for (ListItems items : listItemsImport) {
                    for (Warehouse imports : items.getListItems()) {
                        if (code.equals(imports.getCode())) {
                            if (!name.isEmpty()) {
                                imports.setName(name);
                            }
                            if (!price.isEmpty()) {
                                imports.setPrice(newprice);
                            }
                        }
                    }
                }

                //Thay đổi emport Receipt theo Update (Bao gồm Name, Price)
                for (ListItems items : listItemsExport) {
                    for (Warehouse exports : items.getListItems()) {
                        if (code.equals(exports.getCode())) {
                            if (!name.isEmpty()) {
                                exports.setName(name);
                            }
                            if (!price.isEmpty()) {
                                exports.setPrice(newprice);
                            }
                        }
                    }
                }
            }
        }

        //Nếu sản phẩm không tồn tại, in ra thông báo
        if (check) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|           Product does not exist            |");
            System.out.println("+---------------------------------------------+");
        }

        // duyệt xem user có muốn tt update
        System.out.println();
        System.out.print("Continue Update Product (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            update();
        }

    }

    //hàm xóa 1 sản phảm
    public void delete() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|               Delete Product                |");
        System.out.println("+---------------------------------------------+");

        //nhập Product Code để kiểm tra trong List Product
        String code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                "Code cannot be empty.", "Wrong code format.");

        //Nếu sản phẩm có tồn tại trong List Import thì không thể xóa sản phẩm, quay lại menu Product
        for (ListItems items : listItemsImport) {
            for (Warehouse imports : items.getListItems()) {
                if (code.equals(imports.getCode())) {
                    System.out.println("This product cannot be deleted because it is already entered in the import receipt");
                    return;
                }
            }
        }

        //Xóa sản phẩm 
        boolean check = true;
        for (int i = 0; i < listProduct.size(); i++) {
            if (code.equalsIgnoreCase(listProduct.get(i).getCode())) {
                listProduct.remove(i);
                System.out.println("+---------------------------------------------+");
                System.out.println("|             Delete Successfully             |");
                System.out.println("+---------------------------------------------+");
                check = false;
            }
        }
        if (check) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|            Product does not exist           |");
            System.out.println("+---------------------------------------------+");
        }

        // duyệt xem user có muốn tt xóa
        System.out.println();
        System.out.print("Continue Delete Product (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            delete();
        }
    }

    //hàm in tất cả các sản phẩm
    public void printList() {

        if (listProduct.isEmpty()) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|              No Product in List             |");
            System.out.println("+---------------------------------------------+");
        } else {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("|                                      List of Products                                              |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("| Product Code | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            for (Product product : listProduct) {
                System.out.println(product);
            }
            System.out.println("+----------------------------------------------------------------------------------------------------+");
        }
    }

    //hàm in ra các sản phẩm quá hạn sử dụng
    public void reportExpiredProducts() {
        //Tạo 1 list hết hạn để chứa các sản phẩm hết hạn
        ArrayList<Product> listExpired = new ArrayList<>();
        Date currentDate = new Date();

        //Lọc và In danh sách sản phẩm đã hết hạn
        for (Product product : listProduct) {
            if (product.getExpiryDate().before(currentDate)) {
                listExpired.add(product);
            }
        }
        if (listExpired.isEmpty()) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|              No Product Expired             |");
            System.out.println("+---------------------------------------------+");
        } else {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("|                                      List Products Expired                                         |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("| Product Code | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            for (Product product : listExpired) {
                System.out.println(product);
            }
            System.out.println("+----------------------------------------------------------------------------------------------------+");
        }
    }

    //hàm in ra các sản phẩm đang bán
    public void reportSellingProducts() {
        //Tạo 1 list đang bán để chứa các sản phẩm đang được bán
        ArrayList<Product> listSelling = new ArrayList<>();
        Date currentDate = new Date();

        //Lọc và In danh sách sản phẩm đang còn trong kho
        for (Product product : listProduct) {
            if (product.getExpiryDate().after(currentDate) && product.getQuantity() > 0) {
                listSelling.add(product);
            }
        }
        if (listSelling.isEmpty()) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|              No Product Selling             |");
            System.out.println("+---------------------------------------------+");
        } else {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("|                                     List Products Selling                                          |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("| Product Code | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            for (Product product : listSelling) {
                System.out.println(product);
            }
            System.out.println("+----------------------------------------------------------------------------------------------------+");
        }

    }

    //hàm in ra các sản phẩm sắp hết hàng
    public void reportLowStockProducts() {
        //Tạo 1 list hết hàng chứa các sản phẩm sắp hết hang (bé hơn 3 sản phẩm)
        ArrayList<Product> listOutOfStock = new ArrayList<>();

        //Lọc và In danh sách sản phẩm gần hết hàng theo thứ tăng dần của số lượng sản phẩm
        for (Product product : listProduct) {
            if (product.getQuantity() <= 3) {
                listOutOfStock.add(product);
            }
        }

        if (listOutOfStock.isEmpty()) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|       No Product Running Out Of Stock       |");
            System.out.println("+---------------------------------------------+");
        } else {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("|                                 List Products Running Out Of Stock                                 |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("| Product Code | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            Collections.sort(listOutOfStock);
            for (Product product : listOutOfStock) {
                System.out.println(product);
            }
            System.out.println("+----------------------------------------------------------------------------------------------------+");
        }
    }

    //hàm kiểm tra Code không trùng
    public boolean checkCode(String code) {
        for (Product pro : listProduct) {
            if (code.equals(pro.getCode())) {
                return true;
            }
        }
        return false;
    }

    // hàm lưu file
    public boolean saveTo() {
        boolean status = false;
        try {
            // 1. Tao ra 1 tap tin (File object)
            File fo = new File(this.pathFile);
            // 2. Anh xa vao file da hinh thanh (Stream Output)
            FileOutputStream fos = new FileOutputStream(fo);
            // 3. Tao 1 object output stream
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 4. Ghi du lieu
            for (Product pro : listProduct) {
                oos.writeObject(pro);
            }
            // 5. Dong (
            oos.close();
            fos.close();
            status = true;
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found!!!");
        } catch (IOException ex) {
            System.out.println("Writing file failed!!!");
        }

        return status;
    }

    //hàm load file
    public void loadFrom() {
        // 1. Tao, anh xa, khai bao doi tuong mang du lieu
        File fi = new File(this.pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 2.
            boolean loopMore = true;
            while (loopMore) {
                Product x = (Product) ois.readObject();
                if (x != null) {
                    listProduct.add(x);
                } else {
                    loopMore = false;
                }
            }
            ois.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found!!!");
        } catch (IOException | ClassNotFoundException ex) {

        }

    }

}
