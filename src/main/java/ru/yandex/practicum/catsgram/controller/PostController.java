package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;
import ru.yandex.practicum.catsgram.service.SortOrder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<Post> findAll(
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "sort", required = false, defaultValue = "desc") String sort
    ) {
        if (size <= 0) {
            throw new ParameterNotValidException("size", "size must be greater than 0.");
        }
        if (from < 0) {
            throw new ParameterNotValidException("from", "size must be greater than or equal to 0.");
        }
        if (SortOrder.from(sort) == null) {
            throw new ParameterNotValidException("sort", "incorrect value.");
        }

        return postService.findAll(size, from, SortOrder.from(sort));
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

}
