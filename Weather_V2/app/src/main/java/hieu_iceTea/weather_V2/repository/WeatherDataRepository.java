package hieu_iceTea.weather_V2.repository;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hieu_iceTea.weather_V2.controller.VolleyAppController;
import hieu_iceTea.weather_V2.model.Main;
import hieu_iceTea.weather_V2.model.Weather;


public class WeatherDataRepository {

    String url;

    List<Weather> weathers = new ArrayList<>();
    Main main = new Main();

    public void getData(String cityName, AsyncProcess callBackAsyncProcess, @Nullable Response.ErrorListener errorListener) {

        this.url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=3790f712337cbfece38c0ae41594ad7c";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                url,

                response -> {

                    try {

                        Gson gson = new Gson();

                        String strWeatherArray = response.getJSONArray("weather").toString();
                        String strMainObject = response.getJSONObject("main").toString();

                        Type typeWeathers = new TypeToken<List<Weather>>() {
                        }.getType();
                        Type typeMain = new TypeToken<Main>() {
                        }.getType();

                        weathers = gson.fromJson(strWeatherArray, typeWeathers);
                        main = gson.fromJson(strMainObject, typeMain);

                        if (weathers == null) {
                            weathers = new ArrayList<>();
                        }

                        if (main == null) {
                            main = new Main();
                        }

                        if (callBackAsyncProcess != null) {
                            callBackAsyncProcess.onFinished(weathers, main);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },

                error -> {
                    if (errorListener != null) {
                        errorListener.onErrorResponse(error);
                    }
                }

        );

        VolleyAppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
