package fr.studi.pojo;

import lombok.Data;

@Data
public class Weather {

    private String city;
    private String day;
    private Double temperature;
    private String icon;
    private String description;
    private String windSpeed;
    private String cloudiness;
    private String pressure;
    private String humidity;

    public Weather(String city) {
        this.city = city;
    }

    public Weather() {
    }

}
