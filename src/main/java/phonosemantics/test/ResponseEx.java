package phonosemantics.test;

public class ResponseEx {
    private final String status;
    private final int code;

    public ResponseEx(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }
}
