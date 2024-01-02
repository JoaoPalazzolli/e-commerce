package br.com.project.ECommerce.controller.auth;

import br.com.project.ECommerce.dto.auth.RegisterPessoaFisicaDTO;
import br.com.project.ECommerce.dto.auth.RegisterPessoaJuridicaDTO;
import br.com.project.ECommerce.service.auth.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @PostMapping(value = "/signup/pessoafisica")
    public ResponseEntity<?> signup(@RequestBody RegisterPessoaFisicaDTO data){
        return authServices.signupPessoaFisica(data);
    }

    @PostMapping(value = "/signup/pessoajuridica")
    public ResponseEntity<?> signup(@RequestBody RegisterPessoaJuridicaDTO data){
        return authServices.signupPessoaJuridica(data);
    }

    @GetMapping(value = "/signup/pessoafisica")
    public ResponseEntity<?> teste(){
        return authServices.teste();
    }

}
