package ru.vlapin.experiments.springbootdatajpademo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vlapin.experiments.springbootdatajpademo.dao.FlightRepository;
import ru.vlapin.experiments.springbootdatajpademo.model.Flight;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DerivedQueryTest {

  FlightRepository flightRepository;

  @BeforeEach
  void setUp() {
    flightRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  @DisplayName("Should find flights from London")
  void shouldFindFlightsFromLondonTest() {
    var flight1 = Flight.builder().origin("London").build();
    var flight2 = Flight.builder().origin("London").build();
    var flight3 = Flight.builder().origin("New York").build();

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    List<Flight> flights = flightRepository.findByOrigin("London");

    assertThat(flights).hasSize(2)
        .first()
        .isEqualToComparingFieldByField(flight1);

    assertThat(flights.get(1))
        .isEqualToComparingFieldByField(flight2);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should find flights from London to Paris")
  void shouldFindFlightsFromLondonToParisTest() {
    var flight1 = Flight.builder().origin("London").destination("Paris").build();
    var flight2 = Flight.builder().origin("Paris").destination("Madrid").build();
    var flight3 = Flight.builder().origin("Amsterdam").destination("Paris").build();

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    List<Flight> flights = flightRepository.findByOriginAndDestination("London", "Paris");

    assertThat(flights)
        .hasSize(1)
        .last()
        .isEqualToComparingFieldByField(flight1);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should find flights from London or Madrid")
  void shouldFindFlightsFromLondonOrMadridTest() {
    var flight1 = Flight.builder().origin("London").destination("Paris").build();
    var flight2 = Flight.builder().origin("Madrid").destination("Paris").build();
    var flight3 = Flight.builder().origin("Amsterdam").destination("Paris").build();

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    List<Flight> flights = flightRepository.findByOriginIn("London", "Madrid");

    assertThat(flights)
        .hasSize(2)
        .last()
        .isEqualToComparingFieldByField(flight2);

    assertThat(flights.get(0)).isEqualToComparingFieldByField(flight1);
  }

}
