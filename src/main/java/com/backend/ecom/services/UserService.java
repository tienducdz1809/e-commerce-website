package com.backend.ecom.services;

import com.backend.ecom.dto.user.UserCreateRequestDTO;
import com.backend.ecom.dto.user.UserDetailDTO;
import com.backend.ecom.dto.user.UserShortInfoDTO;
import com.backend.ecom.dto.user.UserUpdateInfoRequestDTO;
import com.backend.ecom.entities.Role;
import com.backend.ecom.entities.User;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.FeedbackRepository;
import com.backend.ecom.repositories.RoleRepository;
import com.backend.ecom.repositories.UserRepository;
import com.backend.ecom.supporters.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private PasswordEncoder encoder;

    public List<UserShortInfoDTO> getAllUsers(Boolean deleted) {
        List<UserShortInfoDTO> userShortInfo = new ArrayList<>();
        List<User> users = userRepository.findAllByDeleted(deleted);

        users.forEach(user -> userShortInfo.add(new UserShortInfoDTO(user)));

        return userShortInfo;
    }

    public List<UserShortInfoDTO> getAllUsersByRole(Boolean deleted, Long roleId) {
        List<UserShortInfoDTO> userShortInfo = new ArrayList<>();
        List<User> users = userRepository.findAllByDeletedAndRoleId(deleted, roleId);

        users.forEach(user -> userShortInfo.add(new UserShortInfoDTO(user)));

        return userShortInfo;
    }

    public ResponseEntity<ResponseObject> getUserDetail(Long id, Boolean deleted) {
        User user = userRepository.findByIdAndDeleted(id, deleted)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));
        UserDetailDTO userDetail = new UserDetailDTO(user);
        return ResponseEntity.ok(new ResponseObject("ok", "Query user successfully", userDetail));
    }

    public ResponseEntity<ResponseObject> createUser(UserCreateRequestDTO userCreateRequestDTO) {
        if (userRepository.existsByUsername(userCreateRequestDTO.getUsername().trim())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject("error", "Username is already taken!", ""));
        }

        if (userRepository.existsByEmail(userCreateRequestDTO.getEmail().trim())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject("error", "Email is already in use!", ""));
        }

        User user = new User(userCreateRequestDTO);
        user.setPassword(encoder.encode(userCreateRequestDTO.getPassword().trim()));

        String role = userCreateRequestDTO.getRole().trim();

        if (role.equals("admin")) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
            user.setRole(adminRole);
        } else if (role.equals("user")) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
            user.setRole(userRole);
        }

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create user successfully!", user));
    }


    public ResponseEntity<ResponseObject> updateUser(Long id, UserUpdateInfoRequestDTO userUpdateInfoRequestDTO) {
        User user = userRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user"));

        if (!userUpdateInfoRequestDTO.getUsername().equals(user.getUsername().trim()) && userRepository.existsByUsername(userUpdateInfoRequestDTO.getUsername().trim())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject("error", "Username is already taken!", ""));
        }

        if (!userUpdateInfoRequestDTO.getEmail().equals(user.getEmail().trim()) && userRepository.existsByEmail(userUpdateInfoRequestDTO.getEmail().trim())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject("error", "Email is already in use!", ""));
        }
        user.setFullName(userUpdateInfoRequestDTO.getFullName().trim());
        user.setUsername(userUpdateInfoRequestDTO.getUsername().trim());
        user.setEmail(userUpdateInfoRequestDTO.getEmail().trim());
        user.setAddress(userUpdateInfoRequestDTO.getAddress().trim());

        String role = userUpdateInfoRequestDTO.getRole().trim();

        if (role.equals("admin")) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
            user.setRole(adminRole);
        } else if (role.equals("user")) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
            user.setRole(userRole);
        }

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create user successfully!", user));
    }

    @Transactional
    public ResponseEntity<ResponseObject> softDeleteOneOrManyUsers(List<Long> ids) {

        for (Long id : ids) {
            if (!userRepository.existsByIdAndDeleted(id, false)) {
                throw new ResourceNotFoundException("Not found user with id: " + id);
            }
        }
        userRepository.softDeleteAllByIds(ids);
        return ResponseEntity.ok(new ResponseObject("ok", "Delete users successfully", ""));

    }

    @Transactional
    public ResponseEntity<ResponseObject> forceDeleteOneOrManyUsers(List<Long> ids) {
        for (Long id : ids) {
            if (!userRepository.existsByIdAndDeleted(id, true)) {
                throw new ResourceNotFoundException("Not found user with id: " + id);
            }
        }
        userRepository.deleteAllById(ids);
        return ResponseEntity.ok(new ResponseObject("ok", "Delete users permanently", ""));
    }

    @Transactional
    public ResponseEntity<ResponseObject> restoreOneOrManyUsers(List<Long> ids) {
        for (Long id : ids) {
            if (!userRepository.existsByIdAndDeleted(id, true)) {
                throw new ResourceNotFoundException("Not found user with id: " + id);
            }
        }
        userRepository.restoreAllByIds(ids);
        return ResponseEntity.ok(new ResponseObject("ok", "Restore users successfully", ""));

    }

    @Transactional
    public ResponseEntity<ResponseObject> deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found feedback with id:" + id);
        }
        feedbackRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete feedback successfully", ""));

    }


}
