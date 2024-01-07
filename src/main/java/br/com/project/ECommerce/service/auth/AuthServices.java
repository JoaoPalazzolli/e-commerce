package br.com.project.ECommerce.service.auth;

import br.com.project.ECommerce.dto.UserDTO;
import br.com.project.ECommerce.dto.auth.RegisterPessoaFisicaDTO;
import br.com.project.ECommerce.dto.auth.RegisterPessoaJuridicaDTO;
import br.com.project.ECommerce.mapper.Mapper;
import br.com.project.ECommerce.model.PessoaFisica;
import br.com.project.ECommerce.model.PessoaJuridica;
import br.com.project.ECommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> signupPessoaFisica(RegisterPessoaFisicaDTO data){

        // verificar se já existe email

        var cliente = PessoaFisica.builder()
                .nomeCompleto(data.getNomeCompleto())
                .email(data.getEmail())
                .password(data.getPassword()) // temporario
                .telefones(data.getTelefones())
                .cpf(data.getCpf())
                .dataNascimento(data.getDataNascimento())
                .build();

        userRepository.save(cliente);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> signupPessoaJuridica(RegisterPessoaJuridicaDTO data){

        // verificar se já existe email

        var cliente = PessoaJuridica.builder()
                .nomeCompleto(data.getNomeCompleto())
                .email(data.getEmail())
                .password(data.getPassword()) // temporario
                .telefones(data.getTelefones())
                .cnpj(data.getCnpj())
                .nomeFantasia(data.getNomeFantasia())
                .razaoSocial(data.getRazaoSocial())
                .build();

        userRepository.save(cliente);

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> teste(){


        return ResponseEntity.ok(userRepository.findAll());
    }

}
