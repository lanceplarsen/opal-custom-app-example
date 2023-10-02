package dev.opal.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.opal.app.entity.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class GroupsResponse {

	@NotNull
	@Valid
	@Schema(name = "groups", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("groups")
	private List<Group> groups = new ArrayList<>();

}