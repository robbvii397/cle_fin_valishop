package starter.unittests;

import starter.database.InMemoryDonHangRepository;
import starter.entity.DonHangEntity;
import starter.frameworks.CapNhatDonHangWebPage;
import starter.frameworks.Main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử CapNhatDonHangWebPage: trang Bootstrap cập nhật đơn hàng, gồm show() / updateFieldByID() / onSave()")
public class CapNhatDonHangWebPageTest {

    private InMemoryDonHangRepository repo;
    private CapNhatDonHangWebPage page;

    @BeforeEach
    void setUp() {
        repo = new InMemoryDonHangRepository();
        page = Main.taoCapNhatWebPage(repo);
    }

    @Test
    @DisplayName("Hàm show() sinh HTML có nạp Bootstrap và đủ 3 ô nhập mã đơn, mã khách hàng, địa chỉ mới")
    void testShowCoBootstrapVaForm() {
        // Given / When
        String html = page.show();
        // Then
        assertTrue(html.contains("bootstrap@5.3.3"));
        assertTrue(html.contains("id='orderId'"));
        assertTrue(html.contains("id='userId'"));
        assertTrue(html.contains("id='shippingAddress'"));
        assertTrue(html.contains("Cập nhật thông tin đơn hàng"));
    }

    @Test
    @DisplayName("Hàm updateFieldByID() đặt giá trị cho ô nhập theo id và show() hiển thị lại đúng giá trị đó")
    void testUpdateFieldByID() {
        // Given / When
        page.updateFieldByID("orderId", "ORD101");
        // Then
        assertEquals("ORD101", page.getFieldByID("orderId"));
        assertTrue(page.show().contains("value='ORD101'"));
    }

    @Test
    @DisplayName("Hàm onSave() với đơn đang chờ xử lý thì cập nhật thành công, ViewModel báo success và trang hiện alert-success")
    void testOnSaveThanhCong() {
        // Given
        repo.themDonHang(new DonHangEntity("ORD101", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi"));
        // When
        page.onSave("ORD101", "KH001", "25 Nguyễn Huệ");
        // Then
        assertTrue(page.getViewModel().getSuccess());
        assertEquals("25 Nguyễn Huệ", repo.layDonHangTheoId("ORD101").getShippingAddress());
        assertTrue(page.show().contains("alert-success"));
    }

    @Test
    @DisplayName("Hàm onSave() với đơn đã vận chuyển thì thất bại, địa chỉ không đổi và trang hiện alert-danger kèm thông báo lỗi")
    void testOnSaveThatBai() {
        // Given
        repo.themDonHang(new DonHangEntity("ORD102", "KH001", DonHangEntity.DA_VAN_CHUYEN, "12 Lê Lợi"));
        // When
        page.onSave("ORD102", "KH001", "25 Nguyễn Huệ");
        // Then
        assertFalse(page.getViewModel().getSuccess());
        assertEquals("12 Lê Lợi", repo.layDonHangTheoId("ORD102").getShippingAddress());
        String html = page.show();
        assertTrue(html.contains("alert-danger"));
        assertTrue(html.contains("Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển"));
    }
}
