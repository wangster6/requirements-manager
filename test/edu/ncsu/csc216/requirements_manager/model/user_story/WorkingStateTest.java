/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.user_story;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.command.Command;

/**
 * Test the Working State inner class
 * 
 * @author raywa
 */
class WorkingStateTest {
	/**
	 * Testing method of the Working State inner class
	 */
	@Test
	public void testWorkingC() {
		UserStory story = new UserStory("title", "user", "action", "value");
		assertEquals("Submitted", story.getState());
		story.update(new Command(Command.CommandValue.BACKLOG, "High"));
		assertEquals("Backlog", story.getState());
		assertEquals("High", story.getPriority());
		story.update(new Command(Command.CommandValue.ASSIGN, "id"));
		assertEquals("Working", story.getState());
		assertEquals("High", story.getPriority());
		assertEquals("id", story.getDeveloperId());
		story.update(new Command(Command.CommandValue.REOPEN, null));
		assertEquals("Backlog", story.getState());
		assertEquals("High", story.getPriority());
		assertEquals(null, story.getDeveloperId());
	}

}
