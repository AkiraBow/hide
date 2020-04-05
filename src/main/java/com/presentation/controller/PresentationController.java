package com.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
@Slf4j
public class PresentationController {
  @Value("${k8s.Bayonetta}")
  private String SERVICE_NAME;

  @GetMapping("/allbooks")
  public String findAll(Model model) throws IOException {
    log.info("Request URL:"+SERVICE_NAME+"/api/books");
    Content content = Request.Get(SERVICE_NAME+"/api/books")
      .execute().returnContent();

    log.info("Json from backend:"+content.asString());
    model.addAttribute("booksJson", content.asString());
    return "book";
  }
}
