package com.example.entity;

import lombok.Data;

/**
 * Created by tanglei-010 on 2016/7/20.
 */
@Data
public class Greeting {

  private  long id;

  private  String content;

  private String link;

  public Greeting(long id, String content,String link) {
    this.id = id;
    this.content = content;
    this.link = link;
  }
}
