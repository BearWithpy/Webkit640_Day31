package org.webit.todo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webit.todo.dto.ResponseDTO;
import org.webit.todo.dto.TestRequestBodyDTO;
import org.webit.todo.service.TodoService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    TodoService todoService;

    @GetMapping("/todo/test")
    public String todoTest(){
        return todoService.testService();
    }

    @GetMapping("/")
    public String hello() {
        // @RestController를 사용하면
        // 리턴되는 문자열이 브라우저에 바로 출력 된다.
        return "{\"message\":\"Hello KIT - GET\"}";
    }

    @PostMapping("/hello")
    public String helloProc(
            @RequestParam("user")
            String user,
            @RequestParam("message")
            String message) {
        return String.format("{\"user\":\"%s\",\"message\":\"%s\"}", user, message);
    }

    @GetMapping("/saram/{id}/{message}")
    public String pathVariables(
            @PathVariable(required = true) String id,
            @PathVariable String message
    ) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("no",1);
        jsonObject.put("id", id);
        jsonObject.put("message", message);
        return jsonObject.toString(2);

    }

    @GetMapping("/requestBody")
    public String testRequestBody(@RequestBody TestRequestBodyDTO reqDTO) {
        return reqDTO.toString();
    }

    @GetMapping("/requestBody2")
    public TestRequestBodyDTO testRequestBody2(@RequestBody TestRequestBodyDTO reqDTO) {
        return reqDTO;
    }

    @GetMapping("/responseBody")
    public ResponseDTO<String> responseBody() {
        List<String> list = new ArrayList();
        list.add("Hello SpringBoot world");
        ResponseDTO<String> response = ResponseDTO.<String>builder().error("200").data(list).build();
        return response;
    }

    @GetMapping("/responseEntity")
    public ResponseEntity<?> responseEntity() {
        List<String> list = new ArrayList<String>();
        list.add("Hello World! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        // http status 400ß
        return ResponseEntity.badRequest().body(response);

        // http status 200
        //return ResponseEntity.ok().body(response);
    }




}
