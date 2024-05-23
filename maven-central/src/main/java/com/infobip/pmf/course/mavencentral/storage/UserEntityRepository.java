package com.infobip.pmf.course.mavencentral.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByApiKey(String apiKey);
}
