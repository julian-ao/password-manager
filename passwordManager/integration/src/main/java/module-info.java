module integration {
  requires spring.boot;
  requires spring.web;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.beans;
  requires spring.core;
  requires ui;

  requires client;
  requires transitive rest;
  requires localpersistence;

  opens integration to spring.core;
  exports integration;
}