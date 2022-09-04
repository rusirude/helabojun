package com.leaf.helabojun.user.controller;


import com.leaf.helabojun.user.dto.LocationDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.service.LocationService;
import com.leaf.helabojun.user.validator.group.ValidationGroupOne;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "web/location-types")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationController {

    private LocationService locationService;

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLocation(@PathVariable String uuid) {
        return ResponseEntity.ok().body(locationService.findLocation(ChannelEnum.WEB, uuid));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveLocation(@Validated(ValidationGroupOne.class) @RequestBody LocationDTO locationDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(locationService.saveLocation(ChannelEnum.WEB, username, locationDTO));
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateLocation(@PathVariable String uuid, @Validated(ValidationGroupOne.class) @RequestBody LocationDTO locationDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(locationService.updateLocation(ChannelEnum.WEB, uuid, locationDTO, username));
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteLocation(@PathVariable String uuid, @RequestParam String username) {
        return ResponseEntity.ok().body(locationService.deleteLocation(ChannelEnum.WEB, uuid, username));
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLocations(
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

        return ResponseEntity.ok().body(locationService.findLocations(ChannelEnum.WEB, requestDTO));
    }
}
