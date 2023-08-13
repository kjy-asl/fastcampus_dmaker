package com.fastcampus.programming.DMaker.controller;

import com.fastcampus.programming.DMaker.dto.*;
import com.fastcampus.programming.DMaker.exception.DMakerException;
import com.fastcampus.programming.DMaker.service.DMakerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
사용자 입력이 처음으로 받아들여지는 컨트롤러
 */
@RestController //@Bean 타입
@Slf4j
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;
    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers(){
        // Get /developers HTTP/1.1
        log.info("Get /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }
    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable final String memberId
    ){
        log.info("Get /developer/{memberId} HTTP/1.1");

        return dMakerService.getDeveloperDetailDto(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDeveloper(
            @Valid @RequestBody final CreateDeveloper.Request request
            ){
        log.info("request {} :", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable final String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ){
        log.info("Get /developer HTTP/1.1");

        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable String memberId
    ){
        return dMakerService.deleteDeveloper(memberId);
    }



}
