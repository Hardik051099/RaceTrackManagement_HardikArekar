package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T> {
    public T save(T entity);
    public List<T> fetchAll();
     public Optional<T> findById(String id);
    // boolean existsById(ID id);
    public void delete(T entity);
    // public void deleteById(ID id);
    // public long count();
}
