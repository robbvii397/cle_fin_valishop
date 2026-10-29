package starter.unittests;

import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.frameworks.HuyDonHangWebPage;
import starter.frameworks.Main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử HuyDonHangWebPage: trang Bootstrap hủy đơn hàng, gồm show() / updateFieldByID() / onSave()")
public class HuyDonHangWebPageTest {

    private InMemoryDonHangRepository repo;
    private HuyDonHangWebPage page;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDonHangRepository();
        page = Main.taoHuyWebPage(repo);
    }

    @Test
    @DisplayName("Hàm show() sinh HTML có nạp Bootstrap, có ô nhập mã đơn và nút hủy màu đỏ")
    void testShowCoBootstrapVaForm() {
        // Given / When
        String html = page.show();
        // Then
        assertTrue(html.contains("bootstrap@5.3.3"));
        assertTrue(html.contains("id='orderId'"));
        assertTrue(html.contains("btn-danger"));
        assertTrue(html.contains("Hủy đơn hàng"));
    }

    @Test
    @DisplayName("Hàm updateFieldByID() đặt giá trị cho ô mã đơn và show() hiển thị lại đúng giá trị đó")
    void testUpdateFieldByID() {
        // Given / When
        page.updateFieldByID("orderId", "ORD001");
        // Then
        assertEquals("ORD001", page.getFieldByID("orderId"));
        assertTrue(page.show().contains("value='ORD001'"));
    }

    @Test
    @DisplayName("Hàm onSave() với đơn đang chờ xử lý thì hủy thành công, trạng thái chuyển sang 'Đã hủy' và trang hiện alert-success")
    void testOnSaveThanhCong() {
        // Given
        repo.themDonHang(new DonHangEntity("ORD001", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        // When
        page.onSave("ORD001");
        // Then
        assertTrue(page.getViewModel().getSuccess());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD001").getStatus());
        assertTrue(page.show().contains("alert-success"));
    }

    @Test
    @DisplayName("Hàm onSave() với đơn đã hủy rồi thì thất bại, trạng thái giữ nguyên và trang hiện alert-danger kèm thông báo 'Đơn hàng này đã bị hủy'")
    void testOnSaveThatBai() {
        // Given
        repo.themDonHang(new DonHangEntity("ORD003", "KH001", DonHangEntity.DA_HUY, "12 Lê Lợi"));
        // When
        page.onSave("ORD003");
        // Then
        assertFalse(page.getViewModel().getSuccess());
        assertEquals("Đã hủy", repo.layDonHangTheoId("ORD003").getStatus());
        String html = page.show();
        assertTrue(html.contains("alert-danger"));
        assertTrue(html.contains("Đơn hàng này đã bị hủy"));
    }
}
