package org.zee.pbook.demo.api;

import com.aafanasev.fonoapi.DeviceEntity;
import com.aafanasev.fonoapi.rxjava.FonoApiFactory;
import com.aafanasev.fonoapi.rxjava.FonoApiService;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.zee.pbook.demo.local.FonoApiServiceImpl;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class PDeviceApiImpl implements PDeviceApi {

    Path devicesPath = Path.of("src", "main", "resources", "devices.json");
    FonoApiService api1 = new FonoApiFactory().create();
    FonoApiService api2 = new FonoApiServiceImpl(devicesPath.toString());
    PDeviceBookingStore bookingStore = new PDeviceBookingStore();

    @Override
    public Single<List<DeviceEntity>> devices() {
        var observableApi1 = api1.getDevice(null, null, null, null).toObservable();
        var observableApi2 = api2.getDevice(null, null, null, null).toObservable();
        return observableApi1.doOnError((PDeviceApiImpl::printError)).onErrorResumeNext(observableApi2).firstOrError();
    }

    @Override
    public Observable<List<PDeviceApiResponse>> bookings() {
        List<DeviceEntity> deviceEntities = devices().blockingGet();
        List<PDeviceApiResponse> apiResponses = deviceEntities.stream()
                .map(d -> new PDeviceApiResponse(d, bookingStore.getBookings(d.getDeviceName()).blockingSingle()))
                .collect(Collectors.toList());
        return Observable.just(apiResponses);
    }

    @Override
    public Observable<PDeviceBooking> book(String device, String user) {
        return bookingStore.addBooking(device, user);
    }

    public static void printError(Throwable throwable) {
        System.out.println("error getting data from the api [error: " + throwable.getMessage() + "]");
    }
}
