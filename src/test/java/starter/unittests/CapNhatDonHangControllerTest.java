package starter.unittests;

import starter.adapter.CapNhatDonHangController;
import starter.adapter.CapNhatDonHangPresenter;
import starter.adapter.CapNhatDonHangViewModel;
import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.usecase.CapNhatDonHangInputData;
import starter.usecase.CapNhatDonHangUseCaseControl;
import starter.usecase.InputBoundary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;








import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử CapNhatDonHangController.capNhatDonHang: chuyển yêu cầu từ tầng giao diện vào UseCase qua InputBoundary")
public class CapNhatDonHangControllerTest {

    private CapNhatDonHangController taoController(InMemoryDonHangRepository repo,
                                                   CapNhatDonHangViewModel viewModel) {
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(
                repo, new CapNhatDonHangPresenter(viewModel));
        return new CapNhatDonHangController(control);
    }

    @Test
    @DisplayName("Gọi capNhatDonHang(InputData) với đơn đang chờ xử lý thì ViewModel nhận kết quả thành công")
    void testCapNhatBangInputData() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD101", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangController controller = taoController(repo, viewModel);
        // When
        controller.capNhatDonHang(new CapNhatDonHangInputData("ORD101", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertTrue(viewModel.getSuccess());
        assertEquals("25 Nguyễn Huệ", repo.layDonHangTheoId("ORD101").getShippingAddress());
        assertNotNull(controller.getInputBoundary());
    }

    @Test
    @DisplayName("Gọi hàm tiện ích capNhatDonHang(orderId, userId, địa chỉ mới) với đơn của khách khác thì bị từ chối")
    void testCapNhatBang3ThamSo() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD103", "KH002", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangController controller = taoController(repo, viewModel);
        // When
        controller.capNhatDonHang("ORD103", "KH001", "25 Nguyễn Huệ");
        // Then
        assertFalse(viewModel.getSuccess());
        assertEquals("Bạn không được phép cập nhật đơn hàng này", viewModel.getMessage());
    }
}
