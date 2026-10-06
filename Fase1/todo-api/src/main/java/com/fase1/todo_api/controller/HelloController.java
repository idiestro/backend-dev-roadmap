package com.fase1.todo_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping("/{name}")
    public String helloPath(@PathVariable String name){
        return "Hello " + name;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody Payload payload){
        return "Hola " + payload.name();
    }

    public record Payload(String name){}

    /*
    Pregunta 3: En mi opinión, es RestController porque trabaja sobre peticiones HTTP REST
     */
}