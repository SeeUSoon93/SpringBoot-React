package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	/* 모델 자체를 반환하지 않고 DTO로 변환해 리턴함
	 * 1. 캡슐화 하기 위함
	 * 	  - 외부 사용자에게 서비스 내부의 로직, DB 구조등을 숨길 수 있음
	 * 2. 클라이언트가 필요한 정보를 모델이 전부 포함하지 않을 수도 있다
	 * 	  - 에러 메시지와 같은 경우, 모델에 담기는 애매함. DTO에 메시지 필드를 선언하고 DTO에 포함하면 됨 */
	
	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
		
	}
	
	// DTO를 Entity로 변환하기 위한 메서드 -
	// 컨트롤러가 유저에게서 TodoDTO를 요청 바디로 넘겨받고 Entity로 변환해 저장해야함
	// 또 서비스의 create()가 리턴하는 Rntity를 DTO로 변환해 리턴해야함
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
	

}
