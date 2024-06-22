package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;

public class MkDirCommand implements Command {
  private FileSystem filesystem;
  private String dirname;

  public MkDirCommand(FileSystem filesystem, String dirname) {
    this.filesystem = filesystem;
    this.dirname = dirname;
  }

  @Override
  public String execute() {
    filesystem.createDirectory(dirname);
    return "'" + dirname + "' directory created";
  }
}
