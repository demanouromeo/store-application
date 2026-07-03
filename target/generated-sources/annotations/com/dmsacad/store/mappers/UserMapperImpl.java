package com.dmsacad.store.mappers;

import com.dmsacad.store.dtos.request.RegisteredUserRequest;
import com.dmsacad.store.dtos.request.UpdateUserRequest;
import com.dmsacad.store.dtos.response.UserDto;
import com.dmsacad.store.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-03T13:19:35-0400",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String email = null;
        String role = null;

        if ( user.getId() != null ) {
            id = user.getId();
        }
        name = user.getName();
        email = user.getEmail();
        if ( user.getRole() != null ) {
            role = user.getRole().name();
        }

        UserDto userDto = new UserDto( id, name, email, role );

        return userDto;
    }

    @Override
    public User toEntity(RegisteredUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( request.getEmail() );
        user.name( request.getName() );
        user.password( request.getPassword() );

        return user.build();
    }

    @Override
    public void update(UpdateUserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        user.setEmail( request.getEmail() );
        user.setName( request.getName() );
    }
}
