package dev.opal.app.codegen.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import dev.opal.app.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UsersResponse {

	@NotNull
	@Valid
	@Schema(name = "users", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("users")
	private List<User> users = new ArrayList<>();

}