package org.webit.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.webit.todo.domain.TodoEntity;
import org.webit.todo.service.TodoService;

import java.util.List;

@SpringBootTest
public class TestTodoService {

    @Autowired
    TodoService todoService;

    @Test
    void test(){
        TodoEntity todo = new TodoEntity("t0001","ADMIN","스터디하기",false);
        List<TodoEntity> list = todoService.create(todo);
        System.out.println(list);
    }

}
