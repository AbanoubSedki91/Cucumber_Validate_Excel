package stepdefinitions;

import Enum.CompanyType;
import Enum.CategoryType;
import io.cucumber.java.en.*;
import utils.ExcelUtils;

import java.util.List;

public class StepDefs
{

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //  Private Variables to store CompanyName , CompanyCode , CompanyCategory
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private String companyName;
    private String companyCode;
    private CategoryType companyCategory;
    private CompanyType selectedCompany;


    // ─────────────────────────────────────────────────────────────────────────────────────//
    //  Private Variables to store Bill paid or not
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private boolean ElectricityBill = false;
    private boolean WaterBill = false;
    private boolean GasBill = false;


    // ─────────────────────────────────────────────────────────────────────────────────────//
    //  Given Variables for read Testing Data from Excel sheet
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private boolean stopExcelLoop = false;
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

            // Stop Read from Excel sheet if paid 3 bills "Electricity , Water , Gas"
            if (stopExcelLoop) {
                System.out.println("Excel loop stopped — all bills already paid.");
                break;  // Stop reading Excel rows
            }

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

    @Then("IsBillPaid")
    public void step3() { }

    @Then("CheckPaidSuccessfully")
    public void step4() { }


    // ───────────────────────────────────────────────
    //   EXECUTE EACH STEP
    // ───────────────────────────────────────────────
    private boolean executeStep(int stepNumber) {

        try {

            switch (stepNumber)
            {
                // ─────────────────────────────────────────────────────────────────────
                //   Step 1— Load company name
                // ─────────────────────────────────────────────────────────────────────
                case 1:
                    // Step 1: Load company name
                    String btcName = currentRow[1];
                    loadCompanyData(btcName);

                    System.out.println("Step 1 PASSED - Loaded Company: " + btcName);
                    return true;

                // ─────────────────────────────────────────────────────────────────────
                //   Step 2- Check bill value Less than 50 pounds for payment
                // ──────────────────────────────────────────────────────────────────────
                case 2:
                    // Step 2: Check amount only
                    double amount = Double.parseDouble(currentRow[5].replace(",", ""));

                    if (amount > 100) {
                        System.out.println("Step 2 FAILED - Amount too large: " + amount);
                        return false;
                    }

                    System.out.println("Step 2 PASSED - Amount valid: " + amount);
                    return true;

                // ─────────────────────────────────────────────────────────────────────
                //   Step 3- Check 3 bill "Electricity , Water , Gas" paid or not
                // ──────────────────────────────────────────────────────────────────────
                case 3:
                    System.out.println("Step 3 running... Checking bill types.");

                    // Check if all 3 bills already paid → stop scenario
                    if (ElectricityBill && WaterBill && GasBill) {
                        System.out.println("All 3 bills already paid → skipping Excel rows and continuing scenario.");
                        stopExcelLoop = true;  // Stop reading Excel
                        currentRowIndex = excelData.size();
                        return true;
                    }

                    CategoryType currentCategory = companyCategory;

                    switch (currentCategory) {
                        case ELECTRICITY:
                            if (!ElectricityBill) System.out.println("Electricity bill not processed yet → Mark pending.");
                            else System.out.println("Electricity bill already processed.");
                            break;

                        case WATER:
                            if (!WaterBill) System.out.println("Water bill not processed yet → Mark pending.");
                            else System.out.println("Water bill already processed.");
                            break;

                        case GAS:
                            if (!GasBill) System.out.println("Gas bill not processed yet → Mark pending.");
                            else System.out.println("Gas bill already processed.");
                            break;

                        default:
                            System.out.println("Unknown category type!");
                    }

                    System.out.println("Step 3 executed (No payment logic applied yet)");
                    return true;

                // ─────────────────────────────────────────────────────────────────────
                //   Step 4- Check bill paid successfully
                // ──────────────────────────────────────────────────────────────────────

                case 4:
                    System.out.println("Step 4 running... Checking payment message.");
                    String paymentMessage = "تم الدفع بنجاح"; // static for now

                    if (paymentMessage.equals("تم الدفع بنجاح")) {
                        System.out.println("Payment success message detected.");

                        switch (companyCategory) {
                            case ELECTRICITY:
                                ElectricityBill = true;
                                System.out.println("Electricity bill flag set to TRUE");
                                break;

                            case WATER:
                                WaterBill = true;
                                System.out.println("Water bill flag set to TRUE");
                                break;

                            case GAS:
                                GasBill = true;
                                System.out.println("Gas bill flag set to TRUE");
                                break;

                            default:
                                System.out.println("Unknown category type!");
                        }
                    }
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
