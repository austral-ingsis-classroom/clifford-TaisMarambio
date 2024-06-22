package edu.austral.ingsis;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.commands.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerImpl implements FileSystemRunner {
  private final FileSystem fileSystem;

  public FileSystemRunnerImpl() {
    this.fileSystem = new FileSystem();
  }

  @Override
  public List<String> executeCommands(List<String> commands) {
    List<String> results = new ArrayList<>();
    for (String commandLine : commands) {
      String[] parts = commandLine.trim().split("\\s+", 2);
      String commandName = parts[0];
      String args = parts.length > 1 ? parts[1] : "";
      Command command = createCommand(commandName, args);
      if (command != null) {
        try {
          results.add(command.execute().trim());
        } catch (IllegalArgumentException e) {
          results.add(e.getMessage().trim());
        }
      } else {
        results.add("Unknown command: " + commandName);
      }
    }
    return results;
  }

  private Command createCommand(String commandName, String args) {
    switch (commandName) {
      case "ls":
        String order = args.startsWith("--ord=") ? args.substring(6) : null;
        return new LsCommand(fileSystem, order);
      case "cd":
        return new CdCommand(fileSystem, args);
      case "touch":
        return new TouchCommand(fileSystem, args);
      case "mkdir":
        return new MkDirCommand(fileSystem, args);
      case "rm":
        boolean recursive = args.contains("--recursive");
        String name = recursive ? args.split(" ")[1] : args;
        return new RmCommand(fileSystem, name, recursive);
      case "pwd":
        return new PwdCommand(fileSystem);
      default:
        return null;
    }
  }
}
