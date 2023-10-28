package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		String str = todoService.testService(); // 테스트 서비스 사용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		// ResponseEntity.ok(response) 를 사용해도 상관 없음
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			String temporaryUserId = "temporary-user";

			// (1) TodoENtity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);

			// (2) id를 null로 초기화 - 생성 당시에는 id가 없어야함
			entity.setId(null);

			// (3) 임시 유저 아이디 설정(아직 인증,인가 기능 없음)
			entity.setUserId(temporaryUserId);

			// (4) 서비스를 이용해 Todo엔티티를 생성
			List<TodoEntity> entities = todoService.create(entity);

			// (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			// (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

			// (7) ResponseDTO를 리턴
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (8) 에러가 나면 dto 대신 error에 메시지를 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping
	public ResponseEntity<?> retrieveTodoList() {
		String temporaryUserId = "temporary-user";

		// (1) 서비스 메서드의 retrieve 메서드를 사용해 Todo리스트를 가져온다
		List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 todoDTO리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		// (6) 변환된 Todo리스트를 이용해 ResponseDTO를 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

		// (7) ResponseDTO를 리턴
		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		String temporaryUserId = "temporary-user";

		// (1) dto를 entity로 변환
		TodoEntity entity = TodoDTO.toEntity(dto);

		// (2)id를 초기화
		entity.setUserId(temporaryUserId);

		// (3) 서비스를 이용해 entity 업데이트
		List<TodoEntity> entities = todoService.update(entity);

		// (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		// (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

		// (7) ResponseDTO를 리턴
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
		try {
			String temporaryUserId = "temporary-user";

			// (1) dto를 entity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			// (2)id를 초기화
			entity.setUserId(temporaryUserId);
			
			//(3)entity삭제
			List<TodoEntity> entities = todoService.delete(entity);
			
			// (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			// (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

			// (7) ResponseDTO를 리턴
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

}
