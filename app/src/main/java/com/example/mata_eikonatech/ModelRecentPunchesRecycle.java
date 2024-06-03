package com.example.mata_eikonatech;
import java.io.Serializable;

public class ModelRecentPunchesRecycle implements Serializable{
    private long timestamp;
    private double latitude;
    private double longitude;
    private String imageBase64;

    public ModelRecentPunchesRecycle(long timestamp,double latitude, double longitude, String imageBase64) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageBase64 = imageBase64;
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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(){
        this.imageBase64=imageBase64;
    }
}
