package org.pub_sub.broker.dtos;

import org.pub_sub.common.generated.AdminProto;
import org.pub_sub.common.generated.SubscriptionProto;

public class SubscriptionDto {
    private String source;
    private AdminProto.SourceType sourceType;

    private String date;
    private SubscriptionProto.Operator dateOperator;

    private Integer temp;
    private SubscriptionProto.Operator tempOperator;

    private String direction;
    private SubscriptionProto.Operator directionOperator;

    private Integer wind;
    private SubscriptionProto.Operator windOperator;

    private Double rain;
    private SubscriptionProto.Operator rainOperator;

    private Integer station;
    private SubscriptionProto.Operator stationOperator;

    private String city;
    private SubscriptionProto.Operator cityOperator;

    private Double avgTemp;
    private SubscriptionProto.Operator avgTempOperator;

    public SubscriptionDto(String source, AdminProto.SourceType sourceType, SubscriptionProto.Subscription subscription) {
        this.source = source;
        this.sourceType = sourceType;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public AdminProto.SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(AdminProto.SourceType sourceType) {
        this.sourceType = sourceType;
    }

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

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
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

    public Double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(Double avgTemp) {
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

    public static SubscriptionProto.Subscription toProto(SubscriptionDto dto) {
        SubscriptionProto.Subscription.Builder builder = SubscriptionProto.Subscription.newBuilder();

        if (dto.hasDate()) {
            builder.setDate(SubscriptionProto.StringFieldCondition.newBuilder()
                    .setValue(dto.getDate())
                    .setOperator(dto.getDateOperator()));
        }

        if (dto.hasTemp()) {
            builder.setTemp(SubscriptionProto.IntFieldCondition.newBuilder()
                    .setValue(dto.getTemp())
                    .setOperator(dto.getTempOperator()));
        }

        if (dto.hasDirection()) {
            builder.setDirection(SubscriptionProto.StringFieldCondition.newBuilder()
                    .setValue(dto.getDirection())
                    .setOperator(dto.getDirectionOperator()));
        }

        if (dto.hasWind()) {
            builder.setWind(SubscriptionProto.IntFieldCondition.newBuilder()
                    .setValue(dto.getWind())
                    .setOperator(dto.getWindOperator()));
        }

        if (dto.hasRain()) {
            builder.setRain(SubscriptionProto.DoubleFieldCondition.newBuilder()
                    .setValue(dto.getRain())
                    .setOperator(dto.getRainOperator()));
        }

        if (dto.hasStation()) {
            builder.setStation(SubscriptionProto.IntFieldCondition.newBuilder()
                    .setValue(dto.getStation())
                    .setOperator(dto.getStationOperator()));
        }

        if (dto.hasCity()) {
            builder.setCity(SubscriptionProto.StringFieldCondition.newBuilder()
                    .setValue(dto.getCity())
                    .setOperator(dto.getCityOperator()));
        }

        if (dto.hasAvgTemp()) {
            builder.setAvgTemp(SubscriptionProto.DoubleFieldCondition.newBuilder()
                    .setValue(dto.getAvgTemp())
                    .setOperator(dto.getAvgTempOperator()));
        }

        return builder.build();
    }
}
