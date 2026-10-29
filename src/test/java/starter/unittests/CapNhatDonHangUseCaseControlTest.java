package starter.unittests;

import starter.adapter.CapNhatDonHangPresenter;
import starter.adapter.CapNhatDonHangViewModel;
import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.usecase.CapNhatDonHangInputData;
import starter.usecase.CapNhatDonHangUseCaseControl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;







import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử CapNhatDonHangUseCaseControl.handle: nhận InputData, chạy nghiệp vụ cập nhật và đẩy kết quả qua Presenter vào ViewModel")
public class CapNhatDonHangUseCaseControlTest {

    @Test
    @DisplayName("Đơn đang chờ xử lý thì handle() đẩy kết quả thành công vào ViewModel và địa chỉ trong kho được đổi")
    void testHandleThanhCong() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD101", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(
                repo, new CapNhatDonHangPresenter(viewModel));
        // When
        control.handle(new CapNhatDonHangInputData("ORD101", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertTrue(viewModel.getSuccess());
        assertEquals("Cập nhật thông tin đơn hàng thành công", viewModel.getMessage());
        assertEquals("25 Nguyễn Huệ", repo.layDonHangTheoId("ORD101").getShippingAddress());
        assertNotNull(control.getUseCase());
    }

    @Test
    @DisplayName("Đơn đã vận chuyển thì handle() đẩy kết quả thất bại vào ViewModel và địa chỉ không đổi")
    void testHandleThatBai() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD102", "KH001", DonHangEntity.DA_VAN_CHUYEN, "12 Lê Lợi"));
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(
                repo, new CapNhatDonHangPresenter(viewModel));
        // When
        control.execute(new CapNhatDonHangInputData("ORD102", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertFalse(viewModel.getSuccess());
        assertEquals("Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển",
                viewModel.getMessage());
        assertEquals("12 Lê Lợi", repo.layDonHangTheoId("ORD102").getShippingAddress());
    }
}
