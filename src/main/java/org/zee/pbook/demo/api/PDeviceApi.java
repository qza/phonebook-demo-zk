package org.zee.pbook.demo.api;

import com.aafanasev.fonoapi.DeviceEntity;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface PDeviceApi {

    Single<List<DeviceEntity>> devices();

    Observable<List<PDeviceApiResponse>> bookings();

    Single<PDeviceBooking> book(String device, String user);

}
