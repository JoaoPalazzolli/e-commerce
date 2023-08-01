package br.com.project.ECommerce.service.auth;

import br.com.project.ECommerce.dto.auth.RegisterRequestDTO;
import br.com.project.ECommerce.model.Cliente;
import br.com.project.ECommerce.model.User;
import br.com.project.ECommerce.repositories.ClienteRepository;
import br.com.project.ECommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = false)
    public ResponseEntity signup(RegisterRequestDTO data){

        Set<String> telefones = Set.of(data.getTelefones());

        var user = User.builder()
                .email(data.getEmail())
                .password(data.getPassword())
                .build();

        userRepository.save(user);

        var cliente = Cliente.builder()
                .cpf(data.getCpf())
                .nomeCompleto(data.getNomeCompleto())
                .telefones(telefones)
                .dataNascimento(data.getDataNascimento())
                .contaCriada(LocalDateTime.now())
                .user(user)
                .build();

        clienteRepository.save(cliente);

        return ResponseEntity.ok(user);
    }


}
