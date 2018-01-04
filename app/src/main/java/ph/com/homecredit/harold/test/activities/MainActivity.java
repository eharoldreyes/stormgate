package ph.com.homecredit.harold.test.activities;

import android.os.Bundle;

import ph.com.homecredit.harold.test.BaseActivity;
import ph.com.homecredit.harold.test.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new MainFragment(), MainFragment.TAG, false);

    }


}
