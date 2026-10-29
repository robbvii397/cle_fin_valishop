package starter.usecase;

import starter.database.DonHangRepository;

/**
 * USE CASE CIRCLE - Lop cha chung cua 2 UseCaseControl don hang (theo so do).
 * Giu repo va outputBoundary de lop con ke thua (Extends).
 */
public abstract class DonHangUseCase<T> {
    protected DonHangRepository repo;
    protected OutputBoundary<T> outputBoundary;

    public DonHangUseCase(DonHangRepository repo, OutputBoundary<T> outputBoundary) {
        this.repo = repo;
        this.outputBoundary = outputBoundary;
    }

    // Ten ham theo so do: DonHangControl(repo, outputBoundary)
    public DonHangUseCase DonHangControl(DonHangRepository repo, OutputBoundary<T> outputBoundary) {
        this.repo = repo;
        this.outputBoundary = outputBoundary;
        return this;
    }

    public DonHangRepository getRepo() {
        return repo;
    }

    public OutputBoundary<T> getOutputBoundary() {
        return outputBoundary;
    }
}
