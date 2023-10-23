package dev.opal.example.custom.integration.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import dev.opal.example.custom.integration.codegen.connector.model.User;
import dev.opal.example.custom.integration.codegen.connector.model.UsersResponse;
import dev.opal.example.custom.integration.dto.UserDTO;
import dev.opal.example.custom.integration.entity.AccessUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// Direct Conversions

	default UsersResponse toUsersResponse(List<AccessUser> users) {
		return new UsersResponse(toUsers(users));
	}

	// Mappings for individual entities

	UserDTO toUserDTO(AccessUser user);

	User toUser(AccessUser user);

	User toUserFromDTO(UserDTO dto);

	// Mappings for collections

	List<UserDTO> toUserDTOs(List<AccessUser> users);

	List<User> toUsers(List<AccessUser> users);

	List<User> toUsersFromDTOs(List<UserDTO> dtoList);
}
