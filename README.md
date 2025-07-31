# GitHub User Activity CLI

This is an implementation of [GitHub User Activity CLI](https://roadmap.sh/projects/github-user-activity)
on [roadmap.sh](https://roadmap.sh).
This is a command-line tool that allows you to view the recent activity of a GitHub user in a terminal.

## Features
- View recent activity of a GitHub user
- limit the number of events displayed

## Installation
- clone the repository:
    ```bash
    git clone https://github.com/mohitk0208/GithubUserActivityCLI
    cd GithubUserActivityCLI
    ```
- create the jar file
    ```bash
    mvn clean package
    ```
- run the jar file
    ```bash
    java -jar target/github-user-activity-cli-1.0-SNAPSHOT.jar <username>
    ```
  
## Usage
- To view the recent activity of a user, run the following command:
    ```bash
    java -jar target/github-user-activity-cli-1.0-SNAPSHOT.jar <username>
    ```
- To limit the number of events displayed, use the `--limit` option:
    ```bash
    java -jar target/github-user-activity-cli-1.0-SNAPSHOT.jar <username> -n <number of events to display>
    ```
  
