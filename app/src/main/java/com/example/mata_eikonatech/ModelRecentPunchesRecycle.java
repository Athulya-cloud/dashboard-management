package com.example.mata_eikonatech;
import java.io.Serializable;

public class ModelRecentPunchesRecycle implements Serializable{
    private long timestamp;
//    private String latitude;
//    private String longitude;
//    private String imageBase64;

    public ModelRecentPunchesRecycle(long timestamp) {
        this.timestamp = timestamp;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.imageBase64 = imageBase64;
    }

//    @Override
//    public String toString() {
//        return "ModelRecentPunchesRecycle{" +
//                "timestamp=" + timestamp +
//                '}';
//    }

    public long getTimestamp() {
        return timestamp;
    }

//    public String getLatitude() {
//        return latitude;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public String getImageBase64() {
//        return imageBase64;
//    }
}
