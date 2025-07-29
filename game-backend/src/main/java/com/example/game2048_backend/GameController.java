package com.example.game2048_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public ResponseEntity<Map<String, int[][]>> startGame() {
        return ResponseEntity.ok(Map.of("board", gameService.initBoard()));
    }

    @PostMapping("/move/{direction}")
    public ResponseEntity<Map<String, int[][]>> move(@PathVariable String direction) {
        return ResponseEntity.ok(Map.of("board", gameService.move(direction)));
    }
}
