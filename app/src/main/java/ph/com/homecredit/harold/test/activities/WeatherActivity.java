package ph.com.homecredit.harold.test.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ph.com.homecredit.harold.test.BaseActivity;
import ph.com.homecredit.harold.test.MyApplication;
import ph.com.homecredit.harold.test.R;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.DaoSession;
import ph.com.homecredit.harold.test.models.Weather;

/**
 * Created by Harold Reyes on 1/5/2018.
 */

public class WeatherActivity extends BaseActivity {


    private City city;
    private TextView tvCity, tvMain, tvDescription, tvDate, tvPressure, tvHumidity, tvWindspeed, tvWindDeg;
    private DaoSession dbSession;
    private ImageView ivIcon;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        dbSession = ((MyApplication) getApplication()).getDbSession();
        city = (City) getIntent().getSerializableExtra("city");
        city = dbSession.getCityDao().load(city.getId());

        ivIcon = findViewById(R.id.ivIcon);
        tvCity = findViewById(R.id.tvCity);
        tvMain = findViewById(R.id.tvMain);
        tvDescription = findViewById(R.id.tvDescription);
        tvDate = findViewById(R.id.tvDate);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWindspeed = findViewById(R.id.tvWindspeed);
        tvWindDeg = findViewById(R.id.tvWindDeg);

        Weather weather = city.getWeather();

        tvCity.setText(String.format("%s, %s", city.getName(), city.getCountry()));
        tvMain.setText(weather.getMain());
        tvDescription.setText(weather.getDescription());
        tvDate.setText(String.format("Date: %d", weather.getDate()));
        tvPressure.setText(String.format("Pressure: %s", weather.getPressure()));
        tvHumidity.setText(String.format("Humidity: %s", weather.getHumidity()));
        tvWindspeed.setText(String.format("Wind Speed: %s", weather.getWindspeed()));
        tvWindDeg.setText(String.format("Wind Deg: %s", weather.getWindDeg()));

        //GlideApp.with(this).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(ivIcon);
        //01d.png
    }
}
