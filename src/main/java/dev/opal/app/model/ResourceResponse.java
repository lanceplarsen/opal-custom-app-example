package dev.opal.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.opal.app.entity.Resource;
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
public class ResourceResponse {

	@NotNull
	@Valid
	@Schema(name = "resource", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("resource")
	private Resource resource;

}