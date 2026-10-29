package starter.unittests;

import starter.usecase.HuyDonHangInputData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử lớp dữ liệu đầu vào HuyDonHangInputData: chỉ đóng gói mã đơn hàng cần hủy theo sơ đồ")
public class HuyDonHangInputDataTest {

    @Test
    @DisplayName("Tạo dữ liệu hủy đơn với mã ORD001 thì getOrderId trả về đúng ORD001")
    void testGetOrderId() {
        // Given / When
        HuyDonHangInputData input = new HuyDonHangInputData("ORD001");
        // Then
        assertEquals("ORD001", input.getOrderId());
    }
}
