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
public class Error {

	@NotNull
	@Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("code")
	private Integer code;

	@NotNull
	@Schema(name = "message", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("message")
	private String message;
}