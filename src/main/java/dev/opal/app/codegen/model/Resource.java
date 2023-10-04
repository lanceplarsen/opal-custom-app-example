package dev.opal.app.codegen.model;

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
public class Resource {

	@NotNull
	@Schema(description = "The id of the resource that uniquely identifies it in your system. It should match the ID (<resource_id>) of the request.", example = "12345")
	@JsonProperty("id")
	private String id;

	@NotNull
	@Schema(description = "The name of the resource", example = "ResourceName")
	@JsonProperty("name")
	private String name;

	@NotNull
	@Schema(description = "The description of the resource", example = "This is a sample resource description.")
	@JsonProperty("description")
	private String description;

}
