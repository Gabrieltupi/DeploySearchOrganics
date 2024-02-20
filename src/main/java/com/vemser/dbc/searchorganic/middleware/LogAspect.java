package com.vemser.dbc.searchorganic.middleware;

import com.vemser.dbc.searchorganic.model.Log;
import com.vemser.dbc.searchorganic.utils.JsonUtils;
import com.vemser.dbc.searchorganic.utils.TipoLog;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;

    @AfterReturning(pointcut = "execution(* com.vemser.dbc.searchorganic.service..*(..))", returning = "result")
    public void logDepoisExecucaoSucesso(JoinPoint joinPoint, Object result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (authentication != null) ? authentication.getName() : "usuário não autenticado";
        String methodName = joinPoint.getSignature().toShortString();

        if (userId.equals("anonymousUser")) {
            userId = "Não autenticado";
        }

        String payload = JsonUtils.convertObjectToJson(result);

        Log log = new Log(userId, methodName, TipoLog.INFO, payload);

        logService.save(log);
    }

    @AfterThrowing(pointcut = "execution(* com.vemser.dbc.searchorganic.service..*(..))", throwing = "exception")
    public void logDepoisExecucaoFalha(JoinPoint joinPoint, Exception exception) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (authentication != null) ? authentication.getName() : "usuário não autenticado";
        String methodName = joinPoint.getSignature().toShortString();

        if (userId.equals("anonymousUser")) {
            userId = "Não autenticado";
        }

        Log log = new Log(userId, methodName, TipoLog.ERROR, exception.getMessage());

        logService.save(log);
    }
}
