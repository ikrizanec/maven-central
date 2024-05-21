package com.infobip.pmf.course.mavencentral;

import com.infobip.pmf.course.mavencentral.exception.*;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntity;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntityRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


@Service
public class LibraryService {

    private LibraryEntityRepository libraryEntityRepository;

    public LibraryService(LibraryEntityRepository libraryEntityRepository){
        this.libraryEntityRepository = libraryEntityRepository;
    }
    public Page<Library> getAll(String groupId, String artifactId, Pageable pageable) {
        Page<LibraryEntity> libraryEntities;
        if (groupId != null && artifactId != null) {
            try {
                libraryEntities =  libraryEntityRepository.findAllByGroupIdAndArtifactId(groupId, artifactId, pageable);
            } catch(Exception e) {
                throw new LibraryGroupNotFoundException(groupId, artifactId);
            }
        } else if (groupId != null) {
            libraryEntities = libraryEntityRepository.findAllByGroupId(groupId, pageable);
        } else if (artifactId != null) {
            libraryEntities = libraryEntityRepository.findAllByArtifactId(artifactId, pageable);
        } else {
            libraryEntities = libraryEntityRepository.findAll(pageable);
        }
        return libraryEntities.map(LibraryEntity::asLibrary);
    }
    public Library getLibraryById(Long id) {
        return libraryEntityRepository.findById(id)
                .map(LibraryEntity::asLibrary)
                .orElseThrow(() -> new LibraryNotFoundException(id));
    }
    public Library createLibrary(Library library) {
        if(library.id() != null){
            throw new LibraryDefinitionConflictException();
        }
        return save(LibraryEntity.from(library)).asLibrary();
    }

    public LibraryEntity save(LibraryEntity libraryEntity) {
        if(libraryEntityRepository.existsLibraryEntityByArtifactIdAndGroupId(libraryEntity.getGroupId(), libraryEntity.getArtifactId())){
            throw new LibraryDefinitionConflictException(libraryEntity.getArtifactId(), libraryEntity.getGroupId());
        }
        return libraryEntityRepository.save(libraryEntity);
    }

    public Library updateNameAndDescription(Long id, LibraryText libraryText){
        return libraryEntityRepository
                .findById(id)
                .map(libraryEntity -> {
                    if(StringUtils.isBlank(libraryText.description())  || StringUtils.isBlank(libraryText.name())){
                        throw new LibraryInvalidUpdateException(id);
                    }
                    libraryEntity.updateNameAndDescription(libraryText.name(), libraryText.description());
                    return libraryEntity;
                })
                .map(this::save)
                .map(LibraryEntity::asLibrary)
                .orElseThrow(() -> new LibraryNotFoundException(id));
    }

    public void deleteLibrary(Long id) {
        if(!libraryEntityRepository.existsById(id)) {
            throw new LibraryNotFoundException(id);
        }
        libraryEntityRepository.deleteById(id);
    }
}
