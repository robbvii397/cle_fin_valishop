package starter.adapter;

import starter.usecase.HuyDonHangInputData;
import starter.usecase.InputBoundary;

/**
 * INTERFACE ADAPTER CIRCLE - Controller cua chuc nang Huy don hang.
 * Nhan du lieu tu WebPage va chuyen vao UseCase qua InputBoundary.
 */
public class HuyDonHangController {
    private InputBoundary<HuyDonHangInputData> inputBoundary;

    public HuyDonHangController(InputBoundary<HuyDonHangInputData> inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public InputBoundary<HuyDonHangInputData> getInputBoundary() {
        return inputBoundary;
    }

    public void huyDonHang(HuyDonHangInputData inputData) {
        inputBoundary.execute(inputData);
    }

    // Ham tien ich: WebPage chi can truyen ma don hang
    public void huyDonHang(String orderId) {
        huyDonHang(new HuyDonHangInputData(orderId));
    }
}
