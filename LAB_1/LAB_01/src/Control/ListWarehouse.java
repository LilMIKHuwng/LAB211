package Control;

import Model.Warehouse;
import Model.Product;
import static Control.ListProduct.listProduct;
import Model.ListItems;
import Model.Receipt;
import Utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ListWarehouse {

    public static ArrayList<ListItems> listItemsImport = new ArrayList<>();
    public static ArrayList<ListItems> listItemsExport = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    //hàm tạo biên lai nhập khẩu
    public void importReceipt() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|           Create Import Receipt             |");
        System.out.println("+---------------------------------------------+");

        //tạo ra listImport để chứa các sản phẩm đưa vào biên lai và cập nhật thời gian tạo
        ArrayList<Warehouse> listImport = new ArrayList<>();
        String codeWarehouse;
        boolean check = true;
        do {
            //tạo mới mã biên lai
            boolean checkCode = true;
            do {
                codeWarehouse = Utils.getStringreg("Enter Import Code: ", "\\d{7}",
                        "Code cannot be empty.", "Wrong code format.");
                if (checkCodeWarehouse(codeWarehouse, listItemsImport)) {
                    System.out.println("Same code.");
                } else {
                    checkCode = false;
                }
            } while (checkCode);

            //nhập các sản phẩm vào listImport và trong list phải chứa ít nhất 1 sản phẩm
            boolean checkCreateImport = true;
            do {
                int quantity;
                //kiểm tra mã sản phẩm có tồn tại hay không
                boolean checkProduct = true;
                Date currentDate = new Date();
                String code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                        "Code cannot be empty.", "Wrong code format.");
                for (Product pro : listProduct) {
                    if (code.equalsIgnoreCase(pro.getCode())
                            && pro.getExpiryDate().after(currentDate)) {

                        //tạo Quantity nhập khẩu > 0
                        quantity = Utils.getInt("Enter Quantity Import: ", 0);
                        // add sản phẩm vào biên lai nhập khẩu
                        Warehouse imports = new Warehouse(new Date(),
                                pro.getCode(), pro.getName(), quantity, pro.getType(), (float) pro.getPrice(), pro.getManufacturingDate(),
                                pro.getExpiryDate());
                        listImport.add(imports);

                        System.out.println("+---------------------------------------------+");
                        System.out.println("|       Add Import Receipt Successfully       |");
                        System.out.println("+---------------------------------------------+");

                        checkProduct = false;

                        // tăng số lượng của sản phẩm có trong kho theo biên lai nhập khẩu
                        pro.setQuantity(pro.getQuantity() + quantity);

                    } else if (pro.getExpiryDate().before(currentDate)
                            && code.equalsIgnoreCase(pro.getCode())) {
                        System.out.println("Product has expired so it cannot be added to the import receipt.");
                        checkProduct = false;
                    }
                }

                if (checkProduct) {
                    System.out.println("+---------------------------------------------+");
                    System.out.println("|           Product code not found            |");
                    System.out.println("+---------------------------------------------+");
                }

                System.out.println();
                System.out.print("Continue Add Import Receipt (Y/N): ");
                String c = sc.nextLine();
                if (!c.equalsIgnoreCase("Y")) {
                    checkCreateImport = false;
                }
            } while (checkCreateImport);

            if (listImport.isEmpty()) {
                System.out.println("Import receipt must contain at least 1 product");
                System.out.println();
            } else {
                ListItems items = new ListItems(codeWarehouse, listImport);
                listItemsImport.add(items);
                check = false;
            }
        } while (check);

        System.out.println("+---------------------------------------------+");
        System.out.println("|     Import receipt created successfully     |");
        System.out.println("+---------------------------------------------+");

        //listImport.clear();
        System.out.println();
        System.out.print("Continue Create Import Receipt (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            importReceipt();
        }

    }

    //hàm tạo biên lai xuất khẩu
    public void exportReceipt() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|           Create Export Receipt             |");
        System.out.println("+---------------------------------------------+");

        //tạo ra listExport để chứa các sản phẩm đưa vào biên lai và cập nhật thời gian tạo
        ArrayList<Warehouse> listExport = new ArrayList<>();
        String codeWarehouse;
        boolean check = true;
        do {
            boolean checkCode = true;
            do {
                codeWarehouse = Utils.getStringreg("Enter Export Code: ", "\\d{7}",
                        "Code cannot be empty.", "Wrong code format.");
                if (checkCodeWarehouse(codeWarehouse, listItemsExport)) {
                    System.out.println("Same code.");
                } else {
                    checkCode = false;
                }
            } while (checkCode);

            boolean checkCreateExport = true;
            do {
                int quantity;
                boolean checkProduct = true;
                Date currentDate = new Date();
                String code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                        "Code cannot be empty.", "Wrong code format.");
                for (Product pro : listProduct) {
                    if (code.equalsIgnoreCase(pro.getCode())
                            && pro.getExpiryDate().after(currentDate)
                            && pro.getQuantity() > 0) {

                        //kiểm tra quantity có đủ để thêm biên lai xuất khẩu
                        boolean checkQuantity = true;
                        do {
                            quantity = Utils.getInt("Enter Export Quantity: ", 0);
                            if (quantity > pro.getQuantity()) {
                                System.out.println("The quantity of products in stock is not enough");
                            } else {
                                checkQuantity = false;
                            }
                        } while (checkQuantity);

                        Warehouse emports = new Warehouse(new Date(),
                                pro.getCode(), pro.getName(), quantity, pro.getType(), (float) pro.getPrice(), pro.getManufacturingDate(),
                                pro.getExpiryDate());
                        listExport.add(emports);
                        System.out.println("+---------------------------------------------+");
                        System.out.println("|       Add Export receipt Successfully       |");
                        System.out.println("+---------------------------------------------+");
                        checkProduct = false;

                        // giảm số lương sản phẩm trong kho theo biên lai xuất khẩu
                        pro.setQuantity(pro.getQuantity() - quantity);

                    } else if (pro.getExpiryDate().before(currentDate) && code.equalsIgnoreCase(pro.getCode())) {
                        System.out.println("Product has expired so it cannot be added to the export receipt.");
                        checkProduct = false;
                    } else if (pro.getQuantity() == 0 && code.equalsIgnoreCase(pro.getCode())) {
                        System.out.println("Product is out of stock so it cannot be added to the export receipt.");
                        checkProduct = false;
                    }
                }
                if (checkProduct) {
                    System.out.println("+---------------------------------------------+");
                    System.out.println("|           Product code not found            |");
                    System.out.println("+---------------------------------------------+");
                }

                // duyệt xem user có muốn tt create Export Receipt
                System.out.println();
                System.out.print("Continue Add Export Receipt (Y/N): ");
                String c = sc.nextLine();
                if (!c.equalsIgnoreCase("Y")) {
                    checkCreateExport = false;
                }
            } while (checkCreateExport);

            if (listExport.isEmpty()) {
                System.out.println("Export receipt must contain at least 1 product");
                System.out.println();
            } else {
                ListItems items = new ListItems(codeWarehouse, listExport);
                listItemsExport.add(items);
                check = false;
            }
        } while (check);

        System.out.println("+---------------------------------------------+");
        System.out.println("|     Export receipt created successfully     |");
        System.out.println("+---------------------------------------------+");

        //listExport.clear();
        System.out.println();
        System.out.print("Continue Create Export Receipt (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            exportReceipt();
        }
    }

    //hàm Lọc và In ra biên lại nhập khẩu và xuất khẩu của 1 sản phẩm
    public void printReceipt() {
        if (listItemsImport.isEmpty() && listItemsExport.isEmpty()) {
            System.out.println("+---------------------------------------------+");
            System.out.println("|           No products in Receipt            |");
            System.out.println("+---------------------------------------------+");
        } else {
            System.out.println("+---------------------------------------------+");
            System.out.println("|           Import/Export Receipt             |");
            System.out.println("+---------------------------------------------+");

            //Nhập Product Code kiểm tra trong 2 list Import và Export
            String code = Utils.getStringreg("Enter Product Code: ", "\\d{4}",
                    "Code cannot be empty.", "Wrong code format.");
            //Tạo 2 list để chứa các mã biên lai của sản phẩm cần tìm để in ra màn hình
            ArrayList<Receipt> listimportreceipt = new ArrayList<>();
            ArrayList<Receipt> listexportreceipt = new ArrayList<>();

            //Lọc và thêm các sản phẩm cần in ra vào 2 list đã tạo
            for (ListItems items : listItemsImport) {
                for (Warehouse imports : items.getListItems()) {
                    if (code.equalsIgnoreCase(imports.getCode())) {
                        Receipt receipt = new Receipt(items.getCodeWarehouse(), imports.getCreateTime(),
                                imports.getCode(), imports.getName(), imports.getQuantity(), imports.getType(),
                                (float) imports.getPrice(), imports.getManufacturingDate(), imports.getExpiryDate());
                        listimportreceipt.add(receipt);
                    }
                }
            }
            for (ListItems items : listItemsExport) {
                for (Warehouse exports : items.getListItems()) {
                    if (code.equalsIgnoreCase(exports.getCode())) {
                        Receipt receipt = new Receipt(items.getCodeWarehouse(), exports.getCreateTime(),
                                exports.getCode(), exports.getName(), exports.getQuantity(), exports.getType(),
                                (float) exports.getPrice(), exports.getManufacturingDate(), exports.getExpiryDate());
                        listexportreceipt.add(receipt);
                    }
                }
            }

            if (listimportreceipt.isEmpty() && listexportreceipt.isEmpty()) {
                System.out.println("+---------------------------------------------+");
                System.out.println("|            Product does not exist           |");
                System.out.println("+---------------------------------------------+");
            }

            if (!listimportreceipt.isEmpty()) {
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
                System.out.println("|                                          List Import Receipt of Product                                          |");
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| Import Code | Time receipt | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
                for (Receipt receipt : listimportreceipt) {
                    System.out.println(receipt);
                }
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
            }

            if (!listexportreceipt.isEmpty()) {
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
                System.out.println("|                                          List Export Receipt of Product                                          |");
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| Export Code | Time receipt | Name of Product | Quantity |   Type    |  Price  | Manufacturing Date | Expiry Date |");
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
                for (Receipt receipt : listexportreceipt) {
                    System.out.println(receipt);
                }
                System.out.println("+------------------------------------------------------------------------------------------------------------------+");
            }
        }
    }

    //hàm kiểm tra Code của Import và Export
    public boolean checkCodeWarehouse(String code, ArrayList<ListItems> list) {
        for (ListItems w : list) {
            if (code.equals(w.getCodeWarehouse())) {
                return true;
            }
        }
        return false;
    }

    //hàm lưu vào file của List Import
    public boolean saveToImport(String pathFile) {
        boolean status = false;
        try {
            // 1. Tao ra 1 tap tin (File object)
            File fo = new File(pathFile);
            // 2. Anh xa vao file da hinh thanh (Stream Output)
            FileOutputStream fos = new FileOutputStream(fo);
            // 3. Tao 1 object output stream
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 4. Ghi du lieu
            for (ListItems items : listItemsImport) {
                oos.writeObject(items);
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

    //hàm load từ file của List Import
    public void loadFromImport(String pathFile) {
        // 1. Tao, anh xa, khai bao doi tuong mang du lieu
        File fi = new File(pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 2.
            boolean loopMore = true;
            while (loopMore) {
                ListItems x = (ListItems) ois.readObject();
                if (x != null) {
                    listItemsImport.add(x);
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

    //hàm lưu vào file của List Export
    public boolean saveToExport(String pathFile) {
        boolean status = false;
        try {
            // 1. Tao ra 1 tap tin (File object)
            File fo = new File(pathFile);
            // 2. Anh xa vao file da hinh thanh (Stream Output)
            FileOutputStream fos = new FileOutputStream(fo);
            // 3. Tao 1 object output stream
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 4. Ghi du lieu
            for (ListItems items : listItemsExport) {
                oos.writeObject(items);
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

    //hàm load từ file của List Export
    public void loadFromExport(String pathFile) {
        // 1. Tao, anh xa, khai bao doi tuong mang du lieu
        File fi = new File(pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 2.
            boolean loopMore = true;
            while (loopMore) {
                ListItems x = (ListItems) ois.readObject();
                if (x != null) {
                    listItemsExport.add(x);
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
