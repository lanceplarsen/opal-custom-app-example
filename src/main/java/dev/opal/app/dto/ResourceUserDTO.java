package dev.opal.app.dto;

import dev.opal.app.codegen.model.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUserDTO {
	private String userId;
	private String email;
	private AccessLevel accessLevel;
}
