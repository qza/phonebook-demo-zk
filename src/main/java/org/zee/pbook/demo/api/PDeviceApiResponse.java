package org.zee.pbook.demo.api;

import com.aafanasev.fonoapi.DeviceEntity;
import io.reactivex.Observable;

public class PDeviceApiResponse {

    private Observable<PDeviceBooking> booking;
    private Observable<DeviceEntity> entity;

    public PDeviceApiResponse() {
    }

    public PDeviceApiResponse(DeviceEntity entity, PDeviceBooking booking) {
        this.entity = Observable.just(entity);
        this.booking = Observable.just(booking);
    }

    public Observable<PDeviceBooking> getBooking() {
        return booking;
    }

    public Observable<DeviceEntity> getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "PDeviceApiResponse{" +
                "booking=" + booking.blockingFirst() +
                ", entity=" + entity.blockingFirst() +
                '}';
    }
}
