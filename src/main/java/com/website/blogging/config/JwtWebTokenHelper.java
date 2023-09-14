package com.website.blogging.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class JwtWebTokenHelper  {
		
	private static  String SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	
	public String getUserName(String token) {

		return extractClaims(token,Claims::getSubject);
	}
	
	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
	
	private Key getSigningKey() {
		byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decode);
	}

	public <T> T extractClaims(String token,Function<Claims, T> claimsResolver) {
		final Claims allClaims = getAllClaims(token);
		return claimsResolver.apply(allClaims);
	}
	public String generateToken(Map<String,Object> claims,UserDetails details) {
		return Jwts.builder().setClaims(claims)
				.setSubject(details.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	public String generateToken(UserDetails details) {
		return generateToken(new HashMap<String, Object>(), details);
	}
	private boolean isTokenExperied(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaims(token,Claims::getExpiration);

	}
	public boolean isTokenValid(String token,UserDetails details) {
		final String username= getUserName(token);
		return (username.equals(details.getUsername()))&&! isTokenExperied(token);
	}

}
