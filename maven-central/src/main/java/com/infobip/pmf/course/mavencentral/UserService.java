package com.infobip.pmf.course.mavencentral;

import com.infobip.pmf.course.mavencentral.storage.UserEntity;
import com.infobip.pmf.course.mavencentral.storage.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserEntityRepository userEntityRepository;

    public UserService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public Optional<UserEntity> findByApiKey(String apiKey) {
        return userEntityRepository.findByApiKey(apiKey);
    }
}
