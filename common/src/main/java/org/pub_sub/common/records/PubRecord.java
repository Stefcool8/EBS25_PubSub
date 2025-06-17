package org.pub_sub.common.records;

import org.pub_sub.common.generated.PublicationProto;

public class PubRecord {
    public int station;
    public String city;
    public String date;
    public String direction;
    public double rain;
    public int wind;
    public int temp;
    public long timestamp;

    public PubRecord() {
    }

    public PubRecord(int station, String city, String date, String direction, double rain, int wind, int temp, long timestamp) {
        this.station = station;
        this.city = city;
        this.date = date;
        this.direction = direction;
        this.rain = rain;
        this.wind = wind;
        this.temp = temp;
        this.timestamp = timestamp;
    }

    public PublicationProto.Publication toPublicationProto() {
        return PublicationProto.Publication.newBuilder()
                .setStation(station)
                .setCity(city)
                .setDate(date)
                .setDirection(direction)
                .setRain(rain)
                .setWind(wind)
                .setTemp(temp)
                .setTimestamp(timestamp)
                .build();
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{ \"station\": \"" + station + "\", \"city\": \"" + city + "\", \"date\": \"" + date + "\", " +
                "\"direction\": \"" + direction + "\", \"rain\": \"" + rain + "\", \"wind\": \"" + wind + "\", " +
                "\"temp\": \"" + temp + "\", \"timestamp\": \"" + timestamp + "\" }";
    }
}
