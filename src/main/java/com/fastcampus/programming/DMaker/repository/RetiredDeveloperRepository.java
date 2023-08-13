package com.fastcampus.programming.DMaker.repository;

import com.fastcampus.programming.DMaker.entity.Developer;
import com.fastcampus.programming.DMaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
