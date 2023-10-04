package dev.opal.app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import dev.opal.app.codegen.model.User;
import dev.opal.app.codegen.model.UsersResponse;
import dev.opal.app.dto.UserDTO;
import dev.opal.app.entity.AccessUser;

public class UsersMapper {

	// Direct Conversions

	public static UsersResponse toUsersResponse(List<AccessUser> users) {
		List<UserDTO> userDTOs = toUserDTOs(users);
		List<User> domainUsers = userDTOs.stream().map(UsersMapper::toUserFromDTO).collect(Collectors.toList());
		return new UsersResponse(domainUsers);
	}

	// Helper methods

	public static UserDTO toUserDTO(AccessUser user) {
		return new UserDTO(user.getId(), user.getEmail());
	}

	public static User toUserFromDTO(UserDTO dto) {
		return new User(dto.getId(), dto.getEmail());
	}

	public static List<UserDTO> toUserDTOs(List<AccessUser> users) {
		return users.stream().map(UsersMapper::toUserDTO).collect(Collectors.toList());
	}

	public static List<User> toUserListFromDTOList(List<UserDTO> dtoList) {
		return dtoList.stream().map(UsersMapper::toUserFromDTO).collect(Collectors.toList());
	}
}