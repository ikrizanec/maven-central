package com.infobip.pmf.course.mavencentral;

import com.infobip.pmf.course.mavencentral.exception.LibraryNotFoundException;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntity;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntityRepository;
import com.infobip.pmf.course.mavencentral.storage.VersionEntity;
import com.infobip.pmf.course.mavencentral.storage.VersionEntityRepository;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Version> getAllVersionsByLibraryId(Long libraryId, Pageable pageable) {
        Library library = libraryEntityRepository.findById(libraryId)
                .map(LibraryEntity::asLibrary)
                .orElseThrow(() -> new LibraryNotFoundException(libraryId));

        return versionEntityRepository.findByLibraryId(libraryId, pageable)
                .stream()
                .map(VersionEntity::asVersion)
                .collect(Collectors.toList());
    }
}
