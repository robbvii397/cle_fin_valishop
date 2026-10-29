package starter.usecase;

import starter.database.DonHangRepository;
import starter.entity.HuyDonHangEntity;

/**
 * USE CASE CIRCLE - Dieu phoi chuc nang Huy don hang:
 * nhan InputData -> goi Entity xu ly nghiep vu -> day OutputData qua OutputBoundary.
 */
public class HuyDonHangUseCaseControl extends DonHangUseCase<HuyDonHangOutputData>
        implements InputBoundary<HuyDonHangInputData> {

    private HuyDonHangEntity useCase;

    public HuyDonHangUseCaseControl(DonHangRepository repo,
                                    OutputBoundary<HuyDonHangOutputData> output) {
        super(repo, output);
        this.useCase = new HuyDonHangEntity(repo);
    }

    // Ten ham theo so do: HuyDonHangUseCaseControl(repo, output)
    public void HuyDonHangUseCaseControl(DonHangRepository repo,
                                         OutputBoundary<HuyDonHangOutputData> output) {
        this.repo = repo;
        this.outputBoundary = output;
        this.useCase = new HuyDonHangEntity(repo);
    }

    public HuyDonHangEntity getUseCase() {
        return useCase;
    }

    // Ham xu ly chinh theo so do: handle(input)
    public void handle(HuyDonHangInputData input) {
        HuyDonHangOutputData output = useCase.execute(input);
        outputBoundary.present(output);
    }

    @Override
    public void execute(HuyDonHangInputData input) {
        handle(input);
    }
}
