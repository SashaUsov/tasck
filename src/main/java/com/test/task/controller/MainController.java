package com.test.task.controller;

import com.test.task.domein.GetInfoRequest;
import com.test.task.domein.InfoResponse;
import com.test.task.service.RequestHandlerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("check")
public class MainController {

    private final RequestHandlerService requestHandlerService;

    public MainController(RequestHandlerService requestHandlerService) {
        this.requestHandlerService = requestHandlerService;
    }

    @PostMapping("id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public InfoResponse doCheck(@RequestBody @Valid GetInfoRequest request) {
        return requestHandlerService.handleRequest(request);
    }
}
