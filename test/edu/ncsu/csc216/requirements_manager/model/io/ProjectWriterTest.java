/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.manager.Project;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * Test the methods in the ProjectWriter class
 * 
 * @author raywa
 */
class ProjectWriterTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.io.ProjectWriter#writeProjectToFile(java.lang.String, edu.ncsu.csc216.requirements_manager.model.manager.Project)}.
	 */
	@Test
	public void testWriteProjecttoFile() {
		UserStory.setCounter(0);
		Project project = new Project("TestName");
		
		project.addUserStory(new UserStory(0, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null));
		project.addUserStory(new UserStory(1, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null));
		project.addUserStory(new UserStory(2, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null));

		
		ProjectWriter.writeProjectToFile("test-files/writer_testing.txt", project);
		checkFiles("test-files/writer_testing_expected.txt", "test-files/writer_testing.txt");
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> ProjectWriter.writeProjectToFile("test-error/testing_dne.txt", project));
		assertEquals("Unable to save file.", e.getMessage());
	}
	
	/**
	 * Test method for ProjectWriter constructor
	 */
	@Test
	public void testProjectWriter() {
		assertDoesNotThrow(() -> new ProjectWriter());
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	/**
	 * Test Method for Project Writer write to file method
	 */
	@Test
	public void testWriteProjectUserStoryWorking() {
		Project project = new Project("Project");
		UserStory story = new UserStory(0, "Verifying", "title", "user", "action", "value", "Medium", "id", null);
		assertDoesNotThrow(() -> project.addUserStory(story));
		ProjectWriter.writeProjectToFile("test-files/actual_verifying.txt", project);
		checkFiles("test-files/expected_verifying.txt", "test-files/actual_verifying.txt");
	}
}
