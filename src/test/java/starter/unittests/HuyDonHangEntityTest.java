package starter.unittests;

import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.entity.HuyDonHangEntity;
import starter.usecase.HuyDonHangInputData;
import starter.usecase.HuyDonHangOutputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;






import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử nghiệp vụ HỦY đơn hàng (HuyDonHangEntity.execute) theo các Rule trong cancel_order.feature")
public class HuyDonHangEntityTest {

    private InMemoryDonHangRepository repo;
    private HuyDonHangEntity huyEntity;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDonHangRepository();
        huyEntity = new HuyDonHangEntity(repo);
    }

    private void themDon(String orderId, String status) {
        repo.themDonHang(new DonHangEntity(orderId, "KH001", status, "12 Lê Lợi"));
    }

    @Test
    @DisplayName("Trường hợp 1: Đơn đang chờ xử lý thì hủy thành công và trạng thái chuyển sang 'Đã hủy'")
    void testHuyThanhCongKhiDangChoXuLy() {
        // Given
        themDon("ORD001", DonHangEntity.DANG_CHO_XU_LY);
        // When
        HuyDonHangOutputData output = huyEntity.execute(new HuyDonHangInputData("ORD001"));
        // Then
        assertTrue(output.isSuccess());
        assertEquals("Hủy đơn hàng thành công", output.getMessage());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD001").getStatus());
    }

    @Test
    @DisplayName("Trường hợp 2: Đơn đã giao hàng thì không hủy được và trạng thái được giữ nguyên")
    void testKhongHuyKhiDaGiaoHang() {
        // Given
        themDon("ORD002", DonHangEntity.DA_GIAO_HANG);
        // When
        HuyDonHangOutputData output = huyEntity.execute(new HuyDonHangInputData("ORD002"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển", output.getMessage());
        assertEquals("Đã giao hàng", repo.layDonHangTheoId("ORD002").getStatus());
    }

    @Test
    @DisplayName("Trường hợp 3: Đơn đã hủy rồi thì không hủy lại lần nữa")
    void testKhongHuyLaiDonDaHuy() {
        // Given
        themDon("ORD003", DonHangEntity.DA_HUY);
        // When
        HuyDonHangOutputData output = huyEntity.execute(new HuyDonHangInputData("ORD003"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Đơn hàng này đã bị hủy", output.getMessage());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD003").getStatus());
    }

    @Test
    @DisplayName("Trường hợp 4: Đơn đang trên đường vận chuyển thì không hủy được")
    void testKhongHuyKhiDaVanChuyen() {
        // Given
        themDon("ORD004", DonHangEntity.DA_VAN_CHUYEN);
        // When
        HuyDonHangOutputData output = huyEntity.execute(new HuyDonHangInputData("ORD004"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển", output.getMessage());
        assertEquals("Đã vận chuyển", repo.layDonHangTheoId("ORD004").getStatus());
    }

    @Test
    @DisplayName("Trường hợp 5: Mã đơn không tồn tại thì báo 'Không tìm thấy đơn hàng'")
    void testKhongHuyKhiKhongTimThay() {
        // Given: kho don hang rong, khong co don ORD404
        // When
        HuyDonHangOutputData output = huyEntity.execute(new HuyDonHangInputData("ORD404"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Không tìm thấy đơn hàng", output.getMessage());
    }
}
