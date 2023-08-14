package br.com.project.ECommerce.controller.admin;

import br.com.project.ECommerce.dto.ProdutoDTO;
import br.com.project.ECommerce.service.admin.ProdutoAdminServices;
import br.com.project.ECommerce.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@Tag(name = "Admin Product Controller Endpoint")
public class ProdutoAdminController {

    @Autowired
    private ProdutoAdminServices produtoAdminServices;

    @GetMapping(
            value = "/{product_id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds a Product", description = "Finds a Product", tags = { "Product" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<ProdutoDTO> findById(@PathVariable(value = "product_id") Long productId){
        return produtoAdminServices.findById(productId);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Adds a new Product", description = "Adds a new product by passing in a JSON or XML representation of the product", tags = {
            "product" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<ProdutoDTO> createProduct(@RequestBody ProdutoDTO produtoDTO){
        return produtoAdminServices.createProduct(produtoDTO);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Updates a Product", description = "Updates a Product by passing in a JSON or XML representation of the Product", tags = {
            "Product" }, responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<ProdutoDTO> updateProduct(@RequestBody ProdutoDTO produtoDTO){
        return produtoAdminServices.updateProduct(produtoDTO);
    }

    @DeleteMapping(value = "/{product_id}")
    @Operation(summary = "Delete a Product", description = "Delete a Product", tags = { "Product" }, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "product_id") Long productId){
        return produtoAdminServices.deleteProduct(productId);
    }
}
