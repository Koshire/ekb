package com.ktaksv.ekb.controller;

import com.ktaksv.ekb.model.dto.UserModelAddDto;
import com.ktaksv.ekb.model.dto.UserModelFilterDto;
import com.ktaksv.ekb.model.dto.UserModelResponseDto;
import com.ktaksv.ekb.service.UserModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "User controller")
public class UserModelController {

    private final UserModelService service;

    @PostMapping(path = "/add/one/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "add one user", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<UserModelResponseDto> addOne(@RequestBody UserModelAddDto dto) {
        try {
            return ResponseEntity.ok(service.save(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @GetMapping(path = "/get/{id}/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "get one user by id", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<UserModelResponseDto> getOne(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.getOne(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @GetMapping(path = "/get/all/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "get all users", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<Page<UserModelResponseDto>> getAll(@RequestParam("page") int page,
                                                             @RequestParam("pageSize") int pageSize) {
        System.out.println();
        try {
            return ResponseEntity.ok(service.getAll(page, pageSize));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @PostMapping(path = "/get/all/filtered/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "get all filtered users", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<Page<UserModelResponseDto>> getAllFiltered(@RequestParam("page") int page,
                                                                     @RequestParam("pageSize") int pageSize,
                                                                     @RequestBody(required = false) UserModelFilterDto dto) {
        System.out.println();
        try {
            return ResponseEntity.ok(service.findAllFiltered(page, pageSize, dto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @GetMapping(path = "/get/me/user/")
    @Operation(summary = "get me", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<UserModelResponseDto> me(Principal principal) {
        try {
            return ResponseEntity.ok(service.getMe());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @PostMapping(path = "/delete/{id}/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "delete one user", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<Boolean> deleteOne(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @PostMapping(path = "/enable/{id}/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "enable/disable one user", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<Boolean> enableOne(@PathVariable("id") Long id,
                                             @RequestParam(value = "enable", required = true) boolean enable) {
        try {
            return ResponseEntity.ok(service.enable(id, enable));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @PostMapping(path = "/update/{id}/user/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "update one user", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<UserModelResponseDto> enableOne(@PathVariable("id") Long id,
                                                          @RequestBody Object body) {
        try {
            return ResponseEntity.ok(service.update(id, body));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }
}
