package fr.studi.manager;

import fr.studi.pojo.Weather;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


@Data
public class WeatherManager {

   private Weather weatherDisplayed;

    public WeatherManager(String city) {
        this.weatherDisplayed = new Weather(city);
        this.changeCity(city);
    }

    public void getWeather(){
        JSONObject json = new JSONObject();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        int day = 0;

        try{
            json = readJsonFromURL("http://api.openweathermap.org/data/2.5/weather?q="
            + this.weatherDisplayed.getCity() + "&appid=f055e781701949f279e6657ceaa1d158&units=metric");
        } catch (IOException e){
            return;
        }
        if(json != null){
            adaptWeatherToNewCity(json);
            calendar.add(Calendar.DATE, day);
            this.weatherDisplayed.setDay(sdf.format(calendar.getTime()));
        }

    }

    private void adaptWeatherToNewCity(JSONObject json) {
        JSONObject jsonToDisplay = new JSONObject();

        jsonToDisplay = json.getJSONObject("main");
        this.weatherDisplayed.setTemperature(jsonToDisplay.getDouble("temp"));
        this.weatherDisplayed.setPressure(jsonToDisplay.get("pressure").toString());
        this.weatherDisplayed.setHumidity(jsonToDisplay.get("humidity").toString());

        jsonToDisplay = json.getJSONObject("wind");
        this.weatherDisplayed.setWindSpeed(jsonToDisplay.get("speed").toString());

        jsonToDisplay = json.getJSONObject("clouds");
        this.weatherDisplayed.setCloudiness(jsonToDisplay.get("all").toString());

        jsonToDisplay = json.getJSONArray("weather").getJSONObject(0);
        this.weatherDisplayed.setIcon(jsonToDisplay.getString("icon"));
        this.weatherDisplayed.setDescription(jsonToDisplay.get("description").toString());
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
        return this.weatherDisplayed.getDay() + " " +
                this.weatherDisplayed.getTemperature() + " " +
                this.weatherDisplayed.getPressure() + " " +
                this.weatherDisplayed.getHumidity();
    }

    public void changeCity(String city) {
        this.weatherDisplayed.setCity(city);
        this.getWeather();
    }
}
