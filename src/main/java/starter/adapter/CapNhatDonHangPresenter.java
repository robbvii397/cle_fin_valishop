package starter.adapter;

import starter.usecase.CapNhatDonHangOutputData;
import starter.usecase.OutputBoundary;

/**
 * INTERFACE ADAPTER CIRCLE - Presenter cua chuc nang Cap nhat don hang.
 * Nhan OutputData tu UseCase (qua OutputBoundary) va do vao ViewModel.
 * Tuong duong SimplePresenter trong project mau cua thay.
 */
public class CapNhatDonHangPresenter implements OutputBoundary<CapNhatDonHangOutputData> {
    private CapNhatDonHangViewModel viewModel;

    public CapNhatDonHangPresenter(CapNhatDonHangViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public CapNhatDonHangViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(CapNhatDonHangOutputData output) {
        viewModel.setSuccess(output.isSuccess());
        viewModel.setMessage(output.getMessage());
    }

    // Goi present(T) theo dung ten trong so do
    public void presentOutput(CapNhatDonHangOutputData output) {
        present(output);
    }
}
