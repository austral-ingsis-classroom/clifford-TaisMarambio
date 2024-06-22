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
      currentDirectory = navigateTo(root, path.substring(1));
    } else if (path.startsWith("./")) {
      currentDirectory = navigateTo(currentDirectory, path.substring(2));
    } else if (path.startsWith("../")) {
      currentDirectory = navigateTo(currentDirectory.getParent(), path.substring(3));
    } else if (path.startsWith("~")) {
      currentDirectory = navigateTo(root, path.substring(1));
    } else {
      String[] parts = path.split("/");
      Directory newCurrent = currentDirectory;
      for (String part : parts) {
        FileSystemElement newElement = newCurrent.getChild(part);
        if (newElement instanceof Directory) {
          newCurrent = (Directory) newElement;
        } else {
          throw new IllegalArgumentException("'" + path + "' directory does not exist");
        }
      }
      currentDirectory = newCurrent;
    }
  }

  private Directory navigateTo(Directory start, String path) {
    String[] parts = path.split("/");
    Directory current = parts[0].isEmpty() ? root : start;
    for (int i = parts[0].isEmpty() ? 1 : 0; i < parts.length; i++) {
      String part = parts[i];
      if (part.isEmpty()) continue;
      FileSystemElement element =
          current.getChild(part); // here we want to get the child of the current directory
      if (element == null || !element.isDirectory()) {
        throw new IllegalArgumentException("'" + path + "' directory does not exist");
      }
      current = (Directory) element;
    }
    return current;
  }

  public String getCurrentPath() {
    if (currentDirectory == root) {
      return "/";
    } else {
      return currentDirectory.getPath();
    }
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
