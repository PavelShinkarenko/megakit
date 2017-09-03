package megakit.shynkarenko_test_task.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import megakit.shynkarenko_test_task.R;
import megakit.shynkarenko_test_task.data.model.Driver;
import megakit.shynkarenko_test_task.presenter.adapter.DriversAdapter;
import megakit.shynkarenko_test_task.data.tables.DriverTable;
import megakit.shynkarenko_test_task.data.tables.RequestTable;
import megakit.shynkarenko_test_task.network.NetworkService;
import megakit.shynkarenko_test_task.network.model.Request;
import megakit.shynkarenko_test_task.network.model.RequestStatus;
import megakit.shynkarenko_test_task.network.model.RequestType;
import ru.arturvasilov.sqlite.core.BasicTableObserver;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;

public class CarsActivity extends AppCompatActivity implements BasicTableObserver {

    @BindView(R.id.all_drivers) ListView listView;
    private DriversAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        ButterKnife.bind(this);
        adapter = new DriversAdapter(getLayoutInflater());
        //loading data from db
        List<Driver> drivers = SQLite.get().query(DriverTable.TABLE);
        adapter.setDrivers(drivers);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SQLite.get().unregisterObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLite.get().registerObserver(RequestTable.TABLE, this);
    }

    @Override
    public void onTableChanged() {
        //after changed table, load data from db
        Request request = SQLite.get().querySingle(RequestTable.TABLE,
                Where.create().equalTo(RequestTable.TYPE, RequestType.GET_ALL));

        if (request.getStatus() == RequestStatus.SUCCESS) {
            //loading data, which came from REST service
            List<Driver> drivers = SQLite.get().query(DriverTable.TABLE);
            adapter.setDrivers(drivers);
        }
        //unsubscribe from table changed
        SQLite.get().unregisterObserver(this);
    }

    @OnClick(R.id.load_from_server)
    public void loadFromServer(View btn){
        //subscribe to change of table
        SQLite.get().registerObserver(RequestTable.TABLE, this);
        //start service to load all drivers from server
        NetworkService.start(this, RequestType.GET_ALL, null);
    }
}
