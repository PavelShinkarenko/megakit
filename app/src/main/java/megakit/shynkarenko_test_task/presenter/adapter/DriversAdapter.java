package megakit.shynkarenko_test_task.presenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import megakit.shynkarenko_test_task.R;
import megakit.shynkarenko_test_task.data.model.Car;
import megakit.shynkarenko_test_task.data.model.Driver;

/**
 * Created by Shynkarenko on 01.09.2017.
 */

public class DriversAdapter extends BaseAdapter {

    private List<Driver> drivers;
    private LayoutInflater inflater;

    public DriversAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        drivers = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return drivers.size();
    }

    @Override
    public Driver getItem(int i) {
        return drivers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup root) {
        ViewHolder holder;
        if (v == null){
            v = inflater.inflate(R.layout.driver_item, root, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else holder = (ViewHolder) v.getTag();

        Driver driver = getItem(i);
        holder.driver.setText(driver.getName());
        holder.cars.setText(carsToString(driver));
        return v;
    }

    private String carsToString(Driver driver){
        StringBuilder builder = new StringBuilder();
        for (Car car : driver.getCars()) {
            builder.append(car.getModel()).append(", ");
        }
        return builder.toString();
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.driver_item)  TextView driver;
        @BindView(R.id.cars_item)  TextView cars;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
