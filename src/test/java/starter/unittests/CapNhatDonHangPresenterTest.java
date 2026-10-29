package starter.unittests;

import starter.adapter.CapNhatDonHangPresenter;
import starter.adapter.CapNhatDonHangViewModel;
import starter.usecase.CapNhatDonHangOutputData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử CapNhatDonHangPresenter.present: đổ dữ liệu OutputData vào ViewModel để trang web hiển thị")
public class CapNhatDonHangPresenterTest {

    @Test
    @DisplayName("present() với kết quả thành công thì ViewModel có success=true và đúng thông báo thành công")
    void testPresentThanhCong() {
        // Given
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangPresenter presenter = new CapNhatDonHangPresenter(viewModel);
        // When
        presenter.present(new CapNhatDonHangOutputData(true, "Cập nhật thông tin đơn hàng thành công"));
        // Then
        assertTrue(viewModel.getSuccess());
        assertEquals("Cập nhật thông tin đơn hàng thành công", viewModel.getMessage());
        assertSame(viewModel, presenter.getViewModel());
    }

    @Test
    @DisplayName("present() với kết quả thất bại thì ViewModel có success=false và đúng thông báo lỗi")
    void testPresentThatBai() {
        // Given
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangPresenter presenter = new CapNhatDonHangPresenter(viewModel);
        // When
        presenter.presentOutput(new CapNhatDonHangOutputData(false, "Không tìm thấy đơn hàng"));
        // Then
        assertFalse(viewModel.getSuccess());
        assertEquals("Không tìm thấy đơn hàng", viewModel.getMessage());
    }
}
