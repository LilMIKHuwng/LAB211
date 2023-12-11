package View;

import Control.ListProduct;
import Control.ListWarehouse;
import java.util.Scanner;

public class Main {

    //tạo các đối tượng từ 2 class ListProduct và ListWarehouse để gọi các hàm
    private static ListProduct product = new ListProduct("./product.dat");
    private static ListWarehouse warehouse = new ListWarehouse();
    private static Scanner sc = new Scanner(System.in);

    private static void menuAddProduct() {

        int choice = 0;
        do {
            //hiển thị menu nhỏ cho user
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Add Daily Product                        |");
            System.out.println("| 2. Add Long-term Product                    |");
            System.out.println("| 3. Back to Product Management               |");
            System.out.println("+---------------------------------------------+");
            boolean check = true;
            do {
                System.out.print("Enter your choice: ");
                try {

                    choice = Integer.parseInt(sc.nextLine());
                    check = false;
                } catch (Exception e) {
                    System.out.println("Please! Input Number.");
                }
            } while (check);
            switch (choice) {
                case 1:
                    // Tạo biên lai nhập hàng
                    // Gọi phương thức importReceipt() của ListWarehouse
                    product.addDailyProduct();
                    break;
                case 2:
                    // Tạo biên lai xuất hàng
                    // Gọi phương thức exportReceipt() của ListWarehouse
                    product.addLongProduct();
                    break;
                case 3:
                    System.out.println("+---------------------------------------------+");
                    System.out.println("| Exiting...                                  |");
                    System.out.println("+---------------------------------------------+");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 3);
    }
    
    //tạo menu nhỏ để sử dụng các nhu cầu của Product
    private static void menuProduct() {

        int choice = 0;
        do {
            //hiển thị menu nhỏ cho user
            System.out.println("+---------------------------------------------+");
            System.out.println("|          Product Management Program         |");
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Add a new Product                        |");
            System.out.println("| 2. Update the product                       |");
            System.out.println("| 3. Delete the product                       |");
            System.out.println("| 4. Print a list of Products                 |");
            System.out.println("| 5. Exit Product Program                     |");
            System.out.println("+---------------------------------------------+");
            boolean check = true;
            do {
                System.out.print("Enter your choice: ");
                try {

                    choice = Integer.parseInt(sc.nextLine());
                    check = false;
                } catch (Exception e) {
                    System.out.println("Please! Input Number.");
                }
            } while (check);
            switch (choice) {
                case 1:
                    // Thêm sản phẩm
                    // Gọi phương thức addProduct() của ListProduct
                    menuAddProduct();
                    break;
                case 2:
                    // Cập nhật thông tin sản phẩm
                    // Gọi phương thức update() của ListProduct
                    product.update();
                    break;
                case 3:
                    // Xóa sản phẩm
                    // Gọi phương thức delete() của ListProduct
                    product.delete();
                    break;
                case 4:
                    // Hiển thị tất cả sản phẩm
                    // Gọi phương thức printList() của ListProduct
                    product.printList();
                    break;
                case 5:
                    System.out.println("+---------------------------------------------+");
                    System.out.println("| Exiting Product Program...                  |");
                    System.out.println("+---------------------------------------------+");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 5);
    }

    //tạo menu nhỏ để tạo các biên lai nhập khẩu và xuất khẩu
    private static void menuWarehouse() {

        int choice = 0;
        do {
            //hiển thị menu nhỏ cho user
            System.out.println("+---------------------------------------------+");
            System.out.println("|        Warehouse Management Program         |");
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Create an import receipt                 |");
            System.out.println("| 2. Create an export receipt                 |");
            System.out.println("| 3. Exit Warehouse Program                   |");
            System.out.println("+---------------------------------------------+");
            boolean check = true;
            do {
                System.out.print("Enter your choice: ");
                try {

                    choice = Integer.parseInt(sc.nextLine());
                    check = false;
                } catch (Exception e) {
                    System.out.println("Please! Input Number.");
                }
            } while (check);
            switch (choice) {
                case 1:
                    // Tạo biên lai nhập hàng
                    // Gọi phương thức importReceipt() của ListWarehouse
                    warehouse.importReceipt();
                    break;
                case 2:
                    // Tạo biên lai xuất hàng
                    // Gọi phương thức exportReceipt() của ListWarehouse
                    warehouse.exportReceipt();
                    break;
                case 3:
                    System.out.println("+---------------------------------------------+");
                    System.out.println("| Exiting Warehouse Program...                |");
                    System.out.println("+---------------------------------------------+");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 3);
    }

    //tạo menu nhỏ để gọi các hàm Report in ra màn hình các sản phẩm và biên lai
    private static void menuReport() {
        int choice = 0;
        do {
            //hiển thị menu nhỏ cho user
            System.out.println("+---------------------------------------------+");
            System.out.println("|                    Report                   |");
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Products that have expired               |");
            System.out.println("| 2. Products that the store is selling       |");
            System.out.println("| 3. Products that are running out of stock   |");
            System.out.println("| 4. Import/Export receipt of a product       |");
            System.out.println("| 5. Exit Report                              |");
            System.out.println("+---------------------------------------------+");
            boolean check = true;
            do {
                System.out.print("Enter your choice: ");
                try {

                    choice = Integer.parseInt(sc.nextLine());
                    check = false;
                } catch (Exception e) {
                    System.out.println("Please! Input Number.");
                }
            } while (check);
            switch (choice) {
                case 1:
                    // Báo cáo sản phẩm hết hạn
                    // Gọi phương thức reportExpiredProducts() từ ListProduct để tìm và hiển thị sản phẩm 
                    product.reportExpiredProducts();
                    break;
                case 2:
                    // Báo cáo sản phẩm còn trong kho và đang được bán
                    // Gọi phương thức từ reportSellingProducts() từ ListProduct để tìm và hiển thị sản phẩm 
                    product.reportSellingProducts();
                    break;
                case 3:
                    // Báo cáo sản phẩm sắp hết hàng
                    // Gọi phương thức reportLowStockProducts() từ ListProduct để tìm và hiển thị sản phẩm
                    product.reportLowStockProducts();
                    break;
                case 4:
                    // Hiển thị biên lai nhập xuất của sản phẩm
                    // Gọi phương thức printReceipt() từ Listwarehouse để tìm và hiển thị biên lai
                    warehouse.printReceipt();
                    break;
                case 5:
                    System.out.println("+---------------------------------------------+");
                    System.out.println("| Exiting Report...                           |");
                    System.out.println("+---------------------------------------------+");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 5);
    }

    public static void main(String[] args) {

        // load dữ liệu từ các File 
        product.loadFrom();
        warehouse.loadFromImport("./warehouseimport.dat");
        warehouse.loadFromExport("./warehouseexport.dat");
        int choice = 0;
        do {
            //hiển thị menu chính cho user
            System.out.println("+---------------------------------------------+");
            System.out.println("|           Store Management Program          |");
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Product Management                       |");
            System.out.println("| 2. Warehouse Management                     |");
            System.out.println("| 3. Report                                   |");
            System.out.println("| 4. Save to File                             |");
            System.out.println("| 5. Exit the Program                         |");
            System.out.println("+---------------------------------------------+");
            boolean check = true;
            do {
                System.out.print("Enter your choice: ");
                try {

                    choice = Integer.parseInt(sc.nextLine());
                    check = false;
                } catch (Exception e) {
                    System.out.println("Please! Input Number.");
                }
            } while (check);
            switch (choice) {
                case 1:
                    menuProduct();
                    break;
                case 2:
                    menuWarehouse();
                    break;
                case 3:
                    menuReport();
                    break;
                case 4:
                    //save các dữ liệu mới vào File
                    if (product.saveTo() && warehouse.saveToImport("./warehouseimport.dat") && warehouse.saveToExport("./warehouseexport.dat")) {
                        System.out.println("+---------------------------------------------+");
                        System.out.println("|            Save to File finish              |");
                        System.out.println("+---------------------------------------------+");
                    } else {
                        System.out.println("Unable to save");
                    }
                    break;
                case 5:
                    System.out.println("+---------------------------------------------+");
                    System.out.println("|            Exiting the program...           |");
                    System.out.println("+---------------------------------------------+");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 5);
    }
}
