package ru.vlapin.experiments.springbootdatajpademo.model;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class Flight {

  @Id
  @GeneratedValue
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  Long id;

  @Default
  @NonNull
  String origin = "New York";

  @NonNull
  @Default
  String destination = "Amsterdam";

  @Default
  @NonNull
  LocalDateTime scheduledAt = LocalDateTime.parse("2011-12-13T12:12:00");
}
