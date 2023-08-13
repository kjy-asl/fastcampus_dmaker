package com.fastcampus.programming.DMaker.dto;

import com.fastcampus.programming.DMaker.code.StatusCode;
import com.fastcampus.programming.DMaker.entity.Developer;
import com.fastcampus.programming.DMaker.type.DeveloperLevel;
import com.fastcampus.programming.DMaker.type.DeveloperSkillType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private StatusCode statusCode;
    private String name;
    private Integer age;

    public static DeveloperDetailDto fromEntity(Developer developer){
        return DeveloperDetailDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .experienceYears(developer.getExperienceYears())
                .statusCode(developer.getStatusCode())
                .name(developer.getName())
                .age(developer.getAge())
                .memberId(developer.getMemberId())
                .build();
    }
}
