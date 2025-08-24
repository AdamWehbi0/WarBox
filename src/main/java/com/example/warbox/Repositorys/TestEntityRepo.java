package com.example.warbox.Repositorys;

import com.example.warbox.models.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepo extends JpaRepository<TestEntity, Long> {
}
