Feature: Cập nhật thông tin đơn hàng
  In order giữ thông chi tiết đơn hàng và thông tin giao hàng chính xác
  As một khách hàng
  I want to cập nhật thông tin giao hàng của đơn hàng của tôi

  Rule: Khách hàng có thể cập nhật thông tin giao hàng khi đơn hàng đang chờ xử lý
    Scenario: Khách hàng cập nhật thành công một đơn hàng đang chờ xử lý
      Given Đơn hàng "ORD101" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng phải là "25 Nguyễn Huệ"
      And Thông báo xác nhận cập nhật phải được hiển thị

    Scenario: Khách hàng cập nhật thành công một đơn hàng khác đang chờ xử lý
      Given Đơn hàng "ORD106" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "45 Trần Hưng Đạo"
      When Khách hàng thay đổi địa chỉ giao hàng thành "78 Hai Bà Trưng"
      Then Địa chỉ giao hàng của đơn hàng phải là "78 Hai Bà Trưng"
      And Thông báo xác nhận cập nhật phải được hiển thị

    Scenario: Khách hàng cập nhật 2 lần liên tiếp thì địa chỉ cuối cùng được giữ lại
      Given Đơn hàng "ORD107" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      When Khách hàng thay đổi địa chỉ giao hàng thành "30 Pasteur"
      Then Địa chỉ giao hàng của đơn hàng phải là "30 Pasteur"
      And Thông báo xác nhận cập nhật phải được hiển thị

  Rule: Không thể cập nhật thông tin giao hàng sau khi đơn hàng đã được vận chuyển
    Scenario: Khách hàng cố gắng cập nhật một đơn hàng đã được vận chuyển
      Given Đơn hàng "ORD102" của khách hàng có trạng thái "Đã vận chuyển"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"
      And Thông báo "Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển" phải được hiển thị

    Scenario: Khách hàng cố gắng cập nhật một đơn hàng đã được giao hàng
      Given Đơn hàng "ORD108" của khách hàng có trạng thái "Đã giao hàng"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"
      And Thông báo "Không thể thay đổi thông tin giao hàng sau khi đơn hàng đã được vận chuyển" phải được hiển thị

    Scenario: Khách hàng cố gắng cập nhật một đơn hàng đã bị hủy
      Given Đơn hàng "ORD109" của khách hàng có trạng thái "Đã hủy"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"
      And Thông báo "Không thể cập nhật đơn hàng đã bị hủy" phải được hiển thị

  Rule: Chỉ chủ đơn hàng mới có thể cập nhật thông tin giao hàng
    Scenario: Một khách hàng cố gắng cập nhật đơn hàng của khách hàng khác
      Given Đơn hàng "ORD103" thuộc về một khách hàng khác có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"
      And Thông báo "Bạn không được phép cập nhật đơn hàng này" sẽ được hiển thị

    Scenario: Một khách hàng khác cố gắng cập nhật đơn hàng đang chờ xử lý của người khác
      Given Đơn hàng "ORD110" thuộc về một khách hàng khác có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "45 Trần Hưng Đạo"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "45 Trần Hưng Đạo"
      And Thông báo "Bạn không được phép cập nhật đơn hàng này" sẽ được hiển thị

    Scenario: Quyền sở hữu được kiểm tra trước trạng thái đơn hàng
      Given Đơn hàng "ORD111" thuộc về một khách hàng khác có trạng thái "Đã vận chuyển"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"
      And Thông báo "Bạn không được phép cập nhật đơn hàng này" sẽ được hiển thị

  Rule: Không thể cập nhật một đơn hàng không tồn tại
    Scenario: Khách hàng cố gắng cập nhật một đơn hàng không tồn tại
      Given Không tồn tại đơn hàng "ORD404"
      When Khách hàng thay đổi địa chỉ giao hàng của đơn hàng "ORD404" thành "25 Nguyễn Huệ"
      Then Thông báo "Không tìm thấy đơn hàng" phải được hiển thị

    Scenario: Khách hàng cố gắng cập nhật một mã đơn hàng khác không tồn tại
      Given Không tồn tại đơn hàng "ORD405"
      When Khách hàng thay đổi địa chỉ giao hàng của đơn hàng "ORD405" thành "25 Nguyễn Huệ"
      Then Thông báo "Không tìm thấy đơn hàng" phải được hiển thị

    Scenario: Cập nhật đơn hàng không tồn tại không làm thay đổi đơn hàng đang chờ xử lý khác
      Given Đơn hàng "ORD112" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng của đơn hàng "ORD406" thành "25 Nguyễn Huệ"
      Then Thông báo "Không tìm thấy đơn hàng" phải được hiển thị
      And Địa chỉ giao hàng của đơn hàng "ORD112" vẫn phải là "12 Lê Lợi"

  Rule: Địa chỉ giao hàng mới không được để trống
    Scenario: Khách hàng cập nhật với địa chỉ giao hàng mới để trống
      Given Đơn hàng "ORD105" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành ""
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"
      And Thông báo "Địa chỉ giao hàng không được để trống" phải được hiển thị

    Scenario: Khách hàng cập nhật một đơn hàng khác với địa chỉ giao hàng mới để trống
      Given Đơn hàng "ORD113" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "45 Trần Hưng Đạo"
      When Khách hàng thay đổi địa chỉ giao hàng thành ""
      Then Địa chỉ giao hàng của đơn hàng vẫn phải là "45 Trần Hưng Đạo"
      And Thông báo "Địa chỉ giao hàng không được để trống" phải được hiển thị

    Scenario: Sau khi cập nhật thất bại vì địa chỉ trống, khách hàng vẫn cập nhật lại được bằng địa chỉ hợp lệ
      Given Đơn hàng "ORD114" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng thay đổi địa chỉ giao hàng thành ""
      Then Thông báo "Địa chỉ giao hàng không được để trống" phải được hiển thị
      When Khách hàng thay đổi địa chỉ giao hàng thành "25 Nguyễn Huệ"
      Then Địa chỉ giao hàng của đơn hàng phải là "25 Nguyễn Huệ"
      And Thông báo xác nhận cập nhật phải được hiển thị
