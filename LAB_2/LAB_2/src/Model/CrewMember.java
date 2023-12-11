package Model;

import java.io.Serializable;

public class CrewMember implements Serializable{

    private String flightNumber;
    private String staffId;
    private String name;
    private String role;

    public CrewMember(String flightNumber, String staffId, String name, String role) {
        this.flightNumber = flightNumber;
        this.staffId = staffId;
        this.name = name;
        this.role = role;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return String.format("| %-8s | %-15s | %-16s |", staffId, name, role);
    }

}
