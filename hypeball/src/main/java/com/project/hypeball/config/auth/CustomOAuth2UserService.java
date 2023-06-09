package com.project.hypeball.config.auth;

import com.project.hypeball.config.auth.dto.OAuthAttributes;
import com.project.hypeball.config.auth.dto.LoginMember;
import com.project.hypeball.domain.Member;
import com.project.hypeball.repository.MemberRepository;
import com.project.hypeball.web.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("======CustomOAuth2UserService loadUser() 실행 ===========");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest); // 유저 정보
        log.info("유저 정보 = {}", oAuth2User);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId(); // OAuth 서비스 이름(ex. kakao, naver, google)
        log.info("registrationId = {}", registrationId);

        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
        log.info("userNameAttributeName = {}", userNameAttributeName);

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(member)); //4

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority((member.getRoleKey()))),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        ); // 리턴할 때 세션 생김
    }

    private Member saveOrUpdate (OAuthAttributes attributes) {
        Member member = memberRepository.findByEmailAndProvider(attributes.getEmail(), attributes.getProvider())
                .map(entity -> entity.update(attributes.getName()
                       // , attributes.getPicture()
                ))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }
}
