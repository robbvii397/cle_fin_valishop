package starter.usecase;

/**
 * USE CASE CIRCLE - Cong vao (Input Boundary) theo so do.
 * Interface Adapter (Controller) chi goi qua cong nay.
 * Tuong duong vai tro Tich2SoService trong project mau cua thay.
 */
public interface InputBoundary<T> {
    void execute(T input);
}
