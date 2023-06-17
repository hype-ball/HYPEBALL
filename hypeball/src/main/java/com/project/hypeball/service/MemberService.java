package com.project.hypeball.service;

import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member get(LoginMember loginMember) {
        return memberRepository.findByEmailAndProvider(loginMember.getEmail(), loginMember.getProvider()).orElse(null);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }
}

