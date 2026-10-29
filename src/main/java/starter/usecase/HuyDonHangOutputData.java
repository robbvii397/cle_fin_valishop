package starter.usecase;

/**
 * USE CASE CIRCLE - Du lieu dau ra cua chuc nang Huy don hang.
 * Theo so do gom: success (boolean), message (String).
 */
public class HuyDonHangOutputData {
    private boolean success;
    private String message;

    public HuyDonHangOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public boolean getSuccess() { return success; }
    public String getMessage() { return message; }
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
}
