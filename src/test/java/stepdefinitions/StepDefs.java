package stepdefinitions;

import Enum.CompanyType;
import Enum.CategoryType;
import io.cucumber.java.en.*;
import utils.ExcelUtils;

import java.util.List;

public class StepDefs
{

    // ───────────────────────────────────────────────
    //  GLOBAL VARIABLES (مستخدمات في كل المشروع)
    // ───────────────────────────────────────────────
    private String companyName;
    private String companyCode;
    private CategoryType companyCategory;
    private CompanyType selectedCompany;


    private static List<String[]> excelData;
    private static int currentRowIndex = 0;
    private String[] currentRow;

    // ───────────────────────────────────────────────
    //  FUNCTION تحميل بيانات الشركة من الـ BTC_NAME
    // ───────────────────────────────────────────────
    private void loadCompanyData(String BTC_NAME) throws Exception {

        selectedCompany = CompanyType.fromName(BTC_NAME);

        if (selectedCompany == null) {
            throw new Exception("❌ Company name NOT found in enum: " + BTC_NAME);
        }

        companyName     = selectedCompany.getName();
        companyCode     = selectedCompany.getCode();
        companyCategory = selectedCompany.getCategory();

        System.out.println("\nLoaded Company Data:");
        System.out.println("Company: " + companyName);
        System.out.println("Code: " + companyCode);
        System.out.println("Category: " + companyCategory);
    }

    // ───────────────────────────────────────────────
    //                 GIVEN
    // ───────────────────────────────────────────────
    @Given("Read Data Testing From Excel Sheet")
    public void i_start_the_scenario()
    {
        if (excelData == null) {
            excelData = ExcelUtils.readAllRows(
                    "D:/AutomationWork/Cash_Regression_Framework/" +
                            "TestingBill/src/test/resources/Data/DataTesting.xlsx"
            );
        }

        while (currentRowIndex < excelData.size()) {

            currentRow = excelData.get(currentRowIndex);
            System.out.println("\n=== Starting scenario for row " + (currentRowIndex + 1) + " ===");

            // STEP 1 — تحميل بيانات الشركة من BTC_NAME
            if (!executeStep(1)) {
                currentRowIndex++;
                continue;
            }

            // STEP 2
            if (!executeStep(2)) {
                currentRowIndex++;
                continue;
            }

            // STEP 3
            if (!executeStep(3)) {
                currentRowIndex++;
                continue;
            }

            // STEP 4
            if (!executeStep(4)) {
                currentRowIndex++;
                continue;
            }

            System.out.println("All steps passed for row " + (currentRowIndex + 1));
            currentRowIndex++;
        }

        System.out.println("\n=== Finished all rows ===");
    }

    @Then("Load Company Data")
    public void step1() { }

    @Then("Step 2")
    public void step2() { }

    @Then("Step 3")
    public void step3() { }

    @Then("Step 4")
    public void step4() { }


    // ───────────────────────────────────────────────
    //   EXECUTE EACH STEP
    // ───────────────────────────────────────────────
    private boolean executeStep(int stepNumber) {

        try {

            if (stepNumber == 1) {
                // BTC_NAME = currentRow[0]
                String btcName = currentRow[1];
                loadCompanyData(btcName);
            }

            double amount = Double.parseDouble(currentRow[5].replace(",", ""));

            if (amount > 100) {
                System.out.println("Step " + stepNumber + ": Amount greater than 100 (Amount=" + amount + ")");
                System.out.println("Skipping remaining steps for this row...");
                return false;
            } else {
                System.out.println("Step " + stepNumber + ": PASSED (Amount=" + amount + ")");
                return true;
            }

        } catch (Exception e) {
            System.out.println("Step " + stepNumber + " FAILED: " + e.getMessage());
            return false;
        }
    }
}
