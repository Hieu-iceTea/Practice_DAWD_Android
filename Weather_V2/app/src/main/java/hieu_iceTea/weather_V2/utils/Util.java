package hieu_iceTea.weather_V2.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    public static double convertTempKelvinToCelsius(double tempKelvin, int roundPlaces) {

        double tempCelsius = tempKelvin - 273.15;

        tempCelsius = round(tempCelsius, roundPlaces);

        return tempCelsius;

    }

    //https://www.baeldung.com/java-round-decimal-number
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    //https://www.baeldung.com/java-round-decimal-number
    //https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        // Cách 1: Cách viết code này tương đương 1 dòng return ở dưới.
        //BigDecimal bd = BigDecimal.valueOf(value);
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();

        // Cách 2: Of course, if you prefer, you can inline the above into a one-liner:
        //return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

}
