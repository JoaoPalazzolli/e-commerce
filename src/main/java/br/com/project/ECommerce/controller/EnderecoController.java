package br.com.project.ECommerce.controller;

import br.com.project.ECommerce.dto.EnderecoDTO;
import br.com.project.ECommerce.service.EnderecoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoServices enderecoServices;

    @PostMapping(value = "/registrar")
    public ResponseEntity registrarEndereco(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoServices.registrarEndereco(enderecoDTO);
    }
}
