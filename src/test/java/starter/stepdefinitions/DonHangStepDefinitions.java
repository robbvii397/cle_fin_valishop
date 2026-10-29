package starter.stepdefinitions;

import starter.adapter.CapNhatDonHangController;
import starter.adapter.CapNhatDonHangPresenter;
import starter.adapter.CapNhatDonHangViewModel;
import starter.adapter.HuyDonHangController;
import starter.adapter.HuyDonHangPresenter;
import starter.adapter.HuyDonHangViewModel;
import starter.database.InMemoryDonHangRepository;
import starter.entity.CapNhatDonHangEntity;
import starter.entity.DonHangEntity;
import starter.entity.HuyDonHangEntity;
import starter.usecase.CapNhatDonHangInputData;
import starter.usecase.CapNhatDonHangUseCaseControl;
import starter.usecase.HuyDonHangInputData;
import starter.usecase.HuyDonHangUseCaseControl;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;














/**
 * Step Definitions cho 2 feature:
 *  - update_order_information.feature (Cap nhat thong tin don hang)
 *  - cancel_order.feature (Huy don hang)
 * Cach lam giong Tich2SoStepDefinitions cua thay: noi day day du cac tang
 * Controller -> UseCaseControl -> Entity -> Repository (InMemory),
 * ket qua di qua Presenter -> ViewModel de kiem tra thong bao.
 * Moi Rule trong feature co 3 Scenario (tong 15 Scenario / feature).
 */
public class DonHangStepDefinitions {

    // Khach hang dang dang nhap / dang thuc hien thao tac
    private static final String KHACH_HIEN_TAI = "KH001";
    private static final String KHACH_KHAC = "KH999";

    private InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
    private String currentOrderId;
    private String hanhDongVuaThucHien; // "CAP_NHAT" hoac "HUY"

    private CapNhatDonHangViewModel capNhatViewModel;
    private HuyDonHangViewModel huyViewModel;

    // ---------- Cac buoc Given: tao du lieu don hang ----------

    private void taoDonHang(String orderId, String ownerId, String status) {
        this.currentOrderId = orderId;
        repo.themDonHang(new DonHangEntity(orderId, ownerId, status, "12 Lê Lợi"));
    }

    @Given("Đơn hàng {string} thuộc về khách hàng và có trạng thái {string}")
    public void donHangThuocVeKhachHang(String orderId, String status) {
        taoDonHang(orderId, KHACH_HIEN_TAI, status);
    }

    @Given("Đơn hàng {string} thuộc về khách hàng và có trạng thái {string}.")
    public void donHangThuocVeKhachHangCoDauCham(String orderId, String status) {
        taoDonHang(orderId, KHACH_HIEN_TAI, status);
    }

    @Given("Đơn hàng {string} của khách hàng có trạng thái {string}")
    public void donHangCuaKhachHang(String orderId, String status) {
        taoDonHang(orderId, KHACH_HIEN_TAI, status);
    }

    @Given("Đơn hàng {string} thuộc về một khách hàng khác có trạng thái {string}")
    public void donHangThuocVeKhachHangKhac(String orderId, String status) {
        taoDonHang(orderId, KHACH_KHAC, status);
    }

    @Given("Không tồn tại đơn hàng {string}")
    public void khongTonTaiDonHang(String orderId) {
        this.currentOrderId = orderId;
        assertNull(repo.layDonHangTheoId(orderId));
    }

    @Given("Không tồn tại đơn hàng {string}.")
    public void khongTonTaiDonHangCoDauCham(String orderId) {
        khongTonTaiDonHang(orderId);
    }

    @And("Địa chỉ giao hàng là {string}")
    public void diaChiGiaoHangLa(String address) {
        DonHangEntity donHang = repo.layDonHangTheoId(currentOrderId);
        donHang.setShippingAddress(address);
        repo.suaDonHang(donHang);
    }

    // ---------- Cac buoc When: thuc hien hanh dong ----------

    private void thucHienCapNhat(String orderId, String diaChiMoi) {
        hanhDongVuaThucHien = "CAP_NHAT";
        capNhatViewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangPresenter presenter = new CapNhatDonHangPresenter(capNhatViewModel);
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(repo, presenter);
        CapNhatDonHangController controller = new CapNhatDonHangController(control);
        controller.capNhatDonHang(new CapNhatDonHangInputData(orderId, KHACH_HIEN_TAI, diaChiMoi));
    }

    private void thucHienHuy(String orderId) {
        hanhDongVuaThucHien = "HUY";
        huyViewModel = new HuyDonHangViewModel();
        HuyDonHangPresenter presenter = new HuyDonHangPresenter(huyViewModel);
        HuyDonHangUseCaseControl control = new HuyDonHangUseCaseControl(repo, presenter);
        HuyDonHangController controller = new HuyDonHangController(control);
        controller.huyDonHang(new HuyDonHangInputData(orderId));
    }

