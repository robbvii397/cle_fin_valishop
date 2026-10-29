package starter.unittests;

import starter.adapter.HuyDonHangViewModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử HuyDonHangViewModel: giữ cờ thành công và thông báo để trang web hiển thị")
public class HuyDonHangViewModelTest {

    @Test
    @DisplayName("Set success=false và message xong thì các hàm get đọc lại đúng giá trị")
    void testGetSet() {
        // Given
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        // When
        viewModel.setSuccess(false);
        viewModel.setMessage("Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển");
        // Then
        assertFalse(viewModel.getSuccess());
        assertFalse(viewModel.isSuccess());
        assertEquals("Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển", viewModel.getMessage());
    }
}
