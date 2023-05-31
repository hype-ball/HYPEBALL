package com.project.hypeball.repository;

import com.project.hypeball.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryInterface{

    Optional<Member> findByEmail(String email);
}
