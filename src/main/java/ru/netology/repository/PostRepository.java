package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
  private volatile long count = 0;
  private ConcurrentHashMap<Long, Post> allPostsForSave = new ConcurrentHashMap<>();
  public List<Post> all() {
    return new ArrayList<>(allPostsForSave.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.of(allPostsForSave.get(id));
  }

  public Post save(Post post) {
    post.setId(++count);
    allPostsForSave.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    allPostsForSave.remove(id);
  }
}
