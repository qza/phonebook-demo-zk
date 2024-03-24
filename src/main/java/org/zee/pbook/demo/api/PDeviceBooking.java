package org.zee.pbook.demo.api;

import java.time.Instant;

public class PDeviceBooking {

    private String device;
    private String user;
    private Instant bookedAt;
    private Boolean availability;

    public PDeviceBooking(String device) {
        this.device = device;
        this.availability = true;
    }

    public PDeviceBooking(String device, String user) {
        this.device = device;
        this.user = user;
        this.bookedAt = Instant.now();
        this.availability = false;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = bookedAt;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "PDeviceBooking{" +
                "device='" + device + '\'' +
                ", user='" + user + '\'' +
                ", bookedAt=" + bookedAt +
                ", availability=" + availability +
                '}';
    }
}
