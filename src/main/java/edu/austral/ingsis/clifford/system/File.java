package edu.austral.ingsis.clifford.system;

public class File extends FileSystemElement {
  public File(String name) {
    super(name);
  }

  @Override
  public boolean isDirectory() {
    return false;
  }
}
