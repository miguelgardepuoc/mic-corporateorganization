package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.jobtitle;

import java.util.UUID;

public record JobTitleResponse(UUID id, String description, String photoUrl) {}
