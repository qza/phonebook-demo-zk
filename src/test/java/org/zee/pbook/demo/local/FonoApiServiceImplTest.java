package org.zee.pbook.demo.local;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FonoApiServiceImplTest {

    String fileName;
    FonoApiServiceImpl impl;

    @BeforeEach
    public void init() {
        fileName = Path.of("src", "test", "resources", "devices.json").toString();
        impl = new FonoApiServiceImpl(fileName);
    }

    @Test
    public void shouldLoadSingleDeviceFromLocalStore() {
        var samsungGalaxyS9 = "Samsung Galaxy S9";
        var galaxyS9 = impl.getDevice(null, samsungGalaxyS9, null, null)
                .doOnSuccess((deviceEntities -> {
                    assertNotNull(deviceEntities);
                    assertTrue(deviceEntities.get(0).getDeviceName().equals(samsungGalaxyS9));
                    System.out.println("device name match [name: " + samsungGalaxyS9 + "]");
                }))
                .doOnError(throwable -> throwable.printStackTrace())
                .doFinally(() -> System.out.println("call finalized"));
        galaxyS9.subscribe();
    }
}
