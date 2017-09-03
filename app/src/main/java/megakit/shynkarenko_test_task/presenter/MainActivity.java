package megakit.shynkarenko_test_task.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import megakit.shynkarenko_test_task.R;
import megakit.shynkarenko_test_task.data.model.Car;
import megakit.shynkarenko_test_task.data.model.Driver;
import megakit.shynkarenko_test_task.network.NetworkService;
import megakit.shynkarenko_test_task.network.model.RequestType;

public class MainActivity extends AppCompatActivity  {

    @BindView(R.id.cars_container) LinearLayout carCont;
    @BindView(R.id.driver_name) EditText driverNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.add_car_btn)
    public void addEditText(View btn){
        carCont.addView(new EditText(this));
    }

    @OnClick(R.id.add_driver_btn)
    public void addDriver(View btn){
        String name = driverNameView.getText().toString();
        if (!name.isEmpty()){
            //create cars from view
            List<Car> cars = new ArrayList<>();
            for (int i = 0; i < carCont.getChildCount(); i++) {
                EditText editText = (EditText) carCont.getChildAt(i);
                String carModel = editText.getText().toString();
                    if (!carModel.isEmpty()) cars.add(new Car(carModel));
            }
            if (!cars.isEmpty()){
                Driver driver = new Driver(name);
                driver.setCars(cars);
                //start service to save driver in DB and post request to server
                startNetworkService(RequestType.SAVE, driver);
                clearField();
            }else{
                Toast toast = Toast.makeText(this, R.string.empty_car_field, Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(this, R.string.empty_driver_field, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @OnClick(R.id.show_drivers_btn)
    public void showAllDrivers(View btn){
        Intent myIntent = new Intent(this, CarsActivity.class);
        startActivity(myIntent);
    }


    private void startNetworkService(RequestType requestType, Driver driver){
        NetworkService.start(this, requestType, driver);
    }

    private void clearField(){
        driverNameView.setText("");
        for (int i = 0; i < carCont.getChildCount(); i++) {
            ((EditText) carCont.getChildAt(i)).setText("");
        }
    }

}
