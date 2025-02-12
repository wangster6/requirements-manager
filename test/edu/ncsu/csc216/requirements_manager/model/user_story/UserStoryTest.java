/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.user_story;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.command.Command;

/**
 * Tests the methods of User Story class
 * 
 * @author raywa
 */
class UserStoryTest {
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
	 * Test method for valid UserStory constructor
	 */
	@Test
	public void testValidUserStory() {
		
		assertDoesNotThrow(() -> new UserStory("Test1", "Test2", "Test3", "Test4"),
				"Should not throw IllegalArgumentException");

		UserStory u = new UserStory("TestTitle", "TestUser", "TestAction", "TestValue");
		assertEquals("Submitted", u.getState());
		assertEquals("TestTitle", u.getTitle());
		assertEquals("TestUser", u.getUser());
		assertEquals("TestAction", u.getAction());
		assertEquals("TestValue", u.getValue());
		assertEquals(null, u.getPriority());
		assertEquals(null, u.getDeveloperId());
		assertEquals(null, u.getRejectionReason());

		UserStory s = new UserStory(35, "Rejected", "TestTitle", "TestUser", "TestAction", "TestValue", null,
				null, "TestReason");
		assertEquals(35, s.getId());
		assertEquals("Rejected", s.getState());
		assertEquals("TestTitle", s.getTitle());
		assertEquals("TestUser", s.getUser());
		assertEquals("TestAction", s.getAction());
		assertEquals("TestValue", s.getValue());
		assertEquals(null, s.getPriority());
		assertEquals(null, s.getDeveloperId());
		assertEquals("TestReason", s.getRejectionReason());
	}

