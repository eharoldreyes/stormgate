package ph.com.homecredit.harold.test.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Edgar Harold Reyes on 8/8/2017.
 * Flat Planet Pty Ltd
 * edgar.reyes@flatplanet.com.au
 */

public interface Client {

    @GET("weather")
    Call<ResponseBody> getWeatherByCityName(
            @Query("q") String cityName,
            @Query("APPID") String apiKey);

    @GET("group")
    Call<ResponseBody> getWeatherByCityIds(
            @Query("id") String ids,
            @Query("units") String units,
            @Query("appid") String apiKey);

}
