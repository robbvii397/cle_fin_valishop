package starter.frameworks;

import starter.adapter.CapNhatDonHangController;
import starter.adapter.CapNhatDonHangViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * FRAMEWORKS & DRIVERS CIRCLE - Trang web Cap nhat thong tin don hang.
 * Giao dien Bootstrap 5 don gian (CDN). Trang chi hien thi + nhan form,
 * moi nghiep vu nam o tang Entity/UseCase phia trong.
 */
public class CapNhatDonHangWebPage {
    private CapNhatDonHangController controller;
    private CapNhatDonHangViewModel viewModel;
    // Gia tri cac o nhap lieu tren trang, khoa theo id cua o (giong DOM tren web)
    private final Map<String, String> fieldsById = new HashMap<>();

    public CapNhatDonHangWebPage(CapNhatDonHangController controller, CapNhatDonHangViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    public CapNhatDonHangViewModel getViewModel() {
        return viewModel;
    }

    // Theo so do: updateFieldByID() - cap nhat gia tri 1 o nhap lieu theo id
    public void updateFieldByID(String fieldId, String value) {
        fieldsById.put(fieldId, value);
    }

    public String getFieldByID(String fieldId) {
        return fieldsById.getOrDefault(fieldId, "");
    }

    // Theo so do: onSave() - nguoi dung bam nut Luu tren trang
    public void onSave() {
        controller.capNhatDonHang(getFieldByID("orderId"), getFieldByID("userId"),
                getFieldByID("shippingAddress"));
    }

    // Goi onSave kem san 3 gia tri (dung trong Main va trong Unit Test)
    public void onSave(String orderId, String userId, String diaChiMoi) {
        updateFieldByID("orderId", orderId);
        updateFieldByID("userId", userId);
        updateFieldByID("shippingAddress", diaChiMoi);
        onSave();
    }

    // Theo so do: show() - tra ve HTML cua trang (Bootstrap)
    public String show() {
        String alert = "";
        if (viewModel.getMessage() != null) {
            String loai = viewModel.getSuccess() ? "alert-success" : "alert-danger";
            alert = "<div class='alert " + loai + "' role='alert'>" + escape(viewModel.getMessage()) + "</div>";
        }
        return "<!DOCTYPE html><html lang='vi'><head><meta charset='UTF-8'>"
                + "<title>Cập nhật thông tin đơn hàng - Shop Túi Xách & Vali</title>"
                + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>"
                + "</head><body class='bg-light'><div class='container py-4'>"
                + "<h2 class='mb-3'>Cập nhật thông tin đơn hàng</h2>"
                + alert
                + "<form method='post' action='/cap-nhat' class='card p-3 shadow-sm'>"
                + "<div class='mb-3'><label class='form-label' for='orderId'>Mã đơn hàng</label>"
                + "<input class='form-control' id='orderId' name='orderId' value='" + escape(getFieldByID("orderId")) + "' required></div>"
                + "<div class='mb-3'><label class='form-label' for='userId'>Mã khách hàng</label>"
                + "<input class='form-control' id='userId' name='userId' value='" + escape(getFieldByID("userId")) + "' required></div>"
                + "<div class='mb-3'><label class='form-label' for='shippingAddress'>Địa chỉ giao hàng mới</label>"
                + "<input class='form-control' id='shippingAddress' name='shippingAddress' value='" + escape(getFieldByID("shippingAddress")) + "' required></div>"
                + "<button type='submit' class='btn btn-primary'>Lưu thay đổi</button> "
                + "<a href='/' class='btn btn-secondary'>Danh sách đơn hàng</a>"
                + "</form></div></body></html>";
    }

    public static String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
