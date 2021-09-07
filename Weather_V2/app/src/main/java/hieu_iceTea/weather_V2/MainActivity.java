package hieu_iceTea.weather_V2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import hieu_iceTea.weather_V2.model.Weather;
import hieu_iceTea.weather_V2.repository.WeatherDataRepository;
import hieu_iceTea.weather_V2.utils.Util;

public class MainActivity extends AppCompatActivity {

    EditText txtCityName;
    TextView lblResult;
    ImageView imageWeatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCityName = findViewById(R.id.txtCityName);
        lblResult = findViewById(R.id.lblResult);
        imageWeatherIcon = findViewById(R.id.imageWeatherIcon);
    }

    public void onBtnGetWeatherClick(View view) {

        hideKeyboard();

        lblResult.setVisibility(View.VISIBLE);
        lblResult.setText("Loading...");

        String cityName = txtCityName.getText().toString();

        if (cityName.isEmpty()) {
            notificationPleaseEnterCityName();
            return;
        }

        new WeatherDataRepository().getData(

                cityName,

                (weathers, main) -> {

                    // 01. Get result text
                    String result = "";

                    if (!weathers.isEmpty()) {
                        for (Weather weather : weathers) {
                            result += weather.getMain() + ": " + weather.getDescription() + "\r\n";
                        }
                        result += "\r\n";
                    }

                    result += "Temp: " + Util.convertTempKelvinToCelsius(main.getTemp(), 2) + " °C" + "\r\n" +
                            "Feels Like: " + Util.convertTempKelvinToCelsius(main.getFeels_like(), 2) + " °C" + "\r\n" +
                            "Temp Min: " + Util.convertTempKelvinToCelsius(main.getTemp_min(), 2) + " °C" + "\r\n" +
                            "Temp Max: " + Util.convertTempKelvinToCelsius(main.getTemp_max(), 2) + " °C" + "\r\n" +
                            "Pressure: " + main.getPressure() + "\r\n" +
                            "Humidity: " + main.getHumidity() + "\r\n" +
                            "Sea Level: " + main.getSea_level() + "\r\n" +
                            "Grnd Level: " + main.getGrnd_level() + "\r\n";


                    lblResult.setText(result);

                    // 02. Get icon
                    if (!weathers.isEmpty()) {
                        String icon = weathers.get(0).getIcon();

                        imageWeatherIcon.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                                .into(imageWeatherIcon);
                    }

                },

                error -> {
                    notificationCouldNotFindWeather();
                }

        );

    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(txtCityName.getWindowToken(), 0);
    }

    private void notificationCouldNotFindWeather() {
        lblResult.setText("");
        Toast.makeText(getApplicationContext(), "❌ Could not find weather. 😯", Toast.LENGTH_SHORT).show();

        imageWeatherIcon.setVisibility(View.INVISIBLE);
    }

    private void notificationPleaseEnterCityName() {
        lblResult.setText("");
        Toast.makeText(getApplicationContext(), "❗❓ Please enter the city name. 🤔", Toast.LENGTH_SHORT).show();

        imageWeatherIcon.setVisibility(View.INVISIBLE);
    }

}