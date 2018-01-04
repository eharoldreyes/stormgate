package ph.com.homecredit.harold.test;

import android.app.Application;

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
        loadCitiesFromLocal();
    }

    private void loadCitiesFromLocal(){
        try {
            List<City> cities = new ArrayList<>();
            JSONArray jsonArray = GeneralUtils.getJSONArrayFromRaw(this, R.raw.city_list);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jCity = jsonArray.getJSONObject(i);
                City city = new City();
                city.setId(jCity.getLong("id"));
                city.setName(jCity.getString("name"));
                cities.add(city);
            }
            dbSession.getCityDao().insertOrReplaceInTx(cities);
        } catch (Exception e) {

        }
    }

    public DaoSession getDbSession() {
        return dbSession;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }


}