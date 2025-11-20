package stepdefinitions;

import Enum.CompanyType;
import Enum.CategoryType;
import io.cucumber.java.en.*;
import utils.ExcelUtils;

import java.util.List;

public class StepDefs
{

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //  GLOBAL VARIABLES to store CompanyName , CompanyCode , CompanyCategory
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private String companyName;
    private String companyCode;
    private CategoryType companyCategory;
    private CompanyType selectedCompany;


    private static List<String[]> excelData;
    private static int currentRowIndex = 0;
    private String[] currentRow;

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //  Function tha pass BTC_Name to load all data for specific every Company
    // ─────────────────────────────────────────────────────────────────────────────────────//
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

            // STEP 1 — Load Company Data //
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

            switch (stepNumber) {

                case 1:
                    // Step 1: Load company name
                    String btcName = currentRow[1];
                    loadCompanyData(btcName);

                    System.out.println("Step 1 PASSED - Loaded Company: " + btcName);
                    return true;


                case 2:
                    // Step 2: Check amount only
                    double amount = Double.parseDouble(currentRow[5].replace(",", ""));

                    if (amount > 100) {
                        System.out.println("Step 2 FAILED - Amount too large: " + amount);
                        return false;
                    }

                    System.out.println("Step 2 PASSED - Amount valid: " + amount);
                    return true;


                case 3:
                    // Step 3 logic
                    System.out.println("Step 3 executed");
                    return true;

                case 4:
                    // Step 4 logic
                    System.out.println("Step 4 executed");
                    return true;

                default:
                    return false;
            }

        } catch (Exception e) {
            System.out.println("Step " + stepNumber + " FAILED: " + e.getMessage());
            return false;
        }
    }

}
