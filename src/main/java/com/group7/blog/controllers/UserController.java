package com.group7.blog.controllers;


import com.group7.blog.dto.Blog.response.BlogResponse;
import com.group7.blog.dto.Tag.response.TagResponse;
import com.group7.blog.dto.User.reponse.ApiResponse;
import com.group7.blog.dto.User.reponse.UserProfileResponse;
import com.group7.blog.dto.User.reponse.UserResponse;
import com.group7.blog.dto.User.request.UserCreationRequest;
import com.group7.blog.dto.User.request.UserFollowRequest;
import com.group7.blog.dto.User.request.UserUpdateRequest;
import com.group7.blog.models.Blog;
import com.group7.blog.models.UserFollow;
import com.group7.blog.models.Users;
import com.group7.blog.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @GetMapping
    List<Users> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    ApiResponse<Users> createUser(@RequestBody UserCreationRequest request){
        ApiResponse<Users> usersApiResponse = new ApiResponse<>();
        Users createdUser = userService.createUser(request);
        usersApiResponse.setResult(createdUser);

        return usersApiResponse;
    }

    @GetMapping("/{userId}")
    ApiResponse<Users> getUser(@PathVariable("userId") UUID userId){
        ApiResponse<Users> usersApiResponse = new ApiResponse<>();
        Users user = userService.getUser(userId);
        usersApiResponse.setResult(user);

        return usersApiResponse;

    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") UUID userId, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> usersApiResponse = new ApiResponse<>();
        UserResponse user = userService.updateUser(userId, request);

        usersApiResponse.setResult(user);
        return usersApiResponse;
    }

    @GetMapping("/me")
    ApiResponse<UserProfileResponse> getMe () {
        return ApiResponse.<UserProfileResponse>builder()
                .message("Get User Profile Successfully!")
                .result(userService.getCurrentUserInfo())
                .build();
    }

    @GetMapping("/{userId}/blogs")
    ApiResponse<UserResponse> getBlogsByUserId(@PathVariable("userId") UUID userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getBlogsByUserId(userId))
                .build();
    }

    @PostMapping("/user_follows")
    ApiResponse<String> unFollowUser(@RequestParam String action, @RequestBody UserFollowRequest request) {
        return ApiResponse.<String>builder()
                .result(
                        Objects.equals(action, "unfollow") ?
                                userService.unFollowUser(request.getTargetUserId()) :
                                userService.followUser(request.getTargetUserId())
                )
                .build();
    }
}
