package dev.opal.app.model;

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
public class ResourceUser {

	@NotNull
	@Schema(name = "user_id", example = "f454d283-ca67-4a8a-bdbb-df212eca5353", description = "The id of the user in your system. Opal will provide this id when making requests for the user to your connector.", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("user_id")
	private String userId;

	@Schema(name = "email", example = "johndoe@mycompany.com", description = "The email of the user. Opal will use this to associate the user with the corresponding Opal user.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("email")
	private String email;

	@Valid
	@Schema(name = "access_level", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("access_level")
	private AccessLevel accessLevel;

}