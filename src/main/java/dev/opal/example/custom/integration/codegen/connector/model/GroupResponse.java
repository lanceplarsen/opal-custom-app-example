package dev.opal.example.custom.integration.codegen.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

	@JsonProperty("group")
	private Group group;
}
