package com.example.warbox.Repositorys;

import com.example.warbox.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepo extends JpaRepository<AppUserEntity, UUID> {
    Optional<AppUserEntity> findByEmail(String email);
    Optional<AppUserEntity> findByHandle(String handle);
}
