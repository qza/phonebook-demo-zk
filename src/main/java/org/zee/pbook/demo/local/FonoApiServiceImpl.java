package org.zee.pbook.demo.local;

import com.aafanasev.fonoapi.DeviceEntity;
import com.aafanasev.fonoapi.rxjava.FonoApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class FonoApiServiceImpl implements FonoApiService {

    List<DeviceEntity> data;

    public FonoApiServiceImpl(String fileName) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileName));
            TypeToken<?> type = TypeToken.getParameterized(List.class, DeviceEntity.class);
            data = (List<DeviceEntity>) gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("file not found");
        }
    }

    @Override
    public Single<List<DeviceEntity>> getDevice(String token, String device, String brand, Integer position) {
        return Observable.fromIterable(data).filter(de -> device == null || de.getDeviceName().equals(device)).toList();
    }

    @Override
    public Single<List<DeviceEntity>> getLatest(String token, String brand, Integer limit) {
        return Observable.fromIterable(data).filter(de -> de.getBrand().equals(brand)).toList();
    }

}

