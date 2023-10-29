package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로깅을 하기 위한 라이브러리 - 디버깅을 하기위함
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	public String testService() {
		// TodoEntity 생성
		TodoEntity todoEntity = TodoEntity.builder().title("첫번째 할일").build();
		// TodoEntity 저장
		todoRepository.save(todoEntity);
		// TodoEntity 검색
		TodoEntity savedEntity = todoRepository.findById(todoEntity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity){
		// validation 검증하기
		validate(entity);
		todoRepository.save(entity);
		log.info("ENtity Id : {} is saved.",entity.getId());
		return todoRepository.findByUserId(entity.getUserId());
	}
	
	// 검증부분은 다른 메서드에서도 쓰이므로 리팩토링
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("entity is null");
			throw new RuntimeException("entity canot be null");
		}
		
		if (entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}
	
	public List<TodoEntity> retrieve(final String userId){
		return todoRepository.findByUserId(userId);
	}

	// update
	public List<TodoEntity> update(final TodoEntity entity){
		//(1) 저장할 엔티티가 유효한지 확인
		validate(entity);
		
		//(2) 넘겨받은 엔티티 id를 이용해 todoEntity를 가져오기
		final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			//(3) 반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			//(4) 데이터베이스에 새값을 저장
			todoRepository.save(entity);
		});
		
		return retrieve(entity.getUserId());
	}
	
	// delete
	public List<TodoEntity> delete(final TodoEntity entity){
		//(1) 저장할 엔티티가 유효한지 확인
		validate(entity);
		
		try {
			//(2)엔티티를 삭제
			todoRepository.delete(entity);			
		} catch (Exception e) {
			//(3) 에러 발생시 id와 exception을 로깅
			log.error("error deleting entity", entity.getId(),e);
			
			throw new RuntimeException("error deleting entity"+entity.getId());
		}
		return retrieve(entity.getUserId());
	}
	
}
