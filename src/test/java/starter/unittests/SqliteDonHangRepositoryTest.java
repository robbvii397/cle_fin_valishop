package starter.unittests;

import starter.database.SqliteDonHangRepository;
import starter.entity.DonHangEntity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;



import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử SqliteDonHangRepository: kho đơn hàng lưu xuống file SQLite, gồm thêm / sửa / xóa / lấy danh sách / lấy theo mã và tính bền vững dữ liệu")
public class SqliteDonHangRepositoryTest {

    @TempDir
    Path thuMucTam;

    private SqliteDonHangRepository taoRepo() {
        return new SqliteDonHangRepository(thuMucTam.resolve("test.db").toString());
    }

    private DonHangEntity donMau(String orderId) {
        return new DonHangEntity(orderId, "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi");
    }

    @Test
    @DisplayName("Hàm themDonHang ghi 1 đơn xuống SQLite thì layDonHangTheoId đọc lại đúng")
    void testThemVaLayTheoId() {
        // Given
        SqliteDonHangRepository repo = taoRepo();
        // When
        repo.themDonHang(donMau("ORD001"));
        // Then
        DonHangEntity timThay = repo.layDonHangTheoId("ORD001");
        assertNotNull(timThay);
        assertEquals("KH001", timThay.getUserId());
        assertEquals("Đang chờ xử lý", timThay.getStatus());
        assertEquals("12 Lê Lợi", timThay.getShippingAddress());
    }

    @Test
    @DisplayName("Hàm suaDonHang sửa địa chỉ và dữ liệu vẫn còn khi mở lại file SQLite (kiểm tra tính bền vững)")
    void testSuaVaMoLaiFile() {
        // Given
        String fileDb = thuMucTam.resolve("test.db").toString();
        SqliteDonHangRepository repo = new SqliteDonHangRepository(fileDb);
        repo.themDonHang(donMau("ORD001"));
        DonHangEntity don = repo.layDonHangTheoId("ORD001");
        don.setShippingAddress("25 Nguyễn Huệ");
        // When
        repo.suaDonHang(don);
        // Then: tao repository moi tro vao cung 1 file db de chung minh du lieu da luu xuong dia
        SqliteDonHangRepository repoMoLai = new SqliteDonHangRepository(fileDb);
        assertEquals("25 Nguyễn Huệ", repoMoLai.layDonHangTheoId("ORD001").getShippingAddress());
    }

    @Test
    @DisplayName("Hàm xoaDonHang xóa đơn khỏi SQLite thì tìm lại không thấy nữa")
    void testXoaDonHang() {
        // Given
        SqliteDonHangRepository repo = taoRepo();
        repo.themDonHang(donMau("ORD001"));
        // When
        repo.xoaDonHang("ORD001");
        // Then
        assertNull(repo.layDonHangTheoId("ORD001"));
        assertEquals(0, repo.layDanhSachDonHang().size());
    }

    @Test
    @DisplayName("Hàm layDanhSachDonHang đọc về đủ số đơn đã ghi xuống SQLite")
    void testLayDanhSach() {
        // Given
        SqliteDonHangRepository repo = taoRepo();
        repo.themDonHang(donMau("ORD001"));
        repo.themDonHang(donMau("ORD002"));
        // When / Then
        assertEquals(2, repo.layDanhSachDonHang().size());
    }

    @Test
    @DisplayName("Hàm layDonHangTheoId với mã không tồn tại trong SQLite thì trả về null")
    void testLayTheoIdKhongTimThay() {
        // Given
        SqliteDonHangRepository repo = taoRepo();
        // When / Then
        assertNull(repo.layDonHangTheoId("ORD404"));
    }
}
