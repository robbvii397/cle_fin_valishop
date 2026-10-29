package starter.unittests;

import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử InMemoryDonHangRepository: kho đơn hàng trên RAM dùng cho test, gồm thêm / sửa / xóa / lấy danh sách / lấy theo mã")
public class InMemoryDonHangRepositoryTest {

    private InMemoryDonHangRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDonHangRepository();
    }

    private DonHangEntity donMau(String orderId) {
        return new DonHangEntity(orderId, "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi");
    }

    @Test
    @DisplayName("Hàm themDonHang thêm 1 đơn thì layDonHangTheoId tìm thấy đơn đó")
    void testThemVaLayTheoId() {
        // Given / When
        repo.themDonHang(donMau("ORD001"));
        // Then
        DonHangEntity timThay = repo.layDonHangTheoId("ORD001");
        assertNotNull(timThay);
        assertEquals("12 Lê Lợi", timThay.getShippingAddress());
        assertEquals(1, repo.getDanhSachDonHang().size());
    }

    @Test
    @DisplayName("Hàm suaDonHang cập nhật lại địa chỉ của đơn đã có trong kho")
    void testSuaDonHang() {
        // Given
        repo.themDonHang(donMau("ORD001"));
        DonHangEntity don = repo.layDonHangTheoId("ORD001");
        don.setShippingAddress("25 Nguyễn Huệ");
        // When
        repo.suaDonHang(don);
        // Then
        assertEquals("25 Nguyễn Huệ", repo.layDonHangTheoId("ORD001").getShippingAddress());
    }

    @Test
    @DisplayName("Hàm xoaDonHang xóa đơn khỏi kho thì tìm lại không thấy nữa")
    void testXoaDonHang() {
        // Given
        repo.themDonHang(donMau("ORD001"));
        // When
        repo.xoaDonHang("ORD001");
        // Then
        assertNull(repo.layDonHangTheoId("ORD001"));
        assertEquals(0, repo.layDanhSachDonHang().size());
    }

    @Test
    @DisplayName("Hàm layDanhSachDonHang trả về đủ số đơn đã thêm vào kho")
    void testLayDanhSach() {
        // Given
        repo.themDonHang(donMau("ORD001"));
        repo.themDonHang(donMau("ORD002"));
        repo.themDonHang(donMau("ORD003"));
        // When / Then
        assertEquals(3, repo.layDanhSachDonHang().size());
    }

    @Test
    @DisplayName("Hàm layDonHangTheoId với mã không tồn tại thì trả về null")
    void testLayTheoIdKhongTimThay() {
        // Given: kho rong
        // When / Then
        assertNull(repo.layDonHangTheoId("ORD404"));
    }
}
