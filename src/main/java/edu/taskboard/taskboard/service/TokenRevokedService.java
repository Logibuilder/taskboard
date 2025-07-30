package edu.taskboard.taskboard.service;


import edu.taskboard.taskboard.model.TokenRevoked;
import edu.taskboard.taskboard.repository.TokenRevokedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRevokedService {

    @Autowired
    private TokenRevokedRepository tokenRevokedRepository;

    public void revokToken(String jti) {
        TokenRevoked tokenRevoked = new TokenRevoked(jti);
    }

    public boolean isTokenRevoked(String jti) {
        return tokenRevokedRepository.existsByJti(jti);
    }
}
