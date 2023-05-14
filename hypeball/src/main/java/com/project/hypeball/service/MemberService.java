package com.project.hypeball.service;

import com.project.hypeball.domain.Member;
import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import com.project.hypeball.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member get(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}

