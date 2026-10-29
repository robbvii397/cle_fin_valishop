package starter.adapter;

/**
 * INTERFACE ADAPTER CIRCLE - ViewModel cua man hinh Cap nhat don hang.
 * Chi chua du lieu de View (WebPage) hien thi: success, message.
 */
public class CapNhatDonHangViewModel {
    private boolean success;
    private String message;

    public boolean getSuccess() { return success; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
}
