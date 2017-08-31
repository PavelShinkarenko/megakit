package megakit.shynkarenko_test_task.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import org.sqlite.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import megakit.shynkarenko_test_task.data.GsonHolder;
import megakit.shynkarenko_test_task.data.model.Car;
import megakit.shynkarenko_test_task.data.model.Driver;
import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class DriverTable extends BaseTable<Driver> {

    public static final Table<Driver> TABLE = new DriverTable();

    public static final String DRIVER_NAME = "driver_name";
    public static final String CAR = "car";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(DRIVER_NAME)
                .textColumn(CAR)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Driver driver) {
        ContentValues values = new ContentValues();
        values.put(DRIVER_NAME, driver.getName());
        values.put(CAR, GsonHolder.getGson().toJson(driver.getCars()));
        return values;
    }

    @NonNull
    @Override
    public Driver fromCursor(@NonNull Cursor cursor) {
        Driver driver = new Driver();
        driver.setName(cursor.getString(cursor.getColumnIndex(DRIVER_NAME)));
        Type carsListType = new TypeToken<ArrayList<Car>>(){}.getType();
        List<Car> cars = GsonHolder.getGson().fromJson(cursor.getString(cursor.getColumnIndex(CAR)), carsListType);
        driver.setCars(cars);
        return driver;
    }
}
