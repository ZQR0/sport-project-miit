package com.sport.project.utils;

import com.sport.project.dto.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthorizedUserUtils {

    /**
     * Код для получения текущего аутентифицированного пользователя из контекста Spring
     * @author Yaroslav
     * */
    public static UserDetailsImpl getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (UserDetailsImpl) principal;
            }
        }

        return null;
    }

}
