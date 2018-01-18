package ph.com.homecredit.harold.test;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;

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

    private static final String TAG = MyApplication.class.getSimpleName();
    private DaoSession dbSession;
    private NetworkComponent networkComponent;
    private SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BuildConfig.DATABASE_NAME);// + (ENCRYPTED ? "-db-encrypted" : "-db"));
        Database db = helper.getWritableDb();//ENCRYPTED ? helper.getEncryptedWritableDb("superhardtohackpassword") : helper.getWritableDb();
        dbSession = new DaoMaster(db).newSession();
        sharedPref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        networkComponent = DaggerNetworkComponent.builder().networkModule(new NetworkModule(this)).build();
        setInitialCities();
        if (!sharedPref.getBoolean("isSeeded", false)) {
            getCitiesFromLocal(new Callback<String>() {
                @Override
                public void onResult(String obj) {
                    Log.i("getCitiesFromLocal", obj);
                    sharedPref.edit().putBoolean("isSeeded", true).apply();
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("getCitiesFromLocal", e.getMessage());
                }
            });
        }

    }

    public DaoSession getDbSession() {
        return dbSession;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    public void setInitialCities() {
        City[] cities = new City[]{
                new City(2643743, "London", "GB", true),
                new City(4548393, "Prague", "US", true),
                new City(5391959, "San Francisco", "US", true)};

        dbSession.getCityDao().insertOrReplaceInTx(cities);

    }

    @SuppressLint("StaticFieldLeak")
    private void getCitiesFromLocal(final Callback<String> callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    JSONArray jsonArray = new JSONArray(GeneralUtils.getStringFromRAW(MyApplication.this, R.raw.cities));

                    List<Long> ids = new ArrayList<>();
                    ids.add((long) 2643743);
                    ids.add((long) 4548393);
                    ids.add((long) 5391959);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jCity = jsonArray.getJSONArray(i);

                        City city = new City();
                        city.setId(jCity.getLong(0));
                        city.setName(jCity.getString(1));
                        city.setCountry(jCity.getString(2));
                        city.setPreferred(ids.contains(jCity.getLong(0)));

                        Log.i(TAG, city.toString());
                        dbSession.getCityDao().insertOrReplace(city);
                    }

                    sharedPref.edit().putBoolean("isSeeded", true).apply();

                    return "Success";
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null)
                    callback.onResult(s);
                else
                    callback.onError(new Error("Failed to load cities"));
            }
        }.execute();

    }

    public interface Callback<T> {
        void onResult(T obj);

        void onError(Throwable e);
    }
}