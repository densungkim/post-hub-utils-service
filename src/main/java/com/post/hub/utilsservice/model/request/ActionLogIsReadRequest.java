package com.post.hub.utilsservice.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionLogIsReadRequest {

    @NotNull(message = "'userId' cannot be null")
    private Integer userId;

    @NotEmpty(message = "'ids' cannot be empty")
    private List<Integer> ids;

}
