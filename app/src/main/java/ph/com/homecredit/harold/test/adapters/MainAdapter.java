package ph.com.homecredit.harold.test.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ph.com.homecredit.harold.test.R;
import ph.com.homecredit.harold.test.models.City;
import ph.com.homecredit.harold.test.models.Weather;

/**
 * Created by Harold Reyes on 12/4/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private final Context context;
    private final List<City> cities;

    public MainAdapter(Context context, List<City> cities) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.tvCity = view.findViewById(R.id.city);
            this.tvWeather = view.findViewById(R.id.weather);
            this.tvTemperature = view.findViewById(R.id.temperature);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        public void load(final City city) {
            tvCity.setText(String.format("%s, %s", city.getName(), city.getCountry()));
            Weather weather = city.getWeather();
            if(weather != null){
                tvWeather.setText(String.format("Weather: %s", weather.getDescription()));
                tvTemperature.setText(String.format("Temp: %s Celsius", weather.getTemp()));
            }
        }
    }



}
