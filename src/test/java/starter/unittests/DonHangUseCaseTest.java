package starter.unittests;

import starter.database.InMemoryDonHangRepository;
import starter.usecase.CapNhatDonHangOutputData;
import starter.usecase.CapNhatDonHangUseCaseControl;
import starter.usecase.DonHangUseCase;
import starter.usecase.OutputBoundary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;






import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử lớp cha DonHangUseCase: giữ Repository và OutputBoundary cho các UseCaseControl con kế thừa")
public class DonHangUseCaseTest {

    @Test
    @DisplayName("Hàm tạo của lớp cha lưu đúng Repository và OutputBoundary được truyền vào")
    void testHamTaoLuuRepoVaOutputBoundary() {
        // Given
        InMemoryDonHangRepository repo = new InMemoryDonHangRepository();
        OutputBoundary<CapNhatDonHangOutputData> output = out -> { };
        // When
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(repo, output);
        // Then
        assertSame(repo, control.getRepo());
        assertSame(output, control.getOutputBoundary());
    }

    @Test
    @DisplayName("Hàm DonHangControl(repo, outputBoundary) gán lại được Repository và OutputBoundary mới")
    void testDonHangControl() {
        // Given
        InMemoryDonHangRepository repoCu = new InMemoryDonHangRepository();
        InMemoryDonHangRepository repoMoi = new InMemoryDonHangRepository();
        OutputBoundary<CapNhatDonHangOutputData> output = out -> { };
        CapNhatDonHangUseCaseControl control = new CapNhatDonHangUseCaseControl(repoCu, output);
        // When
        DonHangUseCase<CapNhatDonHangOutputData> ketQua = control.DonHangControl(repoMoi, output);
        // Then
        assertSame(control, ketQua);
        assertSame(repoMoi, control.getRepo());
    }
}
