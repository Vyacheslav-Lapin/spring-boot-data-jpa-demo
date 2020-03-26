package ru.vlapin.experiments.springbootstartersdemo;

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
import ru.vlapin.experiments.springbootstartersdemo.dao.FlightRepository;
import ru.vlapin.experiments.springbootstartersdemo.model.Flight;

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
    Flight flight1 = createFlight("London");
    Flight flight2 = createFlight("London");
    Flight flight3 = createFlight("New York");

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
    Flight flight1 = createFlight("London", "Paris");
    Flight flight2 = createFlight("Paris", "Madrid");
    Flight flight3 = createFlight("Amsterdam", "Paris");

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
    Flight flight1 = createFlight("London", "Paris");
    Flight flight2 = createFlight("Madrid", "Paris");
    Flight flight3 = createFlight("Amsterdam", "Paris");

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

  public static @NotNull Flight createFlight(@NotNull String origin) {
    return createFlight(origin, "Amsterdam");
  }

  public static @NotNull Flight createFlight(@NotNull String origin, @NotNull String destination) {
    return new Flight()
               .setOrigin(origin)
               .setDestination(destination)
               .setScheduledAt(LocalDateTime.parse("2011-12-13T12:12:00"));
  }

}
