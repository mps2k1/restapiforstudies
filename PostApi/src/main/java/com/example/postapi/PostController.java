package com.example.postapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private static final String  API_URL = "https://jsonplaceholder.typicode.com/";
    private final RestTemplate restTemplate = new RestTemplate();

//GET
    @GetMapping("/byId/{id}")
    public ResponseEntity<String> getUserById(@PathVariable long id) {
        String url =API_URL + "users/" + id;
        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
    //POST
    @PostMapping("/create")
    public ResponseEntity<String> createPost() {
        Post newPost = new Post(1L, "New Post", "This is the body of the new post.");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        HttpEntity<Post> requestEntity = new HttpEntity<>(newPost, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, String.class);
            return new ResponseEntity<>("Utworzono post"+newPost, HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
    //GET
    @GetMapping("/postById/{id}")
    public ResponseEntity<String> getPostById(@PathVariable long id) {
        String url =API_URL + "posts/" + id;
        try {
            String response = restTemplate.getForObject(url, String.class);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
    //DELETE
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        String url = API_URL + "posts/" + id;

        try {
            restTemplate.delete(url);
            return new ResponseEntity<>("Post with ID " + id + " has been deleted.", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

}
