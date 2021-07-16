package com.example.restaurantwishlist.db;

import java.util.List;
import java.util.Optional;

public interface MemoryDbRepositoryLfs<T> {

    Optional<T> findById(int index);
    T save(T entity);
    void deleteById(int index);
    List<T> findAll();

}
