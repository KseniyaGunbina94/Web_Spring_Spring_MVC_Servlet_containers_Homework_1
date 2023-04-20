package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepositoryImpl;
import ru.netology.service.PostService;

@Configuration
public class JavaConfig {
    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepositoryImpl repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepositoryImpl postRepository() {
        return new PostRepositoryImpl();
    }
}