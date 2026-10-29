package starter.unittests;

import starter.adapter.HuyDonHangPresenter;
import starter.adapter.HuyDonHangViewModel;
import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.usecase.HuyDonHangInputData;
import starter.usecase.HuyDonHangUseCaseControl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;







import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử HuyDonHangUseCaseControl.handle: nhận InputData, chạy nghiệp vụ hủy đơn và đẩy kết quả qua Presenter vào ViewModel")
public class HuyDonHangUseCaseControlTest {

    @Test
    @DisplayName("Đơn đang chờ xử lý thì handle() đẩy kết quả thành công vào ViewModel và trạng thái trong kho chuyển sang 'Đã hủy'")
    void testHandleThanhCong() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD001", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangUseCaseControl control = new HuyDonHangUseCaseControl(
                repo, new HuyDonHangPresenter(viewModel));
        // When
        control.handle(new HuyDonHangInputData("ORD001"));
        // Then
        assertTrue(viewModel.getSuccess());
        assertEquals("Hủy đơn hàng thành công", viewModel.getMessage());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD001").getStatus());
        assertNotNull(control.getUseCase());
    }

    @Test
    @DisplayName("Đơn đã hủy rồi thì handle() đẩy kết quả thất bại vào ViewModel và trạng thái giữ nguyên")
    void testHandleThatBai() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        repo.themDonHang(new DonHangEntity("ORD003", "KH001", DonHangEntity.DA_HUY, "12 Lê Lợi"));
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangUseCaseControl control = new HuyDonHangUseCaseControl(
                repo, new HuyDonHangPresenter(viewModel));
        // When
        control.execute(new HuyDonHangInputData("ORD003"));
        // Then
        assertFalse(viewModel.getSuccess());
        assertEquals("Đơn hàng này đã bị hủy", viewModel.getMessage());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD003").getStatus());
    }
}
