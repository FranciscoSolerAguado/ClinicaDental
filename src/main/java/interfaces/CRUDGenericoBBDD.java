package interfaces;

import java.util.List;

public interface CRUDGenericoBBDD<T> {
    List<Object> findAll();
    List<Object> findAllEager();
    void insert(T t);
    void update(int i, T t);
    void deleteById(int i);
}
