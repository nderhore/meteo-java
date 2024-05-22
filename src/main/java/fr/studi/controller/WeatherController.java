package fr.studi.controller;

import fr.studi.manager.WeatherManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;


@Data
public class WeatherController implements Initializable {


    private WeatherManager weatherManager;

    @FXML
    public TextField cityName;

    @FXML
    public Label error;

    @FXML
    private Label temperature, day, desc, windSpeed, cloudiness, pressure, humidity;


    private String citySet;

    public WeatherController() {
        this.citySet = "Toulouse".toLowerCase();
    }

    @FXML
    private void searchWeather(ActionEvent actionEvent) {
        if(cityName.getText() != null ){
            String city = cityName.getText();
            if(!city.isEmpty() ) {
                city = city.toLowerCase();
                weatherManager.changeCity(city);
                System.out.println(weatherManager);
                this.error.setVisible(false);
            }else {
                this.error.setText("Le champ ne peux pas être vide");
                this.error.setTextFill(Color.TOMATO);
                this.error.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cityName.setText(citySet);
        error.setVisible(false);


        weatherManager = new WeatherManager(this.citySet);

        try {
            showWeather();
        } catch (NullPointerException e) {
            this.error.setVisible(true);
            this.error.setTextFill(Color.TOMATO);
            this.error.setText("Pas d'internet !");
        }

        cityName.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.ENTER){
                searchWeather(null);
            }
        });
    }

    private void showWeather() {
        temperature.setText(weatherManager.getTemperature().toString()+"°C");
        day.setText(weatherManager.getDay().toLowerCase());
        desc.setText(weatherManager.getDescription().toLowerCase());
        windSpeed.setText(weatherManager.getWindSpeed()+" m/s");
        cloudiness.setText(weatherManager.getCloudiness()+" %");
        pressure.setText(weatherManager.getPressure()+" hPa");
        humidity.setText(weatherManager.getHumidity()+" %");
    }
}
