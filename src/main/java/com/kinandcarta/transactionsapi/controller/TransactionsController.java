package com.kinandcarta.transactionsapi.controller;

import com.kinandcarta.transactionsapi.service.TransactionsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

  private final TransactionsService transactionsService;

  public TransactionsController(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }
}