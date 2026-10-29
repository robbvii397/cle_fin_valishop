package starter.entity;

import starter.database.DonHangRepository;
import starter.usecase.CapNhatDonHangInputData;
import starter.usecase.CapNhatDonHangOutputData;

/**
 * ENTITY CIRCLE - Nghiep vu cot loi (business rule) cua chuc nang
 * CAP NHAT THONG TIN DON HANG. Doc lap voi web, database va framework.
 *
 * Cac Rule lay tu file update_order_information.feature:
 *  Rule 1: Chi cap nhat duoc khi don dang o trang thai "Dang cho xu ly".
 *  Rule 2: Don da van chuyen / da giao thi khong duoc doi thong tin giao hang.
 *  Rule 3: Chi chu don hang moi duoc cap nhat don hang cua minh.
 *  Rule bo sung: Khong tim thay don hang / dia chi moi de trong thi bao loi.
 */
public class CapNhatDonHangEntity {
    public static final String MSG_THANH_CONG = "Cập nhật thông tin đơn hàng thành công";
    public static final String MSG_DA_VAN_CHUYEN = "Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển";
    public static final String MSG_KHONG_DUOC_PHEP = "Bạn không được phép cập nhật đơn hàng này";
    public static final String MSG_KHONG_TIM_THAY = "Không tìm thấy đơn hàng";
    public static final String MSG_DIA_CHI_TRONG = "Địa chỉ giao hàng không được để trống";
    public static final String MSG_DA_HUY = "Không thể cập nhật đơn hàng đã bị hủy";

    private DonHangRepository repo;

    public CapNhatDonHangEntity(DonHangRepository repo) {
        this.repo = repo;
    }

    public CapNhatDonHangOutputData execute(CapNhatDonHangInputData input) {
        // Buoc 1: tim don hang
        DonHangEntity donHang = repo.layDonHangTheoId(input.getOrderId());
        if (donHang == null) {
            return new CapNhatDonHangOutputData(false, MSG_KHONG_TIM_THAY);
        }
        // Buoc 2 (Rule 3): chi chu don hang moi duoc cap nhat
        if (!donHang.getUserId().equals(input.getUserId())) {
            return new CapNhatDonHangOutputData(false, MSG_KHONG_DUOC_PHEP);
        }
        // Buoc 3: dia chi moi khong duoc de trong
        if (input.getShippingAddress() == null || input.getShippingAddress().isBlank()) {
            return new CapNhatDonHangOutputData(false, MSG_DIA_CHI_TRONG);
        }
        // Buoc 4 (Rule 2): don da van chuyen / da giao thi khong duoc doi
        if (DonHangEntity.DA_VAN_CHUYEN.equals(donHang.getStatus())
                || DonHangEntity.DA_GIAO_HANG.equals(donHang.getStatus())) {
            return new CapNhatDonHangOutputData(false, MSG_DA_VAN_CHUYEN);
        }
        // Buoc 5: don da huy thi khong duoc cap nhat nua
        if (DonHangEntity.DA_HUY.equals(donHang.getStatus())) {
            return new CapNhatDonHangOutputData(false, MSG_DA_HUY);
        }
        // Buoc 6 (Rule 1): don dang cho xu ly -> cap nhat va luu lai
        donHang.setShippingAddress(input.getShippingAddress());
        repo.suaDonHang(donHang);
        return new CapNhatDonHangOutputData(true, MSG_THANH_CONG);
    }
}
