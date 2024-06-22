package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;

public class RmCommand implements Command {
  private final FileSystem fileSystem;
  private final String name;
  private final boolean recursive;

  public RmCommand(FileSystem fileSystem, String name, boolean recursive) {
    this.fileSystem = fileSystem;
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute() {
    fileSystem.remove(name, recursive);
    return "'" + name + "' removed";
  }
}
