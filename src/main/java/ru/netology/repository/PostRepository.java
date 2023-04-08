package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;

// Stub
public class PostRepository {
    private final Map<Long, Post> concurrentRepository = new HashMap<>();
    private long counter = 0;

    public List<Post> all() {
        return new ArrayList<>(concurrentRepository.values());
    }

    public Post getById(long id) {
        if (!concurrentRepository.containsKey(id)) {
            throw new NotFoundException();
        } else {
            return concurrentRepository.get(id);
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            concurrentRepository.put(counter, post);
            post.setId(counter);
            counter++;
        } else if (post.getId() != 0 && concurrentRepository.containsKey(post.getId())) {
            concurrentRepository.replace(post.getId(), post);
        } else {
            throw new NotFoundException();
        }
        return post;
    }

    public void removeById(long id) {
        if (concurrentRepository.containsKey(id)) {
            concurrentRepository.remove(id);
        } else {
            throw new NotFoundException();
        }
    }
}
