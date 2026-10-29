package starter.unittests;

import starter.entity.DonHangEntity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử thực thể DonHangEntity: lưu mã đơn, khách hàng, trạng thái, địa chỉ giao hàng và ngày tạo")
public class DonHangEntityTest {

    @Test
    @DisplayName("Tạo đơn hàng xong thì đọc lại đúng các thuộc tính đã truyền vào")
    void testTaoDonHangVaDocThuocTinh() {
        // Given
        LocalDateTime ngayTao = LocalDateTime.of(2026, 10, 1, 9, 0);
        // When
        DonHangEntity donHang = new DonHangEntity("ORD101", "KH001",
                DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi", ngayTao);
        // Then
        assertEquals("ORD101", donHang.getOrderId());
        assertEquals("KH001", donHang.getUserId());
        assertEquals("Đang chờ xử lý", donHang.getStatus());
        assertEquals("12 Lê Lợi", donHang.getShippingAddress());
        assertEquals(ngayTao, donHang.getCreatedAt());
    }

    @Test
    @DisplayName("Hàm setShippingAddress đổi được địa chỉ giao hàng của đơn")
    void testSetShippingAddress() {
        // Given
        DonHangEntity donHang = new DonHangEntity("ORD101", "KH001",
                DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi");
        // When
        donHang.setShippingAddress("25 Nguyễn Huệ");
        // Then
        assertEquals("25 Nguyễn Huệ", donHang.getShippingAddress());
    }

    @Test
    @DisplayName("Hàm setStatus đổi được trạng thái của đơn hàng")
    void testSetStatus() {
        // Given
        DonHangEntity donHang = new DonHangEntity("ORD001", "KH001",
                DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi");
        // When
        donHang.setStatus(DonHangEntity.DA_HUY);
        // Then
        assertEquals("Đã hủy", donHang.getStatus());
    }
}
