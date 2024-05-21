package com.infobip.pmf.course.mavencentral.api;

import com.infobip.pmf.course.mavencentral.Version;
import com.infobip.pmf.course.mavencentral.VersionService;
import com.infobip.pmf.course.mavencentral.VersionText;
import com.infobip.pmf.course.mavencentral.storage.VersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/libraries")
@RestController
public class VersionController {

    private VersionService versionService;

    public VersionController(VersionService versionService){
        this.versionService = versionService;
    }

    @GetMapping("/{libraryId}/versions")
    public Page<Version> getAllVersionsByLibraryId(@PathVariable Long libraryId,
                                                   @RequestParam(defaultValue = "0") int pageNo,
                                                   @RequestParam(defaultValue = "20") int pageSize) {
        return versionService.getAllVersionsByLibraryId(libraryId, PageRequest.of(pageNo, pageSize));
    }

    @GetMapping("/{libraryId}/versions/{versionId}")
    public Version getVersionById(@PathVariable Long libraryId, @PathVariable Long versionId) {
        return versionService.getVersionById(libraryId, versionId).asVersion();
    }

    @PostMapping("/{libraryId}/versions")
    @ResponseStatus(HttpStatus.CREATED)
    public Version createVersion(@PathVariable Long libraryId, @RequestBody Version version) {
        return versionService.createVersion(libraryId, version);
    }

    @PatchMapping("/{libraryId}/versions/{versionId}")
    public Version updateVersion(@PathVariable Long libraryId,
                                 @PathVariable Long versionId,
                                 @RequestBody VersionText versionText) {
        return versionService.updateVersion(libraryId, versionId, versionText);
    }

}
