package com.ktaksv.ekb.model.mapper;

import com.ktaksv.ekb.model.RoleModel;
import com.ktaksv.ekb.model.RoleType;
import com.ktaksv.ekb.model.UserModel;
import com.ktaksv.ekb.model.dto.UserModelAddDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserModelMapper {

    private final PasswordEncoder encoder;

    public UserModel mapToEntity(UserModelAddDto dto) {
        if (dto == null) {
            return null;
        }

        Set<RoleModel> roles = dto.getAuthorities().stream().map(s -> RoleModel.builder()
                .role(RoleType.valueOf(s)).build()).collect(Collectors.toSet());

        return UserModel.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .fio(dto.getFio())
                .position(dto.getPosition())
                .department(dto.getDepartment())
                .authorities(roles)
                .createdDate(LocalDateTime.now())
                .enabled(true)
                .build();
    }
}
