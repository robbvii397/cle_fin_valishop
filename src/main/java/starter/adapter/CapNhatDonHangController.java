package starter.adapter;

import starter.usecase.CapNhatDonHangInputData;
import starter.usecase.InputBoundary;

/**
 * INTERFACE ADAPTER CIRCLE - Controller cua chuc nang Cap nhat don hang.
 * Nhan du lieu tu WebPage va chuyen vao UseCase qua InputBoundary.
 * Tuong duong Tich2SoController trong project mau cua thay.
 */
public class CapNhatDonHangController {
    private InputBoundary<CapNhatDonHangInputData> inputBoundary;

    public CapNhatDonHangController(InputBoundary<CapNhatDonHangInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public InputBoundary<CapNhatDonHangInputData> getInputBoundary() {
        return inputBoundary;
    }

    public void capNhatDonHang(CapNhatDonHangInputData inputData) {
        inputBoundary.execute(inputData);
    }

    // Ham tien ich: WebPage chi can truyen 3 gia tri tho
    public void capNhatDonHang(String orderId, String userId, String diaChiMoi) {
        capNhatDonHang(new CapNhatDonHangInputData(orderId, userId, diaChiMoi));
    }
}
