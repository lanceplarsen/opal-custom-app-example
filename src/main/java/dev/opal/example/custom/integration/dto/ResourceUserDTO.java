package dev.opal.example.custom.integration.dto;

import dev.opal.example.custom.integration.codegen.connector.model.AccessLevel;
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
