package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;

public class CdCommand implements Command {
  private final FileSystem fileSystem;
  private final String path;

  public CdCommand(FileSystem fileSystem, String path) {
    this.fileSystem = fileSystem;
    this.path = path;
  }

  @Override
  public String execute() {
    try {
      if (path.equals("..") || path.equals("/")) {
        fileSystem.changeDirectory("/");
        return "moved to directory '" + fileSystem.getCurrentPath() + "'";
      }
      fileSystem.changeDirectory(path);
      return "moved to directory '" + fileSystem.getCurrentDirectory().getName() + "'";
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }
}
