package com.example.inventory.repository;

import com.example.inventory.model.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepositoy extends JpaRepository<LogEntity, Long> {
}
