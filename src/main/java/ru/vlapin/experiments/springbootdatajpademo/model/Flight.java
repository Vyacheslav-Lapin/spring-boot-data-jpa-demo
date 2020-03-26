package ru.vlapin.experiments.springbootstartersdemo.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Flight {

  @Id
  @GeneratedValue
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  Long id;

  String origin;
  String destination;
  LocalDateTime scheduledAt;
}
