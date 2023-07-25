package br.com.project.ECommerce.service.auth;

import br.com.project.ECommerce.dto.auth.RegisterRequestDTO;
import br.com.project.ECommerce.model.Cliente;
import br.com.project.ECommerce.model.User;
import br.com.project.ECommerce.repositories.ClienteRepository;
import br.com.project.ECommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity signup(RegisterRequestDTO data){

        Set<String> telefones = Set.of(data.getTelefones());

        System.out.println(telefones);

        var user = User.builder()
                .email(data.getEmail())
                .password(data.getPassword())
                .build();

        userRepository.save(user);

        var cliente = Cliente.builder()
                .cpf(data.getCpf())
                .nomeCompleto(data.getNomeCompleto())
                .telefones(telefones)
                .user(user)
                .build();

        clienteRepository.save(cliente);

        return ResponseEntity.ok(user);
    }


}
