package com.fastcampus.boardserver.aop;

import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class LoginCheckAspect {
    @Around("@annotation(com.fastcampus.boardserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = (HttpSession) ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String id = null;
        int idIndex = 0;

        String userType = loginCheck.type().toString();
        log.error("lwt check: " + userType);
        switch (userType) {
            case "ADMIN":
                id = SessionUtil.getLoginAdminId(session);
                break;
            case "USER":
                id = SessionUtil.getLoginMemberId(session);
            break;
        }
        if (id == null) {
            log.error(joinPoint.toString() + "accountName : " + id);
            throw  new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID 값을 확인해주세요.") {};
        }

        Object[] modifiedArgs = joinPoint.getArgs();

        if (joinPoint != null)
            modifiedArgs[idIndex] = id;

        return joinPoint.proceed(modifiedArgs);
    }
}
