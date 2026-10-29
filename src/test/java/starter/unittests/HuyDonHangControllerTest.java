package starter.unittests;

import starter.adapter.HuyDonHangController;
import starter.adapter.HuyDonHangPresenter;
import starter.adapter.HuyDonHangViewModel;
import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.usecase.HuyDonHangInputData;
import starter.usecase.HuyDonHangUseCaseControl;
import starter.usecase.InputBoundary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;








import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử HuyDonHangController.huyDonHang: chuyển yêu cầu từ tầng giao diện vào UseCase qua InputBoundary")
public class HuyDonHangControllerTest {

    private HuyDonHangController taoController(InMemoryDonHangRepository repo,
                                               HuyDonHangViewModel viewModel) {
        HuyDonHangUseCaseControl control = new HuyDonHangUseCaseControl(
                repo, new HuyDonHangPresenter(viewModel));
        return new HuyDonHangController(control);
    }

    @Test
    @DisplayName("Gọi huyDonHang(InputData) với đơn đang chờ xử lý thì ViewModel nhận kết quả thành công và đơn chuyển sang 'Đã hủy'")
    void testHuyBangInputData() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD001", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangController controller = taoController(repo, viewModel);
        // When
        controller.huyDonHang(new HuyDonHangInputData("ORD001"));
        // Then
        assertTrue(viewModel.getSuccess());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD001").getStatus());
        assertNotNull(controller.getInputBoundary());
    }

    @Test
    @DisplayName("Gọi hàm tiện ích huyDonHang(orderId) với đơn đã giao thì bị từ chối và trạng thái giữ nguyên")
    void testHuyBangMaDon() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD002", "KH001", DonHangEntity.DA_GIAO_HANG, "12 Lê Lợi"));
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangController controller = taoController(repo, viewModel);
        // When
        controller.huyDonHang("ORD002");
        // Then
        assertFalse(viewModel.getSuccess());
        assertEquals("Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển", viewModel.getMessage());
        assertEquals("Đã giao hàng", repo.layDonHangTheoId("ORD002").getStatus());
    }
}
