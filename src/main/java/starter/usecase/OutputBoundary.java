package starter.usecase;

/**
 * USE CASE CIRCLE - Cong ra (Output Boundary) theo so do.
 * UseCase day ket qua ra qua cong nay, Presenter se nhan va do vao ViewModel.
 * Tuong duong vai tro PresentAble trong project mau cua thay.
 */
public interface OutputBoundary<T> {
    void present(T output);
}
