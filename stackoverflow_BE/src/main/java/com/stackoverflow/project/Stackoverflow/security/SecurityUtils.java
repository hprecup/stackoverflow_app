package com.stackoverflow.project.Stackoverflow.security;

import com.stackoverflow.project.Stackoverflow.model.User;
import com.stackoverflow.project.Stackoverflow.model.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtils {

    public static User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User)authentication.getPrincipal();
    }

    public static boolean isUserAdmin(User user) {
        List<UserRole> userRoles = user.getRoles();
        for(UserRole userRole : userRoles){
            if("MODERATOR".equals(userRole.getRole().getName().name())){
                return true;
            }
        }
        return false;
    }
}
