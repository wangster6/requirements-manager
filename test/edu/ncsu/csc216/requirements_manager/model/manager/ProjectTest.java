/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.command.Command;
import edu.ncsu.csc216.requirements_manager.model.io.ProjectReader;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * Test the methods in the Project class
 * 
 * @author raywa
 */
class ProjectTest {
	/**
	 * Resets the manager and sets up a new RequirementsManager.
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	public void setUp() throws Exception {
		UserStory.setCounter(0);
	}
	
	/**
	 * Test the valid Project constructor
	 */
	@Test
	public void testValidProject() {
		Project a = new Project("TestName");
		assertEquals("TestName", a.getProjectName());
		assertEquals(0, a.getUserStories().size());
	}
	
	/**
	 * Test the invalid Project constructor
	 */
	@Test
	public void testInvalidProject() {
		assertThrows(IllegalArgumentException.class, () -> new Project(""));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.Project#addUserStory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddUserStoryStringStringStringString() {
		Project b = new Project("TestName");
		b.addUserStory("TestTitle", "TestUser", "TestAction", "TestValue");
		assertEquals(1, b.getUserStories().size());
		assertEquals(0, b.getUserStories().get(0).getId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.Project#addUserStory(edu.ncsu.csc216.requirements_manager.model.user_story.UserStory)}.
	 */
	@Test
	public void testAddUserStoryUserStory() {
		Project c = new Project("TestName");
		UserStory w = new UserStory(10, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		UserStory x = new UserStory(15, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		UserStory y = new UserStory(5, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		UserStory z = new UserStory(5, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		
		c.addUserStory(w);
		assertEquals(1, c.getUserStories().size());
		c.addUserStory(x);
		c.addUserStory(y);
		assertEquals("TestTitle", c.getUserStories().get(0).getTitle());
		assertEquals("TestUser", c.getUserStories().get(0).getUser());
		assertEquals("TestAction", c.getUserStories().get(0).getAction());
		assertEquals("TestValue", c.getUserStories().get(0).getValue());
		assertThrows(IllegalArgumentException.class, () -> c.addUserStory(z));
		assertDoesNotThrow(() -> c.addUserStory("title", "user", "action", "value"));
		assertDoesNotThrow(() -> c.addUserStory("title", "user", "action", "value"));
	}
	
	/**
	 * Testing adding duplicate user story id
	 */
	@Test
	public void testAddUserStoryDuplicateId() {
		UserStory.setCounter(0);
		Project project = new Project("TestName");
		assertEquals(0, project.getUserStories().size());
		assertEquals(0, project.addUserStory("title", "user", "action", "value"));
		assertEquals(1, project.getUserStories().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.Project#getUserStoryById(int)}.
	 */
	@Test
	public void testGetUserStoryById() {
		Project a = new Project("TestName");
		UserStory x = new UserStory(5, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		
		a.addUserStory(x);
		assertEquals(x, a.getUserStoryById(5));
		assertEquals(null, a.getUserStoryById(10));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.Project#executeCommand(int, edu.ncsu.csc216.requirements_manager.model.command.Command)}.
	 */
	@Test
	public void testExecuteCommand() {
		Project a = new Project("TestName");
		UserStory story = new UserStory(0, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		Command command = new Command(Command.CommandValue.BACKLOG, "TestPriority");
		
		a.addUserStory(story);
		assertDoesNotThrow(() -> a.executeCommand(0, command));
		assertEquals("Backlog", a.getUserStoryById(0).getState());
		assertEquals("TestPriority", a.getUserStoryById(0).getPriority());
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.Project#executeCommand(int, edu.ncsu.csc216.requirements_manager.model.command.Command)}.
	 */
	@Test
	public void testExecuteCommand2() {
		ArrayList<Project> projects = ProjectReader.readProjectFile("test-files/project1.txt");
		assertEquals(6, projects.get(0).getUserStories().size());
		projects.get(0).executeCommand(5, new Command(Command.CommandValue.RESUBMIT, null));
		assertEquals("Submitted", projects.get(0).getUserStories().get(5).getState());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.requirements_manager.model.manager.Project#deleteUserStoryById(int)}.
	 */
	@Test
	public void testDeleteUserStoryById() {
		Project a = new Project("TestName");
		UserStory x = new UserStory(5, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		
		a.addUserStory(x);
		assertEquals(1, a.getUserStories().size());
		a.deleteUserStoryById(10);
		assertEquals(1, a.getUserStories().size());
		a.deleteUserStoryById(5);
		assertEquals(0, a.getUserStories().size());
	}

}
