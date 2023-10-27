package com.example.puclove.Security;

import com.example.puclove.user.AutheticationDTO;
import com.example.puclove.user.RegisterDTO;
import com.example.puclove.user.User;
import com.example.puclove.user.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Esta classe representa os endpoints da API REST relacionados à autenticação e registro de usuários.
 * Todos os endpoints estão mapeados sob o caminho base "/auth".
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint para login de usuário. Espera um corpo de requisição JSON contendo credenciais de login.
     *
     * @param authenticationDTO Um AuthenticationDTO validado contendo login e senha.
     * @return ResponseEntity com um token JWT se a autenticação for bem-sucedida.
     */
    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> login(@RequestBody @Validated AutheticationDTO authenticationDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Endpoint para registro de usuário. Espera um corpo de requisição JSON com campos do RegisterDTO.
     *
     * @param registerDTO Um RegisterDTO validado contendo dados de registro do usuário.
     * @return ResponseEntity indicando o sucesso ou falha do processo de registro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterDTO registerDTO){
        if(this.userRepository.findByLogin(registerDTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = User.builder()
                .name(registerDTO.name())
                .email(registerDTO.email())
                .password(encryptedPassword)
                .birthDate(registerDTO.birthDate())
                .course(registerDTO.course())
                .campus(registerDTO.campus())
                .interests(registerDTO.interests())
                .instagram(registerDTO.instagram())
                .intention(registerDTO.intention())
                .role(registerDTO.role())
                .build();

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}

