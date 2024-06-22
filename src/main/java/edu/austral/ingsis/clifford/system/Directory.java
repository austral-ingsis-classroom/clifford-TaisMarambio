package edu.austral.ingsis.clifford.system;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Directory extends FileSystemElement {
  private final Map<String, FileSystemElement> children = new LinkedHashMap<>();

  public Directory(String name) {
    super(name);
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  public void addChild(FileSystemElement child) {
    children.put(child.getName(), child);
    child.setParent(this);
  }

  public FileSystemElement getChild(String name) {
    return children.get(name);
  }

  public void removeChild(String name) {
    FileSystemElement child = children.remove(name);
    if (child != null) {
      child.setParent(null);
    }
  }

  public List<String> listDirectory() {
    return new ArrayList<>(children.keySet());
  }
}