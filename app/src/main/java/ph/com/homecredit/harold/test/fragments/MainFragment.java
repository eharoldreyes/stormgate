package ph.com.homecredit.harold.test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import ph.com.homecredit.harold.test.MyApplication;
import ph.com.homecredit.harold.test.R;
import ph.com.homecredit.harold.test.activities.MainActivity;
import ph.com.homecredit.harold.test.adapters.MainAdapter;
import ph.com.homecredit.harold.test.api.Api;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.CityDao.Properties;
import ph.com.homecredit.harold.test.models.DaoSession;

/**
 * Created by haroldreyes on 12/26/17.
 */

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();
    public static final String[] CITIES = {"London", "Prague", "San Francisco"};
    private RecyclerView recyclerView;
    private DaoSession dbSession;
    private List<City> cities = new ArrayList<>();
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) getActivity();
        dbSession = ((MyApplication) activity.getApplication()).getDbSession();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MainAdapter(getContext(), cities));

        refresh();
        displayCitiesFromDB();
    }

    private void refresh(){
        final int[] completed = {0};
        for (String city : CITIES) {
            activity.mAbtApi.getWeatherByCity(city, new Api.NetworkCallback() {
                @Override
                public void onSuccess(Object response) {
                    completed[0] += 1;
                    if(completed[0] == CITIES.length) {
                        displayCitiesFromDB();
                    }
                }

                @Override
                public void onError(Throwable error) {
                    completed[0] += 1;
                    if(completed[0] == CITIES.length) {
                        displayCitiesFromDB();
                    }
                }
            });
        }
    }

    private void displayCitiesFromDB(){
        QueryBuilder<City> dbq = dbSession.getCityDao().queryBuilder();
        for (String city : CITIES) {
            dbq.where(Properties.Name.like("%" + city + "%"));
        }
        cities.clear();
        cities.addAll(dbSession.getCityDao().loadAll());
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
