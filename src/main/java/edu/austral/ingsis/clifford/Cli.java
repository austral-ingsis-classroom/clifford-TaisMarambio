package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.*;

import java.util.Arrays;
import java.util.Scanner;

public class Cli {
  private final FileSystem fileSystem;

  public Cli() {
    fileSystem = new FileSystem();
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("$ ");
      String input = scanner.nextLine().trim();
      String[] parts = input.split("\\s+");
      String commandName = parts[0];
      String[] args = Arrays.copyOfRange(parts, 1, parts.length);

      Command command = createCommand(commandName, args);
      if (command != null) {
        try {
          System.out.println(command.execute());
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
      } else {
        System.out.println("Unknown command: " + commandName);
      }
    }
  }

  private Command createCommand(String commandName, String[] args) {
    switch (commandName) {
      case "ls":
        String order = args.length > 0 && args[0].startsWith("--ord=") ? args[0].substring(6) : null;
        return new LsCommand(fileSystem, order);
      case "cd":
        return new CdCommand(fileSystem, args[0]);
      case "touch":
        return new TouchCommand(fileSystem, args[0]);
      case "mkdir":
        return new MkDirCommand(fileSystem, args[0]);
      case "rm":
        boolean recursive = Arrays.asList(args).contains("--recursive");
        String name = recursive ? args[1] : args[0];
        return new RmCommand(fileSystem, name, recursive);
      case "pwd":
        return new PwdCommand(fileSystem);
      default:
        return null;
    }
  }

  public static void main(String[] args) {
    new Cli().start();
  }
}
