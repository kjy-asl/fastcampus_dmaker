package com.fastcampus.programming.DMaker.service;


import com.fastcampus.programming.DMaker.code.StatusCode;
import com.fastcampus.programming.DMaker.dto.CreateDeveloper;
import com.fastcampus.programming.DMaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.DMaker.dto.DeveloperDto;
import com.fastcampus.programming.DMaker.dto.EditDeveloper;
import com.fastcampus.programming.DMaker.entity.Developer;
import com.fastcampus.programming.DMaker.entity.RetiredDeveloper;
import com.fastcampus.programming.DMaker.exception.DMakerException;
import com.fastcampus.programming.DMaker.repository.DeveloperRepository;
import com.fastcampus.programming.DMaker.repository.RetiredDeveloperRepository;
import com.fastcampus.programming.DMaker.type.DeveloperLevel;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fastcampus.programming.DMaker.exception.DMakerErrorCode.*;
import static com.fastcampus.programming.DMaker.type.DeveloperLevel.SENIOR;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;



    @Transactional
    public CreateDeveloper.Response createDeveloper(
            CreateDeveloper.Request request
    ){
        validateCreateDeveloperRequest(request);

        //business logic start
        return CreateDeveloper.Response.fromEntity(
                developerRepository.save(
                        createDeveloperFromRequest(request)
                )
        );
    }

    private Developer createDeveloperFromRequest(CreateDeveloper.Request request){
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
    }

    private void validateCreateDeveloperRequest(@NonNull CreateDeveloper.Request request) {
        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent(developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });
    }

    @Transactional
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeveloperDetailDto getDeveloperDetailDto(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    private Developer getDeveloperByMemberId(String memberId){
        return developerRepository.findByMemberId(memberId)
                .orElseThrow(()->new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(()->new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

    private static void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if(developerLevel == SENIOR
                && experienceYears <10){
            throw new DMakerException(LEVEL_EXPERIENCE_NOT_MATCHED);
        }
        if(developerLevel ==DeveloperLevel.JUNGNIOR
                && (experienceYears <4|| experienceYears >10)){
            throw new DMakerException(LEVEL_EXPERIENCE_NOT_MATCHED);
        }
        if(developerLevel ==DeveloperLevel.JUNIOR
                && experienceYears >4){
            throw new DMakerException(LEVEL_EXPERIENCE_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        //1. EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);


        //2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();
        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }
}
