package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	// TodoDTO뿐 아니라 다른 모델의 DTO도 ResponseDTO를 이용해 리턴할 수 있도록 자바 Generic사용
	// 클래스명<타입 파라미터> 변수명 = new 클래스명<타입 파라미터>(); T사용
	private String error;
	private List<T> data;

}
