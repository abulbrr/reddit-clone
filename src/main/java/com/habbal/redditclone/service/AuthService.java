package com.habbal.redditclone.service;

import com.habbal.redditclone.dto.RegisterRequest;
import com.habbal.redditclone.exception.InvalidTokenException;
import com.habbal.redditclone.exception.UserNotFoundException;
import com.habbal.redditclone.model.User;
import com.habbal.redditclone.model.VerificationToken;
import com.habbal.redditclone.repository.UserRepository;
import com.habbal.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .username(registerRequest.getUsername())
                .created(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);

        String verificationToken = generateVerificationToken(user);

        mailService.sendAccountActivationEmail(user.getEmail(), verificationToken);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
//        verificationToken.setExpiryDate();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchAndEnableUser(verificationToken.orElseThrow(InvalidTokenException::new));
    }

    private void fetchAndEnableUser(VerificationToken verificationToken) {
        Long userId = verificationToken.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(verificationToken.getUser().getEmail()));

        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
    }
}
