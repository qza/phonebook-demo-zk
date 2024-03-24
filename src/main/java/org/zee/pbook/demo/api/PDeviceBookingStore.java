package org.zee.pbook.demo.api;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PDeviceBookingStore {

    final Map<String, PDeviceBooking> bookings = new ConcurrentHashMap<>();

    public boolean isAvailable(String device) {
        return bookings.get(device) != null;
    }

    public Observable<PDeviceBooking> addBooking(String device, String user) {
        var deviceBooking = new PDeviceBooking(device, user);
        deviceBooking = bookings.containsKey(device) ? bookings.replace(device, deviceBooking) : bookings.put(device, deviceBooking);
        return Observable.just(deviceBooking);
    }

    public Observable<PDeviceBooking> removeBooking(String device) {
        return Observable.just(bookings.remove(device));
    }

    public Observable<List<PDeviceBooking>> getBookings() {
        return Observable.just(new ArrayList<>(bookings.values()));
    }

    public Observable<PDeviceBooking> getBookings(String device) {
        return Observable.just(bookings.getOrDefault(device, new PDeviceBooking(device)));
    }

}
