package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LsCommand implements Command {
  private final FileSystem fileSystem;
  private final String order;

  public LsCommand(FileSystem fileSystem, String order) {
    this.fileSystem = fileSystem;
    this.order = order;
  }

  @Override
  public String execute() {
    List<String> elements = new ArrayList<>(fileSystem.listCurrentDirectory());

    if ("asc".equals(order)) {
      Collections.sort(elements);
    } else if ("desc".equals(order)) {
      elements.sort(Collections.reverseOrder());
    }

    // Handle case when directory is empty
    if (elements.isEmpty()) {
      return "";
    } else {
      return String.join(" ", elements.stream().toList());
    }
  }
}