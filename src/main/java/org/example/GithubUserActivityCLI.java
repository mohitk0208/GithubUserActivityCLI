package org.example;

import org.example.service.GithubUserEvents;
import org.example.service.GithubUserEventsImpl;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "github-activity",
        description = "A CLI tool to fetch and display GitHub user activity."
)
public class GithubUserActivityCLI implements Runnable {

    private final GithubUserEvents githubUserEvents;

    @CommandLine.Parameters(index = "0", description = "Username of the github user to fetch its recent activity.")
    private String username;

    @CommandLine.Option(names = {"-n", "--no-of-events"}, description = "Number of events to print")
    private Integer noOfEvents;

    public GithubUserActivityCLI() {
        // Constructor can be used for initialization if needed
        this.githubUserEvents = new GithubUserEventsImpl();
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new GithubUserActivityCLI());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        commandLine.setExecutionStrategy(new CommandLine.RunLast());

        // Parse the command line arguments
        int exitCode = commandLine.execute(args);

        // Exit with the appropriate code
        System.exit(exitCode);
    }

    @Override
    public void run() {
        // This method will be executed when the command is run
        System.out.println("Welcome to the GitHub User Activity CLI!");
        System.out.println("Use the --help option to see available commands and options.");

        // use the GitHub service to fetch the events and process it and display to the CLI
        List<String> messages = this.githubUserEvents.getUserActivity(username);

        if (noOfEvents != null) {
            messages = messages.subList(0, noOfEvents);
        }

        for (String message : messages) {
            System.out.printf("- %s%n", message);
        }
    }
}
