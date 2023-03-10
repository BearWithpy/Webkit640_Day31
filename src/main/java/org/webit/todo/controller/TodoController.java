package org.webit.todo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.webit.todo.domain.TodoEntity;
import org.webit.todo.dto.ResponseDTO;
import org.webit.todo.dto.TodoDTO;
import org.webit.todo.service.TodoService;

import java.util.List;

import java.util.stream.Collectors;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*") // React프로젝트에서 포트번호가 다른 문제 해결
@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService service;

    String temporaryUserId = "temporary-user";

    // 입력 기능
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            // 1. Entity로 변환
            TodoEntity entity = dto.dtoToEntity();
            // 2. entity의 id는 생성시 null이어 한다.(테이블에 insert될때 Generator로 자동 생성)
            entity.setId(null);
            // 3. 임시 UserId 사용
            entity.setUserId(temporaryUserId);
            // 4. serivice를 이용해서 Entity List 생성
            List<TodoEntity> entities = service.create(entity);
            // 5. TodoEntity 리스틀 TodoDTO 리스트로 변환.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // 6. TodoDTO 리스트를 ResponseDTO로 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO
                    .<TodoDTO>builder().data(dtos).build();
            // 7. ResponseDTO 를 리턴한다.
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            // 예외 발생 시 처리
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO
                    .<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 검색
    @GetMapping
    public ResponseEntity<?> findTodoList(@RequestBody TodoDTO dto) {
        try {
            List<TodoEntity> entities = service.retrieve(dto.getUserId());

            List<TodoDTO> todos = entities.stream()
                    .map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error("검색 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    // 수정
    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        try{// 1. dto로 변환
            TodoEntity entity = dto.dtoToEntity();
            // 2. id 초기화
            entity.setUserId(temporaryUserId);
            // 3. entity로 업데이트
            List<TodoEntity> entities = service.update(entity);
            // 4. Entity리스트를 Todo 리스트로 변환
            List<TodoDTO> todos = entities.stream()
                    .map(TodoDTO::new).collect(Collectors.toList());
            // 5. ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO
                    .<TodoDTO>builder().data(todos).build();
            // 6. ResponseDTO 리턴
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e){
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error("수정 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            TodoEntity entity = dto.dtoToEntity();
            // 2. id 초기화
            entity.setUserId(temporaryUserId);
            // 3. entity로 업데이트
            List<TodoEntity> entities = service.delete(entity);
            ResponseDTO<TodoEntity> response = ResponseDTO
                    .<TodoEntity>builder().data(entities).build();
            // 6. ResponseDTO 리턴
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error("삭제 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> selectAll() {
        try {
            List<TodoEntity> entities = service.findAll();

            List<TodoDTO> todos = entities.stream()
                    .map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO
                    .<TodoDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error("search all list 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }
}
