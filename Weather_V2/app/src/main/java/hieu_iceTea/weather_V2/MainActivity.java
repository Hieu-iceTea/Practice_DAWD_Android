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

import hieu_iceTea.weather_V2.repository.WeatherDataRepository;

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

        new WeatherDataRepository(cityName).getData((weathers, main) -> {
            lblResult.setText(weathers.toString() + "\r\n" + "\r\n" +

                    "Temp: " + main.getTemp() + "\r\n" +
                    "Feels Like: " + main.getFeels_like() + "\r\n" +
                    "Temp Min: " + main.getTemp_min() + "\r\n" +
                    "Temp Max: " + main.getTemp_max() + "\r\n" +
                    "Pressure: " + main.getPressure() + "\r\n" +
                    "Humidity: " + main.getHumidity() + "\r\n" +
                    "Sea Level: " + main.getSea_level() + "\r\n" +
                    "Grnd Level: " + main.getGrnd_level() + "\r\n"
            );

            String icon = weathers.get(0).getIcon();

            imageWeatherIcon.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load("http://openweathermap.org/img/wn/" + icon + "@2x.png")
                    .into(imageWeatherIcon);
        });

    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(txtCityName.getWindowToken(), 0);
    }

    private void notificationCouldNotFindWeather() {
        lblResult.setText("");
        Toast.makeText(getApplicationContext(), "Could not find weather. 😯", Toast.LENGTH_SHORT).show();

        imageWeatherIcon.setVisibility(View.INVISIBLE);
    }

    private void notificationPleaseEnterCityName() {
        lblResult.setText("");
        Toast.makeText(getApplicationContext(), "Please enter the city name. 🤔", Toast.LENGTH_SHORT).show();

        imageWeatherIcon.setVisibility(View.INVISIBLE);
    }

}