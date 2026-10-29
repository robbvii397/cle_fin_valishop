Feature: Hủy đơn hàng hiện có
  In order to tránh nhận đơn hàng mà tôi không còn muốn nhận nữa.
  As một khách hàng
  I want to hủy đơn hàng của tôi

  Rule: Đơn hàng đang chờ xử lý có thể bị hủy.
    Scenario: Khách hàng hủy đơn hàng đang chờ xử lý
      Given Đơn hàng "ORD001" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      When Khách hàng yêu cầu hủy đơn hàng "ORD001"
      Then Trạng thái đơn hàng sẽ chuyển sang "Đã hủy"
      And Thông báo xác nhận hủy bỏ sẽ được hiển thị

    Scenario: Khách hàng hủy một đơn hàng khác đang chờ xử lý
      Given Đơn hàng "ORD005" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      When Khách hàng yêu cầu hủy đơn hàng "ORD005"
      Then Trạng thái đơn hàng sẽ chuyển sang "Đã hủy"
      And Thông báo xác nhận hủy bỏ sẽ được hiển thị

    Scenario: Đơn hàng đang chờ xử lý bị hủy thì không còn ở trạng thái chờ xử lý
      Given Đơn hàng "ORD006" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      When Khách hàng yêu cầu hủy đơn hàng "ORD006"
      Then Trạng thái đơn hàng phải là "Đã hủy"

  Rule: Đơn hàng đã được vận chuyển không thể bị hủy.
    Scenario: Khách hàng cố gắng hủy đơn hàng đã được đi giao.
      Given Đơn hàng "ORD002" thuộc về khách hàng và có trạng thái "Đã giao hàng"
      When Khách hàng yêu cầu hủy đơn hàng "ORD002"
      Then Trạng thái đơn hàng phải là "Đã giao hàng"
      And Thông báo "Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển" sẽ được hiển thị

    Scenario: Khách hàng cố gắng hủy một đơn hàng khác đã được đi giao
      Given Đơn hàng "ORD007" thuộc về khách hàng và có trạng thái "Đã giao hàng"
      When Khách hàng yêu cầu hủy đơn hàng "ORD007"
      Then Trạng thái đơn hàng phải là "Đã giao hàng"
      And Thông báo "Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển" sẽ được hiển thị

    Scenario: Đơn hàng đã giao hàng bị yêu cầu hủy nhiều lần vẫn giữ nguyên trạng thái
      Given Đơn hàng "ORD008" thuộc về khách hàng và có trạng thái "Đã giao hàng"
      When Khách hàng yêu cầu hủy đơn hàng "ORD008"
      Then Trạng thái đơn hàng phải là "Đã giao hàng"
      And Thông báo "Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển" sẽ được hiển thị

  Rule: Đơn hàng đã bị hủy không thể hủy lại lần nữa.
    Scenario: Khách hàng cố gắng hủy một đơn hàng đã bị hủy trước đó.
      Given Đơn hàng "ORD003" thuộc về khách hàng và có trạng thái "Đã hủy"
      When Khách hàng yêu cầu hủy đơn hàng "ORD003"
      Then Trạng thái đơn hàng nên được giữ nguyên là "Đã hủy"
      And Thông báo "Đơn hàng này đã bị hủy" sẽ được hiển thị

    Scenario: Khách hàng cố gắng hủy một đơn hàng khác đã bị hủy trước đó
      Given Đơn hàng "ORD009" thuộc về khách hàng và có trạng thái "Đã hủy"
      When Khách hàng yêu cầu hủy đơn hàng "ORD009"
      Then Trạng thái đơn hàng nên được giữ nguyên là "Đã hủy"
      And Thông báo "Đơn hàng này đã bị hủy" sẽ được hiển thị

    Scenario: Đơn hàng vừa bị hủy xong không thể hủy lại lần nữa
      Given Đơn hàng "ORD010" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      When Khách hàng yêu cầu hủy đơn hàng "ORD010"
      Then Trạng thái đơn hàng sẽ chuyển sang "Đã hủy"
      When Khách hàng yêu cầu hủy đơn hàng "ORD010"
      Then Trạng thái đơn hàng nên được giữ nguyên là "Đã hủy"
      And Thông báo "Đơn hàng này đã bị hủy" sẽ được hiển thị

  Rule: Đơn hàng đang trên đường vận chuyển không thể bị hủy.
    Scenario: Khách hàng cố gắng hủy đơn hàng đang trên đường vận chuyển
      Given Đơn hàng "ORD004" thuộc về khách hàng và có trạng thái "Đã vận chuyển"
      When Khách hàng yêu cầu hủy đơn hàng "ORD004"
      Then Trạng thái đơn hàng phải là "Đã vận chuyển"
      And Thông báo "Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển" sẽ được hiển thị

    Scenario: Khách hàng cố gắng hủy một đơn hàng khác đang trên đường vận chuyển
      Given Đơn hàng "ORD011" thuộc về khách hàng và có trạng thái "Đã vận chuyển"
      When Khách hàng yêu cầu hủy đơn hàng "ORD011"
      Then Trạng thái đơn hàng phải là "Đã vận chuyển"
      And Thông báo "Không thể hủy đơn hàng này vì đơn hàng đã được vận chuyển" sẽ được hiển thị

    Scenario: Đơn hàng đang vận chuyển bị yêu cầu hủy vẫn giữ nguyên địa chỉ và trạng thái
      Given Đơn hàng "ORD012" thuộc về khách hàng và có trạng thái "Đã vận chuyển"
      And Địa chỉ giao hàng là "12 Lê Lợi"
      When Khách hàng yêu cầu hủy đơn hàng "ORD012"
      Then Trạng thái đơn hàng phải là "Đã vận chuyển"
      And Địa chỉ giao hàng của đơn hàng vẫn phải là "12 Lê Lợi"

  Rule: Không thể hủy một đơn hàng không tồn tại.
    Scenario: Khách hàng cố gắng hủy một đơn hàng không tồn tại
      Given Không tồn tại đơn hàng "ORD404"
      When Khách hàng yêu cầu hủy đơn hàng "ORD404"
      Then Thông báo "Không tìm thấy đơn hàng" sẽ được hiển thị

    Scenario: Khách hàng cố gắng hủy một mã đơn hàng khác không tồn tại
      Given Không tồn tại đơn hàng "ORD405"
      When Khách hàng yêu cầu hủy đơn hàng "ORD405"
      Then Thông báo "Không tìm thấy đơn hàng" sẽ được hiển thị

    Scenario: Hủy đơn hàng không tồn tại không làm thay đổi đơn hàng đang chờ xử lý khác
      Given Đơn hàng "ORD013" thuộc về khách hàng và có trạng thái "Đang chờ xử lý"
      When Khách hàng yêu cầu hủy đơn hàng "ORD406"
      Then Thông báo "Không tìm thấy đơn hàng" sẽ được hiển thị
      And Trạng thái đơn hàng "ORD013" phải là "Đang chờ xử lý"
