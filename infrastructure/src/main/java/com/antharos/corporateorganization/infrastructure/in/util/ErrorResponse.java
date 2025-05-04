package com.antharos.corporateorganization.infrastructure.in.util;

import java.util.List;

public record ErrorResponse(List<FieldError> errors) {}
