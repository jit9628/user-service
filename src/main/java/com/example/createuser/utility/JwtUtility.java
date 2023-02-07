package com.example.createuser.utility;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.createuser.entity.UserLogin;

public class JwtUtility {
	public static final String SECRET = "secret";
	public static final String ISSUER = "issuer";

	// this function responsible for creating token
	public static String createToken(UserLogin userLogin) {
		String token = JWT.create()
				.withJWTId(String.valueOf(userLogin.getUserId()))
				.withSubject(userLogin.getEmail())
				.withClaim("id", userLogin.getUserId())
				.withClaim("username", userLogin.getUsername())
				.withClaim("email", userLogin.getEmail())
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60L * 60L * 24L)).withIssuer(ISSUER)
				.sign(Algorithm.HMAC256(SECRET));
		return token;
	}

	public static UserLogin getUserDtoFromToken(String token) {
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		System.out.println("decode tockens is ::" + decodedJWT);
		String id = decodedJWT.getClaim("id").asString();
		String username = decodedJWT.getClaim("username").asString();
		String email = decodedJWT.getClaim("email").asString();
		UserLogin userLogin = new UserLogin();
		userLogin.setUserId(Integer.parseInt(id));
		userLogin.setEmail(email);
		userLogin.setUsername(username);
		System.out.println("get the user from the tockens  :: " + userLogin);
		return userLogin;
	}

	public static Boolean verifyToken(String token) {
		try {
			System.out.println("verify tockens is :: "
					+ JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build().verify(token));
			// JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build().verify(token);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
