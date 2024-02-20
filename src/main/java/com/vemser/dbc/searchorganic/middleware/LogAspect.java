package com.vemser.dbc.searchorganic.middleware;

import com.vemser.dbc.searchorganic.model.Log;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;

    @Before("execution(* com.vemser.dbc.searchorganic.service..*(..))")
    public void logAntesExecucao(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (authentication != null) ? authentication.getName() : "usuário não autenticado";
        String methodName = joinPoint.getSignature().toShortString();
        String name = "Usuário ID: " + userId + " executando método: " + methodName;

        if (userId.equals("anonymousUser")) {
            name = "Usuário não autenticado executando método: " + methodName;
        }

        Log log = new Log(name);

        logService.save(log);
    }
}
