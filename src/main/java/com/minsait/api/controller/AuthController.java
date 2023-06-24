package com.minsait.api.controller;

import com.minsait.api.controller.dto.GetTokenRequest;
import com.minsait.api.controller.dto.GetTokenResponse;
import com.minsait.api.repository.UsuarioRepository;
import com.minsait.api.sicurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/get-token")
    public ResponseEntity<GetTokenResponse> getToken(@RequestBody GetTokenRequest request){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final var usuario =  usuarioRepository.findByLogin(request.getUserName());

        if(Objects.isNull(usuario)){
            return new ResponseEntity<>(GetTokenResponse.builder().build(), HttpStatus.UNAUTHORIZED);
        }

        boolean senhaIguais = encoder.matches(request.getPassword(),usuario.getSenha());

        if(senhaIguais){
            final ArrayList<String> permissions = new ArrayList<>();
            final String[] permissonsSprint = usuario.getPermissoes().split(",");

            for (String permissao : permissonsSprint) {
                permissions.add(permissao);
            }

            final var token =jwtUtil.generateToken(usuario.getNome(), permissions, usuario.getId().intValue());
            return new ResponseEntity<>(GetTokenResponse.builder()
                    .accessToken(token)
                    .build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(GetTokenResponse.builder().build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
