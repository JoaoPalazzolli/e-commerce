package br.com.project.ECommerce.controller.auth;

import br.com.project.ECommerce.dto.auth.RegisterRequestDTO;
import br.com.project.ECommerce.service.auth.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @PostMapping(value = "/signup")
    public ResponseEntity register(@RequestBody RegisterRequestDTO data){
        return authServices.signup(data);
    }

}
