package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	private String username; 
	
	@Column(nullable = false) //OAuth를 이용해 SSO를 로그인하려면 password가 필요없기 때문에 nullable 설정
	private String password;
	
	private String role;
	
	private String authProvider; // 나중에 OAuth에서 사용할 유저 정보 제공자

}
