package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepositoryImpl;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private final String GET = "GET";
  private final String POST = "POST";
  private final String DELETE = "DELETE";

  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext("ru.netology");
    controller = (PostController) context.getBean("postController");
    final var repository = context.getBean("postRepository");
    final var service = context.getBean(PostService.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals(GET) && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches("/api/posts/\\d+")) {
        // easy way
        try {
          final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
          controller.getById(id, resp);
          return;
        } catch (Throwable e) {
          e.printStackTrace();
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
      }
      if (method.equals(POST) && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches("/api/posts/\\d+")) {
        // easy way
        try {
          final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
          controller.removeById(id, resp);
          return;
        } catch (Throwable e) {
          e.printStackTrace();
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}

