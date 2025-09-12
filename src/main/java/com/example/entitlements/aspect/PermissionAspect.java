package com.example.entitlements.aspect;

import com.example.entitlements.annotation.HasPermission;
import com.example.entitlements.service.EntitlementService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final EntitlementService entitlementService;

    @Around("@annotation(hasPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, HasPermission hasPermission) throws Throwable {
        String permission = hasPermission.value();
        String paramName = hasPermission.parameter();
        String paramType = hasPermission.parameterType();

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        Object paramValue = null;
        if (paramName != null && !paramName.isEmpty()) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getName().equals(paramName)) {
                    paramValue = args[i];
                    break;
                }
            }
        }

        // get current user SOE id from security context - fallback to null
        String soeId = entitlementService.getCurrentSoeId();

        boolean allowed = entitlementService.hasPermission(soeId, permission, paramValue, paramType);
        if (!allowed) {
            throw new SecurityException("Access denied: " + permission);
        }
        return joinPoint.proceed();
    }
}
