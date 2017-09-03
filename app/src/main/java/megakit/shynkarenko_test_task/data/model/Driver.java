package megakit.shynkarenko_test_task.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class Driver implements Serializable {

    private String name;
    private List<Car> cars;

    public Driver() {
    }

    public Driver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
