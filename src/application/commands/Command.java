package application.commands;

public interface Command {
    void execute();
    void undo();
}
