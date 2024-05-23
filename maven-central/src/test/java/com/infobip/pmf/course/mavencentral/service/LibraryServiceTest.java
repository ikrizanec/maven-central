package com.infobip.pmf.course.mavencentral.service;

import com.infobip.pmf.course.mavencentral.Library;
import com.infobip.pmf.course.mavencentral.LibraryService;
import com.infobip.pmf.course.mavencentral.LibraryText;
import com.infobip.pmf.course.mavencentral.exception.LibraryDefinitionConflictException;
import com.infobip.pmf.course.mavencentral.exception.LibraryInvalidUpdateException;
import com.infobip.pmf.course.mavencentral.exception.LibraryNotFoundException;
import com.infobip.pmf.course.mavencentral.exception.LibraryUniqueAttributesException;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntity;
import com.infobip.pmf.course.mavencentral.storage.LibraryEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class LibraryServiceTest {

    @Mock
    private LibraryEntityRepository libraryEntityRepository;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllLibraries() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        Page<LibraryEntity> libraryEntities = new PageImpl<>(List.of(libraryEntity));
        Pageable pageable = PageRequest.of(0, 10);
        when(libraryEntityRepository.findAll(pageable)).thenReturn(libraryEntities);

        // act
        Page<Library> libraries = libraryService.getAll(null, null, pageable);

        // assert
        assertNotNull(libraries);
        assertEquals(1, libraries.getTotalElements());
        verify(libraryEntityRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnLibraryForGivenId() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));

        // act
        Library library = libraryService.getLibraryById(1L);

        assertNotNull(library);
        assertEquals(1L, library.id());
        verify(libraryEntityRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowError_LibraryNotFoundById() {
        // act
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(LibraryNotFoundException.class, () -> libraryService.getLibraryById(1L));
        verify(libraryEntityRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateLibrary() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setGroupId("org.test");
        libraryEntity.setArtifactId("test-artifact");
        when(libraryEntityRepository.existsByGroupIdAndArtifactId("org.test", "test-artifact")).thenReturn(false);
        when(libraryEntityRepository.save(any(LibraryEntity.class))).thenReturn(libraryEntity);

        // act
        LibraryEntity createdLibrary = LibraryEntity.from(libraryService.createLibrary(libraryEntity.asLibrary()));

        // assert
        assertNotNull(createdLibrary);
        assertEquals("test-artifact", createdLibrary.getArtifactId());
        assertEquals("org.test", createdLibrary.getGroupId());
        verify(libraryEntityRepository, times(1)).existsByGroupIdAndArtifactId("org.test", "test-artifact");
        verify(libraryEntityRepository, times(1)).save(any(LibraryEntity.class));
    }

    @Test
    void shouldThrowError_ExistingLibraryGroupAndArtifactId() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setGroupId("org.test");
        libraryEntity.setArtifactId("test-artifact");

        // act
        when(libraryEntityRepository.existsByGroupIdAndArtifactId(any(), any())).thenReturn(true);

        // assert
        assertThrows(LibraryUniqueAttributesException.class, () -> libraryService.createLibrary(libraryEntity.asLibrary()));
    }

    @Test
    public void shouldUpdateNameAndDescription() {
        // arrange
        LibraryText libraryText = new LibraryText("New Name", "New Description");
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        libraryEntity.setName("Old Name");
        libraryEntity.setDescription("Old Description");
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(libraryEntityRepository.save(any(LibraryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        Library updatedLibrary = libraryService.updateNameAndDescription(1L, libraryText);

        // assert
        assertNotNull(updatedLibrary);
        assertEquals(libraryText.name(), updatedLibrary.name());
        assertEquals(libraryText.description(), updatedLibrary.description());
        verify(libraryEntityRepository, times(1)).findById(1L);
        verify(libraryEntityRepository, times(1)).save(any(LibraryEntity.class));
    }

    @Test
    public void shouldThrowException_UpdateNameAndDescription_LibraryNotFound() {
        // arrange
        LibraryText libraryText = new LibraryText("New Name", "New Description");
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(LibraryNotFoundException.class, () -> libraryService.updateNameAndDescription(1L, libraryText));
        verify(libraryEntityRepository, times(1)).findById(1L);
        verify(libraryEntityRepository, never()).save(any(LibraryEntity.class));
    }

    @Test
    public void shouldThrowException_UpdateNameAndDescription_InvalidUpdate() {
        // arrange
        LibraryText libraryText = new LibraryText("", "New Description");
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        libraryEntity.setName("Old Name");
        libraryEntity.setDescription("Old Description");
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));

        // act & assert
        assertThrows(LibraryInvalidUpdateException.class, () -> libraryService.updateNameAndDescription(1L, libraryText));
        verify(libraryEntityRepository, times(1)).findById(1L);
        verify(libraryEntityRepository, never()).save(any(LibraryEntity.class));
    }

    @Test
    void shouldDeleteLibrary() {
        // arrange
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(1L);
        when(libraryEntityRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        doNothing().when(libraryEntityRepository).deleteById(1L);

        // act
        libraryService.deleteLibrary(1L);

        // assert
        verify(libraryEntityRepository, times(1)).findById(1L);
        verify(libraryEntityRepository, times(1)).deleteById(1L);
    }
}
