package edu.austral.ingsis.clifford.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory extends FileSystemElement {
  private final Map<String, FileSystemElement> children = new HashMap<>();

  public Directory(String name) {
    super(name);
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  public void addChild(FileSystemElement element) {
    element.setParent(this);
    children.put(element.getName(), element);
  }

  public void removeChild(String name) {
    children.remove(name);
  }

  public FileSystemElement getChild(String name) {
    return children.get(name);
  }

  public List<String> listDirectory() {
    return children.keySet().stream().toList();
  }
}
