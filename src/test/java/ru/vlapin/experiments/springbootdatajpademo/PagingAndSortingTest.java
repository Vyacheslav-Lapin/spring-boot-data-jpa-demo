package ru.vlapin.experiments.springbootstartersdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vlapin.experiments.springbootstartersdemo.DerivedQueryTest.createFlight;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import ru.vlapin.experiments.springbootstartersdemo.dao.FlightRepository;
import ru.vlapin.experiments.springbootstartersdemo.model.Flight;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PagingAndSortingTest {

  FlightRepository flightRepository;

  @BeforeEach
  void setUp() {
    flightRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  @DisplayName("Should sort flights by destination")
  void shouldSortFlightsByDestinationTest() {
    Flight flight1 = createFlight("Madrid");
    Flight flight2 = createFlight("London");
    Flight flight3 = createFlight("Paris");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    Iterable<Flight> flights = flightRepository.findAll(Sort.by("origin"));

    assertThat(flights)
        .hasSize(3)
        .containsExactly(flight2, flight1, flight3);
  }
}
