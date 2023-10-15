package dev.opal.example.custom.integration.codegen.connector.model;

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
public class AddResourceUserRequest {

	@NotNull
	@Schema(name = "user_id", example = "123", description = "The ID of the user to add.", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("user_id")
	private String userId;

	@NotNull
	@Schema(name = "app_id", example = "my-custom-app", description = "The identifier of your custom app.", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("app_id")
	private String appId;

	@Schema(name = "access_level_id", example = "123", description = "The ID of the access level to assign to the user.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("access_level_id")
	private String accessLevelId;
}
