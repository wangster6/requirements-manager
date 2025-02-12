/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.user_story;

import edu.ncsu.csc216.requirements_manager.model.command.Command;

/**
 * Represents an user story managed by our system. An UserStory knows its
 * storyId, state, title, user, action, value, priority, developerId, and
 * rejectionReason. Each UserStory has its own state, which is updated from
 * Commands propagated to it from the UI.
 * 
 * @author raywa
 */
public class UserStory {

	/**
	 * Interface for states in the UserStory State Pattern. All concrete user story
	 * states must implement the UserStoryState interface. The UserStoryState
	 * interface should be a private interface of the UserStory class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface UserStoryState {

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		void updateState(Command command);
//			if(command.getCommand() != Command.CommandValue.ASSIGN || command.getCommand() != Command.CommandValue.BACKLOG || command.getCommand() != Command.CommandValue.CONFIRM || command.getCommand() != Command.CommandValue.REJECT || command.getCommand() != Command.CommandValue.REOPEN || command.getCommand() != Command.CommandValue.RESUBMIT || command.getCommand() != Command.CommandValue.REVIEW)
//			throw new UnsupportedOperationException();

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * Class for the submitted state.
	 * 
	 * @author raywa
	 */
	public class SubmittedState implements UserStoryState {
		/**
		 * Constructs the submitted state
		 */
		private SubmittedState() {

		}

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		@Override
		public void updateState(Command command) {
			switch (command.getCommand()) {
			case BACKLOG:
				setState(BACKLOG_NAME);
				setPriority(command.getCommandInformation());
				setDeveloperId(null);
				setRejectionReason(null);
				break;
			case REJECT:
				setState(REJECTED_NAME);
				setRejectionReason(command.getCommandInformation());
				setPriority(null);
				setDeveloperId(null);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the state
		 * 
		 * @return the name of the state
		 */
		public String getStateName() {
			return SUBMITTED_NAME;
		}
	}

	/**
	 * Class for the backlog state.
	 * 
	 * @author raywa
	 */
	public class BacklogState implements UserStoryState {
		/**
		 * Constructs the backlog state
		 */
		private BacklogState() {

		}

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		@Override
		public void updateState(Command command) {
			switch (command.getCommand()) {
			case ASSIGN:
				setState(WORKING_NAME);
				setDeveloperId(command.getCommandInformation());
				setRejectionReason(null);
				break;
			case REJECT:
				setState(REJECTED_NAME);
				setRejectionReason(command.getCommandInformation());
				setDeveloperId(null);
				setPriority(null);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the state
		 * 
		 * @return the name of the state
		 */
		public String getStateName() {
			return BACKLOG_NAME;
		}
	}

	/**
	 * Class for the working state.
	 * 
	 * @author raywa
	 */
	public class WorkingState implements UserStoryState {
		/**
		 * Constructs the working state
		 */
		private WorkingState() {

		}

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		@Override
		public void updateState(Command command) {
			switch (command.getCommand()) {
			case ASSIGN:
				setState(WORKING_NAME);
				setDeveloperId(command.getCommandInformation());
				setRejectionReason(null);
				break;
			case REJECT:
				setState(REJECTED_NAME);
				setRejectionReason(command.getCommandInformation());
				setPriority(null);
				setDeveloperId(null);
				break;
			case REOPEN:
				setState(BACKLOG_NAME);
				setDeveloperId(null);
				break;
			case REVIEW:
				setState(VERIFYING_NAME);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the state
		 * 
		 * @return the name of the state
		 */
		public String getStateName() {
			return WORKING_NAME;
		}
	}

	/**
	 * Class for the verifying state.
	 * 
	 * @author raywa
	 */
	public class VerifyingState implements UserStoryState {
		/**
		 * Constructs the verifying state
		 */
		private VerifyingState() {

		}

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		@Override
		public void updateState(Command command) {
			switch (command.getCommand()) {
			case REOPEN:
				setState(WORKING_NAME);
				break;
			case CONFIRM:
				setState(COMPLETED_NAME);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the state
		 * 
		 * @return the name of the state
		 */
		public String getStateName() {
			return VERIFYING_NAME;
		}
	}

	/**
	 * Class for the completed state.
	 * 
	 * @author raywa
	 */
	public class CompletedState implements UserStoryState {
		/**
		 * Constructs the completed state
		 */
		private CompletedState() {

		}

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		@Override
		public void updateState(Command command) {
			switch (command.getCommand()) {
			case REOPEN:
				setState(WORKING_NAME);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the state
		 * 
		 * @return the name of the state
		 */
		public String getStateName() {
			return COMPLETED_NAME;
		}
	}

	/**
	 * Class for the rejected state.
	 * 
	 * @author raywa
	 */
	public class RejectedState implements UserStoryState {
		/**
		 * Constructs the rejected state
		 */
		private RejectedState() {

		}

		/**
		 * Update the UserStory based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the UserStory's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		@Override
		public void updateState(Command command) {
			switch (command.getCommand()) {
			case RESUBMIT:
				setState(SUBMITTED_NAME);
				setPriority(null);
				setDeveloperId(null);
				setRejectionReason(null);
				break;
			default:
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the state
		 * 
		 * @return the name of the state
		 */
		public String getStateName() {
			return REJECTED_NAME;
		}
	}

	/** Unique id for a user story */
	private int storyId;

	/** Title for the user story as provided by the user on creation */
	private String title;

	/** The user information for the statement As a [user] */
	private String user;

	/** The action information for the statement I want to [action] */
	private String action;

	/** The value information for the statement so I can [value] */
	private String value;

	/** The user story’s priority */
	private String priority;

	/** The user story’s assigned developer */
	private String developerId;

	/** The user story's rejection reason */
	private String rejectionReason;

	/** Current state for the user story */
	private UserStoryState currentState;

	/** Final instance of the SubmittedState inner class */
	private final UserStoryState submittedState = new SubmittedState();

	/** Final instance of the BacklogState inner class */
	private final UserStoryState backlogState = new BacklogState();

	/** Final instance of the WorkingState inner class */
	private final UserStoryState workingState = new WorkingState();

	/** Final instance of the VerifyingState inner class */
	private final UserStoryState verifyingState = new VerifyingState();

	/** Final instance of the CompletedState inner class */
	private final UserStoryState completedState = new CompletedState();

	/** Final instance of the RejectedState inner class */
	private final UserStoryState rejectedState = new RejectedState();

	/**
	 * A constant string for the submitted state’s name with the value Submitted
	 */
	public static final String SUBMITTED_NAME = "Submitted";

	/** A constant string for the backlog state’s name with the value Backlog */
	public static final String BACKLOG_NAME = "Backlog";

	/** A constant string for the working state’s name with the value Working */
	public static final String WORKING_NAME = "Working";

	/**
	 * A constant string for the verifying state’s name with the value Verifying
	 */
	public static final String VERIFYING_NAME = "Verifying";

	/**
	 * A constant string for the completed state’s name with the value Completed
	 */
	public static final String COMPLETED_NAME = "Completed";

	/** A constant string for the rejected state’s name with the value Rejected */
	public static final String REJECTED_NAME = "Rejected";

	/** A constant string for the priority of High */
	public static final String HIGH_PRIORITY = "High";

	/** A constant string for the priority of Medium */
	public static final String MEDIUM_PRIORITY = "Medium";

	/** A constant string for the priority of Low */
	public static final String LOW_PRIORITY = "Low";

	/** A constant string for the rejection reason of Duplicate */
	public static final String DUPLICATE_REJECTION = "Duplicate";

	/** A constant string for the rejection reason of Inappropriate */
	public static final String INAPPROPRIATE_REJECTION = "Inappropriate";

	/** A constant string for the rejection reason of Infeasible */
	public static final String INFEASIBLE_REJECTION = "Infeasible";

	/**
	 * A constant integer for creating the story id for each new instance of a user
	 * story
	 */
	private static int counter = 0;

	/**
	 * Constructs the User Story based on parameters
	 * 
	 * @param title  title of the user story
	 * @param user   user of the user story
	 * @param action action of the user story
	 * @param value  value of the user story
	 */
	public UserStory(String title, String user, String action, String value) {
		this(counter, SUBMITTED_NAME, title, user, action, value, null, null, null);
	}

	/**
	 * Constructs the user story based on the parameters
	 * 
	 * @param id              id of the user story
	 * @param state           state of the user story
	 * @param title           title of the user story
	 * @param user            user of the user story
	 * @param action          action of the user story
	 * @param value           value of the user story
	 * @param priority        priority of the user story
	 * @param developerId     developer id of the user story
	 * @param rejectionReason rejection reason of the user story
	 */
	public UserStory(int id, String state, String title, String user, String action, String value, String priority,
			String developerId, String rejectionReason) {
		// If there is an issue with any of the parameters, an IAE is thrown
		if ("".equals(state) || "".equals(title) || "".equals(user) || "".equals(action) || "".equals(value)
				|| state == null || title == null || user == null || action == null || value == null) {
			throw new IllegalArgumentException();
		}
		// Set user story fields to the parameters
		setId(id);
		incrementCounter();
		setState(state);
		setTitle(title);
		setUser(user);
		setAction(action);
		setValue(value);
		
		if(priority != null && !Character.isUpperCase(priority.trim().charAt(0))) {
			throw new IllegalArgumentException();
		}
		
		if(rejectionReason != null && !Character.isUpperCase(rejectionReason.trim().charAt(0))) {
			throw new IllegalArgumentException();
		}
		
		if("Submitted".equals(state)) {
			if(!(priority == null && developerId == null && rejectionReason == null)) {
				throw new IllegalArgumentException();
			}
		} else if("Backlog".equals(state))	{
			if(priority != null && developerId == null && rejectionReason == null) {
				setPriority(priority);
				setDeveloperId(null);
				setRejectionReason(null);
			} else {
				throw new IllegalArgumentException();
			}
		} else if("Working".equals(state))	{
			if(priority != null && developerId != null && rejectionReason == null) {
				setPriority(priority);
				setDeveloperId(developerId);
				setRejectionReason(null);
			} else {
				throw new IllegalArgumentException();
			}
		} else if("Verifying".equals(state)) {
			if(priority != null && developerId != null && rejectionReason == null) {
				setPriority(priority);
				setDeveloperId(developerId);
				setRejectionReason(null);
			} else {
				throw new IllegalArgumentException();
			}
		} else if("Completed".equals(state)) {
			if(priority != null && developerId != null && rejectionReason == null) {
				setPriority(priority);
				setDeveloperId(developerId);
				setRejectionReason(null);
			} else {
				throw new IllegalArgumentException();
			}
		} else if("Rejected".equals(state)){
			if(priority == null && developerId == null && rejectionReason != null) {
				setRejectionReason(rejectionReason);
			} else {
				throw new IllegalArgumentException();
			}
		}


		// If the id is greater than the current value in counter, update the counter to
		// id + 1
		if (id > counter) {
			setCounter(id + 1);
		}
	}

	/**
	 * Sets the user story id to the parameter
	 * 
	 * @param storyId the storyId to set
	 */
	private void setId(int storyId) {
		this.storyId = storyId;
	}

	/**
	 * Sets the state to the parameter
	 * 
	 * @param state the state to set
	 */
	private void setState(String state) {
		switch (state) {
		case SUBMITTED_NAME:
			this.currentState = submittedState;
			break;
		case BACKLOG_NAME:
			this.currentState = backlogState;
			break;
		case WORKING_NAME:
			this.currentState = workingState;
			break;
		case VERIFYING_NAME:
			this.currentState = verifyingState;
			break;
		case COMPLETED_NAME:
			this.currentState = completedState;
			break;
		case REJECTED_NAME:
			this.currentState = rejectedState;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the id of the user story
	 * 
	 * @return the storyId
	 */
	public int getId() {
		return storyId;
	}

	/**
	 * Returns the state of the user story
	 * 
	 * @return the state
	 */
	public String getState() {
		return currentState.getStateName();
	}

	/**
	 * Returns the title of the user story
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the user story to the parameter
	 * 
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the user of the user story
	 * 
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user of the user story to the parameter
	 * 
	 * @param user the user to set
	 */
	private void setUser(String user) {
		this.user = user;
	}

	/**
	 * Returns the action of the user story
	 * 
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action of the user story to the parameter
	 * 
	 * @param action the action to set
	 */
	private void setAction(String action) {
		this.action = action;
	}

	/**
	 * Returns the value of the user story
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the user story to the parameter
	 * 
	 * @param value the value to set
	 */
	private void setValue(String value) {
		this.value = value;
	}

	/**
	 * Returns the priority of the user story
	 * 
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority of the user story to the parameter
	 * 
	 * @param priority the priority to set
	 */
	private void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Returns the developer id of the user story
	 * 
	 * @return the developerId
	 */
	public String getDeveloperId() {
		return developerId;
	}

	/**
	 * Sets the developer id of the user story to the parameter
	 * 
	 * @param developerId the developerId to set
	 */
	private void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

	/**
	 * Returns the rejection reason of the user story
	 * 
	 * @return the rejectionReason
	 */
	public String getRejectionReason() {
		return rejectionReason;
	}

	/**
	 * Sets the rejection reason of the user story to the parameter
	 * 
	 * @param rejectionReason the rejectionReason to set
	 */
	private void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	/** Increments the counter variable */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * Sets the value of the counter variable to the parameter integer
	 * 
	 * @param counter the counter to set
	 */
	public static void setCounter(int counter) {
		UserStory.counter = counter;
	}

	/**
	 * Returns the string representation of the UserStory that is printed during
	 * file save operations
	 */
	@Override
	public String toString() {
		String priorityString = "";
		String developerIdString = "";
		String rejectionReasonString = "";
		String rtn = null;
		if (currentState.getStateName().equals(REJECTED_NAME)) {
			rtn = "* " + storyId + "," + currentState.getStateName() + "," + title + "," + rejectionReason + "\n- "
					+ user + "\n- " + action + "\n- " + value;
		} else {
			if (this.priority != null && !"".equals(this.priority)) {
				priorityString = this.priority + ",";
			}

			if (this.developerId != null && !"".equals(this.developerId)) {
				developerIdString = this.developerId + ",";
			}

			if (this.rejectionReason != null && !"".equals(this.rejectionReason)) {
				rejectionReasonString = this.rejectionReason;
			}

			rtn = "* " + storyId + "," + currentState.getStateName() + "," + title + "," + priorityString
					+ developerIdString + rejectionReasonString + "\n- " + user + "\n- " + action + "\n- " + value;
		}
		return rtn;
	}

	/**
	 * Drives the finite state machine by delegating to the current state’s
	 * updateState(Command) method. This method throws an
	 * UnsupportedOperationException if the current state determines that the
	 * transition, as encapsulated by the Command, is not appropriate for the FSM
	 * 
	 * @param command Command describing the action that will update the UserStory's
	 *                state.
	 */
	public void update(Command command) {
		// Delegating to the current state's updateState method
		currentState.updateState(command);
	}
}
