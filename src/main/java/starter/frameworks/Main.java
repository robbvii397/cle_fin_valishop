package starter.frameworks;

import starter.adapter.CapNhatDonHangController;
import starter.adapter.CapNhatDonHangPresenter;
import starter.adapter.CapNhatDonHangViewModel;
import starter.adapter.HuyDonHangController;
import starter.adapter.HuyDonHangPresenter;
import starter.adapter.HuyDonHangViewModel;
import starter.database.DonHangRepository;
import starter.database.SqliteDonHangRepository;
import starter.entity.DonHangEntity;
import starter.usecase.CapNhatDonHangUseCaseControl;
import starter.usecase.HuyDonHangUseCaseControl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * FRAMEWORKS & DRIVERS CIRCLE - Diem khoi chay chuong trinh (theo so do: Main).
 * Nhiem vu: khoi tao SQLite Repository, noi day (wiring) cac tang Clean Architecture,
 * chay web server don gian (JDK HttpServer) phuc vu 2 trang Bootstrap:
 *   GET  /           -> danh sach don hang mau cua shop tui xach & vali
 *   GET  /cap-nhat   -> trang cap nhat thong tin don hang
 *   POST /cap-nhat   -> xu ly cap nhat
 *   GET  /huy        -> trang huy don hang
 *   POST /huy        -> xu ly huy
 * Chay bang lenh:  mvn compile exec:java     roi mo http://localhost:8080
 */
public class Main {

    // Tao Repository SQLite cho 1 duong dan file db
    public static SqliteDonHangRepository createRepository(String duongDanFileDb) {
        return new SqliteDonHangRepository(duongDanFileDb);
    }

