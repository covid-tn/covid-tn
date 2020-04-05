package fr.covid.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER"; //todo: rename to ROLE_DOCTOR

    public static final String HEAD_OF_SERVICE = "ROLE_HEAD_OF_SERVICE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String HOSPITAL_ADMIN = "ROLE_HOSPITAL_ADMIN";

    public static final String VISITEUR = "ROLE_VISITEUR";

    private AuthoritiesConstants() {
    }
}
