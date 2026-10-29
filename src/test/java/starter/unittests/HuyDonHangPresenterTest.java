package starter.unittests;

import starter.adapter.HuyDonHangPresenter;
import starter.adapter.HuyDonHangViewModel;
import starter.usecase.HuyDonHangOutputData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử HuyDonHangPresenter.present: đổ dữ liệu OutputData vào ViewModel để trang web hiển thị")
public class HuyDonHangPresenterTest {

    @Test
    @DisplayName("present() với kết quả thành công thì ViewModel có success=true và đúng thông báo thành công")
    void testPresentThanhCong() {
        // Given
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangPresenter presenter = new HuyDonHangPresenter(viewModel);
        // When
        presenter.present(new HuyDonHangOutputData(true, "Hủy đơn hàng thành công"));
        // Then
        assertTrue(viewModel.getSuccess());
        assertEquals("Hủy đơn hàng thành công", viewModel.getMessage());
        assertSame(viewModel, presenter.getViewModel());
    }

    @Test
    @DisplayName("present() với kết quả thất bại thì ViewModel có success=false và đúng thông báo lỗi")
    void testPresentThatBai() {
        // Given
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangPresenter presenter = new HuyDonHangPresenter(viewModel);
        // When
        presenter.presentOutput(new HuyDonHangOutputData(false, "Đơn hàng này đã bị hủy"));
        // Then
        assertFalse(viewModel.getSuccess());
        assertEquals("Đơn hàng này đã bị hủy", viewModel.getMessage());
    }
}
