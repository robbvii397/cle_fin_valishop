package starter.usecase;

/**
 * USE CASE CIRCLE - Du lieu dau vao cua chuc nang Huy don hang.
 * Theo so do chi gom: orderId.
 * Luu y: nguoi dung dang dang nhap tren web chinh la chu don hang
 * (cac don tren trang web deu la don cua khach hang dang xem).
 */
public class HuyDonHangInputData {
    private String orderId;

    public HuyDonHangInputData(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() { return orderId; }
}
