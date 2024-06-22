package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.system.Directory;
import edu.austral.ingsis.clifford.system.File;
import edu.austral.ingsis.clifford.system.FileSystemElement;

import java.util.List;

public class FileSystem {
  private final Directory root;
  private Directory currentDirectory;

  public FileSystem() {
    root = new Directory("");
    currentDirectory = root;
  }

  public void changeDirectory(String path) {
    if (path.equals("..")) {
      if (currentDirectory.getParent() != null) {
        currentDirectory = currentDirectory.getParent();
      }
    } else if (path.equals(".")) {
      // Do nothing
    } else if (path.startsWith("/")) {
      Directory newDir = navigateTo(root, path.substring(1));
      if (newDir != null && newDir.isDirectory()) {
        currentDirectory = newDir;
      } else {
        throw new IllegalArgumentException("'" + path + "' directory does not exist");
      }
    } else {
      Directory newDir = navigateTo(currentDirectory, path);
      if (newDir != null && newDir.isDirectory()) {
        currentDirectory = newDir;
      } else {
        throw new IllegalArgumentException("'" + path + "' directory does not exist");
      }
    }
  }

  private Directory navigateTo(Directory start, String path) {
    String[] parts = path.split("/");
    Directory current = start;
    for (String part : parts) {
      if (part.isEmpty()) continue;
      FileSystemElement element = current.getChild(part);
      if (element == null || !element.isDirectory()) {
        return null;
      }
      current = (Directory) element;
    }
    return current;
  }

  public String getCurrentPath() {
    return currentDirectory == root ? "/" : currentDirectory.getPath();
  }

  public void createFile(String filename) {
    if (filename.contains("/") || filename.contains(" ")) {
      throw new IllegalArgumentException("Invalid file name: " + filename);
    }
    currentDirectory.addChild(new File(filename));
  }

  public void createDirectory(String dirname) {
    if (dirname.contains("/") || dirname.contains(" ")) {
      throw new IllegalArgumentException("Invalid directory name: " + dirname);
    }
    currentDirectory.addChild(new Directory(dirname));
  }

  public void remove(String name, boolean recursive) {
    FileSystemElement element = currentDirectory.getChild(name);
    if (element == null) {
      throw new IllegalArgumentException("Element not found: " + name);
    }
    if (element.isDirectory() && !recursive) {
      throw new IllegalArgumentException("cannot remove '" + name + "', is a directory");
    }
    currentDirectory.removeChild(name);
  }

  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  public List<String> listCurrentDirectory() {
    return currentDirectory.listDirectory();
  }
}
