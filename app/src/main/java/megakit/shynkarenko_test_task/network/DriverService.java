package megakit.shynkarenko_test_task.network;

import java.util.List;
import megakit.shynkarenko_test_task.data.model.Driver;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public interface DriverService {

    @GET("drivers/list")
    Call<List<Driver>> getDrivers();

    @POST("save_driver")
    Call<Driver> saveDriver(@Body Driver driver);

    @POST("update_driver")
    Call<Driver> updateDriver(@Body Driver driver);

    @POST("remove_driver")
    Call<Driver> removeDriver(@Body Driver driver);

}
