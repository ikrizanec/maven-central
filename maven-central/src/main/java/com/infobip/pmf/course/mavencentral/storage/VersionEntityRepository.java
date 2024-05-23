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
public interface VersionEntityRepository extends ListCrudRepository<VersionEntity, Long> {
    Page<VersionEntity> findByLibraryId(Long libraryId, Pageable pageable);
    boolean existsBySemanticVersion(String semantic_version);
}
