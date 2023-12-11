package Control;

import Model.CrewMember;
import Model.Flight;
import Model.Passenger;
import Model.Reservation;
import Utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FlightManagementSystem {
    // tạo 4 list thông qua 4 đối tượng đã tạo
    private List<Flight> listFlight;
    private List<Passenger> listPassengers;
    private List<Reservation> listReservations;
    private List<CrewMember> listCrewMembers;
    Scanner sc = new Scanner(System.in);

    public FlightManagementSystem() {
        listFlight = new ArrayList<>();
        listPassengers = new ArrayList<>();
        listReservations = new ArrayList<>();
        listCrewMembers = new ArrayList<>();
    }
    /**
     * Thêm mới chuyến bay
     */
    public void addFlight() {
        Utils.printMess("Add New Flight ");
        String flightNumber, departureCity, destinationCity;
        LocalDateTime departureTime;
        LocalDateTime arrivalTime;
        int availableSeats, countSeats;
        //kiểm tra thêm chuyến bay 
        //tạo flightNumber mới, nếu tồn tại thì nhập lại
        boolean check = true;
        do {
            flightNumber = Utils.getStringreg("Enter Number Flight: ", "^F\\d{4}$",
                    "Number Flight cannot be empty.", "Wrong Number Flight format.");
            if (checkFlight(flightNumber)) {
                System.out.println("Same flight.");
            } else {
                check = false;
            }
        } while (check);
        //tạo departureCity
        departureCity = Utils.getString("Enter Departure City: ", "Departure City cannot be empty.");
        //tạo destinationCity
        destinationCity = Utils.getString("Enter Destination City: ", "Destination City cannot be empty.");
        //tạo departureTime có cấu trúc dd/mm/yyyy HH:mm và phải after thời gian hiện tại
        boolean checkDepartureTime = true;
        do {
            departureTime = Utils.getDateTime("Enter Departure Time (dd/MM/yyyy HH:mm): ");
            if (departureTime.isBefore(LocalDateTime.now())) {
                System.out.println("Invalid Time. Departure time must be after the current time.");
            } else {
                checkDepartureTime = false;
            }
        } while (checkDepartureTime);
        //tạo arrivalTime có cấu trúc dd/mm/yyyy HH:mm và phải after departureTime
        boolean checkArrivalTime = true;
        do {
            arrivalTime = Utils.getDateTime("Enter Arrival Time (dd/MM/yyyy HH:mm): ");
            if (arrivalTime.isBefore(departureTime)) {
                System.out.println("Arrival time must be after departure time");
            } else {
                checkArrivalTime = false;
            }
        } while (checkArrivalTime);
        //tạo availableSeats lớn hơn 0 và nhỏ hơn hoặc bằng 156 và phải là số chia hết cho 6
        availableSeats = Utils.getInt("Enter Available Seats(divisible by 6): ", 0, 156);
        //tạo countSeats để đếm chỗ ngồi khi được book
        countSeats = availableSeats;
        // tạo và add mới Flight vào listFlight
        Flight flights = new Flight(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, availableSeats, countSeats);
        listFlight.add(flights);
        Utils.printMess(" Add New Flight Successfully ");
        // duyệt xem user có muốn tt add
        System.out.println();
        System.out.print("Continue Add New Flight (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            addFlight();
        }
    }
    /**
     * Tìm chuyến bay theo điểm khởi hành và điểm đến và thời gian bay
     */
    public void searchFlights() {
        Utils.printMess("  Search Available Flights ");
        String departureCity = Utils.getString("Enter Departure City: ", "Departure City cannot be empty.");
        String destinationCity = Utils.getString("Enter Destination City: ", "Destination City cannot be empty.");
        LocalDateTime departureTime = Utils.getDateTime("Enter Departure Time (dd/MM/yyyy HH:mm): ");
        // Thêm một khoảng thời gian cho phép tìm kiếm trong khoảng thời gian gần
        LocalDateTime lowerBound = departureTime.minusDays(1); 
        LocalDateTime upperBound = departureTime.plusDays(2); 
        //tạo mới 1 list flightAvailable để chứa các chuyến bay cần tìm
        List<Flight> flightAvailable = new ArrayList<>();
        for (Flight flight : listFlight) {
            if (flight.getDepartureCity().equalsIgnoreCase(departureCity) && flight.getDestinationCity().equalsIgnoreCase(destinationCity)
                    && flight.getDepartureTime().isAfter(lowerBound) && flight.getDepartureTime().isBefore(upperBound)) {
                flightAvailable.add(flight);
            }
        }
        if (flightAvailable.isEmpty()) {
            Utils.printMess(" Flight does not exist ");
        } else {
            System.out.println("+---------------------------------------------------------------------------------------------------+");
            System.out.println("|                                 List of Available Flights                                         |");
            System.out.println("+---------------------------------------------------------------------------------------------------+");
            System.out.println("| Flight Number |  Departure City  | Destination City |  Departure Time  |   Arrival Time   | Seats |");
            System.out.println("+---------------------------------------------------------------------------------------------------+");
            for (Flight flight : flightAvailable) {
                System.out.println(flight);
            }
            System.out.println("+---------------------------------------------------------------------------------------------------+");

            // duyệt xem user có muốn book flight
            System.out.println();
            System.out.print("Do you want to Book Flight (Y/N): ");
            String c = sc.nextLine();
            if (c.equalsIgnoreCase("Y")) {
                bookFlight();
            }
        }
    }
    /**
     * đặt vé cho hành khách và hiển thị mã vé
     */
    public void bookFlight() {
        Utils.printMess("  Booking Flight ");
        String cccd, reservationId, name, contactDetails;
        String flightNumber = Utils.getStringreg("Enter Number Flight: ", "^F\\d{4}$",
                "Number Flight cannot be empty.", "Wrong Number Flight format.");
        boolean checkFlight = true;
        for (Flight flight : listFlight) {
            //nếu chuyến bay tồn tại và số ghế trông >0 và chưa cất cánh bay thì thực hiện nhập thông tin đặt vé
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber) && flight.getCountSeats() > 0 && flight.getDepartureTime().isAfter(LocalDateTime.now())) {
                //nhập CCCD duy nhất
                boolean checkCccd = true;
                do {
                    cccd = Utils.getStringreg("Enter CCCD: ", "^0\\d{11}$", "CCCD cannot be empty.", "Wrong CCCD format.");
                    if (checkCccd(cccd)) {
                        System.out.println("Same CCCD.");
                    } else {
                        checkCccd = false;
                    }
                } while (checkCccd);
                //tự động tạo reservationId duy nhất
                boolean checkId = true;
                do {
                    reservationId = Utils.getRadomId();
                    if (!checkValidId(reservationId)) {
                        checkId = false;
                    }
                } while (checkId);
                //nhập tên
                name = Utils.getString("Enter Name: ", "Name cannot be empty.");
                //nhập SDT
                contactDetails = Utils.getStringreg("Enter Contact: ", "^(0[0-9]{9})$",
                        "Contact cannot be empty.", "Wrong Contact format.");
                // tạo và add mới passenger vào listPassengers
                Passenger passengers = new Passenger(cccd, reservationId, name, contactDetails, flightNumber, flight.getDepartureCity(), flight.getDestinationCity(),
                        flight.getDepartureTime(), flight.getArrivalTime(), flight.getAvailableSeats() / 6, flight.getCountSeats());
                listPassengers.add(passengers);
                //khi đặt vé được thì countSeat sẽ giảm đi 1 chỗ
                flight.setCountSeats(flight.getCountSeats() - 1);
                checkFlight = false;
                Utils.printMess(" Book Flights Successfully ");
                System.out.println("Here are your Reservation ID: " + passengers.getReservationId());
            } else if (flight.getFlightNumber().equalsIgnoreCase(flightNumber) && flight.getDepartureTime().isBefore(LocalDateTime.now())) {
                Utils.printMess(" The Flight has departed ");
                checkFlight = false;
            } else if (flight.getFlightNumber().equalsIgnoreCase(flightNumber) && flight.getCountSeats() == 0) {
                Utils.printMess("  The Flight Was Full ");
                checkFlight = false;
            }
        }
        if (checkFlight) {
            Utils.printMess(" Flight does not exist ");
        }
        // duyệt xem user có muốn tt book flight
        System.out.println();
        System.out.print("Continue Book Flight (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            bookFlight();
        }
    }
    /**
     * Đăng ký lên máy bay và phân bổ ghế ngồi cho hành khách
     */
    public void checkIn() {
        Utils.printMess("  Check-In and Seat Allocation ");
        String reservationId = Utils.getStringreg("Enter Reservation ID: ", "^[A-Z]{3}-[A-Z0-9]{6}$",
                "Reservation ID cannot be empty.", "Wrong Reservation ID format.");
        //khi mã vé đã được đặt chỗ thì không thể đặt tiếp (mỗi mã vé 1 chỗ ngồi)
        for (Reservation reservation : listReservations) {
            if (reservation.getReservationId().equalsIgnoreCase(reservationId)) {
                System.out.println("Reservation ID has been Check-In and Seat Allocation!!!");
                return;
            }
        }
        // khi mã vé chưa được đặt chỗ thì thực hiện chọn chỗ
        boolean checkIn = true;
        String seat;
        for (Passenger passenger : listPassengers) {
            if (passenger.getReservationId().equalsIgnoreCase(reservationId)) {
                Utils.printMess("  Successfully checked in for the flight ");
                //hiển thị các ghé ngồi và các ghế đã có người đặt               
                System.out.println();
                printAvailableSeat(passenger.getFlightNumber(), passenger.getAvailableSeats());
                System.out.println();
                //nhập số ghê muốn chọn
                boolean checkSeat = true;
                do {
                    seat = Utils.getSeat("Enter Seat Number: ", 0, passenger.getAvailableSeats());
                    if (checkSeats(seat, passenger.getFlightNumber(), listReservations)) {
                        System.out.println("Seat already booked.");
                    } else {
                        checkSeat = false;
                    }
                } while (checkSeat);
                checkIn = false;
                Reservation reservation = new Reservation(reservationId, passenger.getName(), seat, passenger.getFlightNumber(),
                        passenger.getDepartureCity(), passenger.getDestinationCity(), passenger.getDepartureTime(),
                        passenger.getArrivalTime(), passenger.getAvailableSeats(), passenger.getCountSeats());
                listReservations.add(reservation);
                // hiện thị thông tin chi tiết vé máy bay cho User
                System.out.println("Here are your flight ticket details: ");
                System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| Reservation ID |      Name       |  Departure City  | Destination City |  Departure Time  |   Arrival Time   | Seat |");
                System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
                System.out.println(reservation);
                System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
            }
        }
        if (checkIn) {
            Utils.printMess("  Failed checked in for the flight ");
        }
        // duyện xem User có muốn tiếp tục Check In
        System.out.println();
        System.out.print("Continue Check-In and Seat Allocation (Y/N): ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            checkIn();
        }
    }
    /**
     * Thêm thành viên phi hành đoàn
     * @param role
     * @param mess1
     * @param mess2
     * @param mess3 
     */
    public void addCrewMember(String role, String mess1, String mess2, String mess3) {
        do {
            Utils.printMess(mess1);
            String name, staffId;
            String flightNumber = Utils.getStringreg("Enter Number Flight: ", "^F\\d{4}$",
                    "Number Flight cannot be empty.", "Wrong Number Flight format.");
            boolean checkFlight = true;
            for (Flight flight : listFlight) {
                if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                    //tạo 1 biến đếm các phi hành đoàn
                    int countCrewMembers = countRole(flightNumber, role);
                    //nếu các phi hành đoàn vượt quá số quy định thì không thêm
                    if (countCrewMembers < getMaxCrewMembers(role)) {
                        //tạo staffId không được trùng
                        boolean checkStaff = true;
                        do {
                            staffId = Utils.getStringreg("Enter ID Staff: ", "^STI\\d{4}$",
                                    "ID Staff cannot be empty.", "Wrong Staff ID format.");
                            if (checkStaff(staffId)) {
                                System.out.println("Same ID Staff.");
                            } else {
                                checkStaff = false;
                            }
                        } while (checkStaff);
                        //tạo tên Staff
                        name = Utils.getString("Enter Name Staff: ", "Name cannot be empty.");
                        CrewMember member = new CrewMember(flightNumber, staffId, name, role);
                        listCrewMembers.add(member);
                        Utils.printMess(mess2);
                        checkFlight = false;
                    } else {
                        Utils.printMess(mess3);
                        checkFlight = false;
                    }
                }
            }
            if (checkFlight) {
                Utils.printMess(" Flight does not exist ");
            }
            //duyện xem Admin có muốn tt add phi hành đoàn
            System.out.println();
            System.out.print("Continue Add " + role + " (Y/N): ");
            String c = sc.nextLine();
            if (!c.equalsIgnoreCase("Y")) {
                break;
            }
        } while (true);
    }
    /**
     * hàm đưa ra số lượng quy định của phi hành đoàn cho 1 chuyến bay
     * @param role
     * @return 
     */
    private int getMaxCrewMembers(String role) {
        int maxCrewMembers = 0;
        switch (role) {
            case "Pilot":
                maxCrewMembers = 2;
                break;
            case "Flight Attendant":
                maxCrewMembers = 5;
                break;
            case "Ground Staff":
                maxCrewMembers = 4;
                break;
        }
        return maxCrewMembers;
    }
    /**
     * hàm kiểm tra nếu staffId đã tồn tại thì trả về true
     * @param staffId
     * @return true/false 
     */
    private boolean checkStaff(String staffId) {
        for (CrewMember member : listCrewMembers) {
            if (member.getStaffId().equalsIgnoreCase(staffId)) {
                return true;
            }
        }
        return false;
    }
    /**
     * hàm In ra thông tin chi tiết của tất cả các chuyến bay và sắp sếp giảm dần theo ngày bay
     */
    public void printFlights() {
        if (listFlight.isEmpty()) {
            Utils.printMess(" No Flights exist  ");
        } else {
            System.out.println("+---------------------------------------------------------------------------------------------------+");
            System.out.println("|                                      List All Flights                                             |");
            System.out.println("+---------------------------------------------------------------------------------------------------+");
            System.out.println("| Flight Number |  Departure City  | Destination City |  Departure Time  |   Arrival Time   | Seats |");
            System.out.println("+---------------------------------------------------------------------------------------------------+");
            Collections.sort(listFlight);
            for (Flight flight : listFlight) {
                System.out.println(flight);
                printAvailableSeat(flight.getFlightNumber(), flight.getAvailableSeats() / 6);
                System.out.println();
            }
            System.out.println("+---------------------------------------------------------------------------------------------------+");
        }
    }
    /**
     * hàm In ra thông tin chi tiết của các hành khách đã đặt vé
     */
    public void printPassenger() {
        if (listPassengers.isEmpty()) {
            Utils.printMess(" No Passengers exist ");
        } else {
            System.out.println("+-----------------------------------------------------------------------------------+");
            System.out.println("|                               List All Passengers                                 |");
            System.out.println("+-----------------------------------------------------------------------------------+");
            System.out.println("|     CCCD     | Flight Number | Reservation ID |      Name       | Contact Details |");
            System.out.println("+-----------------------------------------------------------------------------------+");
            for (Passenger passenger : listPassengers) {
                System.out.println(passenger);
            }
            System.out.println("+-----------------------------------------------------------------------------------+");
        }
    }
    /**
     * hàm In ra thông tin chi tiết tất cả các vé máy bay của 1 chuyến bay
     */
    public void printReservation() {
        Utils.printMess(" Search information passengers of Flight ");
        //tạo 1 listInforReservation để chứa các vé của chuyến bay cần in ra
        List<Reservation> listInforReservation = new ArrayList<>();
        String flightNumber = Utils.getStringreg("Enter Number Flight: ", "^F\\d{4}$",
                "Number Flight cannot be empty.", "Wrong Number Flight format.");
        if (!checkFlight(flightNumber)) {
            Utils.printMess(" Flight does not exist ");
            return;
        }
        for (Reservation reservation : listReservations) {
            if (reservation.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                listInforReservation.add(reservation);
            }
        }
        if (listInforReservation.isEmpty()) {
            Utils.printMess(" No Passengers exist ");
        } else {
            System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                      List information passengers of Flight                                          |");
            System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| Reservation ID |      Name       |  Departure City  | Destination City |  Departure Time  |   Arrival Time   | Seat |");
            System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
            for (Reservation reservation : listInforReservation) {
                System.out.println(reservation);
            }
            System.out.println("+---------------------------------------------------------------------------------------------------------------------+");
        }
    }
    /**
     * hàm In ra các phi hành đoàn trong 1 chuyến bay
     */
    public void printCrewMember() {
        Utils.printMess(" Search Crew Members of Flight ");
        List<CrewMember> listInforCrew = new ArrayList<>();
        String flightNumber = Utils.getStringreg("Enter Number Flight: ", "^F\\d{4}$",
                "Number Flight cannot be empty.", "Wrong Number Flight format.");
        if (!checkFlight(flightNumber)) {
            Utils.printMess(" Flight does not exist ");
            return;
        }
        for (CrewMember crewMember : listCrewMembers) {
            if (crewMember.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                listInforCrew.add(crewMember);
            }
        }
        if (listInforCrew.isEmpty()) {
            Utils.printMess(" No Crew Members exist ");
        } else {
            System.out.println("+-----------------------------------------------+");
            System.out.println("|           List Crew Members of Flight         |");
            System.out.println("+-----------------------------------------------+");
            System.out.println("| ID Staff |      Name       |       Role       |");
            System.out.println("+-----------------------------------------------+");
            for (CrewMember crewMember : listInforCrew) {
                System.out.println(crewMember);
            }
            System.out.println("+-----------------------------------------------+");
        }
    }
    /**
     * hàm kiểm tra flightNumber nếu tồn tại thì trả về true
     * @param flightNumber
     * @return true/false
     */
    private boolean checkFlight(String flightNumber) {
        for (Flight flight : listFlight) {
            if (flightNumber.equalsIgnoreCase(flight.getFlightNumber())) {
                return true;
            }
        }
        return false;
    }
    /**
     * hàm kiểm tra CCCD nếu tồn tại thì trả về true
     * @param cccd
     * @return true/false
     */
    private boolean checkCccd(String cccd) {
        for (Passenger passenger : listPassengers) {
            if (passenger.getCccd().equalsIgnoreCase(cccd)) {
                return true;
            }
        }
        return false;
    }
    /**
     * hàm kiểm tra reservationId nếu tồn tại thì tra về true
     * @param reservationId
     * @return true/false
     */
    private boolean checkValidId(String reservationId) {
        for (Reservation reservation : listReservations) {
            if (reservation.getReservationId().equalsIgnoreCase(reservationId)) {
                return true;
            }
        }
        return false;
    }
    /**
     * hàm kiểm tra ghế nếu đã tồn tại tại thì tra về true
     * @param seat
     * @param flightnumber
     * @param listReservations
     * @return true/fasle
     */
    private boolean checkSeats(String seat, String flightnumber, List<Reservation> listReservations) {
        for (Reservation reservation : listReservations) {
            if (reservation.getSeat().equalsIgnoreCase(seat) && reservation.getFlightNumber().equalsIgnoreCase(flightnumber)) {
                return true;
            }
        }
        return false;
    }
    /**
     * hàm in ra các các chỗ ngồi trong chuyến bay
     * @param flightNumber
     * @param flightSeats 
     */
    public void printAvailableSeat(String flightNumber, int flightSeats) {
        System.out.println("List of flight seating positions: ");
        for (int i = 1; i <= flightSeats; i++) {
            for (char j = 'A'; j <= 'F'; j++) {
                String seat = String.format("%c%d", j, i); // Định dạng chuỗi vị trí ngồi
                boolean seatSold = false;

                for (Reservation reservation : listReservations) {
                    if (reservation.getFlightNumber().equalsIgnoreCase(flightNumber) && reservation.getSeat().equalsIgnoreCase(seat)) {
                        seatSold = true;
                        break;
                    }
                }
                if (seatSold) {
                    System.out.printf("| Sold |");
                } else {
                    System.out.printf("| %-1c%-3d |", j, i);
                }
            }
            System.out.println();
        }
    }
    /**
     * hàm đếm role của 1 chuyến bay
     * @param flightNumber
     * @param check
     * @return count
     */
    private int countRole(String flightNumber, String check) {
        int count = 0;
        for (CrewMember member : listCrewMembers) {
            if (member.getFlightNumber().equalsIgnoreCase(flightNumber) && member.getRole().equalsIgnoreCase(check)) {
                count++;
            }
        }
        return count;
    }
    
    public boolean saveToFlight(String pathFile) {
        boolean status = false;
        try {
            File fo = new File(pathFile);
            FileOutputStream fos = new FileOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Flight flight : listFlight) {
                oos.writeObject(flight);
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
    public void loadFromFlight(String pathFile) {
        File fi = new File(pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            boolean loopMore = true;
            while (loopMore) {
                Flight x = (Flight) ois.readObject();
                if (x != null) {
                    listFlight.add(x);
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
    public boolean saveToPassenger(String pathFile) {
        boolean status = false;
        try {
            File fo = new File(pathFile);
            FileOutputStream fos = new FileOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Passenger passenger : listPassengers) {
                oos.writeObject(passenger);
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
    public void loadFromPassenger(String pathFile) {
        File fi = new File(pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            boolean loopMore = true;
            while (loopMore) {
                Passenger x = (Passenger) ois.readObject();
                if (x != null) {
                    listPassengers.add(x);
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
    public boolean saveToReservation(String pathFile) {
        boolean status = false;
        try {
            File fo = new File(pathFile);
            FileOutputStream fos = new FileOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Reservation reservation : listReservations) {
                oos.writeObject(reservation);
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
    public void loadFromReservation(String pathFile) {
        File fi = new File(pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            boolean loopMore = true;
            while (loopMore) {
                Reservation x = (Reservation) ois.readObject();
                if (x != null) {
                    listReservations.add(x);
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
    public boolean saveToCrewMember(String pathFile) {
        boolean status = false;
        try {
            File fo = new File(pathFile);
            FileOutputStream fos = new FileOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (CrewMember crewMember : listCrewMembers) {
                oos.writeObject(crewMember);
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
    public void loadFromCrewMember(String pathFile) {
        File fi = new File(pathFile);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(fis);
            boolean loopMore = true;
            while (loopMore) {
                CrewMember x = (CrewMember) ois.readObject();
                if (x != null) {
                    listCrewMembers.add(x);
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
