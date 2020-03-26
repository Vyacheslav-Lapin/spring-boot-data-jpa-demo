package ru.vlapin.experiments.springbootdatajpademo.dao;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.vlapin.experiments.springbootdatajpademo.model.Flight;

//@Repository
@RepositoryRestResource
public interface FlightRepository extends PagingAndSortingRepository<Flight, Long> {
  List<Flight> findByOrigin(String origin);
  List<Flight> findByOriginAndDestination(String origin, String destination);

  @SuppressWarnings("SpringDataRepositoryMethodParametersInspection")
  List<Flight> findByOriginIn(String... city);

}
