package com.dmsacad.store.repositories;

import com.dmsacad.store.dtos.response.UserDto;
import com.dmsacad.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u")
    List<UserDto> findUserAsDto();

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);//May Lead to N+1 PB

    @Query("select u from User u where u.email = :email")//To avoid N+1 PB
    Optional<User> findUserHavingEmail(@Param("email") String email);

    /*
    @Query("select u from User u where u.id = :userId")//To avoid N+1 PB
    Optional<User> findUser(@Param("userId") Long id);*/

}
