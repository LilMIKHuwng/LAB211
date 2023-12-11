package Control;

import Model.Vehicle;
import Utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class VehicleManagement {

    List<Vehicle> listVehicle = new ArrayList<>();
    private String pathFile;

    public VehicleManagement(String pathFile) {
        this.pathFile = pathFile;
    }

    Scanner sc = new Scanner(System.in);

    /**
     * Prompts the user to add a new vehicle, adds it to the list of vehicles,
     * and allows the user to continue adding vehicles.
     */
    public void addNewVehicle() {
        Utils.printMess("Add New Vehicle");
        Vehicle newVehicle = createNewVehicle();
        listVehicle.add(newVehicle);
        Utils.printMess("Add Vehicle Successfully");

        // Ask if the user wants to continue adding vehicles
        if (continueAddingVehicle()) {
            addNewVehicle();
        }
    }

    /**
     * Creates a new vehicle by prompting the user for its details and returns
     * the created vehicle object.
     *
     * @return A new Vehicle object with user-defined attributes.
     */
    private Vehicle createNewVehicle() {
        String idVehicle = getUniqueVehicleId();
        String nameVehicle = Utils.getString("Enter Name Vehicle: ", "Name Vehicle cannot be empty.");
        String colorVehicle = Utils.getString("Enter Color Vehicle: ", "Color Vehicle cannot be empty.");
        double priceVehicle = Utils.getDouble("Enter Price Vehicle: ", 100000);
        String brandVehicle = Utils.getString("Enter Brand Vehicle: ", "Brand Vehicle cannot be empty.");
        String type = Utils.getString("Enter Type: ", "Type cannot be empty.");
        Year productYear = getValidProductYear();

        return new Vehicle(idVehicle, nameVehicle, colorVehicle, priceVehicle, brandVehicle, type, productYear);
    }

    /**
     * Prompts the user to enter a unique vehicle ID, validates the format, and
     * checks if the ID already exists in the list of vehicles. This method
     * ensures that the generated ID is unique.
     *
     * @return A unique vehicle ID entered by the user.
     */
    private String getUniqueVehicleId() {
        String idVehicle;
        boolean idExists;
        do {
            idVehicle = Utils.getStringreg("Enter ID_Vehicle: ", "\\d{4}", "ID_Vehicle cannot be empty.", "Wrong ID_Vehicle format.");
            idExists = checkId(idVehicle);
            if (idExists) {
                System.out.println("Same ID.");
            }
        } while (idExists);
        return idVehicle;
    }

    /**
     * Prompts the user to enter a valid product year, validates it, and ensures
     * that the year is before the current year or equal to the current year.
     *
     * @return A valid Year object representing the product year entered by the
     * user.
     */
    private Year getValidProductYear() {
        Year productYear;
        boolean isValidYear;
        do {
            productYear = Utils.getYear("Enter Product Year: ");
            isValidYear = productYear.isBefore(Year.now()) || productYear.equals(Year.now());
            if (!isValidYear) {
                System.out.println("Product year must be before the current year or equal.");
            }
        } while (!isValidYear);
        return productYear;
    }

    /**
     * Prompts the user if they want to continue adding vehicles and returns a
     * boolean based on the user's choice (Y for yes, N for no).
     *
     * @return True if the user chooses to continue adding vehicles, false if
     * not.
     */
    private boolean continueAddingVehicle() {
        System.out.println();
        System.out.print("Continue Add Daily Product (Y/N): ");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("Y");
    }

    /**
     * Checks if a vehicle with the given ID exists in the list of vehicles.
     * Displays a message indicating whether the vehicle exists or not.
     */
    public void checkVehicleExists() {
        Utils.printMess("Check to Exist Vehicle ");

        String idVehicle = Utils.getStringreg("Enter ID_Vehicle: ", "\\d{4}",
                "ID_Vehicle cannot be empty.", "Wrong ID_Vehicle format.");
        boolean check = true;
        for (Vehicle vehicle : listVehicle) {
            if (vehicle.getIdVehicle().equalsIgnoreCase(idVehicle)) {
                Utils.printMess("Existed Vehicle");
                check = false;
            }
        }
        if (check) {
            Utils.printMess("No Vehicle Found!");
        }
    }

    /**
     * Updates a vehicle by prompting the user for its ID and then attempting to
     * update the vehicle with the provided ID. Displays appropriate messages
     * and allows the user to continue updating vehicles.
     */
    public void updateVehicle() {
        Utils.printMess("Update Vehicle ");
        String idVehicle = Utils.getStringreg("Enter ID_Vehicle: ", "\\d{4}", "ID_Vehicle cannot be empty.", "Wrong ID_Vehicle format");

        boolean vehicleUpdated = updateVehicleById(idVehicle);

        if (!vehicleUpdated) {
            Utils.printMess("Vehicle does not exist");
        }

        // Ask if the user wants to continue updating vehicles
        System.out.println();
        System.out.print("Continue Update Vehicle (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            updateVehicle();
        }
    }

    /**
     * Updates a vehicle in the list of vehicles with the provided ID if it
     * exists.
     *
     * @param idVehicle The ID of the vehicle to be updated.
     * @return True if the vehicle with the given ID is found and updated, false
     * if not.
     */
    private boolean updateVehicleById(String idVehicle) {
        for (Vehicle vehicle : listVehicle) {
            if (vehicle.getIdVehicle().equalsIgnoreCase(idVehicle)) {
                updateVehicleDetails(vehicle);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the details of a vehicle, including its name, color, price,
     * brand, type, and product year.
     *
     * @param vehicle The vehicle object to be updated.
     */
    private void updateVehicleDetails(Vehicle vehicle) {
        String nameVehicle = Utils.getUpdateString("Enter New Name Vehicle (press Enter to skip): ");
        if (!nameVehicle.isEmpty()) {
            vehicle.setNameVehicle(nameVehicle);
        }

        String colorVehicle = Utils.getUpdateString("Enter New Color Vehicle (press Enter to skip): ");
        if (!colorVehicle.isEmpty()) {
            vehicle.setColorVehicle(colorVehicle);
        }

        String priceVehicle;
        double priceVehicleNew = 0;
        boolean checkPrice = true;
        do {
            System.out.print("Enter Price (press Enter to skip): ");
            priceVehicle = sc.nextLine();
            if (priceVehicle.isEmpty()) {
                checkPrice = false;
            } else {
                if (Utils.getUpdateDouble(priceVehicle, 100000)) {
                    priceVehicleNew = Double.parseDouble(priceVehicle);
                    vehicle.setPriceVehicle(priceVehicleNew);
                    checkPrice = false;
                }
            }
        } while (checkPrice);

        String brandVehicle = Utils.getUpdateString("Enter New Brand Vehicle (press Enter to skip): ");
        if (!brandVehicle.isEmpty()) {
            vehicle.setBrandVehicle(brandVehicle);
        }

        String type = Utils.getUpdateString("Enter New Type (press Enter to skip): ");
        if (!type.isEmpty()) {
            vehicle.setType(type);
        }

        String productYear;
        Year productYearNew = null;
        boolean checkYear = true;
        do {
            System.out.print("Enter New Product Year (press Enter to skip): ");
            productYear = sc.nextLine();
            if (productYear.isEmpty()) {
                checkYear = false;
            } else if (productYear.equals(Year.now().toString())) {
                vehicle.setProductYear(Year.now());
                checkYear = false;
            } else {
                if (Utils.getUpdateYear(productYear)) {
                    int input = Integer.parseInt(productYear);
                    productYearNew = Year.of(input);
                    if (productYearNew.isAfter(vehicle.getProductYear()) && productYearNew.isBefore(Year.now())) {
                        vehicle.setProductYear(productYearNew);
                        checkYear = false;
                    } else {
                        System.out.println("New Product Year must be after Old Product Year or Before current Year!");
                    }
                }
            }
        } while (checkYear);

        displayUpdateResult(vehicle, nameVehicle, colorVehicle, brandVehicle, type, priceVehicleNew, productYearNew);
    }

    /**
     * Displays the result of updating a vehicle, including the changes made to
     * its details.
     *
     * @param vehicle The updated vehicle object.
     * @param nameVehicle The new name of the vehicle.
     * @param colorVehicle The new color of the vehicle.
     * @param brandVehicle The new brand of the vehicle.
     * @param type The new type of the vehicle.
     * @param priceVehicleNew The new price of the vehicle.
     * @param productYearNew The new product year of the vehicle.
     */
    private void displayUpdateResult(Vehicle vehicle, String nameVehicle, String colorVehicle, String brandVehicle, String type, double priceVehicleNew, Year productYearNew) {
        System.out.println();
        if (nameVehicle.isEmpty() && colorVehicle.isEmpty() && brandVehicle.isEmpty() && type.isEmpty() && priceVehicleNew == 0 && productYearNew == null) {
            Utils.printMess("Vehicle has no changes after the Update");
        } else {
            Utils.printMess("Vehicle has been successfully updated");
            Utils.printTable();
            System.out.println(vehicle);
            Utils.printLine();
        }
    }

    /**
     * Deletes a vehicle from the list of vehicles if it exists, based on the
     * provided ID. Displays the result of the delete operation and checks if
     * the user wants to continue deleting vehicles.
     */
    public void deleteVehicle() {
        Utils.printMess("Delete Vehicle ");
        String idVehicle = Utils.getStringreg("Enter ID_Vehicle: ", "\\d{4}",
                "ID_Vehicle cannot be empty.", "Wrong ID_Vehicle format.");
        boolean check = true;
        for (int i = 0; i < listVehicle.size(); i++) {
            if (idVehicle.equalsIgnoreCase(listVehicle.get(i).getIdVehicle())) {
                if (confirmDelete()) {
                    listVehicle.remove(i);
                    Utils.printMess("Delete Successfully");
                } else {
                    Utils.printMess("Vehicle has not been Deleted ");
                }
                check = false;
            }
        }
        if (check) {
            Utils.printMess("Vehicle does not exist ");
        }
        // duyệt xem user có muốn tt xóa
        if (continueDeletingVehicle()) {
            deleteVehicle();
        }
    }

    /**
     * Asks the user to confirm the deletion of a vehicle and returns true if
     * the user confirms (by entering 'Y' or 'y').
     *
     * @return True if the user confirms the deletion; otherwise, false.
     */
    private boolean confirmDelete() {
        System.out.print("Confirm to Delete this Vehicle (Y/N): ");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("Y");
    }

    /**
     * Asks the user if they want to continue deleting vehicles and returns true
     * if the user wants to continue (by entering 'Y' or 'y').
     *
     * @return True if the user wants to continue deleting vehicles; otherwise,
     * false.
     */
    private boolean continueDeletingVehicle() {
        System.out.println();
        System.out.print("Continue Delete Vehicle (Y/N): ");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("Y");
    }

    /**
     * Searches for vehicles by their name and displays the results if any
     * vehicles match the provided name.
     */
    public void searchVehicleByName() {
        searchVehicles("Name", Utils.getString("Enter Name Vehicle: ", "Name Vehicle cannot be empty."));
    }

    /**
     * Searches for vehicles by their ID and displays the results if any
     * vehicles match the provided ID.
     */
    public void searchVehicleById() {
        searchVehicles("ID", Utils.getStringreg("Enter ID_Vehicle: ", "\\d{4}", "ID_Vehicle cannot be empty.", "Wrong ID_Vehicle format."));
    }

    /**
     * Searches for vehicles by the provided search criteria and value, and
     * displays the results if any vehicles match the criteria.
     *
     * @param searchType The type of search criteria, such as "Name" or "ID".
     * @param searchValue The value to search for within the specified criteria.
     */
    private void searchVehicles(String searchType, String searchValue) {
        List<Vehicle> matchedVehicles = new ArrayList<>();

        for (Vehicle vehicle : listVehicle) {
            if (("Name".equals(searchType) && vehicle.getNameVehicle().equalsIgnoreCase(searchValue))
                    || ("ID".equals(searchType) && vehicle.getIdVehicle().equalsIgnoreCase(searchValue))) {
                matchedVehicles.add(vehicle);
            }
        }

        if (matchedVehicles.isEmpty()) {
            Utils.printMess("No Vehicle Found!");
        } else {
            Utils.printCenteredMessage(" List of Vehicles search by " + searchType);
            Utils.printTable();
            matchedVehicles.forEach(System.out::println);
            Utils.printLine();
        }
    }

    /**
     * Displays a list of all vehicles in the system. If there are no vehicles,
     * it will show a message indicating no vehicles found.
     */
    public void displayVehiclesAll() {
        if (listVehicle.isEmpty()) {
            Utils.printMess("No Vehicle Found!");
        } else {
            Utils.printCenteredMessage("List of All Vehicles");
            Utils.printTable();
            for (Vehicle vehicle : listVehicle) {
                System.out.println(vehicle);
            }
            Utils.printLine();
        }
    }

    /**
     * Displays a list of vehicles sorted by price in descending order.
     */
    public void displayVehiclesByPrice() {
        List<Vehicle> listVehicleByPrice = new ArrayList<>();
        listVehicleByPrice = displayFilteredVehicles("Price", "Display Vehicle By Price ");
        if (listVehicleByPrice.isEmpty()) {
            Utils.printMess("No Vehicle Found!");
        } else {
            Utils.printCenteredMessage("List of Vehicles By Price ");
            Utils.printTable();
            Collections.sort(listVehicleByPrice, new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle t, Vehicle t1) {
                    return t.getPriceVehicle() < t1.getPriceVehicle() ? 1 : t.getPriceVehicle() == t1.getPriceVehicle() ? 0 : -1;
                }
            });
            for (Vehicle vehicle : listVehicleByPrice) {
                System.out.println(vehicle);
            }
            Utils.printLine();
        }
    }

    /**
     * Displays a list of vehicles filtered by the product year and sorted by
     * year in descending order.
     */
    public void printByYear() {
        List<Vehicle> listVehicleByYear = new ArrayList<>();
        listVehicleByYear = displayFilteredVehicles("Year", " Print vehicle By Year ");
        if (listVehicleByYear.isEmpty()) {
            Utils.printMess("No Vehicle Found!");
        } else {
            Utils.printCenteredMessage("List of Vehicles By Year");
            Utils.printTable();
            Collections.sort(listVehicleByYear, new Comparator<Vehicle>() {
                @Override
                public int compare(Vehicle t, Vehicle t1) {
                    return t.getProductYear().isBefore(t1.getProductYear()) ? 1 : t.getProductYear().equals(t1.getProductYear()) ? 0 : -1;
                }
            });
            for (Vehicle vehicle : listVehicleByYear) {
                System.out.println(vehicle);
            }
            Utils.printLine();
        }
    }

    /**
     * Displays a list of vehicles filtered by either price or product year.
     *
     * @param mess The type of filtering to apply ("Price" or "Year").
     * @param displayMess The message to display before listing the vehicles.
     * @return A list of vehicles that meet the specified filtering criteria.
     */
    private List<Vehicle> displayFilteredVehicles(String mess, String displayMess) {
        Utils.printMess(displayMess);
        List<Vehicle> filteredVehicles = new ArrayList<>();
        double priceVehicle;
        Year productYear;

        if ("Price".equals(mess)) {
            priceVehicle = Utils.getDouble("Enter Price Vehicle: ", 100000);
            for (Vehicle vehicle : listVehicle) {
                if (vehicle.getPriceVehicle() <= priceVehicle) {
                    filteredVehicles.add(vehicle);
                }
            }
        } else {
            productYear = Utils.getYear("Enter Product Year: ");
            for (Vehicle vehicle : listVehicle) {
                if (vehicle.getProductYear().isAfter(productYear) || vehicle.getProductYear().equals(productYear)) {
                    filteredVehicles.add(vehicle);
                }
            }
        }

        return filteredVehicles;
    }

    /**
     * Checks if a given vehicle ID already exists in the list of vehicles.
     *
     * @param idVehicle The vehicle ID to be checked.
     * @return True if the vehicle ID already exists, otherwise False.
     */
    private boolean checkId(String idVehicle) {
        for (Vehicle vehicle : listVehicle) {
            if (idVehicle.equalsIgnoreCase(vehicle.getIdVehicle())) {
                return true;
            }
        }
        return false;
    }

    public boolean saveTo() {
        boolean status = false;
        try {
            File fo = new File(this.pathFile);
            FileOutputStream fos = new FileOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Vehicle vehicle : listVehicle) {
                oos.writeObject(vehicle);
            }
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

    public void loadFrom() {
        File fi = new File(this.pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 2.
            boolean loopMore = true;
            while (loopMore) {
                Vehicle x = (Vehicle) ois.readObject();
                if (x != null) {
                    listVehicle.add(x);
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
