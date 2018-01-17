package ph.com.homecredit.harold.test.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by haroldreyes on 12/26/17.
 */

@Entity
public class City implements Serializable {

    private static final long serialVersionUID = 7532845780240406444L;

    @Id
    @SerializedName("id")
    long id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("preferred")
    private boolean preferred;

    @SerializedName("weatherId")
    private long weatherId;

    @ToOne(joinProperty = "weatherId")
    private Weather Weather;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 448079911)
    private transient CityDao myDao;

    @Generated(hash = 10981549)
    private transient Long Weather__resolvedKey;

    @Generated(hash = 1587281753)
    public City(long id, String name, String country, boolean preferred, long weatherId) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.preferred = preferred;
        this.weatherId = weatherId;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(long weatherId) {
        this.weatherId = weatherId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1048684298)
    public Weather getWeather() {
        long __key = this.weatherId;
        if (Weather__resolvedKey == null || !Weather__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WeatherDao targetDao = daoSession.getWeatherDao();
            Weather WeatherNew = targetDao.load(__key);
            synchronized (this) {
                Weather = WeatherNew;
                Weather__resolvedKey = __key;
            }
        }
        return Weather;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 812134721)
    public void setWeather(@NotNull Weather Weather) {
        if (Weather == null) {
            throw new DaoException(
                    "To-one property 'weatherId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.Weather = Weather;
            weatherId = Weather.getId();
            Weather__resolvedKey = weatherId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean getPreferred() {
        return this.preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    @Keep
    public JSONObject toJSON() throws JSONException {
        return new JSONObject(new Gson().toJson(this));
    }

    @Override
    public String toString() {
        try {
            return toJSON().toString();
        } catch (JSONException e) {
            return super.toString();
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 293508440)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCityDao() : null;
    }

}
