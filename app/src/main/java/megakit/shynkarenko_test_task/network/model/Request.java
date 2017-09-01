package megakit.shynkarenko_test_task.network.model;

import android.support.annotation.NonNull;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class Request {

    private final RequestType type;
    private RequestStatus status;
    private String error;

    public Request(@NonNull RequestType type,
                   @NonNull RequestStatus status, @NonNull String error) {
        this.type = type;
        this.status = status;
        this.error = error;
    }

    public RequestType getType() {
        return type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
