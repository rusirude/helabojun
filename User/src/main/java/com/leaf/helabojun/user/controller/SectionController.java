package com.leaf.helabojun.user.controller;


import com.leaf.helabojun.user.dto.SectionDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.service.SectionService;
import com.leaf.helabojun.user.validator.group.ValidationGroupOne;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "web/sections")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SectionController {

    private SectionService sectionService;

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSection(@PathVariable String uuid) {
        return ResponseEntity.ok().body(sectionService.findSection(ChannelEnum.WEB, uuid));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveSection(@Validated(ValidationGroupOne.class) @RequestBody SectionDTO sectionDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(sectionService.saveSection(ChannelEnum.WEB, username, sectionDTO));
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateSection(@PathVariable String uuid, @Validated(ValidationGroupOne.class) @RequestBody SectionDTO sectionDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(sectionService.updateSection(ChannelEnum.WEB, uuid, sectionDTO, username));
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteSection(@PathVariable String uuid, @RequestParam String username) {
        return ResponseEntity.ok().body(sectionService.deleteSection(ChannelEnum.WEB, uuid, username));
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSections(
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String status) {

        SearchRequestDTO requestDTO = new SearchRequestDTO();
        requestDTO.setStart(start);
        requestDTO.setLimit(limit);
        requestDTO.setOrderBy(orderBy);
        requestDTO.setOrder(order);
        requestDTO.setKey1(description);
        requestDTO.setKey2(status);

        return ResponseEntity.ok().body(sectionService.findSections(ChannelEnum.WEB, requestDTO));
    }
}
