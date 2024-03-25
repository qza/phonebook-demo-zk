package org.zee.pbook.demo.api;

import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PDeviceApiImplTest {

    PDeviceApi api = new PDeviceApiImpl();

    @BeforeEach
    public void init() {
    }

    @Test
    public void shouldFailoverToLocalApiAndReturnValue() {
        var samsungGalaxyS9 = "Samsung Galaxy S9";
        var galaxyS9 = api.devices()
                .doOnSuccess((deviceEntities -> {
                    assertNotNull(deviceEntities);
                    assertTrue(deviceEntities.get(0).getDeviceName().equals(samsungGalaxyS9));
                    System.out.println("device found [name: " + samsungGalaxyS9 + "]");
                }))
                .doOnError(throwable -> throwable.printStackTrace())
                .doFinally(() -> System.out.println("call finalized"));
        galaxyS9.subscribe();
    }

    @Test
    public void shouldGetDevicesWithBookings() {
        var galaxyS9 = api.bookings()
                .doOnError(throwable -> throwable.printStackTrace())
                .doOnNext((deviceEntities -> {
                    assertNotNull(deviceEntities);
                    assertTrue(deviceEntities.get(0).getBooking().blockingFirst().getAvailability());
                    System.out.println("devices: " + Arrays.toString(deviceEntities.toArray()));
                }))
                .doFinally(() -> System.out.println("call finalized"));
        galaxyS9.subscribe();
    }

    @Test
    public void shouldBookDevice() {
        var samsungGalaxyS9 = "Samsung Galaxy S9";
        var galaxyS9 = api.book(samsungGalaxyS9, "testUser")
                .doOnNext((booking -> {
                    assertNotNull(booking);
                }))
                .doFinally(() -> System.out.println("action complete"));

        galaxyS9.subscribe(getpDeviceBookingConsumer(), getThrowableConsumer());
        galaxyS9.subscribe(getpDeviceBookingConsumer(), getThrowableConsumer());
        galaxyS9.subscribe(getpDeviceBookingConsumer(), getThrowableConsumer());
    }

    @NotNull
    private Consumer<PDeviceBooking> getpDeviceBookingConsumer() {
        return (e) -> {
            System.out.println("device [data: " + e + "]");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        };
    }

    @NotNull
    private Consumer<Throwable> getThrowableConsumer() {
        return (t) -> {
            System.out.println("final consumer error: " + t.getMessage());
        };
    }

}
