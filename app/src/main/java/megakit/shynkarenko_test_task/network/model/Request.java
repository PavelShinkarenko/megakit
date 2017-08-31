package megakit.shynkarenko_test_task.network.model;

import android.support.annotation.NonNull;

/**
 * Created by Shynkarenko on 31.08.2017.
 */

public class Request {

    private final String idRequest;

    private RequestStatus status;

    private String error;

    public Request(@NonNull String request) {
        idRequest = request;
    }

    public Request(@NonNull String request,
                   @NonNull RequestStatus status, @NonNull String error) {
        idRequest = request;
        this.status = status;
        this.error = error;
    }

    public String getIdRequest() {
        return idRequest;
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
