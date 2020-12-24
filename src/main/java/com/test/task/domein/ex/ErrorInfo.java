package com.test.task.domein.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@AllArgsConstructor
public class ErrorInfo {
    private final String code;
    private final String message;
}
