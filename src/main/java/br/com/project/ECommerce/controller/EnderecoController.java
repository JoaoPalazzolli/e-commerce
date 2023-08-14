package br.com.project.ECommerce.controller;

import br.com.project.ECommerce.dto.EnderecoDTO;
import br.com.project.ECommerce.service.EnderecoServices;
import br.com.project.ECommerce.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@Tag(name = "Address Endpoint")
public class EnderecoController {

    @Autowired
    private EnderecoServices enderecoServices;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds all Adresses", description = "Finds all Adresses", tags = { "Address" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = EnderecoDTO.class)))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<EnderecoDTO>>> findAll(
            @RequestParam(value = "Page", defaultValue = "0") Integer page,
            @RequestParam(value = "Size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", defaultValue = "logradouro") String orderBy) {
        return enderecoServices.findAll(page, size, direction, orderBy);
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds a Address", description = "Finds a Address", tags = { "Address" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<EnderecoDTO> findById(@PathVariable(value = "id") Long id){
        return  enderecoServices.findById(id);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Adds a new Address", description = "Adds a new Address by passing in a JSON or XML representation of the Address", tags = {
            "Address" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<EnderecoDTO> createAddress(@RequestBody EnderecoDTO enderecoDTO) {
        return enderecoServices.createAddress(enderecoDTO);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Updates a Address", description = "Updates a Address by passing in a JSON or XML representation of the Address", tags = {
            "Address" }, responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<EnderecoDTO> updateAddress(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoServices.updateAddress(enderecoDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Address", description = "Delete a Address", tags = { "Address" }, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<?> deleteAddress(@PathVariable(value = "id") Long id){
        return enderecoServices.deleteAddress(id);
    }
}
