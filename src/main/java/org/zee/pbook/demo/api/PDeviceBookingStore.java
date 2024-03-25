package org.zee.pbook.demo.api;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PDeviceBookingStore {

    final Map<String, PDeviceBooking> bookings = new ConcurrentHashMap<>();

    public boolean isAvailable(String device) {
        return bookings.get(device) == null || bookings.get(device).getAvailability();
    }

    public Observable<PDeviceBooking> addBooking(String device, String user) {
        return Observable.create((ObservableOnSubscribe<PDeviceBooking>) emitter -> {
            logThreadAndTimestamp();
            emitter.setDisposable(null);
            var deviceBooking = new PDeviceBooking(device, user);
            if (!isAvailable(device)) {
                emitter.onNext(bookings.get(device));
                emitter.onError(new Throwable("device not available"));
            } else {
                System.out.println("placing new booking [devide: " + device + "] [user: " + user + "] [available: true]");
                if (bookings.containsKey(device)) {
                    bookings.replace(device, deviceBooking);
                } else {
                    bookings.put(device, deviceBooking);
                }
                emitter.onNext(bookings.get(device));
                emitter.onComplete();
            }
        }).doOnDispose(() -> removeBooking(device));
    }

    private void logThreadAndTimestamp() {
        System.out.println("checking booking [time: " + System.currentTimeMillis() + "] [thread: " + Thread.currentThread().getId() + "]");
    }

    public Observable<PDeviceBooking> getBookings(String device) {
        return Observable.just(bookings.getOrDefault(device, new PDeviceBooking(device)));
    }

    public Observable<PDeviceBooking> removeBooking(String device) {
        System.out.println("removing booking [device: " + device + "]");
        bookings.remove(device);
        return Observable.empty();
    }

}
