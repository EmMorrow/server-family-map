package Data;

/**
 * Created by emilychandler on 10/25/17.
 */

public class Location {
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;

    public Location() {
        country = null;
        city = null;
        latitude = null;
        longitude = null;
    }

    public Location(String country, String city, Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
