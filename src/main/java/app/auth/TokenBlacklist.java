package app.auth;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBlacklist {
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blacklist(String jti) {
        blacklistedTokens.add(jti);
    }

    public boolean isBlacklisted(String jti) {
        return blacklistedTokens.contains(jti);
    }
}
