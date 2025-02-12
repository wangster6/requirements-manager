/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.user_story;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.command.Command;

/**
 * Testing the methods of BacklogState
 * 
 * @author raywa
 */
class BacklogStateTest {

	@Test
	public void testBacklogA() {
		UserStory testing = new UserStory("TestTitle", "TestUser", "TestAction", "TestValue");
		assertEquals("Submitted", testing.getState());
		testing.update(new Command(Command.CommandValue.BACKLOG, "High"));
		assertEquals("Backlog", testing.getState());
		assertEquals("High", testing.getPriority());
		assertEquals(null, testing.getDeveloperId());
		assertEquals(null, testing.getRejectionReason());
		testing.update(new Command(Command.CommandValue.ASSIGN, "id"));
		assertEquals("Working", testing.getState());
		assertEquals("High", testing.getPriority());
		assertEquals("id", testing.getDeveloperId());
		assertEquals(null, testing.getRejectionReason());
	}

}
