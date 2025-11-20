package stepdefinitions;

import io.cucumber.java.en.*;
import utils.ExcelUtils;

import java.util.List;

public class StepDefs {

    private static List<String[]> excelData;
    private static int currentRowIndex = 0;
    private String[] currentRow;

    @Given("I start the scenario")
    public void i_start_the_scenario() {
        if (excelData == null) {
            excelData = ExcelUtils.readAllRows(
                    "D:/AutomationWork/Cash_Regression_Framework/TestingBill/src/test/resources/Data/Datatesting.xlsx"
            );
        }

        while (currentRowIndex < excelData.size()) {
            currentRow = excelData.get(currentRowIndex);
            System.out.println("\n=== Starting scenario for row " + (currentRowIndex + 1) + " ===");

            // نفذ كل Then داخليًا
            if (!executeStep(1)) {
                currentRowIndex++;
                continue;
            }
            if (!executeStep(2)) {
                currentRowIndex++;
                continue;
            }
            if (!executeStep(3)) {
                currentRowIndex++;
                continue;
            }
            if (!executeStep(4)) {
                currentRowIndex++;
                continue;
            }

            // الصف كله نجح
            System.out.println("All steps passed for row " + (currentRowIndex + 1));
            currentRowIndex++;
        }

        System.out.println("\n=== Finished all rows ===");
    }

    @Then("Step 1")
    public void step1() {
        // تم تنفيذها ضمن Given
    }

    @Then("Step 2")
    public void step2() {
        // تم تنفيذها ضمن Given
    }

    @Then("Step 3")
    public void step3() {
        // تم تنفيذها ضمن Given
    }

    @Then("Step 4")
    public void step4() {
        // تم تنفيذها ضمن Given
    }

    // ترجع false لو Amount > 100
    private boolean executeStep(int stepNumber) {
        double amount = Double.parseDouble(currentRow[5].replace(",", ""));
        if (amount > 100) {
            System.out.println("Step " + stepNumber + ": Amount أكبر من 100 (Amount=" + amount + ")");
            System.out.println("Skipping remaining steps for this row...");
            return false;
        } else {
            System.out.println("Step " + stepNumber + ": PASSED (Amount=" + amount + ")");
            return true;
        }
    }
}
