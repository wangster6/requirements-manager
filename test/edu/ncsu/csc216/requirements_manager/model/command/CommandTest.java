/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.command;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests to test the methods in Command class
 * 
 * @author raywa
 */
class CommandTest {
	/**
	 * Test method for valid Command constructor
	 */
	@Test
	public void testCommandValid() {
		// Command value that requires command info does not throw if command info exists
		assertDoesNotThrow(() -> new Command(Command.CommandValue.ASSIGN, "test1"), "Should not throw exception");
		
		// Command value that requires no command info does not throw if no command info exists
		assertDoesNotThrow(() -> new Command(Command.CommandValue.RESUBMIT, null));
		
		// Created command object fields are equal to the fields of the instance
		Command c2 = new Command(Command.CommandValue.BACKLOG, "test2");
		assertEquals(Command.CommandValue.BACKLOG, c2.getCommand());
		assertEquals("test2", c2.getCommandInformation());
	}
	
	/**
	 * Test method for invalid Command constructor
	 */
	@Test
	public void testCommandInvalid() {
		// Invalid command value
		assertThrows(IllegalArgumentException.class, () -> new Command(null, null));
		
		// Command value requires command info but command info is null/empty/blank
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.BACKLOG, null));
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.ASSIGN, ""));
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.REJECT, "    "));
		
		// Command value requires no command info but command info exists
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.REVIEW, "testing"));
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.CONFIRM, "testing"));
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.REOPEN, "testing"));
		assertThrows(IllegalArgumentException.class, () -> new Command(Command.CommandValue.RESUBMIT, "testing"));
	}
}
