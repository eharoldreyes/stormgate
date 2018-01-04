package ph.com.homecredit.harold.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ph.com.homecredit.harold.test.api.Api;
import ph.com.homecredit.harold.test.api.Client;

public class BaseActivity extends AppCompatActivity {

    @Inject
    public Api mAbtApi;
    @Inject
    public Client mAbtClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApplication) getApplication()).getNetworkComponent().inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void addFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(currentFragment != null)
            ft.hide(currentFragment);
        ft.add(R.id.container, fragment, tag);
        if (addToBackStack)
            ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, tag);
        if (addToBackStack) ft.addToBackStack(tag);
        ft.commit();
    }

}
