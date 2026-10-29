package starter.adapter;

import starter.usecase.HuyDonHangOutputData;
import starter.usecase.OutputBoundary;

/**
 * INTERFACE ADAPTER CIRCLE - Presenter cua chuc nang Huy don hang.
 * Nhan OutputData tu UseCase (qua OutputBoundary) va do vao ViewModel.
 */
public class HuyDonHangPresenter implements OutputBoundary<HuyDonHangOutputData> {
    private HuyDonHangViewModel viewModel;

    public HuyDonHangPresenter(HuyDonHangViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public HuyDonHangViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(HuyDonHangOutputData output) {
        viewModel.setSuccess(output.isSuccess());
        viewModel.setMessage(output.getMessage());
    }

    public void presentOutput(HuyDonHangOutputData output) {
        present(output);
    }
}
