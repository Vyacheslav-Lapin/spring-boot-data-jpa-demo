package ru.vlapin.experiments.springbootstartersdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vlapin.experiments.springbootstartersdemo.dao.FlightRepository;
import ru.vlapin.experiments.springbootstartersdemo.model.Flight;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CrudTest {

  FlightRepository flightRepository;

  @Test
  @SneakyThrows
  @DisplayName("Should perform CRUD operations")
  void shouldPerformCRUDOperationsTest() {

    int count = (int) flightRepository.count();

    var flight = new Flight()
                     .setOrigin("Amsterdam")
                     .setDestination("New York")
                     .setScheduledAt(LocalDateTime.parse("2011-12-13T12:12:00"));

    flightRepository.save(flight);

    assertThat(flightRepository.findById(flight.getId()))
        .contains(flight);

    assertThat(flightRepository.findAll())
        .hasSize(count + 1)
        .last()
        .isEqualToComparingFieldByField(flight);

    flightRepository.deleteById(flight.getId());

    assertThat(flightRepository.count())
        .isZero();
  }
}
