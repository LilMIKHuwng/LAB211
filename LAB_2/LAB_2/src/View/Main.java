package View;

import Control.FlightManagementSystem;
import java.util.Scanner;

public class Main {

    private static FlightManagementSystem flight = new FlightManagementSystem();
    private static Scanner sc = new Scanner(System.in);
    private static String role = "";
    
    /**
     * tạo menu để chọn là User hay Admin, nếu là Admin phải nhập password
     */
    private static void menuAdministrator() {
        int choice = 0;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. User                                     |");
            System.out.println("| 2. Admin                                    |");
            System.out.println("| 3. Back to Flight Management System         |");
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
                    role = "User";
                    Utils.Utils.printMess(" Your role has been changed to User  ");
                    break;
                case 2:
                    System.out.print("Enter Password for Admin: ");
                    String password = sc.nextLine();
                    if (password.equals("23082003")) {
                        role = "Admin";
                        Utils.Utils.printMess(" Your role has been changed to Admin ");
                    } else {
                        System.out.println("Wrong Password!!!");
                    }
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
     * tạo menu để Admin chọn add phi hành đoàn cho chuyến bay
     */
    private static void menuAddCrewMember() {
        int choice = 0;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Add Pilot                                |");
            System.out.println("| 2. Add Flight Attendant                     |");
            System.out.println("| 3. Add Ground Staff                         |");
            System.out.println("| 4. Back to Flight Management System         |");
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
                    flight.addCrewMember("Pilot", " Add Pilot ", " Add Pilot Successfully  ", "  Flight has enough Pilots ");
                    break;
                case 2:
                    flight.addCrewMember("Flight Attendant", " Add Flight Attendant  ", " Add Flight Attendant Successfully ", " Flight has enough Flight Attendants ");
                    break;
                case 3:
                    flight.addCrewMember("Ground Staff", " Ground Staff ", " Add Ground Staff Successfully ", " Flight has enough Ground Staffs ");
                    break;
                case 4:
                    Utils.Utils.printMess("Exiting... ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 4);
    }
    /**
     * tạo menu để User xem thông tin chi tiết các chuyến bay và User
     */
    private static void menuPrint() {
        int choice = 0;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. All Flights                              |");
            System.out.println("| 2. All Passengers                           |");
            System.out.println("| 3. Search information passengers of Flight  |");
            System.out.println("| 4. Search Crew Members of Flight            |");
            System.out.println("| 5. Back to Flight Management System         |");
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
                    flight.printFlights();
                    break;
                case 2:
                    flight.printPassenger();
                    break;
                case 3:
                    flight.printReservation();
                    break;
                case 4:
                    flight.printCrewMember();
                    break;
                case 5:
                    Utils.Utils.printMess("Exiting... ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 5);
    }

    public static void main(String[] args) {

        flight.loadFromFlight("./flight.dat");
        flight.loadFromPassenger("./passenger.dat");
        flight.loadFromReservation("./reservation.dat");
        flight.loadFromCrewMember("./crewmember.dat");

        int choice = 0;
        do {
            System.out.println("+---------------------------------------------+");
            System.out.println("|           Flight Management System          |");
            System.out.println("+---------------------------------------------+");
            System.out.println("| 1. Add New Flights                          |");
            System.out.println("| 2. Search Available Flights and Booking     |");
            System.out.println("| 3. Check In and Seat Allocation             |");
            System.out.println("| 4. Add Crew Member for Flights              |");
            System.out.println("| 5. Print List                               |");
            System.out.println("| 6. Administrator                            |");
            System.out.println("| 7. Save to File                             |");
            System.out.println("| 8. Exit the Program                         |");
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
                    if (role.equals("Admin")) {
                        flight.addFlight();
                    } else {
                        System.out.println("Your role must be Admin to add flights.");
                    }
                    break;
                case 2:
                    if (role.equals("User")) {
                        flight.searchFlights();
                    } else {
                        System.out.println("Your role must be User to Search Flights and Booking.");
                    }
                    break;
                case 3:
                    if (role.equals("User")) {
                        flight.checkIn();
                    } else {
                        System.out.println("Your role must be User to Search Flights and Booking.");
                    }
                    break;
                case 4:
                    if (role.equals("Admin")) {
                        menuAddCrewMember();
                    } else {
                        System.out.println("Your role must be Admin to add flights.");
                    }
                    break;
                case 5:
                    if (role.equals("Admin")) {
                        menuPrint();
                    } else {
                        System.out.println("Your role must be Admin to add flights.");
                    }
                    break;
                case 6:
                    menuAdministrator();
                    break;
                case 7:
                    //save các dữ liệu mới vào File
                    if (flight.saveToFlight("./flight.dat") && flight.saveToPassenger("./passenger.dat")
                            && flight.saveToReservation("./reservation.dat") && flight.saveToCrewMember("./crewmember.dat")) {
                        Utils.Utils.printMess("Save to File finish");
                    } else {
                        System.out.println("Unable to save");
                    }
                    break;
                case 8:
                    Utils.Utils.printMess("Exiting the program... ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 8);
    }
}
