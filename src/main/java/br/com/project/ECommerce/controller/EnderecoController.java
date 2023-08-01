package br.com.project.ECommerce.controller;

import br.com.project.ECommerce.dto.EnderecoDTO;
import br.com.project.ECommerce.service.EnderecoServices;
import br.com.project.ECommerce.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoServices enderecoServices;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<List<EnderecoDTO>> findAll(){
        return enderecoServices.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON})
    public ResponseEntity<EnderecoDTO> findById(@PathVariable(value = "id") Long id){
        return  enderecoServices.findById(id);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON},
            consumes = {MediaType.APPLICATION_JSON})
    public ResponseEntity<EnderecoDTO> createAddress(@RequestBody EnderecoDTO enderecoDTO) {
        return enderecoServices.createAddress(enderecoDTO);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON},
            consumes = {MediaType.APPLICATION_JSON})
    public ResponseEntity<EnderecoDTO> updateAddress(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoServices.updateAddress(enderecoDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable(value = "id") Long id){
        return enderecoServices.deleteAddress(id);
    }
}
