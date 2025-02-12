/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.requirements_manager.model.manager.Project;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * Read in the list of projects from a given file.
 * 
 * @author raywa
 */
public class ProjectReader {

	/**
	 * Reads in projects from a file
	 * 
	 * @param fileName name of file being read in from
	 * @return array list of projects that were read in from file
	 * @throws IllegalArgumentException if file to load cannot be found
	 */
	public static ArrayList<Project> readProjectFile(String fileName) {
		ArrayList<Project> projectList = new ArrayList<Project>(); // Create an empty array of Project objects

		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file

			String file = "";
			while (fileReader.hasNextLine()) { // While we have more lines in the file
				String line = fileReader.nextLine().trim();
				file = file + "\n" + line;
			}
			fileReader.close();

			Scanner projectReader = new Scanner(file);
			projectReader.useDelimiter("\\r?\\n?[#]");
			while (projectReader.hasNext()) {
				String projectToken = projectReader.next().trim();
				Project project;
//				System.out.println(projectToken);
				if (projectToken.trim().charAt(0) != '*') {
					project = processProject(projectToken);
				} else {
					project = null;
				}

				if (project != null) {
					projectList.add(project);
				}
			}
			projectReader.close();

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}

		if (projectList.size() == 0) {
			projectList = new ArrayList<Project>();
		}
		return projectList;
	}

	/**
	 * Processes the Project
	 * 
	 * @param projectToken the project token string
	 * @return the processed project
	 */
	private static Project processProject(String projectToken) {
		Project project;

		// Construct Scanner to process the line parameter.
		Scanner lineReader = new Scanner(projectToken);

		String projectName = lineReader.nextLine().trim();
//		System.out.println(projectName);
		if (projectName == null || "".equals(projectName.trim())) {
			lineReader.close();
			return null;
		} else {
			project = new Project(projectName);
//			System.out.println(projectName);
		}

		lineReader.useDelimiter("\\r?\\n?[*]");
//		System.out.println(lineReader.next().trim());

		while (lineReader.hasNext()) {
			String storyToken = lineReader.next().trim();
//			System.out.println(storyToken);
			UserStory story = processUserStory(storyToken);
//			System.out.println(story);
			if (story != null) {
				project.addUserStory(story);
			}
		}

		if (project.getUserStories().size() == 0) {
			lineReader.close();
			return null;
		}
		lineReader.close();
//		project.setUserStoryId();
		return project;
	}

	/**
	 * Processes the user story
	 * 
	 * @param storyToken the story token String
	 * @return the processed user story or null if the user story in incorrect
	 */
	private static UserStory processUserStory(String storyToken) {
		String idString = "";
		int id = 0;
		String state = "";
		String title = "";
		String priority = null;
		String developerId = null;
		String rejectionReason = null;
		String user = "";
		String action = "";
		String value = "";
				
		Scanner lineReader = new Scanner(storyToken);
//		System.out.println(storyToken);
		String storyFields = lineReader.nextLine();

		lineReader.useDelimiter("\\r?\\n?[-]"); // Setting delimiter to -
		if (lineReader.hasNext()) { // Check if next segment exists
			user = lineReader.next().trim(); // Sets temporary user field
		} else {
			user = ""; // Sets temp user field to empty string if no next exists
		}

		if (lineReader.hasNext()) { // Check if next segment exists
			action = lineReader.next().trim(); // Sets temp action field
		} else {
			action = ""; // Sets temp action field to empty string if no next exists
		}

		if (lineReader.hasNext()) { // Check if next segment exists
			value = lineReader.next().trim(); // Sets temp value fied
		} else {
			value = ""; // Sets temp value field to empty string if no next exists
		}

		if (lineReader.hasNext()) {// Check if there are extra user/action/value lines
			lineReader.close(); // Close reader
			return null; // returning null if there are extra user/action/value lines
		}

		lineReader.close();
		// Sifts through story field line to set id, state, and title
		Scanner storyReader = new Scanner(storyFields);
		storyReader.useDelimiter(",");

		if (storyReader.hasNext()) {
			idString = storyReader.next();
			try {
				id = Integer.parseInt(idString.trim());
			} catch (NumberFormatException e) {
				storyReader.close();
				return null;
			}
		}

		if (storyReader.hasNext()) {
			state = storyReader.next().trim();
			if (!"Submitted".equals(state) && !"Backlog".equals(state) && !"Working".equals(state)
					&& !"Verifying".equals(state) && !"Completed".equals(state) && !"Rejected".equals(state)) {
				storyReader.close();
				return null;
			}
		} else {
			storyReader.close();
			return null;
		}

		if (storyReader.hasNext()) {
			title = storyReader.next().trim();
		} else {
			storyReader.close();
			return null;
		}
		// Checks and sets priority, developerId, and rejectionReason through
		// switch-case.
		switch (state) {
		case "Backlog":
			if (storyReader.hasNext()) {
				priority = storyReader.next().trim();
				if (priority == null || "".equals(priority)) {
					storyReader.close();
					return null;
				}
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				storyReader.close();
				return null;
			}
			break;
		case "Working":
			if (storyReader.hasNext()) {
				priority = storyReader.next().trim();
				if (priority == null || "".equals(priority)) {
					storyReader.close();
					return null;
				}
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				developerId = storyReader.next().trim();
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				storyReader.close();
				return null;
			}
			break;
		case "Verifying":
			if (storyReader.hasNext()) {
				priority = storyReader.next().trim();
				if (priority == null || "".equals(priority)) {
					storyReader.close();
					return null;
				}
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				developerId = storyReader.next().trim();
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				storyReader.close();
				return null;
			}
			break;
		case "Completed":
			if (storyReader.hasNext()) {
				priority = storyReader.next().trim();
				if (priority == null || "".equals(priority)) {
					storyReader.close();
					return null;
				}
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				developerId = storyReader.next().trim();
			} else {
				storyReader.close();
				return null;
			}
			if (storyReader.hasNext()) {
				storyReader.close();
				return null;
			}
			break;
		case "Rejected":
			if (storyReader.hasNext()) {
				String temp = storyReader.next().trim();
				if (temp.equals(UserStory.HIGH_PRIORITY) || temp.equals(UserStory.MEDIUM_PRIORITY)
						|| temp.equals(UserStory.LOW_PRIORITY)) {
					storyReader.close();
					return null;
				} else if (temp.equals(UserStory.DUPLICATE_REJECTION) || temp.equals(UserStory.INAPPROPRIATE_REJECTION)
						|| temp.equals(UserStory.INFEASIBLE_REJECTION)) {
					rejectionReason = temp;
				} else {
					storyReader.close();
					return null;
				}
			} else {
				storyReader.close();
				return null;
			}
			break;
		case "Submitted":
			if (storyReader.hasNext()) {
				priority = storyReader.next().trim();
			}
			if (storyReader.hasNext()) {
				developerId = storyReader.next().trim();
			}
			if (storyReader.hasNext()) {
				rejectionReason = storyReader.next().trim();
			}
		default:
//			priority = null;
//			developerId = null;
//			rejectionReason = null;
			break;
		}

		storyReader.close(); // Close reader

		// Create return user story
		UserStory rtn;
		// Check if state, title, user, action, or value are empty strings. return null
		// if they are
		if ("".equals(state) || "".equals(title) || "".equals(user) || "".equals(action) || "".equals(value)) {
			return null;
		}
		// Set return user story as new user story created with the variable fields.
		// return null if IAE is caught
		try {
			rtn = new UserStory(id, state, title, user, action, value, priority, developerId, rejectionReason);
		} catch (IllegalArgumentException e) {
			return null;
		}

		return rtn;
	}

//	public static void main(String[] args) {
//		readProjectFile("test-files/project27.txt");
//	}
}