    // Nap du lieu mau cua shop (chi nap khi bang dang trong)
    public static void seedData(DonHangRepository repo) {
        if (!repo.layDanhSachDonHang().isEmpty()) {
            return;
        }
        LocalDateTime ngay = LocalDateTime.of(2026, 10, 1, 9, 0);
        repo.themDonHang(new DonHangEntity("ORD001", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi", ngay));
        repo.themDonHang(new DonHangEntity("ORD002", "KH001", DonHangEntity.DA_GIAO_HANG, "45 Trần Hưng Đạo", ngay));
        repo.themDonHang(new DonHangEntity("ORD003", "KH001", DonHangEntity.DA_HUY, "78 Hai Bà Trưng", ngay));
        repo.themDonHang(new DonHangEntity("ORD101", "KH001", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi", ngay));
        repo.themDonHang(new DonHangEntity("ORD102", "KH001", DonHangEntity.DA_VAN_CHUYEN, "12 Lê Lợi", ngay));
        repo.themDonHang(new DonHangEntity("ORD103", "KH002", DonHangEntity.DANG_CHO_XU_LY, "12 Lê Lợi", ngay));
    }

    // Noi day cho chuc nang Cap nhat: WebPage -> Controller -> UseCaseControl -> Entity -> Repository
    //                                                              UseCaseControl -> Presenter -> ViewModel -> WebPage
    public static CapNhatDonHangWebPage taoCapNhatWebPage(DonHangRepository repo) {
        CapNhatDonHangViewModel viewModel = new CapNhatDonHangViewModel();
        CapNhatDonHangPresenter presenter = new CapNhatDonHangPresenter(viewModel);
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(repo, presenter);
        CapNhatDonHangController controller = new CapNhatDonHangController(control);
        return new CapNhatDonHangWebPage(controller, viewModel);
    }

    // Noi day cho chuc nang Huy don hang
    public static HuyDonHangWebPage taoHuyWebPage(DonHangRepository repo) {
        HuyDonHangViewModel viewModel = new HuyDonHangViewModel();
        HuyDonHangPresenter presenter = new HuyDonHangPresenter(viewModel);
        HuyDonHangUseCaseControl control = new HuyDonHangUseCaseControl(repo, presenter);
        HuyDonHangController controller = new HuyDonHangController(control);
        return new HuyDonHangWebPage(controller, viewModel);
    }

    // Doc body cua form POST (application/x-www-form-urlencoded) thanh Map
    public static Map<String, String> parseForm(String body) {
        Map<String, String> form = new HashMap<>();
        if (body == null || body.isBlank()) {
            return form;
        }
        for (String pair : body.split("&")) {
            String[] kv = pair.split("=", 2);
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String value = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            form.put(key, value);
        }
        return form;
    }

    // Trang chu: bang danh sach don hang (Bootstrap)
    public static String trangDanhSach(DonHangRepository repo) {
        StringBuilder rows = new StringBuilder();
        for (DonHangEntity dh : repo.layDanhSachDonHang()) {
            rows.append("<tr><td>").append(CapNhatDonHangWebPage.escape(dh.getOrderId())).append("</td><td>")
                    .append(CapNhatDonHangWebPage.escape(dh.getUserId())).append("</td><td>")
                    .append(CapNhatDonHangWebPage.escape(dh.getStatus())).append("</td><td>")
                    .append(CapNhatDonHangWebPage.escape(dh.getShippingAddress())).append("</td><td>")
                    .append("<a class='btn btn-sm btn-primary' href='/cap-nhat?orderId=").append(dh.getOrderId())
                    .append("&userId=").append(dh.getUserId()).append("'>Cập nhật</a> ")
                    .append("<a class='btn btn-sm btn-danger' href='/huy?orderId=").append(dh.getOrderId())
                    .append("'>Hủy đơn</a></td></tr>");
        }
        return "<!DOCTYPE html><html lang='vi'><head><meta charset='UTF-8'>"
                + "<title>Shop Túi Xách & Vali - Đơn hàng của tôi</title>"
                + "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>"
                + "</head><body class='bg-light'><div class='container py-4'>"
                + "<h2 class='mb-3'>Shop Túi Xách &amp; Vali — Đơn hàng của tôi</h2>"
                + "<table class='table table-striped table-bordered bg-white'><thead><tr>"
                + "<th>Mã đơn</th><th>Khách hàng</th><th>Trạng thái</th><th>Địa chỉ giao hàng</th><th>Thao tác</th>"
                + "</tr></thead><tbody>" + rows + "</tbody></table>"
                + "</div></body></html>";
    }

    public static void main(String[] args) throws IOException {
        String dbPath = args.length > 0 ? args[0] : "donhang.db";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        DonHangRepository repo = createRepository(dbPath);
        seedData(repo);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", exchange -> {
            if (!"/".equals(exchange.getRequestURI().getPath())) {
                send(exchange, 404, "text/plain; charset=UTF-8", "Not Found");
                return;
            }
            send(exchange, 200, "text/html; charset=UTF-8", trangDanhSach(repo));
        });
        server.createContext("/cap-nhat", exchange -> {
            CapNhatDonHangWebPage page = taoCapNhatWebPage(repo);
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> form = parseForm(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
                page.onSave(form.getOrDefault("orderId", ""), form.getOrDefault("userId", ""),
                        form.getOrDefault("shippingAddress", ""));
            } else {
                Map<String, String> q = parseForm(exchange.getRequestURI().getRawQuery());
                page.updateFieldByID("orderId", q.getOrDefault("orderId", ""));
                page.updateFieldByID("userId", q.getOrDefault("userId", ""));
            }
            send(exchange, 200, "text/html; charset=UTF-8", page.show());
        });
        server.createContext("/huy", exchange -> {
            HuyDonHangWebPage page = taoHuyWebPage(repo);
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> form = parseForm(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
                page.onSave(form.getOrDefault("orderId", ""));
            } else {
                Map<String, String> q = parseForm(exchange.getRequestURI().getRawQuery());
                page.updateFieldByID("orderId", q.getOrDefault("orderId", ""));
            }
            send(exchange, 200, "text/html; charset=UTF-8", page.show());
        });
        server.start();
        System.out.println("Shop Tui Xach & Vali dang chay tai http://localhost:" + port + " (SQLite: " + dbPath + ")");
    }

    private static void send(HttpExchange exchange, int status, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
