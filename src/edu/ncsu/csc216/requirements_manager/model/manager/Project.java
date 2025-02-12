/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.ncsu.csc216.requirements_manager.model.command.Command;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * This class creates the project object that holds the user stories
 * 
 * @author raywa
 */
public class Project {

	/** Name of the project */
	private String projectName = "";

	/** The list of user stories */
	private ArrayList<UserStory> stories = null;

	/**
	 * Constructs the project based on parameters
	 * 
	 * @param name name of the project
	 * @throws IllegalArgumentException if name of project is null or empty
	 */
	public Project(String name) {
		if ("".equals(name) || name == null) {
			throw new IllegalArgumentException();
		}
		setProjectName(name);
		stories = new ArrayList<UserStory>();
		UserStory.setCounter(0);
	}

	/**
	 * Set the counter for the UserStory instances to the value of the maximum id in
	 * the list of UserStorys for the project + 1.
	 */
	public void setUserStoryId() {
		int max = 0;
		for (int i = 0; i < stories.size(); i++) {
			if (stories.get(i).getId() > max) {
				max = stories.get(i).getId();
			}
		}
		if (max == 0) {
			UserStory.setCounter(max);
		} else if (max > 0) {
			UserStory.setCounter(max + 1);
		}
//		UserStory.setCounter(max + 1);
	}

	/**
	 * Sets the name of the current project to the parameter
	 * 
	 * @param projectName the projectName to set
	 */
	private void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Returns the project Name
	 * 
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Creates a new UserStory in the submitted state, adds it to the list in sorted
	 * order, and returns the id. If a story already exists with the given id, an
	 * IllegalArgumentException will be thrown.
	 * 
	 * @param title  title of the user story
	 * @param user   user of the user story
	 * @param action action of the user story
	 * @param value  value of the user story
	 * @return the id of the user story that is added
	 */
	public int addUserStory(String title, String user, String action, String value) {
		UserStory story = new UserStory(title, user, action, value);
		addUserStory(story);
		return story.getId();
	}

	/**
	 * adds the user story to the list in sorted order by id. The list will be
	 * maintained in sorted order, so you will be able to add a new story in order.
	 * If a story already exists with the given id, an IllegalArgumentException will
	 * be thrown.
	 * 
	 * @param story the user story to add to the list
	 * @throws IllegalArgumentException if a story already exists with the given id
	 */
	public void addUserStory(UserStory story) {
		// Checks if a story already exists with the given id
		for (int i = 0; i < this.stories.size(); i++) {
			if (this.stories.get(i).getId() == story.getId()) {
				throw new IllegalArgumentException("Duplicate user story id."); // Throws IAE if a story already exists with the given id
			}
		}

		// Adds story to the list in sorted order based on the story id
		stories.add(story);
		Collections.sort(stories, Comparator.comparing(UserStory::getId));
	}

	/**
	 * returns the List of UserStorys.
	 * 
	 * @return the List of user stories
	 */
	public ArrayList<UserStory> getUserStories() {
		return this.stories;
	}

	/**
	 * Returns the UserStory in the list with the given id. If there is no UserStory
	 * with that id, the method returns null.
	 * 
	 * @param id of the user story
	 * @return User story in the list with the given id
	 */
	public UserStory getUserStoryById(int id) {
		for (int i = 0; i < stories.size(); i++) {
			if (stories.get(i).getId() == id) {
				return stories.get(i);
			}
		}
		return null;
	}

	/**
	 * Find the UserStory with the given id and update it by passing in the given
	 * Command.
	 * 
	 * @param id of the user story
	 * @param c  the given command
	 */
	public void executeCommand(int id, Command c) {
		for (int i = 0; i < this.stories.size(); i++) {
			if (this.stories.get(i).getId() == id) {
				this.stories.get(i).update(c);
			}
		}
	}

	/**
	 * Removes the user story with the given id from the list
	 * 
	 * @param id id of the user story
	 */
	public void deleteUserStoryById(int id) {
		for (int i = 0; i < this.stories.size(); i++) {
			if (this.stories.get(i).getId() == id) {
				this.stories.remove(i);
			}
		}
		setUserStoryId();
	}

//	public static void main(String[] args) {
//		Project project = new Project("project");
////		project.addUserStory("title", "user", "action", "value");
//		System.out.println(project.addUserStory("title", "user", "action", "value"));
//	}
}
