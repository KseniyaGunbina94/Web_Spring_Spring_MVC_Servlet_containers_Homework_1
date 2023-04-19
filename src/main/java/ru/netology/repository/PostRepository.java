package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
  private AtomicLong count = new AtomicLong(0);
  private ConcurrentHashMap<Long, Post> allPostsForSave = new ConcurrentHashMap<>();
  public List<Post> all() {
    return new ArrayList<>(allPostsForSave.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.of(allPostsForSave.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(count.incrementAndGet());
      allPostsForSave.put(post.getId(), post);
    } else {
      allPostsForSave.put(post.getId(), post);
    }
    return post;
  }

  public void removeById(long id) {
    allPostsForSave.remove(id);
  }
}
