package dev.opal.example.custom.integration.codegen.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class GroupResource {

	@NotNull
	@Schema(name = "resource_id", example = "f454d283-ca67-4a8a-bdbb-df212eca5353", description = "The id of the resource in your system. Opal will provide this id when making requests for the resource to your connector.", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("resource_id")
	private String resourceId;

	@Valid
	@Schema(name = "access_level", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("access_level")
	private AccessLevel accessLevel;

}
