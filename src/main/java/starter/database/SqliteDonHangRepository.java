package starter.database;

import starter.entity.DonHangEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DATABASE CIRCLE - Repository luu xuong file SQLite (donhang.db).
 * Dung cho chuong trinh web chay that. Bang: don_hang(order_id, user_id, status, shipping_address, created_at)
 */
public class SqliteDonHangRepository implements DonHangRepository {
    static {
        // Nap tuong minh driver SQLite. Neu thieu thu vien sqlite-jdbc tren classpath
        // (khai bao sai trong pom.xml) thi bao loi ro rang ngay tai day.
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "Khong tim thay driver SQLite (org.sqlite.JDBC). " +
                    "Hay kiem tra pom.xml co dependency org.xerial:sqlite-jdbc trong muc <dependencies> chinh.");
        }
    }

    private final String jdbcUrl;

    public SqliteDonHangRepository(String duongDanFileDb) {
        this.jdbcUrl = "jdbc:sqlite:" + duongDanFileDb;
        taoBangNeuChuaCo();
    }

    private Connection ketNoi() throws SQLException {
        return DriverManager.getConnection(jdbcUrl);
    }

    public void taoBangNeuChuaCo() {
        String sql = "CREATE TABLE IF NOT EXISTS don_hang ("
                + "order_id TEXT PRIMARY KEY,"
                + "user_id TEXT NOT NULL,"
                + "status TEXT NOT NULL,"
                + "shipping_address TEXT,"
                + "created_at TEXT)";
        try (Connection c = ketNoi(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Khong tao duoc bang SQLite: " + e.getMessage(), e);
        }
    }

    @Override
    public void themDonHang(DonHangEntity donHang) {
        String sql = "INSERT OR REPLACE INTO don_hang(order_id, user_id, status, shipping_address, created_at) VALUES (?,?,?,?,?)";
        try (Connection c = ketNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, donHang.getOrderId());
            ps.setString(2, donHang.getUserId());
            ps.setString(3, donHang.getStatus());
            ps.setString(4, donHang.getShippingAddress());
            ps.setString(5, donHang.getCreatedAt() == null ? null : donHang.getCreatedAt().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Khong them duoc don hang vao SQLite: " + e.getMessage(), e);
        }
    }

    @Override
    public void suaDonHang(DonHangEntity donHang) {
        String sql = "UPDATE don_hang SET user_id=?, status=?, shipping_address=?, created_at=? WHERE order_id=?";
        try (Connection c = ketNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, donHang.getUserId());
            ps.setString(2, donHang.getStatus());
            ps.setString(3, donHang.getShippingAddress());
            ps.setString(4, donHang.getCreatedAt() == null ? null : donHang.getCreatedAt().toString());
            ps.setString(5, donHang.getOrderId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Khong sua duoc don hang trong SQLite: " + e.getMessage(), e);
        }
    }

    @Override
    public void xoaDonHang(String orderId) {
        String sql = "DELETE FROM don_hang WHERE order_id=?";
        try (Connection c = ketNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Khong xoa duoc don hang trong SQLite: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DonHangEntity> layDanhSachDonHang() {
        List<DonHangEntity> ketQua = new ArrayList<>();
        String sql = "SELECT order_id, user_id, status, shipping_address, created_at FROM don_hang ORDER BY order_id";
        try (Connection c = ketNoi(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ketQua.add(docDong(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Khong doc duoc danh sach don hang tu SQLite: " + e.getMessage(), e);
        }
        return ketQua;
    }

    @Override
    public DonHangEntity layDonHangTheoId(String orderId) {
        String sql = "SELECT order_id, user_id, status, shipping_address, created_at FROM don_hang WHERE order_id=?";
        try (Connection c = ketNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return docDong(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Khong doc duoc don hang tu SQLite: " + e.getMessage(), e);
        }
    }

    private DonHangEntity docDong(ResultSet rs) throws SQLException {
        String created = rs.getString("created_at");
        LocalDateTime createdAt = (created == null || created.isBlank()) ? LocalDateTime.now() : LocalDateTime.parse(created);
        return new DonHangEntity(rs.getString("order_id"), rs.getString("user_id"),
                rs.getString("status"), rs.getString("shipping_address"), createdAt);
    }
}
