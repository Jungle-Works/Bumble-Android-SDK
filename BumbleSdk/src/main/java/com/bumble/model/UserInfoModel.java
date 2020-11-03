package com.bumble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("geo")
    @Expose
    private Geo geo;
    @SerializedName("original")
    @Expose
    private Original original;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Original getOriginal() {
        return original;
    }

    public void setOriginal(Original original) {
        this.original = original;
    }

    public class Data {

        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("continent_code")
        @Expose
        private String continentCode;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getContinentCode() {
            return continentCode;
        }

        public void setContinentCode(String continentCode) {
            this.continentCode = continentCode;
        }

    }

    class Geo {

        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("continent_code")
        @Expose
        private String continentCode;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getContinentCode() {
            return continentCode;
        }

        public void setContinentCode(String continentCode) {
            this.continentCode = continentCode;
        }

    }

    class Location {

        @SerializedName("accuracy_radius")
        @Expose
        private Integer accuracyRadius;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("time_zone")
        @Expose
        private String timeZone;

        public Integer getAccuracyRadius() {
            return accuracyRadius;
        }

        public void setAccuracyRadius(Integer accuracyRadius) {
            this.accuracyRadius = accuracyRadius;
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

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

    }

    class Original {

        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("continent")
        @Expose
        private String continent;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("subdivision")
        @Expose
        private String subdivision;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
            this.continent = continent;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getSubdivision() {
            return subdivision;
        }

        public void setSubdivision(String subdivision) {
            this.subdivision = subdivision;
        }
    }
}