package org.pub_sub.broker.dtos;

import org.pub_sub.common.generated.SubscriptionProto;

public class SubscriptionDto {
    private String source;
    
    // Date field
    private String date;
    private SubscriptionProto.Operator dateOperator;
    
    // Temp field
    private Integer temp;
    private SubscriptionProto.Operator tempOperator;
    
    // Direction field
    private String direction;
    private SubscriptionProto.Operator directionOperator;
    
    // Wind field
    private Integer wind;
    private SubscriptionProto.Operator windOperator;
    
    // Rain field
    private Float rain;
    private SubscriptionProto.Operator rainOperator;
    
    // Station field
    private Integer station;
    private SubscriptionProto.Operator stationOperator;
    
    // City field
    private String city;
    private SubscriptionProto.Operator cityOperator;
    
    // AvgTemp field
    private Float avgTemp;
    private SubscriptionProto.Operator avgTempOperator;

    // Constructor
    public SubscriptionDto(String source, SubscriptionProto.Subscription subscription) {
        this.source = source;
        
        if (subscription.hasDate()) {
            this.date = subscription.getDate().getValue();
            this.dateOperator = subscription.getDate().getOperator();
        }
        
        if (subscription.hasTemp()) {
            this.temp = subscription.getTemp().getValue();
            this.tempOperator = subscription.getTemp().getOperator();
        }
        
        if (subscription.hasDirection()) {
            this.direction = subscription.getDirection().getValue();
            this.directionOperator = subscription.getDirection().getOperator();
        }
        
        if (subscription.hasWind()) {
            this.wind = subscription.getWind().getValue();
            this.windOperator = subscription.getWind().getOperator();
        }
        
        if (subscription.hasRain()) {
            this.rain = subscription.getRain().getValue();
            this.rainOperator = subscription.getRain().getOperator();
        }
        
        if (subscription.hasStation()) {
            this.station = subscription.getStation().getValue();
            this.stationOperator = subscription.getStation().getOperator();
        }
        
        if (subscription.hasCity()) {
            this.city = subscription.getCity().getValue();
            this.cityOperator = subscription.getCity().getOperator();
        }
        
        if (subscription.hasAvgTemp()) {
            this.avgTemp = subscription.getAvgTemp().getValue();
            this.avgTempOperator = subscription.getAvgTemp().getOperator();
        }
    }

    // Getters and Setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    // Date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SubscriptionProto.Operator getDateOperator() {
        return dateOperator;
    }

    public void setDateOperator(SubscriptionProto.Operator dateOperator) {
        this.dateOperator = dateOperator;
    }

    public boolean hasDate() {
        return date != null;
    }

    // Temp
    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public SubscriptionProto.Operator getTempOperator() {
        return tempOperator;
    }

    public void setTempOperator(SubscriptionProto.Operator tempOperator) {
        this.tempOperator = tempOperator;
    }

    public boolean hasTemp() {
        return temp != null;
    }

    // Direction
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public SubscriptionProto.Operator getDirectionOperator() {
        return directionOperator;
    }

    public void setDirectionOperator(SubscriptionProto.Operator directionOperator) {
        this.directionOperator = directionOperator;
    }

    public boolean hasDirection() {
        return direction != null;
    }

    // Wind
    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }

    public SubscriptionProto.Operator getWindOperator() {
        return windOperator;
    }

    public void setWindOperator(SubscriptionProto.Operator windOperator) {
        this.windOperator = windOperator;
    }

    public boolean hasWind() {
        return wind != null;
    }

    // Rain
    public Float getRain() {
        return rain;
    }

    public void setRain(Float rain) {
        this.rain = rain;
    }

    public SubscriptionProto.Operator getRainOperator() {
        return rainOperator;
    }

    public void setRainOperator(SubscriptionProto.Operator rainOperator) {
        this.rainOperator = rainOperator;
    }

    public boolean hasRain() {
        return rain != null;
    }

    // Station
    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public SubscriptionProto.Operator getStationOperator() {
        return stationOperator;
    }

    public void setStationOperator(SubscriptionProto.Operator stationOperator) {
        this.stationOperator = stationOperator;
    }

    public boolean hasStation() {
        return station != null;
    }

    // City
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public SubscriptionProto.Operator getCityOperator() {
        return cityOperator;
    }

    public void setCityOperator(SubscriptionProto.Operator cityOperator) {
        this.cityOperator = cityOperator;
    }

    public boolean hasCity() {
        return city != null;
    }

    // AvgTemp
    public Float getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(Float avgTemp) {
        this.avgTemp = avgTemp;
    }

    public SubscriptionProto.Operator getAvgTempOperator() {
        return avgTempOperator;
    }

    public void setAvgTempOperator(SubscriptionProto.Operator avgTempOperator) {
        this.avgTempOperator = avgTempOperator;
    }

    public boolean hasAvgTemp() {
        return avgTemp != null;
    }

    @Override
    public String toString() {
        return "SubscriptionDto{" +
                "source='" + source + '\'' +
                ", date='" + date + '\'' +
                ", dateOperator=" + dateOperator +
                ", temp=" + temp +
                ", tempOperator=" + tempOperator +
                ", direction='" + direction + '\'' +
                ", directionOperator=" + directionOperator +
                ", wind=" + wind +
                ", windOperator=" + windOperator +
                ", rain=" + rain +
                ", rainOperator=" + rainOperator +
                ", station=" + station +
                ", stationOperator=" + stationOperator +
                ", city='" + city + '\'' +
                ", cityOperator=" + cityOperator +
                ", avgTemp=" + avgTemp +
                ", avgTempOperator=" + avgTempOperator +
                '}';
    }
}
