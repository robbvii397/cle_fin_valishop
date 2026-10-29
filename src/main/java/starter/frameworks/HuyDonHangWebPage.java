package starter.frameworks;

import starter.adapter.HuyDonHangController;
import starter.adapter.HuyDonHangViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * FRAMEWORKS & DRIVERS CIRCLE - Trang web Huy don hang.
 * Giao dien Bootstrap 5 don gian (CDN).
 */
public class HuyDonHangWebPage {
    private HuyDonHangController controller;
    private HuyDonHangViewModel viewModel;
    private final Map<String, String> fieldsById = new HashMap<>();

    public HuyDonHangWebPage(HuyDonHangController controller, HuyDonHangViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    public HuyDonHangViewModel getViewModel() {
        return viewModel;
    }

    // Theo so do: updateFieldByID() - cap nhat gia tri 1 o nhap lieu theo id
    public void updateFieldByID(String fieldId, String value) {
        fieldsById.put(fieldId, value);
    }

    public String getFieldByID(String fieldId) {
        return fieldsById.getOrDefault(fieldId, "");
    }

    // Theo so do: onSave() - nguoi dung bam nut Xac nhan huy tren trang
    public void onSave() {
        controller.huyDonHang(getFieldByID("orderId"));
    }

    public void onSave(String orderId) {
        updateFieldByID("orderId", orderId);
        onSave();
    }

    // Theo so do: show() - tra ve HTML cua trang (Bootstrap)
    public String show() {
        String alert = "";
        if (viewModel.getMessage() != null) {
            String loai = viewModel.getSuccess() ? "alert-success" : "alert-danger";
            alert = "<div class='alert " + loai + "' role='alert'>" + CapNhatDonHangWebPage.escape(viewModel.getMessage()) + "</div>";
        }
        return "<!DOCTYPE html><html lang='vi'><head><meta charset='UTF-8'>"
                + "<title>Hủy đơn hàng - Shop Túi Xách & Vali</title>"
                + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>"
                + "</head><body class='bg-light'><div class='container py-4'>"
                + "<h2 class='mb-3'>Hủy đơn hàng</h2>"
                + alert
                + "<form method='post' action='/huy' class='card p-3 shadow-sm'>"
                + "<div class='mb-3'><label class='form-label' for='orderId'>Mã đơn hàng</label>"
                + "<input class='form-control' id='orderId' name='orderId' value='" + CapNhatDonHangWebPage.escape(getFieldByID("orderId")) + "' required></div>"
                + "<button type='submit' class='btn btn-danger'>Xác nhận hủy đơn hàng</button> "
                + "<a href='/' class='btn btn-secondary'>Danh sách đơn hàng</a>"
                + "</form></div></body></html>";
    }
}
