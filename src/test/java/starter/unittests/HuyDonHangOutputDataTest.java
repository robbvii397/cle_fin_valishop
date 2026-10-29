package starter.unittests;

import starter.usecase.HuyDonHangOutputData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử lớp dữ liệu đầu ra HuyDonHangOutputData: chứa cờ thành công và thông báo kết quả")
public class HuyDonHangOutputDataTest {

    @Test
    @DisplayName("Hàm tạo truyền success=true và message thì các hàm get đọc lại đúng")
    void testHamTaoVaGet() {
        // Given / When
        HuyDonHangOutputData output = new HuyDonHangOutputData(true, "Hủy đơn hàng thành công");
        // Then
        assertTrue(output.isSuccess());
        assertTrue(output.getSuccess());
        assertEquals("Hủy đơn hàng thành công", output.getMessage());
    }

    @Test
    @DisplayName("Các hàm set đổi được giá trị success và message sau khi tạo")
    void testSet() {
        // Given
        HuyDonHangOutputData output = new HuyDonHangOutputData(true, "cu");
        // When
        output.setSuccess(false);
        output.setMessage("Đơn hàng này đã bị hủy");
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Đơn hàng này đã bị hủy", output.getMessage());
    }
}
