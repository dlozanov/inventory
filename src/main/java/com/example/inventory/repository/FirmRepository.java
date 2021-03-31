package com.example.inventory.repository;

import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.service.WarehouseServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FirmRepository extends JpaRepository<FirmEntity, Long> {

    Optional<FirmEntity> findByName(String name);

    Optional<FirmEntity> findByBulstat(String bulstat);
}
