/**
 * 
 */
package edu.ncsu.csc216.requirements_manager.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.requirements_manager.model.manager.Project;
import edu.ncsu.csc216.requirements_manager.model.user_story.UserStory;

/**
 * Test the methods in the ProjectReader class
 * 
 * @author raywa
 */
class ProjectReaderTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.requirements_manager.model.io.ProjectReader#readProjectFile(java.lang.String)}.
	 */
	@Test
	public void testReadProjectFile() {
		UserStory.setCounter(0);
		ArrayList<Project> testRead = ProjectReader.readProjectFile("test-files/project2.txt");

		ArrayList<Project> testExpected = new ArrayList<Project>();
		Project a = new Project("WolfScheduler");
		a.addUserStory(new UserStory(0, "Completed", "Load Catalog", "student", "load a course catalog file",
				"plan a schedule for next semester", "High", "sesmith5", null));
		a.addUserStory(
				new UserStory(1, "Verifying", "Select Course", "student", "select a course to add to my schedule",
						"plan a schedule for next semester", "Medium", "jctetter", null));
		a.addUserStory(new UserStory(2, "Working", "Add Event", "student", "add a recurring event",
				"schedule extra curricular activities", "Medium", "ignacioxd", null));
		a.addUserStory(new UserStory(3, "Backlog", "Export Schedule", "student", "export my schedule",
				"so it's available when I register for classes", "Low", null, null));
		a.addUserStory(new UserStory(4, "Submitted", "Change Title", "student", "change the title of my schedule",
				"so I can have different schedules to consider", null, null, null));
		a.addUserStory(new UserStory(5, "Rejected", "Post to Piazza", "student",
				"post my schedule to Piazza for feedback",
				"see which of my friends are interested in taking the same courses", null, null, "Inappropriate"));

		Project b = new Project("PackScheduler");
		b.addUserStory(new UserStory(1, "Backlog", "Load Student Directory", "administrator",
				"load the student directory into the registration system",
				"students can register for classes next semester", "High", null, null));

		testExpected.add(a);
		testExpected.add(b);

		assertEquals(testExpected.get(0).getUserStories().get(0).toString(),
				testRead.get(0).getUserStories().get(0).toString());
		
		assertThrows(IllegalArgumentException.class, () -> ProjectReader.readProjectFile("doesnotexist/dne.txt"));
	}

	/**
	 * Test method for ProjectReader constructor
	 */
	@Test
	public void testProjectReader() {
		assertDoesNotThrow(() -> new ProjectReader());
	}

	/**
	 * Test method for project reader read project file. Testing project3.txt
	 */
	@Test
	public void testValidFile3() {
		UserStory.setCounter(0);
		ArrayList<Project> testRead = ProjectReader.readProjectFile("test-files/project3.txt");
		assertEquals(6, testRead.get(0).getUserStories().size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, testRead.get(0).getUserStories().get(i).getId());
		}
	}
	
	/**
	 * Testing invalid files being read in
	 */
	@Test
	public void testInvalidFile1() {
		UserStory.setCounter(0);
		ArrayList<Project> testRead = ProjectReader.readProjectFile("test-files/project5.txt");
		assertEquals(0, testRead.size());
//		System.out.println(testRead);
	}
	
	/**
	 * Testing to reach coverage for Project Reader Class
	 */
	@Test
	public void testForCoverage() {
		ArrayList<Project> testRead = ProjectReader.readProjectFile("test-files/coverage1.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/coverage2.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project4.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project5.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project6.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project7.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project8.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project9.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project10.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project11.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project12.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project13.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project14.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project15.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project16.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project17.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project18.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project19.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project20.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project21.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project22.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project23.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project24.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project25.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project26.txt");
		assertEquals(0, testRead.size());
		
		testRead = ProjectReader.readProjectFile("test-files/project27.txt");
		assertEquals(0, testRead.size());
	}
}
