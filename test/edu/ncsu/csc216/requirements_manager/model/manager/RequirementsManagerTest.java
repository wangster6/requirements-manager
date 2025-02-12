/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.command.Command;

/**
 * Test the methods in the Requirements Manager class
 * 
 * @author raywa
 */
class RequirementsManagerTest {
	/** The Requirements Manager manager */
	private RequirementsManager manager;

	/**
	 * Resets the manager and sets up a new RequirementsManager.
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = RequirementsManager.getInstance();
		manager.resetManager();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.RequirementsManager#saveCurrentProjectToFile(java.lang.String)}.
	 */
	@Test
	public void testSaveCurrentProjectToFile() {
		assertThrows(IllegalArgumentException.class, () -> manager.saveCurrentProjectToFile("test-files/does_not_exist_file.txt"));
		manager.loadProjectsFromFile("test-files/project1.txt");
		manager.saveCurrentProjectToFile("test-files/manager_testing.txt");
		
		checkFiles("test-files/manager_testing_expected.txt", "test-files/manager_testing.txt");
		
//		Exception e = assertThrows(IllegalArgumentException.class, () -> manager.saveCurrentProjectToFile("test-error/testing_dne.txt"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.RequirementsManager#loadProjectsFromFile(java.lang.String)}.
	 */
	@Test
	public void testLoadProjectsFromFile() {
		manager.loadProjectsFromFile("test-files/project1.txt");
		assertEquals(1, manager.getProjectList().length);
		assertEquals("WolfScheduler", manager.getProjectList()[0]);
		
		manager.loadProjectsFromFile("test-files/project2.txt");
		assertEquals(3, manager.getProjectList().length);
		assertEquals("WolfScheduler", manager.getProjectList()[1]);
		assertEquals("PackScheduler", manager.getProjectList()[2]);
		
//		assertThrows(NoSuchElementException.class, () -> manager.loadProjectsFromFile("test-files/project4.txt"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.RequirementsManager#createNewProject(java.lang.String)}.
	 */
	@Test
	public void testCreateNewProject() {
		manager.createNewProject("TestName");
		assertEquals(1, manager.getProjectList().length);
		assertEquals("TestName", manager.getProjectName());
		
		manager.createNewProject("TestName2");
		assertEquals(2, manager.getProjectList().length);
		
		assertThrows(IllegalArgumentException.class, () -> manager.createNewProject(null));
		assertThrows(IllegalArgumentException.class, () -> manager.createNewProject(""));
		assertThrows(IllegalArgumentException.class, () -> manager.createNewProject("   "));
		assertThrows(IllegalArgumentException.class, () -> manager.createNewProject("TestName2"));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.RequirementsManager#getUserStoriesAsArray()}.
	 */
	@Test
	public void testGetUserStoriesAsArray() {
		manager.createNewProject("TestName4");
		manager.addUserStoryToProject("TestTitle1", "TestUser1", "TestAction1", "TestValue1");
		assertEquals("0", manager.getUserStoriesAsArray()[0][0]);
		assertEquals("Submitted", manager.getUserStoriesAsArray()[0][1]);
		
		manager.loadProjectsFromFile("test-files/project2.txt");
		assertEquals("0", manager.getUserStoriesAsArray()[0][0]);
		assertEquals("Working", manager.getUserStoriesAsArray()[2][1]);
		assertEquals("Change Title", manager.getUserStoriesAsArray()[4][2]);
		assertEquals("jctetter", manager.getUserStoriesAsArray()[1][3]);
		assertEquals("", manager.getUserStoriesAsArray()[5][3]);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.RequirementsManager#executeCommand(int, edu.ncsu.csc216.requirements_manager.model.command.Command)}.
	 */
	@Test
	public void testExecuteCommand() {
		manager.loadProjectsFromFile("test-files/project1.txt");
		Command backlog = new Command(Command.CommandValue.BACKLOG, "TestPriority");
		Command reject = new Command(Command.CommandValue.REJECT, "TestReason");
		
		manager.executeCommand(4, backlog);
		assertEquals("Backlog", manager.getUserStoryById(4).getState());
		assertEquals("TestPriority", manager.getUserStoryById(4).getPriority());
		
		manager.executeCommand(3, reject);
		assertEquals("Rejected", manager.getUserStoryById(3).getState());
		assertEquals("TestReason", manager.getUserStoryById(3).getRejectionReason());
		
		manager.executeCommand(4, reject);
		assertEquals("Rejected", manager.getUserStoryById(4).getState());
		assertEquals("TestReason", manager.getUserStoryById(4).getRejectionReason());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.RequirementsManager#deleteUserStoryById(int)}.
	 */
	@Test
	public void testDeleteUserStoryById() {
		assertDoesNotThrow(() -> manager.deleteUserStoryById(0));
		
		manager.loadProjectsFromFile("test-files/project1.txt");
		assertEquals(6, manager.getUserStoriesAsArray().length);
		manager.deleteUserStoryById(3);
		assertEquals(null, manager.getUserStoryById(3));
		assertEquals(5, manager.getUserStoriesAsArray().length);
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
	 * Testing addUserStoryToProject method in requirements manager
	 */
	@Test
	public void testAddUserStoryToProject() {
		manager.loadProjectsFromFile("test-files/project2.txt");
//		assertThrows(IllegalArgumentException.class, () -> manager.addUserStoryToProject("title", "user", "action", "value"));
		assertDoesNotThrow(() -> manager.addUserStoryToProject("title", "user", "action", "value"));
	}
}
