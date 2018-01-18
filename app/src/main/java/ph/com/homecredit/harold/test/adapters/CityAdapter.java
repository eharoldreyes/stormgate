package ph.com.homecredit.harold.test.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ph.com.homecredit.harold.test.R;
import ph.com.homecredit.harold.test.activities.WeatherActivity;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.Weather;

/**
 * Created by Harold Reyes on 12/4/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {


    private final Context context;
    private final List<City> cities;

    public CityAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.load(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView tvCity, tvWeather, tvTemperature;
        private final ImageView ivIcon;
        private City city;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.ivIcon = view.findViewById(R.id.ivIcon);
            this.tvCity = view.findViewById(R.id.city);
            this.tvWeather = view.findViewById(R.id.weather);
            this.tvTemperature = view.findViewById(R.id.temperature);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, WeatherActivity.class);
                    i.putExtra("city", city);
                    context.startActivity(i);
                }
            });

        }

        public void load(final City city) {
            this.city = city;
            tvCity.setText(String.format("%s, %s", city.getName(), city.getCountry()));
            Weather weather = city.getWeather();
            if(weather != null){
                tvWeather.setText(String.format("Weather: %s", weather.getDescription()));
                tvTemperature.setText(String.format("Temp: %s Celsius", weather.getTemp()));
                Glide.with(context).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(ivIcon);
            }
        }
    }



}
