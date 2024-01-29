package com.example.demoasync.service;


import com.example.demoasync.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Service
public class JokeService {

    private static final String USERS_ROOT_URL = "https://official-joke-api.appspot.com/random_joke";

    @Autowired
    RestTemplate restTemplate;


    @Async
    public CompletableFuture<Joke> getJokeAsync() {
        ResponseEntity<Joke> result = restTemplate.getForEntity(USERS_ROOT_URL, Joke.class, new HashMap<>());
        return CompletableFuture.completedFuture(result.getBody());
    }

    public Joke getJoke() {
        ResponseEntity<Joke> result = restTemplate.getForEntity(USERS_ROOT_URL, Joke.class, new HashMap<>());
        return result.getBody();
    }
}
