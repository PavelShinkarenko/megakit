package megakit.shynkarenko_test_task.data.model;

import java.io.Serializable;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class Car implements Serializable {

    private String model;

    public Car(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
