# Đồ án: Shop Túi Xách & Vali — Cập nhật thông tin đơn hàng & Hủy đơn hàng (Clean Architecture)

Project được viết **đúng theo mẫu project của thầy** (Serenity BDD + Cucumber, package `starter`,
Java 21, Maven) và **đúng theo sơ đồ Clean Architecture** đã gửi:

- **Chức năng 1:** Cập nhật thông tin (địa chỉ giao hàng) của đơn hàng
- **Chức năng 2:** Hủy đơn hàng
- **Giao diện:** Bootstrap 5 đơn giản (3 trang: danh sách đơn, cập nhật, hủy)
- **Database:** SQLite đơn giản (file `donhang.db`, 1 bảng `don_hang`) + bản InMemory dùng cho test

---

## 1. Ánh xạ sơ đồ Clean Architecture → file code

| Vòng (Circle) | Package | File |
|---|---|---|
|---|---|
| **Entity** | `starter.entity` | `DonHangEntity.java` (orderId, userId, status, shippingAddress, createdAt), `CapNhatDonHangEntity.java`, `HuyDonHangEntity.java` — chứa toàn bộ Rule nghiệp vụ |
| **Use case** | `starter.usecase` | `CapNhatDonHangInputData.java`, `CapNhatDonHangOutputData.java`, `HuyDonHangInputData.java`, `HuyDonHangOutputData.java`, `DonHangUseCase.java` (lớp cha), `CapNhatDonHangUseCaseControl.java`, `HuyDonHangUseCaseControl.java`, `InputBoundary.java`, `OutputBoundary.java` |
| **Interface Adapter** | `starter.adapter` | `CapNhatDonHangController.java`, `HuyDonHangController.java`, `CapNhatDonHangPresenter.java`, `HuyDonHangPresenter.java`, `CapNhatDonHangViewModel.java`, `HuyDonHangViewModel.java` |
| **Frameworks & Drivers** | `starter.frameworks` | `CapNhatDonHangWebPage.java`, `HuyDonHangWebPage.java` (Bootstrap), `Main.java` (khởi chạy web server + nối dây các tầng) |
| **Database** | `starter.database` | `DonHangRepository.java` (interface: themDonHang / suaDonHang / xoaDonHang / layDanhSachDonHang), `SqliteDonHangRepository.java` (chạy thật, tự nạp driver `org.sqlite.JDBC`), `InMemoryDonHangRepository.java` (dùng cho test, giống `InMemoryDBMock` của thầy) |

Luồng xử lý (giống hệt project mẫu của thầy):

```
WebPage → Controller → UseCaseControl → Entity (Rule nghiệp vụ) → Repository (SQLite / InMemory)
UseCaseControl → Presenter → ViewModel → WebPage hiển thị thông báo (alert Bootstrap)
```

**Điểm bổ sung nhỏ so với sơ đồ** (bắt buộc phải có thì code mới chạy được, đã ghi chú ngay trong code):
`DonHangEntity.getUserId()`, `getCreatedAt()` và `DonHangRepository.layDonHangTheoId(orderId)`
để kiểm tra "chỉ chủ đơn mới được cập nhật" và để tìm 1 đơn hàng theo mã.

## 2. Các Rule nghiệp vụ (lấy từ 2 file .feature)

**Cập nhật đơn hàng** (`CapNhatDonHangEntity`):
1. Đơn `Đang chờ xử lý` + đúng chủ đơn → cập nhật thành công: *"Cập nhật thông tin đơn hàng thành công"*
2. Đơn `Đã vận chuyển` / `Đã giao hàng` → *"Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển"*
3. Không phải chủ đơn → *"Bạn không được phép cập nhật đơn hàng này"* (kiểm tra quyền **trước** trạng thái)
4. Đơn `Đã hủy` → *"Không thể cập nhật đơn hàng đã bị hủy"*
5. Không tìm thấy đơn → *"Không tìm thấy đơn hàng"*; Địa chỉ mới trống → *"Địa chỉ giao hàng không được để trống"*

**Hủy đơn hàng** (`HuyDonHangEntity`):
1. Đơn `Đang chờ xử lý` → chuyển sang `Đã hủy`: *"Hủy đơn hàng thành công"*
2. Đơn `Đã vận chuyển` / `Đã giao hàng` → *"Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển"*
3. Đơn `Đã hủy` → *"Đơn hàng này đã bị hủy"*
4. Không tìm thấy đơn → *"Không tìm thấy đơn hàng"*

## 3. Cách chạy chương trình web

```bash
mvn compile exec:java
```

Mở trình duyệt: **http://localhost:8080**

- `/` — danh sách đơn hàng mẫu của shop (ORD001, ORD002, ORD003, ORD101, ORD102, ORD103 của khách KH001/KH002)
- `/cap-nhat` — trang cập nhật địa chỉ giao hàng (Bootstrap form)
- `/huy` — trang hủy đơn hàng (Bootstrap form)

