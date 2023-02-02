package com.example.MyProject.greetings;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingsController {

    @GetMapping
    public ResponseEntity<String> sayHello() { //ResponseEntity: Create a ResponseEntity with a body, headers, and a status code.
        return ResponseEntity.ok("Hello from API");
    }

    @GetMapping("/goodbye")
    public ResponseEntity<String> sayBye() {
        return ResponseEntity.ok("Goodbye");
    }

}
