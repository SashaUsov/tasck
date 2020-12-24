package com.test.task.domein;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class GetInfoRequest {

    @Positive(message = "id must be positive")
    @NotNull(message = "Please enter id")
    private Long id;
}
