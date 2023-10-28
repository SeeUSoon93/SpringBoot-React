package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //  생성자들 사용한 오브젝트 생성과 비슷 - 생성자와는 달리 매개변수 순서를 기억할 필요 없음
@NoArgsConstructor // 매개변수가 없는 생성자 구현
@AllArgsConstructor // 모든 변수를 매개변수로 받는 생성자 구현
@Data
@Entity
public class TodoEntity {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String userId;
	private String title;
	private boolean done;
}
