package com.reservemanager.gateway.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reserves")
public class ReserveController {

    private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list() {
        try {
            ResponseEntity<Map[]> reserveResponse = restTemplate.getForEntity(
                "http://reserve:8083/api/reserve",
                Map[].class
            );
            List<Map> reserves = Arrays.asList(reserveResponse.getBody());

            ResponseEntity<Map[]> roomResponse = restTemplate.getForEntity(
                "http://room:8082/api/rooms",
                Map[].class
            );
            List<Map> rooms = Arrays.asList(roomResponse.getBody());

            ResponseEntity<Map[]> userResponse = restTemplate.getForEntity(
                "http://user:8081/api/users",
                Map[].class
            );
            List<Map> users = Arrays.asList(userResponse.getBody());

            Map<Long, String> roomNames = rooms.stream()
                .collect(Collectors.toMap(
                    r -> Long.valueOf(r.get("id").toString()),
                    r -> r.get("name").toString()
                ));
            Map<Long, String> userNames = users.stream()
                .collect(Collectors.toMap(
                    u -> Long.valueOf(u.get("id").toString()),
                    u -> u.get("name").toString()
                ));

            for (Map reserve : reserves) {
                if (reserve.get("roomId") == null || reserve.get("userId") == null) {
                    logger.error("Reserve missing data: {}", reserve);
                    continue;
                }
                Long roomId = Long.valueOf(reserve.get("roomId").toString());
                Long userId = Long.valueOf(reserve.get("userId").toString());

                reserve.put("roomName", roomNames.getOrDefault(roomId, "Room not found"));
                reserve.put("userName", userNames.getOrDefault(userId, "User not found"));

                if (reserve.get("dateTime") != null) {
                    try {
                        String dateTimeStr = reserve.get("dateTime").toString();
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
                        reserve.put("formattedDateTime", dateTime.format(DISPLAY_FORMATTER));
                    } catch (DateTimeParseException e) {
                        logger.error("Date formatting error: {}", e.getMessage());
                        reserve.put("formattedDateTime", reserve.get("dateTime").toString());
                    }
                }
            }

            for (Map room : rooms) {
                Long roomId = Long.valueOf(room.get("id").toString());
                boolean occupied = reserves.stream()
                    .anyMatch(r -> r.get("roomId") != null && Long.valueOf(r.get("roomId").toString()).equals(roomId));
                room.put("available", !occupied);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("reserves", reserves);
            result.put("rooms", rooms);
            result.put("users", users);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error loading data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error loading data: " + e.getMessage()));
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Map<String, String>> create(@RequestBody Map<String, Object> reserveJson) {
        try {
            logger.info("Received reserve JSON: {}", reserveJson);

            Integer roomIdInt = (Integer) reserveJson.get("roomId");
            Integer userIdInt = (Integer) reserveJson.get("userId");
            String dateTime = (String) reserveJson.get("dateTime");

            if (roomIdInt == null || userIdInt == null || dateTime == null) {
                logger.error("Invalid data: roomId={}, userId={}, dateTime={}", roomIdInt, userIdInt, dateTime);
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid data"));
            }

            Map<String, Object> reserve = new HashMap<>();
            reserve.put("roomId", Long.valueOf(roomIdInt));
            reserve.put("userId", Long.valueOf(userIdInt));
            reserve.put("dateTime", dateTime);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://reserve:8083/api/reserve",
                reserve,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Reserve created successfully via JSON");
                return ResponseEntity.ok(Map.of("success", "Reserve created successfully"));
            } else {
                logger.error("Error creating reserve via JSON. Status: {}", response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Error creating reserve"));
            }
        } catch (Exception e) {
            logger.error("Error creating reserve via JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        try {
            logger.info("Received request to delete reserve with ID: {}", id);
            restTemplate.delete("http://reserve:8083/api/reserve/" + id);
            return ResponseEntity.ok(Map.of("message", "Reserve deleted successfully"));
        } catch (Exception e) {
            logger.error("Error deleting reserve: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error deleting reserve: " + e.getMessage()));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody Map<String, Object> userJson) {
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://user:8081/api/users",
                userJson,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(Map.of("success", "User created successfully"));
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Error creating user"));
            }
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error creating user: " + e.getMessage()));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> userJson) {
        try {
            restTemplate.put("http://user:8081/api/users/" + id, userJson);
            return ResponseEntity.ok(Map.of("message", "User updated successfully"));
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error updating user: " + e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        try {
            restTemplate.delete("http://user:8081/api/users/" + id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            logger.error("Error deleting user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error deleting user: " + e.getMessage()));
        }
    }

    @PostMapping("/rooms")
    public ResponseEntity<Map<String, String>> createRoom(@RequestBody Map<String, Object> roomJson) {
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://room:8082/api/rooms",
                roomJson,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(Map.of("success", "Room created successfully"));
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Error creating room"));
            }
        } catch (Exception e) {
            logger.error("Error creating room: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error creating room: " + e.getMessage()));
        }
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Map<String, String>> updateRoom(@PathVariable Long id, @RequestBody Map<String, Object> roomJson) {
        try {
            restTemplate.put("http://room:8082/api/rooms/" + id, roomJson);
            return ResponseEntity.ok(Map.of("message", "Room updated successfully"));
        } catch (Exception e) {
            logger.error("Error updating room: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error updating room: " + e.getMessage()));
        }
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long id) {
        try {
            restTemplate.delete("http://room:8082/api/rooms/" + id);
            return ResponseEntity.ok(Map.of("message", "Room deleted successfully"));
        } catch (Exception e) {
            logger.error("Error deleting room: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error deleting room: " + e.getMessage()));
        }
    }
}