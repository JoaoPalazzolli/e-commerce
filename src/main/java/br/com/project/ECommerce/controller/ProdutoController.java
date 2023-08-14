package br.com.project.ECommerce.controller;

import br.com.project.ECommerce.dto.ProdutoDTO;
import br.com.project.ECommerce.service.ProdutoServices;
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
@RequestMapping("api/v1/product")
@Tag(name = "Product Controller Endpoint")
public class ProdutoController {

    @Autowired
    private ProdutoServices produtoServices;

    @GetMapping(
            value = "/{productId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds a Product", description = "Finds a Product", tags = { "Product" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<ProdutoDTO> findById(@PathVariable(value = "productId") Long productId){
        return produtoServices.findById(productId);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds all Products", description = "Finds all Products", tags = { "Product" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = ProdutoDTO.class)))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<ProdutoDTO>>> findAllProducts(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy){

        return produtoServices.findAllProducts(page, size, direction, orderBy);
    }

    @GetMapping(
            value = "/search/{nomeProduto}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Search Products by Name", description = "Search Products by Name", tags = { "Product" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<ProdutoDTO>>> searchByName(
            @PathVariable(value = "nomeProduto") String nomeProduto,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy){

        return produtoServices.searchByName(nomeProduto, page, size, direction, orderBy);
    }

    @GetMapping(
            value = "/category/{categoryId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(summary = "Finds All Products by Category", description = "Finds All Products by Category", tags = { "Product" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PagedModel<EntityModel<ProdutoDTO>>> findAllByCategory(
            @PathVariable(value = "categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy){

        return produtoServices.findAllByCategory(categoryId, page, size, direction, orderBy);
    }

}
