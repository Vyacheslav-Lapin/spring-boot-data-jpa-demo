package ru.vlapin.experiments.springbootdatajpademo;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import ru.vlapin.experiments.springbootdatajpademo.dao.FlightRepository;
import ru.vlapin.experiments.springbootdatajpademo.model.Flight;

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
    var flight1 = Flight.builder().origin("Madrid").build();
    var flight2 = Flight.builder().origin("London").build();
    var flight3 = Flight.builder().origin("Paris").build();

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    assertThat(flightRepository.findAll(Sort.by("origin")))
        .hasSize(3)
        .containsExactly(flight2, flight1, flight3);
  }
}
