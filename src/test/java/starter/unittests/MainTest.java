package starter.unittests;

import starter.database.DonHangRepository;
import starter.database.InMemoryDonHangRepository;
import starter.database.SqliteDonHangRepository;
import starter.frameworks.Main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;





import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử Main: khởi tạo Repository SQLite, nạp dữ liệu mẫu cho shop và đọc dữ liệu form gửi lên từ trang web")
public class MainTest {

    @TempDir
    Path thuMucTam;

    @Test
    @DisplayName("Hàm seedData nạp 6 đơn hàng mẫu của shop và gọi lại lần nữa không bị trùng dữ liệu")
    void testSeedData() {
        // Given
        DonHangRepository repo = new InMemoryDonHangRepository();
        // When
        Main.seedData(repo);
        Main.seedData(repo);
        // Then
        assertEquals(6, repo.layDanhSachDonHang().size());
        assertNotNull(repo.layDonHangTheoId("ORD001"));
        assertNotNull(repo.layDonHangTheoId("ORD103"));
        assertEquals("KH002", repo.layDonHangTheoId("ORD103").getUserId());
    }

    @Test
    @DisplayName("Hàm createRepository tạo được Repository SQLite trên file mới và ghi/đọc được dữ liệu")
    void testCreateRepository() {
        // Given / When
        SqliteDonHangRepository repo = Main.createRepository(thuMucTam.resolve("app.db").toString());
        Main.seedData(repo);
        // Then
        assertEquals(6, repo.layDanhSachDonHang().size());
        assertEquals("Đã giao hàng", repo.layDonHangTheoId("ORD002").getStatus());
    }

    @Test
    @DisplayName("Hàm parseForm giải mã đúng dữ liệu form tiếng Việt được gửi lên bằng POST")
    void testParseForm() {
        // Given: chuoi form da ma hoa URL, dia chi la "25 Nguyen Hue" co dau
        String body = "orderId=ORD101&userId=KH001&shippingAddress=25+Nguy%E1%BB%85n+Hu%E1%BB%87";
        // When
        Map<String, String> form = Main.parseForm(body);
        // Then
        assertEquals("ORD101", form.get("orderId"));
        assertEquals("KH001", form.get("userId"));
        assertEquals("25 Nguyễn Huệ", form.get("shippingAddress"));
    }

    @Test
    @DisplayName("Hàm trangDanhSach sinh HTML Bootstrap liệt kê đủ các đơn hàng mẫu của shop")
    void testTrangDanhSach() {
        // Given
        DonHangRepository repo = new InMemoryDonHangRepository();
        Main.seedData(repo);
        // When
        String html = Main.trangDanhSach(repo);
        // Then
        assertTrue(html.contains("bootstrap@5.3.3"));
        assertTrue(html.contains("ORD001"));
        assertTrue(html.contains("ORD103"));
        assertTrue(html.contains("/cap-nhat"));
        assertTrue(html.contains("/huy"));
    }
}
