package com.zeroturnaround.rebelanswers.mvc.tools;

import org.pegdown.PegDownProcessor;

/**
 * PegDown isn't thread safe and takes a long time to initialize
 */
public class ThreadSafePegDownProcessor {
  private final ThreadLocal<PegDownProcessor> processor = new ThreadLocal<PegDownProcessor>();

  public String markdownToHtml(String text) {
    PegDownProcessor p = processor.get();
    if (null == p) {
      p = new PegDownProcessor();
      processor.set(p);
    }

    return p.markdownToHtml(text);
  }
}
