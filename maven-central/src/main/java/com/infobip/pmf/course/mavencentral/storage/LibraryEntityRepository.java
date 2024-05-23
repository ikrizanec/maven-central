package com.infobip.pmf.course.mavencentral.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryEntityRepository extends ListCrudRepository<LibraryEntity, Long> {

    Page<LibraryEntity> findAll(Pageable pageable);
    Page<LibraryEntity> findAllByGroupId(String groupId, Pageable pageable);
    Page<LibraryEntity> findAllByArtifactId(String artifactId, Pageable pageable);
    Page<LibraryEntity> findAllByGroupIdAndArtifactId(String groupId, String artifactId, Pageable pageable);
    boolean existsByGroupIdAndArtifactId(String groupId, String artifactId);
}
