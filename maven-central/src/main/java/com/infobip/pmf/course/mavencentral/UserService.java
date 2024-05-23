package com.infobip.pmf.course.mavencentral;

import com.infobip.pmf.course.mavencentral.storage.UserEntityRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private UserEntityRepository userEntityRepository;

    public UserService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public boolean existsByApiKey(String apiKey) {
        return userEntityRepository.existsByApiKey(apiKey);
    }
}
