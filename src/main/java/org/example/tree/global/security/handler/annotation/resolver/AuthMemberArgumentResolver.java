package org.example.tree.global.security.handler.annotation.resolver;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.converter.MemberConverter;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.security.handler.annotation.AuthMember;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * supportsParameter
     * - 해당 파라미터를 지원하는지 여부를 반환
     * - AuthMember 어노테이션이 없거나, Member 타입이 아닌 경우 false 반환
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthMember authUser = parameter.getParameterAnnotation(AuthMember.class); // 메서드 파라미터에서 @AuthMember 찾기
        if (authUser == null) return false;
        if (parameter.getParameterType().equals(Member.class) == false) {
            return false;
        }
        return true;
    }

    /**
     * resolveArgument
     * 실제로 파라미터의 값을 해석해주는 메서드
     *  파라미터에 전달할 객체를 반환
     * - SecurityContextHolder에서 인증 객체를 가져와서 Member 객체로 변환하여 반환
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        if (principal == null || principal.getClass() == String.class) { //Authentication 객체가 null이거나, principal이 String 타입('anonymousUser')인 경우
            throw new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        Member member = MemberConverter.toMemberSecurity(authenticationToken.getName());
        return member;
    }
}
