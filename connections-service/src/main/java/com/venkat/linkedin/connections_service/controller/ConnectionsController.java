package com.venkat.linkedin.connections_service.controller;

import com.venkat.linkedin.connections_service.entity.Person;
import com.venkat.linkedin.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-connections")
    public ResponseEntity<List<Person>> getFirstConnections(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections(userId));
    }
}
