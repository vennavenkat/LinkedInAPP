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

    @Query("Match (p1:Person)-[r:REQUESTED_TO]->(p2:Person)\n"+
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId\n"+
            "RETURN count(r) > 0")
    boolean connectionRequestExists(Long senderId, Long receiverId);

    @Query("Match (p1:Person)-[r:CONNECTED_TO]-(p2:Person)\n"+
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId\n"+
            "RETURN count(r) > 0")
    boolean alreadyConnected(Long senderId, Long receiverId);

    @Query("Match (p1:Person),(p2:Person)\n"+
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId\n"+
            "CREATE (p1)-[:REQUESTED_TO]->(p2)")
    void addConnectionRequest(Long senderId, Long receiverId);

    @Query("Match (p1:Person)-[r:REQUESTED_TO]->(p2:Person)\n"+
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId\n"+
            "DELETE r \n"+
            "CREATE (p1)-[:CONNECTED_TO]->(p2)")
    void acceptConnectionRequest(Long senderId, Long receiverId);

    @Query("Match (p1:Person)-[r:REQUESTED_TO]->(p2:Person)\n"+
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId\n"+
            "DELETE r")
    void rejectConnectionRequest(Long senderId, Long receiverId);
}
