package com.github.api.reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom TestNG listener that generates a clean, readable plain-text (.txt) test report.
 * Excludes ignored/skipped tests from the report as requested.
 */
public class TextReportListener implements ITestListener {

    private final List<TestRecord> passedTests = Collections.synchronizedList(new ArrayList<>());
    private final List<TestRecord> failedTests = Collections.synchronizedList(new ArrayList<>());
    private static final String REPORT_DIR = "reports";
    private static final String REPORT_FILE = "reports/test-report.txt";

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests.add(new TestRecord(result, "PASSED"));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        passedTests.removeIf(t -> t.methodName.equals(result.getMethod().getMethodName())); // safety
        failedTests.add(new TestRecord(result, "FAILED"));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Ignored / Skipped tests are intentionally NOT included in the text report.
    }

    @Override
    public void onFinish(ITestContext context) {
        File dir = new File(REPORT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        int totalExecuted = passedTests.size() + failedTests.size();
        int totalPassed = passedTests.size();
        int totalFailed = failedTests.size();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (PrintWriter writer = new PrintWriter(new FileWriter(REPORT_FILE))) {
            writer.println("================================================================================");
            writer.println("                   GITHUB REST ASSURED TEST EXECUTION REPORT                    ");
            writer.println("================================================================================");
            writer.println("Execution Date & Time : " + timestamp);
            writer.println("Test Suite Name       : " + context.getName());
            writer.println("Total Executed Tests  : " + totalExecuted);
            writer.println("Passed Tests          : " + totalPassed);
            writer.println("Failed Tests          : " + totalFailed);
            writer.println("================================================================================");
            writer.println();

            // PASSED TESTS SECTION
            writer.println("--------------------------------------------------------------------------------");
            writer.println("PASSED TESTS (" + totalPassed + ")");
            writer.println("--------------------------------------------------------------------------------");
            if (passedTests.isEmpty()) {
                writer.println("None");
            } else {
                for (int i = 0; i < passedTests.size(); i++) {
                    TestRecord record = passedTests.get(i);
                    writer.printf("%d. [PASS] %s :: %s (%d ms)%n",
                            (i + 1), record.className, record.methodName, record.durationMs);
                    if (record.description != null && !record.description.trim().isEmpty()) {
                        writer.println("   Description : " + record.description);
                    }
                    writer.println();
                }
            }

            // FAILED TESTS SECTION
            writer.println("--------------------------------------------------------------------------------");
            writer.println("FAILED TESTS (" + totalFailed + ")");
            writer.println("--------------------------------------------------------------------------------");
            if (failedTests.isEmpty()) {
                writer.println("None");
            } else {
                for (int i = 0; i < failedTests.size(); i++) {
                    TestRecord record = failedTests.get(i);
                    writer.printf("%d. [FAIL] %s :: %s (%d ms)%n",
                            (i + 1), record.className, record.methodName, record.durationMs);
                    if (record.description != null && !record.description.trim().isEmpty()) {
                        writer.println("   Description : " + record.description);
                    }
                    if (record.errorMessage != null) {
                        writer.println("   Error       : " + record.errorMessage);
                    }
                    writer.println();
                }
            }

            writer.println("================================================================================");
            writer.println("                              END OF REPORT                                     ");
            writer.println("================================================================================");

            System.out.println("📄 Plain text report generated at: " + new File(REPORT_FILE).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write text report: " + e.getMessage());
        }
    }

    private static class TestRecord {
        String className;
        String methodName;
        String description;
        long durationMs;
        String errorMessage;

        TestRecord(ITestResult result, String status) {
            this.className = result.getTestClass().getRealClass().getSimpleName();
            this.methodName = result.getMethod().getMethodName();
            this.description = result.getMethod().getDescription();
            this.durationMs = result.getEndMillis() - result.getStartMillis();
            if ("FAILED".equals(status) && result.getThrowable() != null) {
                this.errorMessage = result.getThrowable().getMessage();
            }
        }
    }
}
