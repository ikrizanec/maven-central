package com.infobip.pmf.course.mavencentral.storage;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.infobip.pmf.course.mavencentral.Library;
import com.infobip.pmf.course.mavencentral.Version;

@Entity
@Table(name = "library", schema = "maven_central",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"groupId", "artifactId"})})
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupId;

    @Column(nullable = false)
    private String artifactId;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VersionEntity> versions;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public static LibraryEntity from (Library library) {
        var libraryEntity = new LibraryEntity();
        libraryEntity.setId(library.id());
        libraryEntity.setGroupId(library.groupId());
        libraryEntity.setArtifactId(library.artifactId());

        List<VersionEntity> versionEntities = library.versions() != null
                ? library.versions().stream()
                .map(version -> {
                    VersionEntity versionEntity = new VersionEntity();
                    versionEntity.setSemanticVersion(version.semanticVersion());
                    versionEntity.setDescription(version.description());
                    versionEntity.setDeprecated(version.deprecated());
                    return versionEntity;
                })
                .toList()
                : Collections.emptyList();

        libraryEntity.setVersions(versionEntities);
        libraryEntity.setName(library.name());
        libraryEntity.setDescription(library.description());
        return libraryEntity;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public List<VersionEntity> getVersions(){ return versions;}

    public void setVersions(List<VersionEntity> versions){
        this.versions = versions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Library asLibrary() {
        List<Version> versionList;
        if (versions != null) {
            versionList = versions.stream()
                    .map(VersionEntity::asVersion)
                    .collect(Collectors.toList());
        } else {
            versionList = new ArrayList<>();
        }
        Library library = new Library(id, groupId, artifactId, versionList, name, description);
        return library;
    }

    public LibraryEntity updateNameAndDescription(String name, String description) {
        this.name = name;
        this.description = description;
        return this;
    }

    public void addVersion(VersionEntity versionEntity){
        versions.add(versionEntity);
    }

    public void removeVersion(VersionEntity versionEntity){
        versions.remove(versionEntity);
    }
}
