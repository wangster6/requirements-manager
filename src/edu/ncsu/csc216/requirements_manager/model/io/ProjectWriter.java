/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.requirements_manager.model.manager.Project;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * Writes the project or projects to the given file
 * 
 * @author raywa
 */
public class ProjectWriter {

	/**
	 * Writes the Project to the provided file
	 * 
	 * @param fileName the file to write to
	 * @param project  the project being written
	 * @throws IllegalArgumentException if file is not found
	 */
	public static void writeProjectToFile(String fileName, Project project) {
		ArrayList<UserStory> stories = project.getUserStories();
		String storyListString = "";
		for (int i = 0; i < stories.size(); i++) {
			if (i == 0) {
				storyListString = stories.get(i).toString();
			} else {
				storyListString = storyListString + "\n" + stories.get(i).toString();
			}
		}

		String finalString = "# " + project.getProjectName() + "\n" + storyListString;

		try {
			PrintStream fileWriter = new PrintStream(new File(fileName));
			fileWriter.println(finalString);
			fileWriter.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
