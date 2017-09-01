package megakit.shynkarenko_test_task.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.sqlite.database.sqlite.SQLiteDatabase;

import megakit.shynkarenko_test_task.network.model.Request;
import megakit.shynkarenko_test_task.network.model.RequestStatus;
import megakit.shynkarenko_test_task.network.model.RequestType;
import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class RequestTable extends BaseTable<Request> {

    public static final Table<Request> TABLE = new RequestTable();

    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final String ERROR = "error";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(TYPE)
                .textColumn(STATUS)
                .textColumn(ERROR)
                .primaryKey(TYPE)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Request request) {
        ContentValues values = new ContentValues();
        values.put(TYPE, request.getType().name());
        values.put(STATUS, request.getStatus().name());
        values.put(ERROR, request.getError());
        return values;
    }

    @NonNull
    @Override
    public Request fromCursor(@NonNull Cursor cursor) {
        RequestType type = RequestType.valueOf(cursor.getString(cursor.getColumnIndex(TYPE)));
        RequestStatus status = RequestStatus.valueOf(cursor.getString(cursor.getColumnIndex(STATUS)));
        String error = cursor.getString(cursor.getColumnIndex(ERROR));
        return new Request(type, status, error);
    }
}
