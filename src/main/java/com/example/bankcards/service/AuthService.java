package com.example.bankcards.service;

import com.example.bankcards.dto.JwtResponseDto;
import com.example.bankcards.dto.LoginRequestDto;
import com.example.bankcards.dto.RegisterUserRequestDto;
import com.example.bankcards.entity.AccountStatus;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ConflictException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void register(RegisterUserRequestDto dto){
        if (userRepository.existsByEmail(dto.email())){
            throw new ConflictException("Email already exists");
        }
        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setRole(Role.USER);
        user.setAccountStatus(AccountStatus.ACTIVE);

        userRepository.save(user);
    }

    public JwtResponseDto login(LoginRequestDto dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        String token = jwtProvider.generateToken(
                principal.getUsername(),
                principal.getAuthorities().stream()
                        .map(a -> a.getAuthority().replace("ROLE_",""))
                        .toList()
        );
        return new JwtResponseDto(token);
    }
}
