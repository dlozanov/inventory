package com.example.inventory.repository;

import com.example.inventory.model.entity.StockGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockGroupRepository extends JpaRepository<StockGroupEntity, Long> {
    StockGroupEntity findByName(String name);
}
