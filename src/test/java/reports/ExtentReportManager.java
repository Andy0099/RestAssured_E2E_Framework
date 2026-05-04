package reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Why ITestListener: It is a TestNG interface that allows us to 'listen' 
 * to the test execution events like Start, Success, or Failure.
 */
public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter; // Handles the UI/Look of the report (HTML/Theme)
    public ExtentReports extent;               // The actual report engine that collects data
    
    /**
     * Why ThreadLocal: In a real project, we run tests in Parallel (multiple at once).
     * ThreadLocal creates a separate "log container" for each test so logs don't get mixed up.
     */
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
     * This method runs ONCE before the entire test suite starts.
     * We use it to set up the file name, theme, and system info.
     */
    public void onStart(ITestContext testContext) {
        // Define where to save the report file
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/TestReport.html");

        sparkReporter.config().setDocumentTitle("RestAssured Automation Report"); // Browser Tab Title
        sparkReporter.config().setReportName("User API Testing");               // Main Header
        sparkReporter.config().setTheme(Theme.DARK);                             // Professional dark mode

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter); // Connecting the UI to the Engine
        
        // Adding metadata (Useful for debugging in large teams)
        extent.setSystemInfo("Application", "User API");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Anand");
    }

    /**
     * This method runs at the START of every single @Test method.
     */
    public void onTestStart(ITestResult result) {
        // Create an entry in the report using the @Test method name
        ExtentTest extentTest = extent.createTest(result.getName());
        test.set(extentTest); // Save this entry into our 'ThreadLocal' container
    }

    public void onTestSuccess(ITestResult result) {
        // .get() retrieves the correct test log for the current thread
        test.get().log(Status.PASS, "Test Passed: " + result.getName());
    }

    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getName());
        // result.getThrowable() captures the exact error/assertion failure message
        test.get().log(Status.FAIL, result.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped: " + result.getName());
    }

    /**
     * This is the most important step. Without .flush(), the report file is NOT created.
     */
    public void onFinish(ITestContext testContext) {
        extent.flush(); // Finalize and write everything to the HTML file
    }

    /**
     * Mastery Helper: This static method allows us to write logs from ANY class
     * without needing to create an object of ExtentReportManager.
     */
    public static void logStep(String message) {
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
    }
}
