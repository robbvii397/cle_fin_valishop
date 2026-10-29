package starter.usecase;

import starter.database.DonHangRepository;
import starter.entity.CapNhatDonHangEntity;

/**
 * USE CASE CIRCLE - Dieu phoi chuc nang Cap nhat don hang:
 * nhan InputData -> goi Entity xu ly nghiep vu -> day OutputData qua OutputBoundary.
 */
public class CapNhatDonHangUseCaseControl extends DonHangUseCase<CapNhatDonHangOutputData>
        implements InputBoundary<CapNhatDonHangInputData> {

    private CapNhatDonHangEntity useCase;

    public CapNhatDonHangUseCaseControl(DonHangRepository repo,
                                        OutputBoundary<CapNhatDonHangOutputData> output) {
        super(repo, output);
        this.useCase = new CapNhatDonHangEntity(repo);
    }

    // Ten ham theo so do: CapNhatDonHangControl(repo, output)
    public void CapNhatDonHangControl(DonHangRepository repo,
                                      OutputBoundary<CapNhatDonHangOutputData> output) {
        this.repo = repo;
        this.outputBoundary = output;
        this.useCase = new CapNhatDonHangEntity(repo);
    }

    public CapNhatDonHangEntity getUseCase() {
        return useCase;
    }

    // Ham xu ly chinh theo so do: handle(input)
    public void handle(CapNhatDonHangInputData input) {
        CapNhatDonHangOutputData output = useCase.execute(input);
        outputBoundary.present(output);
    }

    @Override
    public void execute(CapNhatDonHangInputData input) {
        handle(input);
    }
}
