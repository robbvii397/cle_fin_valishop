package starter.unittests;

import starter.usecase.CapNhatDonHangOutputData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử lớp dữ liệu đầu ra CapNhatDonHangOutputData: chứa cờ thành công và thông báo kết quả")
public class CapNhatDonHangOutputDataTest {

    @Test
    @DisplayName("Hàm tạo truyền success=true và message thì các hàm get đọc lại đúng")
    void testHamTaoVaGet() {
        // Given / When
        CapNhatDonHangOutputData output = new CapNhatDonHangOutputData(true, "Cập nhật thông tin đơn hàng thành công");
        // Then
        assertTrue(output.isSuccess());
        assertTrue(output.getSuccess());
        assertEquals("Cập nhật thông tin đơn hàng thành công", output.getMessage());
    }

    @Test
    @DisplayName("Các hàm set đổi được giá trị success và message sau khi tạo")
    void testSet() {
        // Given
        CapNhatDonHangOutputData output = new CapNhatDonHangOutputData(true, "cu");
        // When
        output.setSuccess(false);
        output.setMessage("Không tìm thấy đơn hàng");
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Không tìm thấy đơn hàng", output.getMessage());
    }
}
