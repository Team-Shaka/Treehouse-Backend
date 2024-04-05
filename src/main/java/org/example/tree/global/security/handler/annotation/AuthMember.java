package org.example.tree.global.security.handler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임 중에 어노테이션 정보를 조회하고 처리할 수 있도록 설정
@Target(ElementType.PARAMETER) // 어노테이션을 파라미터에만 적용
public @interface AuthMember {
}
