package com.backend.ecom.controllers;

import com.backend.ecom.dto.user.UserCreateRequestDTO;
import com.backend.ecom.dto.user.UserShortInfoDTO;
import com.backend.ecom.dto.user.UserUpdateInfoRequestDTO;
import com.backend.ecom.payload.request.ArrayRequest;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserShortInfoDTO> getAllUsers(@Valid @RequestParam(value = "deleted", defaultValue = "false") Boolean deleted) {
        return userService.getAllUsers(deleted);
    }

    @GetMapping("/all/{roleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserShortInfoDTO> getAllUsersByRole(@Valid @PathVariable("roleId") Long id,
                                                    @Valid @RequestParam(value = "deleted", defaultValue = "false") Boolean deleted) {
        return userService.getAllUsersByRole(deleted, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getUserDetail(@Valid @PathVariable("id") Long id,
                                                        @RequestParam(value = "deleted", defaultValue = "false") Boolean deleted) {
        return userService.getUserDetail(id, deleted);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        return userService.createUser(userCreateRequestDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateUser(@Valid @PathVariable("id") Long id,
                                                     @Valid @RequestBody UserUpdateInfoRequestDTO userUpdateInfoRequestDTO) {
        return userService.updateUser(id, userUpdateInfoRequestDTO);
    }

    @PatchMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> softDeleteManyUsers(@Valid @RequestBody ArrayRequest ids) {
        return userService.softDeleteOneOrManyUsers(Arrays.asList(ids.getIds()));
    }

    @DeleteMapping("/delete/force")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> forceDeleteManyUsers(@Valid @RequestBody ArrayRequest ids) {
        return userService.forceDeleteOneOrManyUsers(Arrays.asList(ids.getIds()));
    }

    @PatchMapping("/restore")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> restoreOneOrManyUsers(@Valid @RequestBody ArrayRequest ids) {
        return userService.restoreOneOrManyUsers(Arrays.asList(ids.getIds()));
    }

    @DeleteMapping("/feedbacks/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> deleteFeedback(@PathVariable("id") Long id) {
        return userService.deleteFeedback(id);
    }

}
