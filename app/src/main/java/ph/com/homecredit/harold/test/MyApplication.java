package ph.com.homecredit.harold.test;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ph.com.homecredit.harold.test.deps.DaggerNetworkComponent;
import ph.com.homecredit.harold.test.deps.NetworkComponent;
import ph.com.homecredit.harold.test.deps.NetworkModule;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.DaoMaster;
import ph.com.homecredit.harold.test.models.DaoSession;
import ph.com.homecredit.harold.test.utils.GeneralUtils;

/**
 * Created by Harold Reyes on 12/4/2017.
 */

public class MyApplication extends Application {

    private DaoSession dbSession;
    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BuildConfig.DATABASE_NAME);// + (ENCRYPTED ? "-db-encrypted" : "-db"));
        Database db = helper.getWritableDb();//ENCRYPTED ? helper.getEncryptedWritableDb("superhardtohackpassword") : helper.getWritableDb();
        dbSession = new DaoMaster(db).newSession();
        networkComponent = DaggerNetworkComponent.builder().networkModule(new NetworkModule(this)).build();

        new LoadCitiesFromFile(this).execute();
    }

    private static class LoadCitiesFromFile extends AsyncTask<Void, Void, List<City>> {

        @SuppressLint("StaticFieldLeak")
        private final Context context;
        private final DaoSession dbSession;

        public LoadCitiesFromFile(Context context) {
            this.context = context;
            this.dbSession = ((MyApplication) context.getApplicationContext()).dbSession;
        }

        @Override
        protected List<City> doInBackground(Void... voids) {

            SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);

            if (!sharedPref.getBoolean("isSeeded", false)) {
                try {
                    List<City> cities = new ArrayList<>();
                    JSONArray jsonArray = GeneralUtils.getJSONArrayFromRaw(context, R.raw.city_list);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jCity = jsonArray.getJSONObject(i);

                        City city = new Gson().fromJson(jCity.toString(), City.class);

                        cities.add(city);
                    }
                    dbSession.getCityDao().insertOrReplaceInTx(cities);
                    sharedPref.edit().putBoolean("isSeeded", true).apply();

                    return cities;
                } catch (Exception e) {
                    e.printStackTrace();
                    return dbSession.getCityDao().loadAll();
                }
            } else {
                return dbSession.getCityDao().loadAll();
            }
        }
    }

    public DaoSession getDbSession() {
        return dbSession;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }


}


//                    City city = new City();
//                    city.setId(jCity.getLong("id"));
//                    city.setName(jCity.getString("name"));
//                    city.setCountry(jCity.getString("country"));
//                    try{
//                        city.setPreferred(jCity.getBoolean("preferred"));
//                    } catch (Exception e){}