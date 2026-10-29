package starter.database;

import starter.entity.DonHangEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * DATABASE CIRCLE - Repository luu tren RAM.
 * Dung cho Unit Test / Cucumber Test giong InMemoryDBMock cua thay.
 */
public class InMemoryDonHangRepository implements DonHangRepository {
    private final List<DonHangEntity> danhSachDonHang;

    public InMemoryDonHangRepository(List<DonHangEntity> danhSachDonHang) {
        this.danhSachDonHang = danhSachDonHang;
    }

    public InMemoryDonHangRepository() {
        this(new ArrayList<>());
    }

    public List<DonHangEntity> getDanhSachDonHang() {
        return danhSachDonHang;
    }

    @Override
    public void themDonHang(DonHangEntity donHang) {
        danhSachDonHang.add(donHang);
    }

    @Override
    public void suaDonHang(DonHangEntity donHang) {
        for (int i = 0; i < danhSachDonHang.size(); i++) {
            if (danhSachDonHang.get(i).getOrderId().equals(donHang.getOrderId())) {
                danhSachDonHang.set(i, donHang);
                return;
            }
        }
    }

    @Override
    public void xoaDonHang(String orderId) {
        danhSachDonHang.removeIf(dh -> dh.getOrderId().equals(orderId));
    }

    @Override
    public List<DonHangEntity> layDanhSachDonHang() {
        return new ArrayList<>(danhSachDonHang);
    }

    @Override
    public DonHangEntity layDonHangTheoId(String orderId) {
        for (DonHangEntity dh : danhSachDonHang) {
            if (dh.getOrderId().equals(orderId)) {
                return dh;
            }
        }
        return null;
    }
}
