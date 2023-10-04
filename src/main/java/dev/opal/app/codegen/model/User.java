package dev.opal.app.codegen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@NotNull
	@Schema(description = "The id of the user that uniquely identifies it in your system.", example = "u12345")
	@JsonProperty("id")
	private String id;

	@NotNull
	@Schema(description = "The email of the user, this is required to associate the user in Opal.", example = "user@example.com")
	@JsonProperty("email")
	private String email;

}