package View;

import Control.VehicleManagement;
import java.util.Scanner;

public class Main {

    private static VehicleManagement vehicle = new VehicleManagement("./vehicle.dat");
    private static Scanner sc = new Scanner(System.in);

    /**
     * Displays a sub-menu for searching vehicles by name or ID and processes
     * the user's choice.
     */
    private static void menuSearchVehicle() {
        int choice;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Search Vehicle By Name                   |");
            System.out.println("| 2. Search Vehicle By ID                     |");
            System.out.println("| 3. Back to Vehicle Management               |");
            System.out.println("+---------------------------------------------+");
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    vehicle.searchVehicleByName();
                    break;
                case 2:
                    vehicle.searchVehicleById();
                    break;
                case 3:
                    Utils.Utils.printMess("Exiting... ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 3);
    }

    /**
     * Displays a sub-menu for displaying vehicles by different criteria and
     * processes the user's choice.
     */
    private static void menuDisplay() {
        int choice = 0;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Display All Vehicles                     |");
            System.out.println("| 2. Display Vehicles By Price                |");
            System.out.println("| 3. Back to Vehicle Management               |");
            System.out.println("+---------------------------------------------+");
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    vehicle.displayVehiclesAll();
                    break;
                case 2:
                    vehicle.displayVehiclesByPrice();
                    break;
                case 3:
                    Utils.Utils.printMess("Exiting... ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 3);
    }

    /**
     * Displays a sub-menu for printing vehicles and processes the user's
     * choice.
     */
    private static void menuPrint() {
        int choice = 0;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Print All Vehicles                       |");
            System.out.println("| 2. Print Vehicles By Year                   |");
            System.out.println("| 3. Back to Vehicle Management               |");
            System.out.println("+---------------------------------------------+");
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    vehicle.displayVehiclesAll();
                    break;
                case 2:
                    vehicle.printByYear();
                    break;
                case 3:
                    Utils.Utils.printMess("Exiting... ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 3);
    }

    public static void main(String[] args) {
        vehicle.loadFrom();
        int choice;

        do {
            displayMainMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    vehicle.addNewVehicle();
                    break;
                case 2:
                    vehicle.checkVehicleExists();
                    break;
                case 3:
                    vehicle.updateVehicle();
                    break;
                case 4:
                    vehicle.deleteVehicle();
                    break;
                case 5:
                    menuSearchVehicle();
                    break;
                case 6:
                    menuDisplay();
                    break;
                case 7:
                    saveDataToFile();
                    break;
                case 8:
                    menuPrint();
                    break;
                case 9:
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    private static void displayMainMenu() {
        System.out.println("+---------------------------------------------+");
        System.out.println("|          Vehicle Management Program         |");
        System.out.println("+---------------------------------------------+");
        System.out.println("| 1. Add New Vehicle                          |");
        System.out.println("| 2. Check Exits Vehicle                      |");
        System.out.println("| 3. Update Vehicle                           |");
        System.out.println("| 4. Delete Vehicle                           |");
        System.out.println("| 5. Search Vehicle                           |");
        System.out.println("| 6. Display Vehicle List                     |");
        System.out.println("| 7. Save Data to File                        |");
        System.out.println("| 8. Print Vehicle List                        |");
        System.out.println("| 9. Exit the Program                         |");
        System.out.println("+---------------------------------------------+");
    }

    /**
     * Helper function to get and validate the user's choice as an integer.
     */
    private static int getUserChoice() {
        int choice = 0;
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

        return choice;
    }

    private static void saveDataToFile() {
        if (vehicle.saveTo()) {
            Utils.Utils.printMess("Save to File finish");
        } else {
            System.out.println("Unable to save");
        }
    }

    private static void exitProgram() {
        Utils.Utils.printMess("Exiting the program... ");
    }
}
