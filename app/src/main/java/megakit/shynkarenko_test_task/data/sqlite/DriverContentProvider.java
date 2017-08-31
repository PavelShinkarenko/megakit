package megakit.shynkarenko_test_task.data.sqlite;

import android.support.annotation.NonNull;

import megakit.shynkarenko_test_task.data.tables.DriverTable;
import megakit.shynkarenko_test_task.data.tables.RequestTable;
import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.SQLiteSchema;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class DriverContentProvider extends SQLiteContentProvider {

    private static final String DATABASE_NAME = "driver_cars.db";
    private static final String CONTENT_AUTHORITY = "megakit.shynkarenko";

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);
    }

    @Override
    protected void prepareSchema(@NonNull SQLiteSchema schema) {
        schema.register(DriverTable.TABLE);
        schema.register(RequestTable.TABLE);
    }
}
