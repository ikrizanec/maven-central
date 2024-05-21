package com.infobip.pmf.course.mavencentral.storage;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "maven_central")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "users_username")
    private String username;
    @Column(nullable = false, name = "users_apikey")
    private String apiKey;

    public UserEntity() {}
    public UserEntity(String username, String apiKey){
        this.username = username;
        this.apiKey = apiKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
