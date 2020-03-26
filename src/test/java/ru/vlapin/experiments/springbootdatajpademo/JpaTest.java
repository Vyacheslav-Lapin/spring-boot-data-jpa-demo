package ru.vlapin.experiments.springbootdatajpademo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vlapin.experiments.springbootdatajpademo.model.Flight;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JpaTest {

  EntityManager entityManager;

  @Test
  @SneakyThrows
  //  @Transactional
  @DisplayName("Verify flight can be saved")
  void verifyFlightCanBeSavedTest() {

    int count = entityManager
                    .createQuery("select count(f) from Flight f", Long.class)
                    .getSingleResult()
                    .intValue();

    var flight = Flight.builder().origin("Amsterdam").destination("New York").build();

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
