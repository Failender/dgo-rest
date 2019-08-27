package de.failender.dgo.security;


import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;

import java.net.UnknownServiceException;
import java.util.List;

public class DgoSecurityContext {

    private static ThreadLocal<SecurityContext> contextThreadLocal = new ThreadLocal<>();

    public static void resetContext() {
        contextThreadLocal.set(null);
    }

    public static final String VIEW_ALL = "VIEW_ALL";

    public static void login(String username, List<String> permissions) {
        contextThreadLocal.set(new SecurityContext(UserRepositoryService.findUserByName(username), permissions));

    }

    private static class SecurityContext {
        private final UserEntity userEntity;
        private final List<String> permissions;

        public SecurityContext(UserEntity userEntity, List<String> permissions) {
            this.userEntity = userEntity;
            this.permissions = permissions;
        }

        public UserEntity getUserEntity() {
            return userEntity;
        }
    }

    public static void checkPermission(String permission) {
        SecurityContext ctx = contextThreadLocal.get();
        if (ctx == null) {
            throw new UserNotLoggedInException();
        }
        if(ctx.permissions.contains(permission)) {
            return;
        }
        throw new NoPermissionException();
    }

    public static UserEntity getAuthenticatedUser() {
        SecurityContext ctx = contextThreadLocal.get();
        if (ctx == null) {
            throw new UserNotLoggedInException();
        }
        return ctx.userEntity;
    }
}
