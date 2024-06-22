package edu.austral.ingsis.clifford.system;

public abstract class FileSystemElement {
  protected String name;
  protected Directory parent;

  public FileSystemElement(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Directory getParent() {
    return parent;
  }

  public void setParent(Directory parent) {
    this.parent = parent;
  }

  public abstract boolean isDirectory();

  public String getPath() {
    if (parent == null) return name;
    return parent.getPath() + (parent.getParent() != null ? "/" : "") + name;
  }
}
