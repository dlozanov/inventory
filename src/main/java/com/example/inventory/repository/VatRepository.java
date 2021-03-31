package com.example.inventory.repository;

import com.example.inventory.model.entity.VatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatRepository extends JpaRepository<VatEntity, Long> {
    VatEntity findByLetter(char letter);
}
