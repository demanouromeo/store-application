package com.dmsacad.store.controllers;

import com.dmsacad.store.dtos.request.ChangePasswordRequest;
import com.dmsacad.store.dtos.request.RegisteredUserRequest;
import com.dmsacad.store.dtos.request.UpdateUserRequest;
import com.dmsacad.store.dtos.request.WhisListRequest;
import com.dmsacad.store.dtos.response.UserDto;
import com.dmsacad.store.entities.Product;
import com.dmsacad.store.entities.Role;
import com.dmsacad.store.mappers.UserMapper;
//import org.springframework.http.HttpStatus;
import com.dmsacad.store.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.dmsacad.store.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Users")//The default controller on swagger UI is user-controller. Using Here we changed it into "Users"
public class UserController {
    private final UserMapper userMapper;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    /*************** USING MAPPER ***************/
    @GetMapping
    @Operation(summary = "Find all the users sorted by a sort criteria 'sort' provided as request parameter")//@Operation Enable us to provide short description of end point that can be seen on swagger UI
    Iterable<UserDto> getAllUsers(@RequestParam(name = "sort", defaultValue = "" /*, required = false*/) String sortParam) {
        if (!Set.of("name", "email").contains(sortParam)) {
            sortParam = "name";
        }
        return userRepository.findAll(Sort.by(sortParam).ascending())
                .stream().map(userMapper::toDto).toList();
        /*
        return userRepository.findAll()
                .stream()
                .map(user-> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
        */
        //return userRepository.findAll()
        //        .stream().map(user -> userMapper.toDto(user)).toList();

    }

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUser(@PathVariable long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        /*
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userDto);
        */
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody RegisteredUserRequest request
    ) {
        if(userRepository.existsByEmail((request.getEmail()))){
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email already registered")
            );
        }
        var user = userMapper.toEntity(request);
        //System.out.println(user);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            UserDto userDto = userMapper.toDto(user);
            System.out.println(userDto);
            var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
            return ResponseEntity.created(uri).body(userDto);
        } catch (Exception e) {
            System.out.println("Failed to created the user: " + user);
            //return ResponseEntity.status(HttpStatusCode.valueOf(400)).build();//400 = bad request. For our case email already exist
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UpdateUserRequest request
    ) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, user);
        System.out.println("Updating user: " + user);
        try {
            userRepository.save(user);
            UserDto userDto = userMapper.toDto(user);
            //System.out.println(userDto);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            System.out.println("Failed to update user: " + user);
            e.printStackTrace();
            //return ResponseEntity.status(HttpStatusCode.valueOf(400)).build();//400 = bad request. For our case email already exist
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            //return ResponseEntity.internalServerError().build();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable("id") Long id,
            @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!user.getPassword().equals(request.getOldPassword())) {
            return ResponseEntity.badRequest().build();//Code = 400
        } else {
            try {
                user.setPassword(request.getNewPassword());
                userRepository.save(user);
                return ResponseEntity.noContent().build(); // Code = 204
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build(); //Code = 500
            }
        }
    }


    @PostMapping("/add-fav-product")
    public ResponseEntity<?> addProduct(
           @Valid @RequestBody WhisListRequest request) {
        var user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        Product favProduct = user.getFavoriteProducts().stream()
                .filter(item -> item.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if(favProduct!=null){
            //(product_id,user_id) is unique pair in table whislist
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.badRequest().body(
                    Map.of("Attempt to duplicate Unique key", "(product_id,user_id) is unique pair in table whislist. Cannot be duplicated")
            );
        }else {
            try {
                user.addFavoriteProduct(product);
                userRepository.save(user);
                return ResponseEntity.ok().body(null);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        }


    }
}
