package com.ktaksv.ekb.model.mapper;

import com.ktaksv.ekb.model.RoleModel;
import com.ktaksv.ekb.model.UserModel;
import com.ktaksv.ekb.model.dto.UserModelResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserModelResponseDto mapToResponse(UserModel model);

    default String getAuthorities(RoleModel model) {
        return model.getRole().name();
    }
}
