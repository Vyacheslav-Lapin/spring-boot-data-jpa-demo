package ru.vlapin.experiments.springbootstartersdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vlapin.experiments.springbootstartersdemo.model.Flight;

//@SpringBootTest
@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class SpringBootStartersDemoApplicationTests {

  EntityManager entityManager;

  @Test
  void contextLoads() {
  }

  @Test
  @SneakyThrows
  //  @Transactional
  @DisplayName("Verify flight can be saved")
  void verifyFlightCanBeSavedTest() {

    int count = entityManager
                    .createQuery("select count(f) from Flight f", Long.class)
                    .getSingleResult()
                    .intValue();

    var flight = new Flight()
                     .setOrigin("Amsterdam")
                     .setDestination("New York")
                     .setScheduledAt(LocalDateTime.parse("2011-12-13T12:12:00"));

    entityManager.persist(flight);
    entityManager.flush();

    assertThat(entityManager
                   .createQuery("select f from Flight f", Flight.class)
                   .getResultList())
        .hasSize(count + 1)
        .last()
        .isEqualTo(flight);
  }

}
