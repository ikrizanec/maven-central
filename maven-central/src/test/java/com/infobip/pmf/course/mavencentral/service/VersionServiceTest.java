package com.infobip.pmf.course.mavencentral.service;

import com.infobip.pmf.course.mavencentral.Library;
import com.infobip.pmf.course.mavencentral.Version;
import com.infobip.pmf.course.mavencentral.VersionService;
import com.infobip.pmf.course.mavencentral.VersionText;
import com.infobip.pmf.course.mavencentral.exception.LibraryNotFoundException;
import com.infobip.pmf.course.mavencentral.exception.VersionNotFoundException;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntity;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntityRepository;
import com.infobip.pmf.course.mavencentral.storage.VersionEntity;
import com.infobip.pmf.course.mavencentral.storage.VersionEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class VersionServiceTest {
    @Mock
    private VersionEntityRepository versionEntityRepository;
    @Mock
    private LibraryEntityRepository libraryEntityRepository;
    @InjectMocks
    private VersionService versionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllVersionsByLibraryId() {
        // arrange
        Pageable pageable = PageRequest.of(0, 10);
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        Library library = libraryEntity.asLibrary();
        List<VersionEntity> versionEntities = Collections.singletonList(new VersionEntity());
        Page<VersionEntity> versionEntityPage = new PageImpl<>(versionEntities);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(versionEntityRepository.findByLibraryId(1L, pageable)).thenReturn(versionEntityPage);

        // act
        Page<Version> versionsPage = versionService.getAllVersionsByLibraryId(1L, pageable);

        // assert
        assertNotNull(versionsPage);
        assertEquals(versionEntities.size(), versionsPage.getTotalElements());
        verify(libraryEntityRepository, times(1)).findById(1L);
        verify(versionEntityRepository, times(1)).findByLibraryId(1L, pageable);
    }

    @Test
    public void shouldReturnVersionForGivenId() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setId(1L);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(versionEntityRepository.findById(1L)).thenReturn(Optional.of(versionEntity));

        // act
        VersionEntity foundVersion = versionService.getVersionById(1L, 1L);

        // assert
        assertNotNull(foundVersion);
        assertEquals(1L, foundVersion.getId());
        verify(versionEntityRepository, times(1)).findById(1L);
        verify(libraryEntityRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldThrowException_GetVersion_VersionNotFoundById() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(versionEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(VersionNotFoundException.class, () -> versionService.getVersionById(1L, 1L));
    }

    @Test
    public void shouldThrowException_GetVersion_LibraryNotFoundById() {
        // arrange
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(LibraryNotFoundException.class, () -> versionService.getVersionById(1L, 1L));
    }

    @Test
    void shouldCreateVersion() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setSemanticVersion("5.6.11");
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(versionEntityRepository.existsBySemanticVersion(any())).thenReturn(false);
        when(versionEntityRepository.save(any(VersionEntity.class))).thenReturn(versionEntity);

        // act
        VersionEntity createdVersion = VersionEntity.from(versionService.createVersion(1L, versionEntity.asVersion()));

        // assert
        assertNotNull(createdVersion);
        assertEquals("5.6.11", createdVersion.getSemanticVersion());
    }

    @Test
    public void shouldThrowException_CreateVersion_LibraryIdNotFound() {
        // arrange
        VersionEntity versionEntity = new VersionEntity();
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(LibraryNotFoundException.class, () -> versionService.createVersion(1L, versionEntity.asVersion()));
    }

    @Test
    public void shouldUpdateVersion() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setId(1L);
        versionEntity.setSemanticVersion("6.9.12");
        versionEntity.setDeprecated(false);
        VersionText versionText = new VersionText("New description", true);
        when(versionEntityRepository.findById(1L)).thenReturn(Optional.of(versionEntity));
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(versionEntityRepository.save(any(VersionEntity.class))).thenReturn(versionEntity);

        // act
        VersionEntity updatedVersion = VersionEntity.from(versionService.updateVersion(1L, 1L, versionText));

        // assert
        assertNotNull(updatedVersion);
        assertEquals("New description", updatedVersion.getDescription());
        assertTrue(updatedVersion.isDeprecated());
        verify(libraryEntityRepository, times(1)).findById(1L);
        verify(versionEntityRepository, times(1)).findById(1L);
        verify(versionEntityRepository, times(1)).save(any(VersionEntity.class));
    }

    @Test
    public void shouldThrowException_UpdateVersion_LibraryIdNotFound() {
        // arrange
        VersionEntity versionEntity = new VersionEntity();
        VersionText versionText = new VersionText("New description", true);
        when(versionEntityRepository.findById(1L)).thenReturn(Optional.of(versionEntity));

        // act & assert
        assertThrows(LibraryNotFoundException.class, () -> versionService.updateVersion(1L, 1L,  versionText));
    }

    @Test
    public void shouldThrowException_UpdateVersion_VersionIdNotFound() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        VersionText versionText = new VersionText("New description", true);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));

        // act & assert
        assertThrows(VersionNotFoundException.class, () -> versionService.updateVersion(1L, 1L,  versionText));
    }
    @Test
    public void shouldThrowException_UpdateVersion_InvalidUpdate() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        VersionText versionText = new VersionText("", true);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));

        // act & assert
        assertThrows(VersionNotFoundException.class, () -> versionService.updateVersion(1L, 1L,  versionText));
        verify(versionEntityRepository, times(1)).findById(1L);
        verify(versionEntityRepository, never()).save(any(VersionEntity.class));
    }
}
