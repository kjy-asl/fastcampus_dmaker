package com.fastcampus.programming.DMaker.dto;

import com.fastcampus.programming.DMaker.entity.Developer;
import com.fastcampus.programming.DMaker.type.DeveloperLevel;
import com.fastcampus.programming.DMaker.type.DeveloperSkillType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    public static DeveloperDto fromEntity(Developer developer){
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}
