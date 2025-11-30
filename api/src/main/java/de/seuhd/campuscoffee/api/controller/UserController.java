package de.seuhd.campuscoffee.api.controller;

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.api.exceptions.ErrorResponse;
import de.seuhd.campuscoffee.api.mapper.UserDtoMapper;
import de.seuhd.campuscoffee.domain.ports.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static de.seuhd.campuscoffee.api.util.ControllerUtils.getLocation;

@Tag(name = "Users", description = "Operations related to user management.")
@Controller
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    //TODO: Implement user controller
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("")

    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(
                userService.getAll().stream()
                        .map(userDtoMapper::toUserDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")

    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userDtoMapper.toUserDto(userService.getById(id))
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<UserDto> filter(@RequestParam("name") String name) {
        return ResponseEntity.ok(
                userDtoMapper.toUserDto(userService.getByName(name))
        );
    }

    @PostMapping("")
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = userDtoMapper.toUserDto(
                userService.create(userDtoMapper.toUser(userDto))
        );
        return ResponseEntity
                .created(getLocation(createdUser.id()))
                .body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UserDto userDto) {
        if (!id.equals(userDto.id())) {
            throw new IllegalArgumentException("User ID in path and body do not match.");
        }
        UserDto updatedUser = userDtoMapper.toUserDto(
                userService.update(userDtoMapper.toUser(userDto))
        );
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id); // throws NotFoundException if no user with the provided ID exists
        return ResponseEntity.noContent().build();
    }
}
