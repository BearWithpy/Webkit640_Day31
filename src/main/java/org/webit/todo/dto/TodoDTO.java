package org.webit.todo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.webit.todo.domain.TodoEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TodoDTO {
    private String id; // 오브젝트 아이디
    private String userId;
    private String title;
    private boolean done;

    // Entity -> DTO 변환
    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity dtoToEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }

    public TodoEntity dtoToEntity() {
        return TodoEntity.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .done(done)
                .build();
    }

}