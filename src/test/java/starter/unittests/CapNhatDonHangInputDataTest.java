package starter.unittests;

import starter.usecase.CapNhatDonHangInputData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử lớp dữ liệu đầu vào CapNhatDonHangInputData: đóng gói mã đơn, khách hàng, trạng thái, địa chỉ mới và ngày tạo")
public class CapNhatDonHangInputDataTest {

    @Test
    @DisplayName("Hàm tạo đầy đủ 5 tham số thì các hàm get đọc lại đúng giá trị")
    void testHamTaoDayDu() {
        // Given
        LocalDateTime ngayTao = LocalDateTime.of(2026, 10, 1, 9, 0);
        // When
        CapNhatDonHangInputData input = new CapNhatDonHangInputData(
                "ORD101", "KH001", "Đang chờ xử lý", "25 Nguyễn Huệ", ngayTao);
        // Then
        assertEquals("ORD101", input.getOrderId());
        assertEquals("KH001", input.getUserId());
        assertEquals("Đang chờ xử lý", input.getStatus());
        assertEquals("25 Nguyễn Huệ", input.getShippingAddress());
        assertEquals(ngayTao, input.getCreatedAt());
    }

    @Test
    @DisplayName("Hàm tạo gọn 3 tham số (mã đơn, khách hàng, địa chỉ mới) thì trạng thái và ngày tạo để trống")
    void testHamTaoGon() {
        // Given / When
        CapNhatDonHangInputData input = new CapNhatDonHangInputData("ORD101", "KH001", "25 Nguyễn Huệ");
        // Then
        assertEquals("ORD101", input.getOrderId());
        assertEquals("KH001", input.getUserId());
        assertEquals("25 Nguyễn Huệ", input.getShippingAddress());
        assertNull(input.getStatus());
        assertNull(input.getCreatedAt());
    }
}
