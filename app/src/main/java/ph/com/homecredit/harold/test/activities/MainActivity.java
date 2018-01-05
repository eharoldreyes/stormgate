package ph.com.homecredit.harold.test.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import ph.com.homecredit.harold.test.BaseActivity;
import ph.com.homecredit.harold.test.MyApplication;
import ph.com.homecredit.harold.test.R;
import ph.com.homecredit.harold.test.api.Api;
import ph.com.homecredit.harold.test.fragments.MainFragment;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.CityDao;
import ph.com.homecredit.harold.test.models.DaoSession;

public class MainActivity extends BaseActivity {

    private List<OnUpdateListener> onUpdateListeners = new ArrayList<>();
    private DaoSession dbSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbSession = ((MyApplication) getApplication()).getDbSession();
        getFromWS();
        addFragment(new MainFragment(), MainFragment.TAG, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            getFromWS();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFromWS() {
        QueryBuilder<City> dbq = dbSession.getCityDao().queryBuilder();
        dbq.where(CityDao.Properties.Preferred.eq(true));

        List<Long> cityIds = new ArrayList<>();
        List<City> cx = dbq.build().list();
        for (City city : cx)
            cityIds.add(city.getId());

        mAbtApi.getWeatherByCityIds(cityIds, new Api.NetworkCallback() {
            @Override
            public void onSuccess(Object response) {
                for (OnUpdateListener onUpdateListener : onUpdateListeners)
                    if (onUpdateListener != null)
                        onUpdateListener.onUpdate();
            }

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public void addOnUpdateListener(OnUpdateListener onUpdateListener) {
        if (!onUpdateListeners.contains(onUpdateListener))
            onUpdateListeners.add(onUpdateListener);
    }

    public void removeOnUpdateListener(OnUpdateListener onUpdateListener) {
        if (onUpdateListeners.contains(onUpdateListener))
            onUpdateListeners.remove(onUpdateListener);
    }

    public interface OnUpdateListener {
        void onUpdate();
    }

}
