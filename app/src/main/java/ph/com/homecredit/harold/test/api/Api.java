package ph.com.homecredit.harold.test.api;

import android.app.Application;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import ph.com.homecredit.harold.test.BuildConfig;
import ph.com.homecredit.harold.test.MyApplication;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.DaoSession;
import ph.com.homecredit.harold.test.models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Edgar Harold Reyes on 8/8/2017.
 * Flat Planet Pty Ltd
 * edgar.reyes@flatplanet.com.au
 */

public class Api {

    public static final String TAG = Api.class.getSimpleName();

    private static volatile Api sSoleInstance;
    private final Client apiClient;
    private final DaoSession dbSession;

    public interface NetworkCallback<T> {
        void onSuccess(T response);

        void onError(Throwable error);
    }

    public static Api getInstance(Application app, Client abtClient) {
        if (sSoleInstance == null) {
            synchronized (Api.class) {
                if (sSoleInstance == null)
                    sSoleInstance = new Api(app, abtClient);
            }
        }

        return sSoleInstance;
    }

    private Api(Application app, Client abtClient) {
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        } else {
            this.apiClient = abtClient;
            this.dbSession = ((MyApplication) app).getDbSession();
        }
    }

    public void getWeatherByCityIds(List<Long> ids, final NetworkCallback<List<City>> callback){
        String stringIds = TextUtils.join(",",ids.toArray());
        apiClient.getWeatherByCityIds(stringIds, "metric", BuildConfig.APP_ID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject j = new JSONObject(response.body().string());
                    JSONArray jCityList = j.getJSONArray("list");
                    List<City> cities = new ArrayList<>();
                    for (int i = 0; i < jCityList.length(); i++) {
                        City city = parseCityResponse(jCityList.getJSONObject(i));
                        cities.add(city);
                    }
                    if(callback != null)
                        callback.onSuccess(cities);
                } catch (Exception e) {
                    onFailure(call, e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (callback != null)
                    callback.onError(t);
            }
        });
    }


    public void getWeatherByCityId(long id, final NetworkCallback<City> callback) {

        apiClient.getWeatherByCityId(id, BuildConfig.APP_ID)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject j = new JSONObject(response.body().string());
                            City city = parseCityResponse(j);
                            if (callback != null)
                                callback.onSuccess(city);

                        } catch (Exception e) {
                            onFailure(call, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        if (callback != null)
                            callback.onError(t);
                    }
                });
    }

    private City parseCityResponse(JSONObject j) throws Exception{

        City city = dbSession.getCityDao().load(j.getLong("id"));
        city.setName(j.getString("name"));
        city.setPreferred(true);

        //city = dbSession.getCityDao().load(dbSession.getCityDao().insertOrReplace(city));

        Weather weather = new Weather();

        JSONArray jWeathers = j.getJSONArray("weather");
        if(jWeathers.length() > 0) {
            JSONObject jWeather = jWeathers.getJSONObject(0);
            weather.setMain(jWeather.getString("main"));
            weather.setDescription(jWeather.getString("description"));
            weather.setIcon(jWeather.getString("icon"));
        }

        try {
            JSONObject jWind = j.getJSONObject("wind");
            weather.setWindDeg(jWind.getDouble("deg"));
            weather.setWindspeed(jWind.getDouble("speed"));
        } catch (Exception e) {}

        try {
            JSONObject jCoord = j.getJSONObject("coord");
            city.setLat(jCoord.getDouble("lat"));
            city.setLng(jCoord.getDouble("lng"));
        } catch (Exception e) {}


        weather.setDate(j.getLong("dt"));

        JSONObject jMain = j.getJSONObject("main");
        weather.setTemp(jMain.getDouble("temp"));
        weather.setPressure(jMain.getDouble("pressure"));
        weather.setHumidity(jMain.getDouble("humidity"));

        weather = dbSession.getWeatherDao().load(dbSession.getWeatherDao().insert(weather));

        city.setWeather(weather);
        city.update();
        return city;
    }

}
