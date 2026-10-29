package starter.unittests;

import starter.adapter.CapNhatDonHangViewModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử CapNhatDonHangViewModel: giữ cờ thành công và thông báo để trang web hiển thị")
public class CapNhatDonHangViewModelTest {

    @Test
    @DisplayName("Set success=true và message xong thì các hàm get đọc lại đúng giá trị")
    void testGetSet() {
        // Given
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        // When
        viewModel.setSuccess(true);
        viewModel.setMessage("Cập nhật thông tin đơn hàng thành công");
        // Then
        assertTrue(viewModel.getSuccess());
        assertTrue(viewModel.isSuccess());
        assertEquals("Cập nhật thông tin đơn hàng thành công", viewModel.getMessage());
    }
}
