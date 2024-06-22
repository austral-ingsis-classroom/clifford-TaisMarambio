package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;

public class PwdCommand implements Command {
  private final FileSystem fileSystem;

  public PwdCommand(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute() {
    return "/" + fileSystem.getCurrentDirectory().getPath();
  }
}
