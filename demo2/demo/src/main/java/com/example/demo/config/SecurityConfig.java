package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.JwtAuthenticationFilter;

@Configuration // 스프링의 환경설정 파일임을 명시 - 스프링 시큐리티 설정을 위해 사용
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http 	
			.authorizeHttpRequests((authorizeHttpRequests)-> authorizeHttpRequests					
					.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) 
			.csrf((csrf)-> csrf
					.ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
			.headers((headers)->headers
					.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			.formLogin((formLogin)->formLogin
					.loginPage("/auth/login") // 로그인시 어디로 이동해야할지 알려줌
					.defaultSuccessUrl("/"))
			.logout((logout)->logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")) // 로그아웃 요청시 이동하는 페이지
					.logoutSuccessUrl("/") // 로그아웃 성공시 이동
					.invalidateHttpSession(true))
			.sessionManagement((sessionManagement)->sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    ; // 로그아웃시 세션 삭제
		
		http.addFilterAfter(jwtAuthenticationFilter,CorsFilter.class);
		return http.build();	
	}
	
	// 암호화하는 빈
	@Bean
	PasswordEncoder passwordEncoder() {
		// 비밀번호를 암호화하기 위해 BCrypt 해싱 함수를 사용하는 PasswordEncoder 빈을 생성함
		return new BCryptPasswordEncoder();
	}
	
	// 인증과정을 관리하는 빈
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
		// AuthenticationManager - 얘가 인증을 관리함
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
}
