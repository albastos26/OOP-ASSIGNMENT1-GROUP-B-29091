package com.auca.classroom;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class Classroom implements Identifiable, Registrable {
    private String roomNumber;
    private String building;
    private int capacity;
    private int floor;
    private boolean hasProjector;

    public Classroom(String roomNumber, String building, int capacity, int floor, boolean hasProjector) {
        this.roomNumber = roomNumber;
        this.building = building;
        this.capacity = capacity;
        this.floor = floor;
        this.hasProjector = hasProjector;
    }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    public boolean isHasProjector() { return hasProjector; }
    public void setHasProjector(boolean hasProjector) { this.hasProjector = hasProjector; }

    @Override
    public String getId() { return building + ID_SEPARATOR + roomNumber; }

    @Override
    public String getDisplayName() { return building + " " + roomNumber; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (roomNumber == null || roomNumber.trim().isEmpty())
            throw new ValidationException("Room number is required.");
        if (building == null || building.trim().isEmpty())
            throw new ValidationException("Building is required.");
        if (capacity < 1 || capacity > 1000)
            throw new ValidationException("Capacity must be between 1 and 1000.");
        if (floor < 0)
            throw new ValidationException("Floor cannot be negative.");
    }

    @Override
    public void printDetails() {
        System.out.println("=== CLASSROOM ===");
        System.out.println("Room           : " + roomNumber);
        System.out.println("Building       : " + building);
        System.out.println("Capacity       : " + capacity);
        System.out.println("Floor          : " + floor);
        System.out.println("Projector      : " + (hasProjector ? "YES" : "NO"));
    }

    @Override
    public String getRegistrationSummary() {
        return getId() + " | capacity " + capacity + " | floor " + floor;
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}
