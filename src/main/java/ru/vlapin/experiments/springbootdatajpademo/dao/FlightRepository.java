package ru.vlapin.experiments.springbootstartersdemo.dao;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.vlapin.experiments.springbootstartersdemo.model.Flight;

//@Repository
public interface FlightRepository extends PagingAndSortingRepository<Flight, Long> {
  List<Flight> findByOrigin(String origin);
  List<Flight> findByOriginAndDestination(String origin, String destination);

  @SuppressWarnings("SpringDataRepositoryMethodParametersInspection")
  List<Flight> findByOriginIn(String... city);

}
