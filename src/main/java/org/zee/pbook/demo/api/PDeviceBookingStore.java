package org.zee.pbook.demo.api;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PDeviceBookingStore {

    final Map<String, PDeviceBooking> bookings = new ConcurrentHashMap<>();

    public boolean isAvailable(String device) {
        return bookings.get(device) == null || bookings.get(device).getAvailability();
    }

    public Observable<PDeviceBooking> addBooking(String device, String user) {
        return Observable.create(emitter -> {
            logThreadAndTimestamp();
            var deviceBooking = new PDeviceBooking(device, user);
            if (!isAvailable(device)) {
                emitter.onError(new Throwable("device not available"));
            } else {
                if (bookings.containsKey(device)) {
                    bookings.replace(device, deviceBooking);
                } else {
                    bookings.put(device, deviceBooking);
                }
                emitter.onNext(bookings.get(device));
                emitter.onComplete();
            }
        });
    }

    private void logThreadAndTimestamp() {
        System.out.println("checking booking [time: " + System.currentTimeMillis() + "] [thread: " + Thread.currentThread().getId() + "]");
    }

    public Observable<List<PDeviceBooking>> getBookings() {
        return Observable.just(new ArrayList<>(bookings.values()));
    }

    public Observable<PDeviceBooking> getBookings(String device) {
        return Observable.just(bookings.getOrDefault(device, new PDeviceBooking(device)));
    }

    public Observable<PDeviceBooking> removeBooking(String device) {
        return Observable.just(bookings.remove(device));
    }

}
