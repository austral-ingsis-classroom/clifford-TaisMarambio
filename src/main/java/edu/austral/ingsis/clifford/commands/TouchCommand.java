package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;

public class TouchCommand implements Command {
  private FileSystem filesystem;
  private String filename;

  public TouchCommand(FileSystem filesystem, String filename) {
    this.filesystem = filesystem;
    this.filename = filename;
  }

  @Override
  public String execute() {
    filesystem.createFile(filename);
    return "'" + filename + "' file created";
  }
}
