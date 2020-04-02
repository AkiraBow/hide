package com.presentation.controller;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
public class PresentationController {
  @Value("${k8s.Bayonetta}")
  private String SERVICE_NAME;

  @GetMapping("/allbooks")
  public String findAll(Model model) throws IOException {
    Content content = Request.Get(SERVICE_NAME+"/api/books")
      .execute().returnContent();

    model.addAttribute("booksJson", content.asString());
    return "book";
  }
}
