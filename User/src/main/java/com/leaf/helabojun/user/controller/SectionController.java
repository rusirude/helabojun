package com.leaf.helabojun.user.controller;


import com.leaf.helabojun.user.dto.SectionDTO;
import com.leaf.helabojun.user.service.SectionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "sections")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SectionController {
    
    private SectionService sectionService;

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSection(@PathVariable String uuid) {
        return ResponseEntity.ok().body(sectionService.findSection(uuid));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveSection(@RequestBody SectionDTO sectionDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(sectionService.saveSection(sectionDTO, username));
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateSection(@PathVariable String uuid, @RequestBody SectionDTO sectionDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(sectionService.updateSection(uuid, sectionDTO, username));
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteSection(@PathVariable String uuid, @RequestParam String username) {
        return ResponseEntity.ok().body(sectionService.deleteSection(uuid, username));
    }
}
