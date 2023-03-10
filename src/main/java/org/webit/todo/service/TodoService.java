package org.webit.todo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.webit.todo.domain.TodoEntity;
import org.webit.todo.repository.TodoRepository;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("entity가 null입니다.");
            throw new RuntimeException("entity가 null입니다.");
        }
        if(entity.getUserId() == null) {
            log.warn("userId가 없습니다!");
            throw new RuntimeException("userId가 없습니다!");
        }
    }

    // 매개 변수로 사용된 entity참조 변수를 이용해서 원본이 바뀌지 않도록 final 선언
    public List<TodoEntity> create(final TodoEntity entity) {
        // 유효성 검사 (null 체크) 필수!
        validate(entity);

        repository.save(entity);
        // 처리 후 로그 출력
        log.info("Entity id: {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    // 검색 기능
    public List<TodoEntity> retrieve(final String userId) {
        // containing을 사용하고 있기 때문에 모든 글자를 치지 않아도 검색이 가능함!
        return repository.findByUserIdContaining(userId);
    }

    // 수정
    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);
        // 검색
        // userid가 아닌 todo의 id를 사용하고 있다!!!!

        // json 예시
//        {
//            "id":"ff80808186c8f7940186c8f7e3be0000",
//                "userId" : "temporary-user",
//                "title" : "그냥 자고 싶다"
//        }
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        // 새로운 내용으로 수정한다. NPE 체크.
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            // 다시 저장 (update메소드가 따로 없고 save를 활용)
            repository.save(todo);
        });
        return retrieve(entity.getUserId());
    }

    // 삭제
    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        } catch (Exception e) {
            throw new RuntimeException(entity.getId() + " 삭제 시 에러 발생 >>> " + e.getMessage());
        }
        return retrieve(entity.getUserId());
    }

    public String testService() {
        TodoEntity todoEntity = TodoEntity.builder().title("저녁 먹고 스터디 하기").build();
        repository.save(todoEntity);
        System.out.println();
        log.info(todoEntity.getId());
        TodoEntity savedEntity = repository.findById(todoEntity.getId()).get();
        return savedEntity.toString();
    }

    public List<TodoEntity> findAll() {
        return repository.findAll();
    }
}