package starter.usecase;

import java.time.LocalDateTime;

/**
 * USE CASE CIRCLE - Du lieu dau vao cua chuc nang Cap nhat thong tin don hang.
 * Theo so do gom: orderId, userId, status, shippingAddress, createdAt.
 * Trong do shippingAddress la DIA CHI MOI khach hang muon doi sang.
 */
public class CapNhatDonHangInputData {
    private String orderId;
    private String userId;
    private String status;
    private String shippingAddress;
    private LocalDateTime createdAt;

    public CapNhatDonHangInputData(String orderId, String userId, String status,
                                   String shippingAddress, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.createdAt = createdAt;
    }

    // Ham tao gon: khi cap nhat chi can ma don, nguoi yeu cau va dia chi moi
    public CapNhatDonHangInputData(String orderId, String userId, String shippingAddress) {
        this(orderId, userId, null, shippingAddress, null);
    }

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getStatus() { return status; }
    public String getShippingAddress() { return shippingAddress; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
