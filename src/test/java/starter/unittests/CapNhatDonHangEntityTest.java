package starter.unittests;

import starter.database.InMemoryDonHangRepository;
import starter.entity.CapNhatDonHangEntity;
import starter.entity.DonHangEntity;
import starter.usecase.CapNhatDonHangInputData;
import starter.usecase.CapNhatDonHangOutputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;






import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử nghiệp vụ CẬP NHẬT thông tin đơn hàng (CapNhatDonHangEntity.execute) theo các Rule trong update_order_information.feature")
public class CapNhatDonHangEntityTest {

    private InMemoryDonHangRepository repo;
    private CapNhatDonHangEntity capNhatEntity;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDonHangRepository();
        capNhatEntity = new CapNhatDonHangEntity(repo);
    }

    private void themDon(String orderId, String userId, String status) {
        repo.themDonHang(new DonHangEntity(orderId, userId, status, "12 Lê Lợi"));
    }

    @Test
    @DisplayName("Trường hợp 1: Đơn đang chờ xử lý của chính khách hàng thì cập nhật địa chỉ thành công")
    void testCapNhatThanhCongKhiDangChoXuLy() {
        // Given
        themDon("ORD101", "KH001", DonHangEntity.DANG_CHO_XU_LY);
        // When
        CapNhatDonHangOutputData output = capNhatEntity.execute(
                new CapNhatDonHangInputData("ORD101", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertTrue(output.isSuccess());
        assertEquals("Cập nhật thông tin đơn hàng thành công", output.getMessage());
        assertEquals("25 Nguyễn Huệ", repo.layDonHangTheoId("ORD101").getShippingAddress());
    }

    @Test
    @DisplayName("Trường hợp 2: Đơn đã vận chuyển thì không cập nhật được và địa chỉ cũ được giữ nguyên")
    void testKhongCapNhatKhiDaVanChuyen() {
        // Given
        themDon("ORD102", "KH001", DonHangEntity.DA_VAN_CHUYEN);
        // When
        CapNhatDonHangOutputData output = capNhatEntity.execute(
                new CapNhatDonHangInputData("ORD102", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển",
                output.getMessage());
        assertEquals("12 Lê Lợi", repo.layDonHangTheoId("ORD102").getShippingAddress());
    }

    @Test
    @DisplayName("Trường hợp 3: Khách hàng cập nhật đơn của khách hàng khác thì bị từ chối")
    void testKhongCapNhatDonCuaKhachKhac() {
        // Given
        themDon("ORD103", "KH002", DonHangEntity.DANG_CHO_XU_LY);
        // When
        CapNhatDonHangOutputData output = capNhatEntity.execute(
                new CapNhatDonHangInputData("ORD103", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Bạn không được phép cập nhật đơn hàng này", output.getMessage());
        assertEquals("12 Lê Lợi", repo.layDonHangTheoId("ORD103").getShippingAddress());
    }

    @Test
    @DisplayName("Trường hợp 4: Mã đơn không tồn tại thì báo 'Không tìm thấy đơn hàng'")
    void testKhongCapNhatKhiKhongTimThay() {
        // Given: kho don hang rong, khong co don ORD404
        // When
        CapNhatDonHangOutputData output = capNhatEntity.execute(
                new CapNhatDonHangInputData("ORD404", "KH001", "25 Nguyễn Huệ"));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Không tìm thấy đơn hàng", output.getMessage());
    }

    @Test
    @DisplayName("Trường hợp 5: Địa chỉ giao hàng mới để trống thì bị từ chối và địa chỉ cũ được giữ nguyên")
    void testKhongCapNhatKhiDiaChiMoiTrong() {
        // Given
        themDon("ORD105", "KH001", DonHangEntity.DANG_CHO_XU_LY);
        // When
        CapNhatDonHangOutputData output = capNhatEntity.execute(
                new CapNhatDonHangInputData("ORD105", "KH001", ""));
        // Then
        assertFalse(output.isSuccess());
        assertEquals("Địa chỉ giao hàng không được để trống", output.getMessage());
        assertEquals("12 Lê Lợi", repo.layDonHangTheoId("ORD105").getShippingAddress());
    }
}
