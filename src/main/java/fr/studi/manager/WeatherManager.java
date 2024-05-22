package fr.studi.manager;

import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


@Data
public class WeatherManager {

    private String city;
    private String day;
    private Double temperature;
    private String icon;
    private String description;
    private String windSpeed;
    private String cloudiness;
    private String pressure;
    private String humidity;

    public WeatherManager(String city) {
        this.city = city;
    }

    public void getWeather(){
        JSONObject json = new JSONObject();
        JSONObject jsonToDisplay = new JSONObject();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        int day = 0;

        try{
            json = readJsonFromURL("http://api.openweathermap.org/data/2.5/weather?q="
            + city + "&appid=f055e781701949f279e6657ceaa1d158&units=metric");
        } catch (IOException e){
            return;
        }
        if(json != null){
            jsonToDisplay = json.getJSONObject("main");
            this.temperature = jsonToDisplay.getDouble("temp");
            this.pressure = jsonToDisplay.get("pressure").toString();
            this.humidity = jsonToDisplay.get("humidity").toString();

            jsonToDisplay = json.getJSONObject("wind");
            this.windSpeed = jsonToDisplay.get("speed").toString();

            jsonToDisplay = json.getJSONObject("clouds");
            this.cloudiness = jsonToDisplay.get("all").toString();

            jsonToDisplay = json.getJSONArray("weather").getJSONObject(0);
            this.icon = jsonToDisplay.getString("icon");
            this.description = jsonToDisplay.get("description").toString();

            calendar.add(Calendar.DATE, day);
            this.day = sdf.format(calendar.getTime());
        }

    }

    private JSONObject readJsonFromURL(String url) throws IOException, JSONException {
        JSONObject json = null;
        try(InputStream is = new URL(url).openStream()){
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(reader);
            json = new JSONObject(jsonText);
        }
        //ICI

        return json;

    }

    private String readAll(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while((cp = reader.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();

    }

    @Override
    public String toString(){
        return this.day + " " + this.temperature + " " + this.pressure + " " + this.humidity;
    }

    public void changeCity(String city) {
        this.city = city;
        this.getWeather();
    }
}
