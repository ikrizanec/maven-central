package com.infobip.pmf.course.mavencentral.api;

import com.infobip.pmf.course.mavencentral.Library;
import com.infobip.pmf.course.mavencentral.LibraryService;
import com.infobip.pmf.course.mavencentral.LibraryText;

import com.infobip.pmf.course.mavencentral.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/libraries")
@RestController
public class LibraryController {

    private LibraryService libraryService;

    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping
    public Page<Library> findAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String artifactId) {
        return libraryService.getAll(groupId, artifactId, PageRequest.of(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    public Library getLibraryById(@PathVariable Long id){
        return libraryService.getLibraryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Library createLibrary(@RequestBody @Valid Library library) {
        return libraryService.createLibrary(library);
    }

    @PatchMapping("/{id}")
    public Library updateLibrary(@PathVariable Long id, @RequestBody @Valid LibraryText libraryText) {
        return libraryService.updateNameAndDescription(id, libraryText);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLibraryById(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
    }

}
