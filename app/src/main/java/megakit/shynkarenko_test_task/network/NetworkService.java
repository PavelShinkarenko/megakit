package megakit.shynkarenko_test_task.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.List;
import megakit.shynkarenko_test_task.data.GsonHolder;
import megakit.shynkarenko_test_task.data.model.Driver;
import megakit.shynkarenko_test_task.data.tables.DriverTable;
import megakit.shynkarenko_test_task.data.tables.RequestTable;
import megakit.shynkarenko_test_task.network.model.Request;
import megakit.shynkarenko_test_task.network.model.RequestStatus;
import megakit.shynkarenko_test_task.network.model.RequestType;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;


/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class NetworkService extends IntentService {

    private static final String REQUEST_KEY = "request";
    private static final String DRIVER_KEY = "driver";

    public static void start(@NonNull Context context, @NonNull RequestType requestType, Driver driver) {
        Intent intent = new Intent(context, NetworkService.class);
        intent.putExtra(REQUEST_KEY, GsonHolder.getGson().toJson(requestType));
        intent.putExtra(DRIVER_KEY, GsonHolder.getGson().toJson(driver));
        context.startService(intent);
    }

    @SuppressWarnings("unused")
    public NetworkService() {
        super(NetworkService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RequestType requestType = GsonHolder.getGson().fromJson(intent.getStringExtra(REQUEST_KEY), RequestType.class);
        //get current request from DB.
        Request savedRequest = SQLite.get().querySingle(RequestTable.TABLE,
                Where.create().equalTo(RequestTable.TYPE, requestType));

        //If request exist and it in process - nothing to do
        if (savedRequest != null && savedRequest.getStatus() == RequestStatus.IN_PROGRESS) {
            return;
        } else if (savedRequest != null) {//update status of saved request
            savedRequest.setStatus(RequestStatus.IN_PROGRESS);
            SQLite.get().update(RequestTable.TABLE, Where.create().equalTo(RequestTable.TYPE,
                    savedRequest.getType()), savedRequest);
        } else {//If current request does'n exist - save it
            savedRequest = new Request(requestType, RequestStatus.IN_PROGRESS, "");
            SQLite.get().insert(RequestTable.TABLE, savedRequest);
        }

        //notify subscribers
        SQLite.get().notifyTableChanged(RequestTable.TABLE);

        //get driver from intent
        Driver driver = null;
        if (!savedRequest.getType().equals(RequestType.GET_ALL)) {
            driver = GsonHolder.getGson().fromJson(intent.getStringExtra(DRIVER_KEY), Driver.class);
        }
        switch (savedRequest.getType()){
            case GET_ALL:
                executeGetAllDriversRequest(savedRequest);
                break;
            case SAVE:
                executeSaveDriverRequest(driver, savedRequest);
                break;
            case REMOVE:
                executeRemoveDriverRequest(driver, savedRequest);
                break;
            case UPDATE:
                executeUpdateDriverRequest(driver, savedRequest);
                break;
        }
    }

    private void executeGetAllDriversRequest(@NonNull Request request) {
        try { //load all drivers request
            List<Driver> drivers = ApiFactory.getDriverService()
                    .getDrivers()
                    .execute()
                    .body();
            //clear table and save new data
            SQLite.get().delete(DriverTable.TABLE);
            if (drivers != null) SQLite.get().insert(DriverTable.TABLE, drivers);
            request.setStatus(RequestStatus.SUCCESS);
        } catch (IOException e) {
            request.setStatus(RequestStatus.ERROR);
            request.setError(e.getMessage());
        }
        //update REQUEST table
        finally {
            SQLite.get().update(RequestTable.TABLE, Where.create().equalTo(RequestTable.TYPE,
                    request.getType()), request);
            SQLite.get().notifyTableChanged(RequestTable.TABLE);
        }
    }

    private void executeSaveDriverRequest(@NonNull Driver driver, @NonNull Request request) {
        try { //save new driver request
            SQLite.get().insert(DriverTable.TABLE, driver);
            ApiFactory.getDriverService().saveDriver(driver).execute();
            request.setStatus(RequestStatus.SUCCESS);
        } catch (IOException e) {
            request.setStatus(RequestStatus.ERROR);
            request.setError(e.getMessage());
        }
    }

    private void executeRemoveDriverRequest(@NonNull Driver driver, @NonNull Request request) {
        try { //remove driver request
            SQLite.get().delete(DriverTable.TABLE, Where.create().equalTo(DriverTable.DRIVER_NAME, driver.getName()));
            ApiFactory.getDriverService().removeDriver(driver).execute();
            request.setStatus(RequestStatus.SUCCESS);
        } catch (IOException e) {
            request.setStatus(RequestStatus.ERROR);
            request.setError(e.getMessage());
        }
    }

    private void executeUpdateDriverRequest(@NonNull Driver driver, @NonNull Request request) {
        try { //update new driver
            SQLite.get().update(DriverTable.TABLE, Where.create().equalTo(DriverTable.DRIVER_NAME, driver.getName()), driver);
            ApiFactory.getDriverService().updateDriver(driver).execute();
            request.setStatus(RequestStatus.SUCCESS);
        } catch (IOException e) {
            request.setStatus(RequestStatus.ERROR);
            request.setError(e.getMessage());
        }
    }
}
