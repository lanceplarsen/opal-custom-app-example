package dev.opal.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class GroupDetail {

	@NotNull
	@Schema(name = "id", example = "f454d283-ca67-4a8a-bdbb-df212eca5353", description = "The id of the group in your system. Opal will provide this id when making requests for the group to your connector.", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("id")
	private String id;

	@NotNull
	@Schema(name = "name", example = "Admin", description = "The name of the group", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("name")
	private String name;

	@Schema(name = "description", example = "This group represents the engineering team.", description = "The description of the group", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("description")
	private String description;
}