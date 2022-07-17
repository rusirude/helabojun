package com.leaf.helabojun.user.controller;


import com.leaf.helabojun.user.dto.UserRoleDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.service.UserRoleService;
import com.leaf.helabojun.user.validator.group.ValidationGroupOne;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "web/user-roles")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleController {

    private UserRoleService userRoleService;

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserRole(@PathVariable String uuid) {
        return ResponseEntity.ok().body(userRoleService.findUserRole(ChannelEnum.WEB, uuid));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveUserRole(@Validated(ValidationGroupOne.class) @RequestBody UserRoleDTO userRoleDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(userRoleService.saveUserRole(ChannelEnum.WEB, username, userRoleDTO));
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUserRole(@PathVariable String uuid, @Validated(ValidationGroupOne.class) @RequestBody UserRoleDTO userRoleDTO, @RequestParam String username) {
        return ResponseEntity.ok().body(userRoleService.updateUserRole(ChannelEnum.WEB, uuid, userRoleDTO, username));
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUserRole(@PathVariable String uuid, @RequestParam String username) {
        return ResponseEntity.ok().body(userRoleService.deleteUserRole(ChannelEnum.WEB, uuid, username));
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserRoles(
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

        return ResponseEntity.ok().body(userRoleService.findUserRoles(ChannelEnum.WEB, requestDTO));
    }
}
