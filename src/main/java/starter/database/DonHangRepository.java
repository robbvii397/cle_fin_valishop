package starter.database;

import starter.entity.DonHangEntity;

import java.util.List;

/**
 * DATABASE CIRCLE - Interface Repository (cong ra phia Database).
 * Tang Entity/UseCase chi biet interface nay, khong biet SQLite hay InMemory.
 * Ten ham theo so do: themDonHang, suaDonHang, xoaDonHang, layDanhSachDonHang.
 */
public interface DonHangRepository {
    void themDonHang(DonHangEntity donHang);

    void suaDonHang(DonHangEntity donHang);

    void xoaDonHang(String orderId);

    List<DonHangEntity> layDanhSachDonHang();

    // Bo sung so voi so do: can tim 1 don hang theo ma de thuc hien cap nhat / huy
    DonHangEntity layDonHangTheoId(String orderId);
}
