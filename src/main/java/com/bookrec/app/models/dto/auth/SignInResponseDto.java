package com.bookrec.app.models.dto.auth;

import com.bookrec.app.models.entities.User;

public record SignInResponseDto(User user, String authToken) {

}
