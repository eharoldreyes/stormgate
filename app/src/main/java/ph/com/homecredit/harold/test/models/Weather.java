package ph.com.homecredit.harold.test.models;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import ph.com.homecredit.harold.test.utils.GeneralUtils;

/**
 * Created by haroldreyes on 12/26/17.
 */

@Entity
public class Weather implements Serializable {

    private static final long serialVersionUID = 7532845780240406777L;

    @Id
    @SerializedName("id")
    private long id = GeneralUtils.GenerateId();

    @SerializedName("main")
    private String main;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    @SerializedName("dt")
    private long date;

    @SerializedName("temp")
    private double temp;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("windspeed")
    private double windspeed;
    @SerializedName("windDeg")
    private double windDeg;
    @Generated(hash = 1244058922)
    public Weather(long id, String main, String description, String icon, long date,
            double temp, double pressure, double humidity, double windspeed,
            double windDeg) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.date = date;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windspeed = windspeed;
        this.windDeg = windDeg;
    }
    @Generated(hash = 556711069)
    public Weather() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMain() {
        return this.main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public long getDate() {
        return this.date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public double getTemp() {
        return this.temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getPressure() {
        return this.pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getHumidity() {
        return this.humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    public double getWindspeed() {
        return this.windspeed;
    }
    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }
    public double getWindDeg() {
        return this.windDeg;
    }
    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

//    "dt": 1514281800,
//    "temp": 277.13,
//    "pressure": 999,
//    "humidity": 75,
//    "temp_min": 276.15,
//    "temp_max": 278.15
//    "windspeed":6.7,
//    "deg":240

}
