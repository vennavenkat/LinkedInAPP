package com.venkat.linkedin.connections_service.repository;

import com.venkat.linkedin.connections_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> getByName(String name);

    @Query("MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person)\n" +
            "WHERE personA.userId = $userId\n" +
            "RETURN personB\n")
    List<Person> getFirstDegreeConnections(Long userId);
}
