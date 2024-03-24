package org.zee.pbook.demo.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
                .doOnNext((deviceEntities -> {
                    assertNotNull(deviceEntities);
                    assertTrue(deviceEntities.get(0).getBooking().blockingFirst().getAvailability());
                    System.out.println("device is available");
                }))
                .doOnError(throwable -> throwable.printStackTrace())
                .doFinally(() -> System.out.println("call finalized"));
        galaxyS9.subscribe();
    }

    @Test
    public void shouldBookDevice() {
        var samsungGalaxyS9 = "Samsung Galaxy S9";
        var galaxyS9 = api.book(samsungGalaxyS9, "testUser")
                .doOnSuccess((booking -> {
                    assertNotNull(booking);
                    assertFalse(booking.getAvailability());
                    System.out.println("device is not available");
                }))
                .doOnError(throwable -> throwable.printStackTrace())
                .doFinally(() -> System.out.println("call finalized"));
        galaxyS9.subscribe();
    }

}
