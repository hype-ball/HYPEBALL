package com.project.hypeball.repository;

import com.project.hypeball.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryInterface{

}
