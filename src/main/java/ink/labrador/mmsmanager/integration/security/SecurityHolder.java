package ink.labrador.mmsmanager.integration.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import ink.labrador.mmsmanager.domain.LoggedSysUser;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Order(-1)
public class SecurityHolder {
    private static Cache<String, LoggedSysUser> cache;

    public SecurityHolder(
            final SecurityProperties securityProperties
            ) {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(securityProperties.getTokenExpireTimeInMinute(), TimeUnit.MINUTES)
                .maximumSize(100_000)
                .build();
    }

    public static void put(String token, LoggedSysUser user) {
        cache.put(token, user);
    }

    public static LoggedSysUser get(String token) {
        return cache.getIfPresent(token);
    }

    public static void remove(String token) {
        cache.invalidate(token);
    }

    public static void removeAll(List<String> tokens) {
        tokens.forEach(SecurityHolder::remove);
    }
}
