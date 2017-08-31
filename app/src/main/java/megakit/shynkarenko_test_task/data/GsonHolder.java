package megakit.shynkarenko_test_task.data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class GsonHolder {

    private static final Gson GSON = new Gson();

    private GsonHolder() {
    }

    @NonNull
    public static Gson getGson() {
        return GSON;
    }

}
