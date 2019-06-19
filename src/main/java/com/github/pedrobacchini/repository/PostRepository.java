package com.github.pedrobacchini.repository;

import com.github.pedrobacchini.domain.Post;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class PostRepository {

    private static final Map<Long, Post> DATA = new HashMap<>();
    private static long ID_COUNTER = 1L;

    static {
        Stream.of("Fist Post", "Second Post")
                .forEach(title -> {
                    long id = ID_COUNTER++;
                    DATA.put(id, Post.builder().id(id).title(title).content("content of " + title).build());
                });
    }

    public Flux<Post> findAll() { return Flux.fromIterable(DATA.values()); }

    public Mono<Post> findByID(Long id) { return Mono.just(DATA.get(id)); }

    public Mono<Post> save(Post post) {
        long id = ID_COUNTER++;
        post.setId(id);
        DATA.put(id, post);
        return Mono.just(post);
    }
}
