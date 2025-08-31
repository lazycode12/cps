package com.cps.app.mapper;

import com.cps.app.dto.response.UserDtoResponse;
import com.cps.app.model.User;

public class UserMapper {

	public static  UserDtoResponse toDto(User user) {
		return new UserDtoResponse(
				user.getId(),
				user.getNom(),
				user.getPrenom(),
				user.getEmail(),
				user.getRole() != null ? user.getRole() : null
				);
	}
}
