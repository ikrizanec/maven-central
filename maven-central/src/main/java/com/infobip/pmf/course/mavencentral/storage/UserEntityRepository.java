package com.infobip.pmf.course.mavencentral.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByApiKey(String apiKey);
}
