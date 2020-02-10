package pl.connectis.cinemareservationsapp.security;

public class JwtProperties {
    public static final String SECRET = "TajnyTermosowyToken";
    public static final int EXPIRATION_TIME = 3600000; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
