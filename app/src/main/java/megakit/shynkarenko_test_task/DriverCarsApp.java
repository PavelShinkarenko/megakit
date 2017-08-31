package megakit.shynkarenko_test_task;

import android.app.Application;

import ru.arturvasilov.sqlite.core.SQLite;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class DriverCarsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLite.initialize(this);
    }
}
