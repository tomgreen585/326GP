package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This class runs all the test classes in the tests package.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestAutopilot.class,
    TestSensorData.class,
    TestFlightManagement.class,
    TestHazards.class,
    TestConsolePanel.class
})
public class TestAll {} // This class remains empty, don't put things here.
