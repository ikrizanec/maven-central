package com.infobip.pmf.course.mavencentral;

import com.infobip.pmf.course.mavencentral.exception.*;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntity;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntityRepository;
import com.infobip.pmf.course.mavencentral.storage.VersionEntity;
import com.infobip.pmf.course.mavencentral.storage.VersionEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class VersionService {

    private VersionEntityRepository versionEntityRepository;
    private LibraryEntityRepository libraryEntityRepository;

    public VersionService(VersionEntityRepository versionEntityRepository,
                          LibraryEntityRepository libraryEntityRepository){
        this.versionEntityRepository = versionEntityRepository;
        this.libraryEntityRepository = libraryEntityRepository;
    }

    @Transactional(readOnly = true)
    public Page<Version> getAllVersionsByLibraryId(Long libraryId, Pageable pageable) {
        Library library = libraryEntityRepository.findById(libraryId)
                .map(LibraryEntity::asLibrary)
                .orElseThrow(() -> new LibraryNotFoundException(libraryId));

        return versionEntityRepository.findByLibraryId(libraryId, pageable)
                .map(VersionEntity::asVersion);
    }

    @Transactional(readOnly = true)
    public VersionEntity getVersionById(Long libraryId, Long versionId) {
        LibraryEntity libraryEntity = libraryEntityRepository.findById(libraryId)
                .orElseThrow(() -> new LibraryNotFoundException());
        return versionEntityRepository.findById(versionId)
                .orElseThrow(() -> new VersionNotFoundException(libraryId, versionId));
    }

    @Transactional
    public Version createVersion(Long libraryId, Version version) {
        LibraryEntity libraryEntity = libraryEntityRepository.findById(libraryId)
                .orElseThrow(LibraryDefinitionConflictException::new);
        VersionEntity versionEntity = VersionEntity.from(version);
        versionEntity.setLibrary(libraryEntity);
        return save(versionEntity).asVersion();
    }

    @Transactional
    public VersionEntity save(VersionEntity versionEntity) {
        if(versionEntityRepository.existsBySemanticVersion(versionEntity.getSemanticVersion())){
            throw new VersionDefinitionConflictException(versionEntity.getSemanticVersion());
        }
        return versionEntityRepository.save(versionEntity);
    }

    @Transactional
    public Version updateVersion(Long libraryId, Long versionId, VersionText versionText) {
        LibraryEntity libraryEntity = libraryEntityRepository.findById(libraryId)
                .orElseThrow(() -> new LibraryNotFoundException(libraryId));
        VersionEntity versionEntity = versionEntityRepository.findById(versionId)
                .orElseThrow(() -> new VersionNotFoundException(libraryId, versionId));
        if(versionEntity.isDeprecated()) throw new VersionDeprecatedException(libraryId, versionId);
        versionEntity.setDescription(versionText.description());
        versionEntity.setDeprecated(versionText.deprecated());
        return versionEntity.asVersion();
    }
}