Dữ liệu được lưu thật xuống file SQLite `donhang.db` (tự tạo + tự nạp dữ liệu mẫu lần chạy đầu tiên).

## 4. Kiểm thử (phần quan trọng nhất)

Chạy toàn bộ test giống hệt cách chạy project của thầy:

```bash
mvn verify
```

- Unit test chạy bằng Failsafe, Cucumber chạy bằng `CucumberTestSuite` (Serenity)
- Báo cáo Serenity sinh ra tại `target/site/serenity/index.html`

### 4.1. Cucumber / BDD — 2 feature, 30 scenario, **30/30 PASS**

Mỗi feature giữ nguyên các Rule và Scenario gốc được giao, mỗi Rule mở rộng thành **3 scenario** (tổng 5 Rule × 3 = 15 scenario/feature):

- `src/test/resources/features/update_order_information.feature` — 15 scenario
- `src/test/resources/features/cancel_order.feature` — 15 scenario
- Step Definitions: `src/test/java/starter/stepdefinitions/DonHangStepDefinitions.java` (import các class từ 5 package trên)
  (nối dây đầy đủ Controller → UseCase → Entity → InMemory Repository → Presenter → ViewModel, giống `Tich2SoStepDefinitions` của thầy)

### 4.2. Unit Test — 21 class, 58 test, **58/58 PASS**

**Mỗi class/mỗi hàm trong code chính đều có đúng 1 class unit test tương ứng** trong
`src/test/java/starter/unittests/`. Mọi class test và mọi test case đều có `@DisplayName`
mô tả tiếng Việt và bố cục `// Given – // When – // Then` giống hệt phong cách của thầy:

| Class được test | Class unit test | Số test |
|---|---|---|
| DonHangEntity | DonHangEntityTest | 3 |
| CapNhatDonHangEntity | CapNhatDonHangEntityTest | 5 (đủ 5 trường hợp của feature cập nhật) |
| HuyDonHangEntity | HuyDonHangEntityTest | 5 (đủ 5 trường hợp của feature hủy) |
| CapNhatDonHangInputData / OutputData | CapNhatDonHangInputDataTest / OutputDataTest | 2 + 2 |
| HuyDonHangInputData / OutputData | HuyDonHangInputDataTest / OutputDataTest | 1 + 2 |
| DonHangUseCase (lớp cha) | DonHangUseCaseTest | 2 |
| CapNhat/HuyDonHangUseCaseControl | CapNhat/HuyDonHangUseCaseControlTest | 2 + 2 |
| CapNhat/HuyDonHangController | CapNhat/HuyDonHangControllerTest | 2 + 2 |
| CapNhat/HuyDonHangPresenter | CapNhat/HuyDonHangPresenterTest | 2 + 2 |
| CapNhat/HuyDonHangViewModel | CapNhat/HuyDonHangViewModelTest | 1 + 1 |
| InMemoryDonHangRepository | InMemoryDonHangRepositoryTest | 5 (thêm/sửa/xóa/danh sách/theo mã) |
| SqliteDonHangRepository | SqliteDonHangRepositoryTest | 5 (gồm test mở lại file để chứng minh dữ liệu lưu thật xuống đĩa) |
| CapNhat/HuyDonHangWebPage | CapNhat/HuyDonHangWebPageTest | 4 + 4 (show / updateFieldByID / onSave) |
| Main | MainTest | 4 (seedData, createRepository, parseForm, trangDanhSach) |

3 interface `DonHangRepository`, `InputBoundary`, `OutputBoundary` không có logic riêng nên
được kiểm thử gián tiếp qua các class cài đặt chúng (Repository qua 2 class test Repository,
Boundary qua các test UseCaseControl/Presenter) — đúng như thầy làm với `StoreAble`/`PresentAble`.

### 4.3. Kết quả kiểm chứng thực tế

Toàn bộ test đã được chạy thật và đạt **58/58 unit test + 30/30 scenario Cucumber**.
Chương trình web cũng đã được chạy thử: cập nhật ORD101 đổi địa chỉ thành công
và hủy ORD001 chuyển trạng thái sang "Đã hủy", dữ liệu lưu đúng trong SQLite.

## 5. Công nghệ

Java 21 · Maven · Serenity BDD 5.3.8 · Cucumber 7.34.2 · JUnit 6.0.3 (y hệt `pom.xml` của thầy)
· SQLite JDBC 3.47.1.0 (khai báo trong `<dependencies>` chính của pom.xml, kèm slf4j-api 2.0.16) · Bootstrap 5.3.3 (CDN)
· Web server dùng `com.sun.net.httpserver` có sẵn trong JDK (không cần Tomcat), class khởi chạy: `starter.frameworks.Main`

Kiểm tra nhanh thư viện SQLite đã được Maven nhận hay chưa:

```bash
mvn dependency:tree -Dincludes=org.xerial:sqlite-jdbc
```

Kết quả đúng phải có dòng: `org.xerial:sqlite-jdbc:jar:3.47.1.0:compile`
