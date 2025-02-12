package edu.ncsu.csc216.requirements_manager.model.command;

/**
 * Creates objects that encapsulate user actions (or transitions) that cause the state of a UserStory to update.
 * 
 * @author raywa
 */
public class Command {

	/**
	 * An enumeration for the possible command values that can cause transitions in
	 * our FSM
	 */
	public enum CommandValue {
		/** Backlog a user story */
		BACKLOG,
		/** Assign a developer to a user story */
		ASSIGN,
		/** Review a user story */
		REVIEW,
		/** Confirm a user story */
		CONFIRM,
		/** Reopen a user story */
		REOPEN,
		/** Reject a user story */
		REJECT, 
		/** Resubmit a user story */
		RESUBMIT 
	}

	/** The command value of the Command object */
	private CommandValue command;
	
	/** The command information of the Command object */
	private String commandInformation;

	/**
	 * Constructs the command object with the given parameters. Throws IAE if a
	 * Command with a null CommandValue parameter, a Command with a CommandValue of
	 * BACKLOG, ASSIGN, or REJECT has a null or empty string commandInformation, or
	 * a Command with a CommandValue of REVIEW, CONFIRM, REOPEN, or RESUBMIT has a
	 * non-null commandInformation.
	 * 
	 * @param command            the command value of the Command
	 * @param commandInformation the command information of the Command
	 * @throws IllegalArgumentException if any of the conditions are met
	 */
	public Command(CommandValue command, String commandInformation) {
		if (command == null) {
			throw new IllegalArgumentException();
		} else if (command == CommandValue.BACKLOG || command == CommandValue.ASSIGN || command == CommandValue.REJECT) {
			if (commandInformation == null || commandInformation.isEmpty() || commandInformation.trim().isEmpty()) {
				throw new IllegalArgumentException();
			}
		} else if ((command == CommandValue.REVIEW || command == CommandValue.CONFIRM || command == CommandValue.REOPEN
				|| command == CommandValue.RESUBMIT) &&  commandInformation != null) {
				throw new IllegalArgumentException();
		}
		
		this.command = command;
		this.commandInformation = commandInformation;
	}

	/**
	 * Returns the command value
	 * 
	 * @return the command
	 */
	public CommandValue getCommand() {
		return this.command;
	}

	/**
	 * Returns the command information String
	 * 
	 * @return the commandInformation
	 */
	public String getCommandInformation() {
		return this.commandInformation;
	}

}
