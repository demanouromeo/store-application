package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.request.RegisteredUserRequest;
import com.dmsacad.store.dtos.request.UpdateUserRequest;
import com.dmsacad.store.dtos.response.UserDto;
import com.dmsacad.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisteredUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);

}
