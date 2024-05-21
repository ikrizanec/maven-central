package com.infobip.pmf.course.mavencentral.api;

import com.infobip.pmf.course.mavencentral.Version;
import com.infobip.pmf.course.mavencentral.VersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/libraries")
@RestController
public class VersionController {

    private VersionService versionService;

    public VersionController(VersionService versionService){
        this.versionService = versionService;
    }

    @GetMapping("/{libraryId}/versions")
    public ResponseEntity<List<Version>> getAllVersionsByLibraryId(
            @PathVariable Long libraryId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "releaseDate") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        List<Version> versions = versionService.getAllVersionsByLibraryId(libraryId, pageNo, pageSize, sortBy, sortDirection);
        return ResponseEntity.ok().body(versions);
    }
}
