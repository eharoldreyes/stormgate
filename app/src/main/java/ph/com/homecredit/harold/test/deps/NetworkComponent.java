package ph.com.homecredit.harold.test.deps;


import javax.inject.Singleton;

import dagger.Component;
import ph.com.homecredit.harold.test.BaseActivity;

/**
 * Created by Edgar Harold Reyes on 8/8/2017.
 * Flat Planet Pty Ltd
 * edgar.reyes@flatplanet.com.au
 */

@Singleton
@Component(modules = { NetworkModule.class})
public interface NetworkComponent {
    void inject(BaseActivity activity);
}