    @When("Khách hàng thay đổi địa chỉ giao hàng thành {string}")
    public void khachHangThayDoiDiaChi(String diaChiMoi) {
        thucHienCapNhat(currentOrderId, diaChiMoi);
    }

    @When("Khách hàng thay đổi địa chỉ giao hàng của đơn hàng {string} thành {string}")
    public void khachHangThayDoiDiaChiCuaDon(String orderId, String diaChiMoi) {
        this.currentOrderId = orderId;
        thucHienCapNhat(orderId, diaChiMoi);
    }

    @When("Khách hàng yêu cầu hủy đơn hàng {string}")
    public void khachHangYeuCauHuy(String orderId) {
        this.currentOrderId = orderId;
        thucHienHuy(orderId);
    }

    // ---------- Cac buoc Then: kiem tra ket qua ----------

    private String thongBaoHienTai() {
        if ("HUY".equals(hanhDongVuaThucHien)) {
            return huyViewModel.getMessage();
        }
        return capNhatViewModel.getMessage();
    }

    private void kiemTraDiaChi(String orderId, String expected) {
        assertEquals(expected, repo.layDonHangTheoId(orderId).getShippingAddress());
    }

    private void kiemTraTrangThai(String orderId, String expected) {
        assertEquals(expected, repo.layDonHangTheoId(orderId).getStatus());
    }

    @Then("Địa chỉ giao hàng của đơn hàng phải là {string}")
    public void diaChiPhaiLa(String expected) {
        kiemTraDiaChi(currentOrderId, expected);
    }

    @Then("Địa chỉ giao hàng của đơn hàng vẫn phải là {string}")
    public void diaChiVanPhaiLa(String expected) {
        kiemTraDiaChi(currentOrderId, expected);
    }

    @Then("Địa chỉ giao hàng của đơn hàng {string} vẫn phải là {string}")
    public void diaChiCuaDonVanPhaiLa(String orderId, String expected) {
        kiemTraDiaChi(orderId, expected);
    }

    @Then("Trạng thái đơn hàng sẽ chuyển sang {string}")
    public void trangThaiSeChuyenSang(String expected) {
        kiemTraTrangThai(currentOrderId, expected);
    }

    @Then("Trạng thái đơn hàng sẽ chuyển sang {string}.")
    public void trangThaiSeChuyenSangCoDauCham(String expected) {
        kiemTraTrangThai(currentOrderId, expected);
    }

    @Then("Trạng thái đơn hàng phải là {string}")
    public void trangThaiPhaiLa(String expected) {
        kiemTraTrangThai(currentOrderId, expected);
    }

    @Then("Trạng thái đơn hàng phải là {string}.")
    public void trangThaiPhaiLaCoDauCham(String expected) {
        kiemTraTrangThai(currentOrderId, expected);
    }

    @Then("Trạng thái đơn hàng nên được giữ nguyên là {string}")
    public void trangThaiGiuNguyenLa(String expected) {
        kiemTraTrangThai(currentOrderId, expected);
    }

    @Then("Trạng thái đơn hàng nên được giữ nguyên là {string}.")
    public void trangThaiGiuNguyenLaCoDauCham(String expected) {
        kiemTraTrangThai(currentOrderId, expected);
    }

    @Then("Trạng thái đơn hàng {string} phải là {string}")
    public void trangThaiCuaDonPhaiLa(String orderId, String expected) {
        kiemTraTrangThai(orderId, expected);
    }

    @And("Thông báo xác nhận cập nhật phải được hiển thị")
    public void thongBaoXacNhanCapNhat() {
        assertTrue(capNhatViewModel.getSuccess());
        assertEquals(CapNhatDonHangEntity.MSG_THANH_CONG, capNhatViewModel.getMessage());
    }

    @And("Thông báo xác nhận hủy bỏ sẽ được hiển thị")
    public void thongBaoXacNhanHuy() {
        assertTrue(huyViewModel.getSuccess());
        assertEquals(HuyDonHangEntity.MSG_THANH_CONG, huyViewModel.getMessage());
    }

    @And("Thông báo xác nhận hủy bỏ sẽ được hiển thị.")
    public void thongBaoXacNhanHuyCoDauCham() {
        thongBaoXacNhanHuy();
    }

    @Then("Thông báo {string} phải được hiển thị")
    public void thongBaoPhaiDuocHienThi(String expected) {
        assertEquals(expected, thongBaoHienTai());
    }

    @Then("Thông báo {string} sẽ được hiển thị")
    public void thongBaoSeDuocHienThi(String expected) {
        assertEquals(expected, thongBaoHienTai());
    }

    @Then("Thông báo {string} sẽ được hiển thị.")
    public void thongBaoSeDuocHienThiCoDauCham(String expected) {
        assertEquals(expected, thongBaoHienTai());
    }
}
