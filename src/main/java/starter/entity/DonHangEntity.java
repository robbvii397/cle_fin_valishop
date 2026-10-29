package starter.entity;

import java.time.LocalDateTime;

/**
 * ENTITY CIRCLE - Thuc the DonHang (theo so do Clean Architecture).
 * Chi chua du lieu va cac ham truy cap don gian, khong phu thuoc framework.
 */
public class DonHangEntity {
    // Cac trang thai don hang dung trong 2 feature
    public static final String DANG_CHO_XU_LY = "Đang chờ xử lý";
    public static final String DA_VAN_CHUYEN = "Đã vận chuyển";
    public static final String DA_GIAO_HANG = "Đã giao hàng";
    public static final String DA_HUY = "Đã hủy";

    private String orderId;
    private String userId;
    private String status;
    private String shippingAddress;
    private LocalDateTime createdAt;

    public DonHangEntity(String orderId, String userId, String status,
                         String shippingAddress, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.createdAt = createdAt;
    }

    public DonHangEntity(String orderId, String userId, String status, String shippingAddress) {
        this(orderId, userId, status, shippingAddress, LocalDateTime.now());
    }

    public String getOrderId() {
        return orderId;
    }

    // Bo sung so voi so do: can userId de kiem tra "chi chu don hang moi duoc cap nhat"
    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    // Bo sung so voi so do: can createdAt de luu SQLite va hien thi
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setShippingAddress(String address) {
        this.shippingAddress = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
