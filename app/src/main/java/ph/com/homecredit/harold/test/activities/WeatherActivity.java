package ph.com.homecredit.harold.test.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ph.com.homecredit.harold.test.BaseActivity;
import ph.com.homecredit.harold.test.MyApplication;
import ph.com.homecredit.harold.test.R;
import ph.com.homecredit.harold.test.api.Api;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.DaoSession;
import ph.com.homecredit.harold.test.models.Weather;

/**
 * Created by Harold Reyes on 1/5/2018.
 */

public class WeatherActivity extends BaseActivity {


    private City city;
    private TextView tvMain, tvDescription, tvPressure, tvHumidity, tvWindspeed, tvWindDeg, tvTemp;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(String.format("%s, %s", city.getName(), city.getCountry()));
        }

        ivIcon = findViewById(R.id.ivIcon);
        tvMain = findViewById(R.id.tvMain);
        tvDescription = findViewById(R.id.tvDescription);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWindspeed = findViewById(R.id.tvWindspeed);
        tvWindDeg = findViewById(R.id.tvWindDeg);
        tvTemp = findViewById(R.id.tvTemp);

        displayWeather(city.getWeather());
    }

    private void displayWeather(Weather weather) {

        tvMain.setText(weather.getMain());
        tvDescription.setText(weather.getDescription());
        tvPressure.setText(String.valueOf(weather.getPressure()));
        tvHumidity.setText(String.valueOf(weather.getHumidity()));
        tvWindspeed.setText(String.valueOf(weather.getWindspeed()));
        tvWindDeg.setText(String.valueOf(weather.getWindDeg()));
        tvTemp.setText(String.valueOf(weather.getTemp()));

        Glide.with(this).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(ivIcon);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        mAbtApi.getWeatherByCityId(city.getId(), new Api.NetworkCallback<City>() {
            @Override
            public void onSuccess(City response) {
                displayWeather(response.getWeather());
            }

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
                Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
