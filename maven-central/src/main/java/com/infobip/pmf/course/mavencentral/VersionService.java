package com.infobip.pmf.course.mavencentral;

import com.infobip.pmf.course.mavencentral.exception.LibraryDefinitionConflictException;
import com.infobip.pmf.course.mavencentral.exception.LibraryNotFoundException;
import com.infobip.pmf.course.mavencentral.exception.VersionDefinitionConflictException;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntity;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntityRepository;
import com.infobip.pmf.course.mavencentral.storage.VersionEntity;
import com.infobip.pmf.course.mavencentral.storage.VersionEntityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<Version> getAllVersionsByLibraryId(Long libraryId, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Library library = libraryEntityRepository.findById(libraryId)
                .map(LibraryEntity::asLibrary)
                .orElseThrow(() -> new LibraryNotFoundException(libraryId));

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return versionEntityRepository.findByLibraryId(library.id(), pageable)
                .stream()
                .map(VersionEntity::asVersion)
                .collect(Collectors.toList());
    }
}
