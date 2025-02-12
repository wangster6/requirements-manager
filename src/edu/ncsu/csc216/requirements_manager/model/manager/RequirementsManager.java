/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.requirements_manager.model.command.Command;
import edu.ncsu.csc216.requirements_manager.model.io.ProjectReader;
import edu.ncsu.csc216.requirements_manager.model.io.ProjectWriter;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * This class controls the creation and modification of many Projects.
 * 
 * @author raywa
 */
public class RequirementsManager {
	/** Implements the singleton design pattern */
	private static RequirementsManager singleton;

	/** The list of projects */
	private ArrayList<Project> projects;

	/** The current project that is selected */
	private Project currentProject;

	/** Constructs the requirements manager */
	private RequirementsManager() {
		projects = new ArrayList<Project>();
		currentProject = null;
	}

	/**
	 * Check if the singleton is null
	 * 
	 * @return the requirement manager instance
	 */
	public static RequirementsManager getInstance() {
		if (singleton == null) {
			singleton = new RequirementsManager();
		}
		return singleton;
	}

	/**
	 * If the currentProject is null or if there are no UserStorys in the
	 * currentProject an IllegalArgumentException should be thrown. Otherwise, write
	 * the Project to the file using the ProjectWriter class.
	 * 
	 * @param fileName name of file being written to
	 * @throws IllegalArgumentException if the currentProject is null or if there
	 *                                  are no UserStorys in the currentProject
	 */
	public void saveCurrentProjectToFile(String fileName) {
		// Check if current project is null or if there are no user stories in the
		// current project
		if (currentProject == null || currentProject.getUserStories().size() == 0) {
			throw new IllegalArgumentException();
		} else {
			ProjectWriter.writeProjectToFile(fileName, currentProject);
		}
	}

	/**
	 * Uses the ProjectReader to read the given fileName. The returned list of
	 * Projects are added to the projects field. The first project in the list
	 * returned from ProjectReader is made the currentProject.
	 * 
	 * @param fileName name of the file being read in from
	 */
	public void loadProjectsFromFile(String fileName) {
		int idx = projects.size();
		ArrayList<Project> temp = ProjectReader.readProjectFile(fileName);
		if(temp.size() > 0) {
			for(int i = 0; i < temp.size(); i++) {
				projects.add(temp.get(i));
			}
			currentProject = projects.get(idx);
			currentProject.setUserStoryId();
		}
	}

	/**
	 * Creates a new Project with the given name and adds it to the end of the
	 * projects list. The project is then loaded as the currentProject by calling
	 * the loadProject(String projectName) method. An IllegalArgumentException is
	 * thrown if the projectName parameter is null, empty string, or a duplicate of
	 * an existing project name (case-insensitive).
	 * 
	 * @param projectName name of the project
	 * @throws IllegalArgumentException if project name is null, empty, or a
	 *                                  duplicate of an existing project name
	 */
	public void createNewProject(String projectName) {
		// Check if projectName parameter is null or an empty string
		if (projectName == null || projectName.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		// Check if projectName is a duplicate of an existing project name
		// (case-insensitive)
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).getProjectName().toUpperCase().equals(projectName.toUpperCase())) {
				throw new IllegalArgumentException();
			}
		}

		// Create new project with given name
		Project project = new Project(projectName);
		
		// Add created project to end of project list
		projects.add(project);

		// Load created project as the current project
		loadProject(projectName);
	}

	/**
	 * Returns a 2D Object array that is used to populate the UserStoryTableModel
	 * (inner class of the RequirementsManagerGUI.UserStoryListPanel) with
	 * information. The 2D Object array stores [rows][columns]. The array should
	 * have 1 row for every UserStory that you need to return. There should be 4
	 * columns: id, state name, title, and developer id (or an empty string if there
	 * is no current developer assigned) in the respective order..
	 * 
	 * @return the user stories as an array
	 */
	public String[][] getUserStoriesAsArray() {
		if(currentProject != null) {
			ArrayList<UserStory> storiesAL = currentProject.getUserStories();
			String[][] stories = new String[storiesAL.size()][4];
			for (int i = 0; i < storiesAL.size(); i++) {
				stories[i][0] = String.valueOf(storiesAL.get(i).getId());
				stories[i][1] = storiesAL.get(i).getState();
				stories[i][2] = storiesAL.get(i).getTitle();
				if (storiesAL.get(i).getDeveloperId() == null) {
					stories[i][3] = "";
				} else {
					stories[i][3] = storiesAL.get(i).getDeveloperId();
				}
			}
			return stories;
		} else {
			return null;
		}
	}

	/**
	 * Returns the UserStory in the current project with the given id. If there is
	 * no UserStory with that id, the method returns null.
	 * 
	 * @param storyId the id of the user story
	 * @return the user story
	 */
	public UserStory getUserStoryById(int storyId) {
		if(currentProject != null) {
			return currentProject.getUserStoryById(storyId);
		}
		return null;
	}

	/**
	 * Find the UserStory in the current project with the given id and update it by
	 * passing in the given Command.
	 * 
	 * @param id	id of the user story that the command is being executed on
	 * @param command     the command to be executed
	 */
	public void executeCommand(int id, Command command) {
		if(currentProject != null) {
			currentProject.executeCommand(id, command);
		}
	}

	/**
	 * Deletes a user story from the current project
	 * 
	 * @param storyId id of the user story being deleted
	 */
	public void deleteUserStoryById(int storyId) {
		if(currentProject != null) {
			currentProject.deleteUserStoryById(storyId);
		}
	}

	/**
	 * Adds a user story to the current project
	 * 
	 * @param title  of the user story
	 * @param user   of the user story
	 * @param action of the user story
	 * @param value  of the user story
	 */
	public void addUserStoryToProject(String title, String user, String action, String value) {
		if(currentProject != null) {
			currentProject.addUserStory(title, user, action, value);
		}
	}

	/**
	 * Find the Project with the given name in the list, makes it the active or
	 * currentProject, and sets the user story id for that project so that any new
	 * UserStorys added to the project are created with the next correct id.
	 * 
	 * @param projectName name of the project being loaded
	 */
	public void loadProject(String projectName) {
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).getProjectName() == projectName) {
				currentProject = projects.get(i);
				currentProject.setUserStoryId();
			}
		}
	}

	/**
	 * Returns the project name for the currentProject. If the currentProject is
	 * null, then null is returned
	 * 
	 * @return the name of the project
	 */
	public String getProjectName() {
		if(currentProject != null) {
			if (currentProject.getProjectName() == null) {
				return null;
			} else {
				return currentProject.getProjectName();
			}
		} else {
			return null;
		}
	}

	/**
	 * Returns a String array of project names in the order they are listed in the
	 * projects list. This is used by the GUI to populate the project drop down.
	 * 
	 * @return a string array of the projects
	 */
	public String[] getProjectList() {
		String[] list = new String[projects.size()];
		for (int i = 0; i < projects.size(); i++) {
			list[i] = projects.get(i).getProjectName();
		}
		return list;
	}

	/**
	 * Resets the requirements manager
	 */
	protected void resetManager() {
		singleton = null;
	}
}