package com.infobip.pmf.course.mavencentral.storage;

import com.infobip.pmf.course.mavencentral.Version;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="version", schema="maven_central")
public class VersionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String semanticVersion;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean deprecated;

    @Column(nullable = false)
    private LocalDateTime releaseDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private LibraryEntity library;

    @PrePersist
    protected void onCreate() {
        this.releaseDate = LocalDateTime.now();
    }

    public static VersionEntity from (Version version) {
        var versionEntity = new VersionEntity();
        versionEntity.setId(version.id());
        versionEntity.setSemanticVersion(version.semanticVersion());
        versionEntity.setDescription(version.description());
        versionEntity.setDeprecated(version.deprecated());
        return versionEntity;
    }

    public Version asVersion() {
        return new Version(id, semanticVersion, description, deprecated, releaseDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemanticVersion() {
        return semanticVersion;
    }

    public void setSemanticVersion(String semanticVersion) {
        this.semanticVersion = semanticVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public LibraryEntity getLibrary(){
        return library;
    }

    public void setLibrary(LibraryEntity libraryEntity){
        this.library = libraryEntity;
    }
}
