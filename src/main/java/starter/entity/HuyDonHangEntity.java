package starter.entity;

import starter.database.DonHangRepository;
import starter.usecase.HuyDonHangInputData;
import starter.usecase.HuyDonHangOutputData;

/**
 * ENTITY CIRCLE - Nghiep vu cot loi (business rule) cua chuc nang
 * HUY DON HANG. Doc lap voi web, database va framework.
 *
 * Cac Rule lay tu file cancel_order.feature:
 *  Rule 1: Don dang cho xu ly thi huy duoc -> trang thai chuyen sang "Da huy".
 *  Rule 2: Don da van chuyen / da giao thi khong huy duoc.
 *  Rule 3: Don da huy roi thi khong huy lai lan nua.
 *  Rule bo sung: Khong tim thay don hang thi bao loi.
 */
public class HuyDonHangEntity {
    public static final String MSG_THANH_CONG = "Hủy đơn hàng thành công";
    public static final String MSG_DA_VAN_CHUYEN = "Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển";
    public static final String MSG_DA_HUY = "Đơn hàng này đã bị hủy";
    public static final String MSG_KHONG_TIM_THAY = "Không tìm thấy đơn hàng";

    private DonHangRepository repo;

    public HuyDonHangEntity(DonHangRepository repo) {
        this.repo = repo;
    }

    public HuyDonHangOutputData execute(HuyDonHangInputData input) {
        // Buoc 1: tim don hang
        DonHangEntity donHang = repo.layDonHangTheoId(input.getOrderId());
        if (donHang == null) {
            return new HuyDonHangOutputData(false, MSG_KHONG_TIM_THAY);
        }
        // Buoc 2 (Rule 3): don da huy roi -> khong huy lai
        if (DonHangEntity.DA_HUY.equals(donHang.getStatus())) {
            return new HuyDonHangOutputData(false, MSG_DA_HUY);
        }
        // Buoc 3 (Rule 2): don da van chuyen / da giao -> khong huy duoc
        if (DonHangEntity.DA_VAN_CHUYEN.equals(donHang.getStatus())
                || DonHangEntity.DA_GIAO_HANG.equals(donHang.getStatus())) {
            return new HuyDonHangOutputData(false, MSG_DA_VAN_CHUYEN);
        }
        // Buoc 4 (Rule 1): don dang cho xu ly -> chuyen sang "Da huy" va luu lai
        donHang.setStatus(DonHangEntity.DA_HUY);
        repo.suaDonHang(donHang);
        return new HuyDonHangOutputData(true, MSG_THANH_CONG);
    }
}
