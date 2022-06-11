package com.leaf.helabojun.user.controller;


import com.leaf.helabojun.user.dto.LocationTypeDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.service.LocationTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "web/location-types")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationTypeController {

    private LocationTypeService locationTypeService;

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLocationType(@PathVariable String uuid) {
        return ResponseEntity.ok().body(locationTypeService.findLocationType(ChannelEnum.WEB, uuid));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveLocationType(@RequestBody LocationTypeDTO locationTypeDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(locationTypeService.saveLocationType(ChannelEnum.WEB, username, locationTypeDTO));
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateLocationType(@PathVariable String uuid, @RequestBody LocationTypeDTO locationTypeDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(locationTypeService.updateLocationType(ChannelEnum.WEB, uuid, locationTypeDTO, username));
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteLocationType(@PathVariable String uuid, @RequestParam String username) {
        return ResponseEntity.ok().body(locationTypeService.deleteLocationType(ChannelEnum.WEB, uuid, username));
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLocationTypes(
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

        return ResponseEntity.ok().body(locationTypeService.findLocationTypes(ChannelEnum.WEB, requestDTO));
    }
}
