package com.antharos.corporateorganization.infrastructure.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_COMPANY_MANAGEMENT', 'ROLE_DEPARTMENT_HEAD')")
public @interface ManagementOnly {}
