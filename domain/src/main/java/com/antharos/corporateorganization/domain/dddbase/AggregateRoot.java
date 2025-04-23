package com.antharos.corporateorganization.domain.dddbase;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class AggregateRoot {
  private final List<Object> domainEvents = new ArrayList<>();

  protected void addDomainEvent(Object event) {
    domainEvents.add(event);
  }

  public void clearDomainEvents() {
    domainEvents.clear();
  }
}
