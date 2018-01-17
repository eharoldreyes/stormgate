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
import ph.com.homecredit.harold.test.adapters.CityAdapter;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.CityDao;
import ph.com.homecredit.harold.test.models.DaoSession;

/**
 * Created by haroldreyes on 12/26/17.
 */

public class MainFragment extends Fragment implements MainActivity.OnUpdateListener{

    public static final String TAG = MainFragment.class.getSimpleName();
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

        activity.addOnUpdateListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CityAdapter(getContext(), cities));

    }

    @Override
    public void onUpdate() {
        QueryBuilder<City> dbq = dbSession.getCityDao().queryBuilder();
        dbq.where(CityDao.Properties.Preferred.eq(true));

        cities.clear();
        cities.addAll(dbq.build().list());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.removeOnUpdateListener(this);
    }
}
