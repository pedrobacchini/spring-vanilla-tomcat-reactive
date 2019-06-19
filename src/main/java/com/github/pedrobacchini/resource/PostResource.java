package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.domain.Post;
import com.github.pedrobacchini.repository.PostRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
public class PostResource {

    private final PostRepository postRepository;

    public PostResource(PostRepository postRepository) { this.postRepository = postRepository; }

    @GetMapping
    public Flux<Post> getAll() { return postRepository.findAll(); }

    @GetMapping("/{id}")
    public Mono<Post> getById(@PathVariable Long id) { return postRepository.findByID(id); }

    @PostMapping
    public Mono<Post> savePost(@RequestBody Post post) { return postRepository.save(post); }
}
