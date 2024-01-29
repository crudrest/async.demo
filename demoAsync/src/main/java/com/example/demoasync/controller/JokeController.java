package com.example.demoasync.controller;


import com.example.demoasync.model.Joke;
import com.example.demoasync.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    @Autowired
    JokeService jokeService;


    @GetMapping()
    public ResponseEntity<List<Joke>> getJokes(@RequestParam(required = false, defaultValue = "5") Integer quantity) {
        List<Joke> resultList = Collections.emptyList();
        if (quantity > 100) {
            throw new RuntimeException("You can get no more than 100 jokes at a time");
        } else if (quantity > 1) {
             resultList = getJokesAsync(quantity);
        } else if (quantity == 1) {
            resultList = Collections.singletonList(jokeService.getJoke());
        }

        ResponseEntity<List<Joke>> responseEntity = ResponseEntity.ofNullable(resultList);
        return responseEntity;
    }


    private List<Joke> getJokesAsync(Integer quantity) {
        List<Joke> resultList = new ArrayList<>();
        CompletableFuture[] futures = new CompletableFuture[quantity];
        for (int i = 0; i < quantity; i++) {
            CompletableFuture<Joke> futureJoke = CompletableFuture.supplyAsync(() -> jokeService.getJoke());
            futures[i] = futureJoke;
        }
        CompletableFuture.allOf(futures).join();

        for (CompletableFuture future : futures) {
            resultList.add((Joke) future.join());
        }
        return resultList;

    }
}