	/**
	 * Test method for invalid UserStory constructor
	 */
	@Test
	public void testInvalidUserStory() {
		assertThrows(IllegalArgumentException.class, () -> new UserStory("", "Test2", "Test3", "Test4"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory("Test1", "", "Test3", "Test4"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory("Test1", "Test2", "", "Test4"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory("Test1", "Test2", "Test3", ""));
		
		assertThrows(IllegalArgumentException.class, () -> new UserStory(35, "", "TestTitle", "TestUser", "TestAction", "TestValue", "High", "TestId", "TestReason"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory(35, "Rejected", "", "TestUser", "TestAction", "TestValue", null, null, "TestReason"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory(35, "Rejected", "TestTitle", "", "TestAction", "TestValue", null, null, "TestReason"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory(35, "Rejected", "TestTitle", "TestUser", "", "TestValue", null, null, "TestReason"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory(35, "Rejected", "TestTitle", "TestUser", "TestAction", "", null, null, "TestReason"));
		
		assertThrows(IllegalArgumentException.class, () -> new UserStory(0, "Rejected", "title", "user", "action", "value", null, null, "inappropriate"));
		assertThrows(IllegalArgumentException.class, () -> new UserStory(0, "Rejected", "title", "user", "action", "value", "high", null, null));
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.requirements_manager.model.user_story.UserStory#toString()}.
	 */
	@Test
	public void testToString() {
		UserStory a = new UserStory(35, "Rejected", "TestTitle", "TestUser", "TestAction", "TestValue", null,
				null, "TestReason");
		String testA = "* 35,Rejected,TestTitle,TestReason\n- TestUser\n- TestAction\n- TestValue";
		assertEquals(testA, a.toString());
		
		UserStory b = new UserStory(35, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority",
				"TestId", null);
		String testB = "* 35,Working,TestTitle,TestPriority,TestId,\n- TestUser\n- TestAction\n- TestValue";
		assertEquals(testB, b.toString());
		
		UserStory c = new UserStory(35, "Backlog", "TestTitle", "TestUser", "TestAction", "TestValue", "High",
				null, null);
		String testC = "* 35,Backlog,TestTitle,High,\n- TestUser\n- TestAction\n- TestValue";
		assertEquals(testC, c.toString());
		
		UserStory d = new UserStory(35, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null,
				null, null);
		String testD = "* 35,Submitted,TestTitle,\n- TestUser\n- TestAction\n- TestValue";
		assertEquals(testD, d.toString());
	}

	/**
	 * Test UserStory.setState method
	 */
	@Test
	public void testSetState() {
		UserStory a = new UserStory(10, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		assertEquals("Submitted", a.getState());
		
		UserStory b = new UserStory(10, "Backlog", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", null, null);
		assertEquals("Backlog", b.getState());
		
		UserStory c = new UserStory(10, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertEquals("Working", c.getState());
		
		UserStory d = new UserStory(10, "Verifying", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertEquals("Verifying", d.getState());
		
		UserStory e = new UserStory(10, "Completed", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertEquals("Completed", e.getState());
		
		UserStory f = new UserStory(10, "Rejected", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, "TestReason");
		assertEquals("Rejected", f.getState());
		
		assertThrows(IllegalArgumentException.class, () -> new UserStory(10, "Invalid", "TestTitle", "TestUser", "TestAction", "TestValue", "", "", ""));
	}
	
	/**
	 * Test UserStory.update method
	 */
	@Test
	public void testUpdate() {
		Command backlog = new Command(Command.CommandValue.BACKLOG, "TestPriority");
		Command reject = new Command(Command.CommandValue.REJECT, "TestReason");
		Command assign = new Command(Command.CommandValue.ASSIGN, "TestId");
		Command resubmit = new Command(Command.CommandValue.RESUBMIT, null);
		Command review = new Command(Command.CommandValue.REVIEW, null);
		Command reopen = new Command(Command.CommandValue.REOPEN, null);
		Command confirm = new Command(Command.CommandValue.CONFIRM, null);
		
		UserStory submitted1 = new UserStory(1, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		assertDoesNotThrow(() -> submitted1.update(backlog));
		assertEquals("Backlog", submitted1.getState());
		assertEquals("TestPriority", submitted1.getPriority());
		assertEquals(null, submitted1.getDeveloperId());
		assertEquals(null, submitted1.getRejectionReason());
		
		UserStory submitted2 = new UserStory(1, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		assertDoesNotThrow(() -> submitted2.update(reject));
		assertEquals("Rejected", submitted2.getState());
		assertEquals("TestReason", submitted2.getRejectionReason());
		
		UserStory backlog1 = new UserStory(1, "Backlog", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", null, null);
		assertDoesNotThrow(() -> backlog1.update(assign));
		assertEquals("Working", backlog1.getState());
		assertEquals("TestId", backlog1.getDeveloperId());
		assertEquals("TestPriority", backlog1.getPriority());
		
		UserStory backlog2 = new UserStory(1, "Backlog", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", null, null);
		assertDoesNotThrow(() -> backlog2.update(reject));
		assertEquals("Rejected", backlog2.getState());
		assertEquals("TestReason", backlog2.getRejectionReason());
		
		UserStory working1 = new UserStory(1, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> working1.update(assign));
		assertEquals("Working", working1.getState());
		assertEquals("TestId", working1.getDeveloperId());
		
		UserStory working2 = new UserStory(1, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> working2.update(reject));
		assertEquals("Rejected", working2.getState());
		assertEquals("TestReason", working2.getRejectionReason());
		
		UserStory working3 = new UserStory(1, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> working3.update(reopen));
		assertEquals("Backlog", working3.getState());
		assertEquals(null, working3.getDeveloperId());
		
		UserStory submitted3 = new UserStory(1, "Submitted", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, null);
		assertThrows(UnsupportedOperationException.class, () -> submitted3.update(assign));
		
		UserStory backlog3 = new UserStory(1, "Backlog", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", null, null);
		assertThrows(UnsupportedOperationException.class, () -> backlog3.update(confirm));

		UserStory working4 = new UserStory(1, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> working4.update(review));
		assertEquals("Verifying", working4.getState());
		
		UserStory working5 = new UserStory(1, "Working", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertThrows(UnsupportedOperationException.class, () -> working5.update(resubmit));
		
		UserStory verifying1 = new UserStory(1, "Verifying", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> verifying1.update(reopen));
		assertEquals("Working", verifying1.getState());

		UserStory verifying2 = new UserStory(1, "Verifying", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> verifying2.update(confirm));
		assertEquals("Completed", verifying2.getState());
		
		UserStory verifying3 = new UserStory(1, "Verifying", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertThrows(UnsupportedOperationException.class, () -> verifying3.update(resubmit));
		
		UserStory completed1 = new UserStory(1, "Completed", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertDoesNotThrow(() -> completed1.update(reopen));
		assertEquals("Working", completed1.getState());
		
		UserStory completed2 = new UserStory(1, "Completed", "TestTitle", "TestUser", "TestAction", "TestValue", "TestPriority", "TestId", null);
		assertThrows(UnsupportedOperationException.class, () -> completed2.update(resubmit));
		
		UserStory rejected1 = new UserStory(1, "Rejected", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, "TestReason");
		assertDoesNotThrow(() -> rejected1.update(resubmit));
		assertEquals("Submitted", rejected1.getState());
		
		UserStory rejected2 = new UserStory(1, "Rejected", "TestTitle", "TestUser", "TestAction", "TestValue", null, null, "TestReason");
		assertThrows(UnsupportedOperationException.class, () -> rejected2.update(reopen));
	}
	
//	/**
//	 * Test method for
//	 * {@link edu.ncsu.csc216.requirements_manager.model.user_story.UserStory#update(edu.ncsu.csc216.requirements_manager.model.command.Command)}.
//	 */
//	@Test
//	public void testUpdate() {
//		fail("Not yet implemented"); 
//	}

}
