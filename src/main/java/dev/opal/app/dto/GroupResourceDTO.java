package dev.opal.app.dto;

import dev.opal.app.codegen.model.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResourceDTO {
	private String resourceId;
	private AccessLevel accessLevel;
}