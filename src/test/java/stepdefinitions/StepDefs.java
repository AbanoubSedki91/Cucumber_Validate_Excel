package stepdefinitions;

import Enum.CompanyType;
import Enum.CategoryType;
import io.cucumber.java.en.*;
import utils.ExcelUtils;
import java.util.List;
import java.util.Properties;

public class StepDefs
{

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //        Private Variables to store CompanyName , CompanyCode , CompanyCategory
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private String companyName;
    private String companyCode;
    private String categoryCode;
    private CategoryType companyCategory;
    private CompanyType selectedCompany;

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //        Billing Account Number and Billing Payment USSD Code
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private String billingAccountNumber;
    private String currentBillPaymentCode;
    private String confirmationPaymentCode;


    // ─────────────────────────────────────────────────────────────────────────────────────//
    //        Private Variables to store Bill paid or not
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private boolean ElectricityBill = false;
    private boolean WaterBill = false;
    private boolean GasBill = false;

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //        Given Variables for read Testing Data from Excel sheet
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private boolean stopExcelLoop = false;
    private static List<String[]> excelData;
    private static int currentRowIndex = 0;
    private String[] currentRow;

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //        Properties object to simulate USSD options storage
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private Properties ussdRatePlanPropertiesUtilities = new Properties();
    private Properties ussdConfirmationPaymentCode = new Properties();

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //        Function tha pass BTC_Name to load all data for specific every Company
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private void loadCompanyData(String BTC_NAME) throws Exception
    {
        selectedCompany = CompanyType.fromName(BTC_NAME);
        if (selectedCompany == null)
        {
            throw new Exception("❌ Company name NOT found in enum: " + BTC_NAME);
        }

        companyName     = selectedCompany.getName();
        companyCode     = selectedCompany.getCode();
        companyCategory = selectedCompany.getCategory();
        categoryCode = companyCategory.getCode();

        System.out.println("\nLoaded Company Data:");
        System.out.println("Company: " + companyName);
        System.out.println("Company Code: " + companyCode);
        System.out.println("Category: " + companyCategory);
        System.out.println("Category Code: " + categoryCode);
    }

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //                 GIVEN Read Data Testing From Excel Sheet
    // ─────────────────────────────────────────────────────────────────────────────────────//
    @Given("Read Data Testing From Excel Sheet")
    public void i_start_the_scenario()
    {
        if (excelData == null) {
            excelData = ExcelUtils.readAllRows(
                    " D:/AutomationWork/Cash_Regression_Framework/TestingBill/src/" +
                            "test/resources/Data/DataTesting.xlsx"
            );
        }

        while (currentRowIndex < excelData.size()) {

            // Stop Read from Excel sheet if paid 3 bills "Electricity , Water , Gas"
            if (stopExcelLoop)
            {
                System.out.println("Excel loop stopped — all bills already paid.");
                break;  // Stop reading Excel rows
            }

            currentRow = excelData.get(currentRowIndex);
            System.out.println("\n=== Starting scenario for row " + (currentRowIndex + 1) + " ===");

            // STEP 1- Load Company Data
            if (!executeStep(1))
            {
                currentRowIndex++;
                continue;
            }

            // STEP 2- IsBillPaid
            if (!executeStep(2))
            {
                currentRowIndex++;
                continue;
            }

            // STEP 3- Generate Bill Payment Ussd Code
            if (!executeStep(3))
            {
                currentRowIndex++;
                continue;
            }

            // STEP 4- Set placeholder for Bill Payment Ussd Code
            if (!executeStep(4))
            {
                currentRowIndex++;
                continue;
            }

            // STEP 5- Generate Confirmation Bill Payment Ussd Code
            if (!executeStep(5))
            {
                currentRowIndex++;
                continue;
            }


            // STEP 6- Set placeholder for Confirmation Bill Payment Ussd Code
            if (!executeStep(6))
            {
                currentRowIndex++;
                continue;
            }


            System.out.println("All steps passed for row " + (currentRowIndex + 1));
            currentRowIndex++;
        }

        System.out.println("\n=== Finished all rows ===");
    }

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //                 Steps Implementation For every bill
    // ─────────────────────────────────────────────────────────────────────────────────────//
    @Then("Load Company Data")
    public void step1(){ }

    @Then("IsBillPaid")
    public void step2(){ }

    @Then("Generate bill payment code for current category")
    public void step3(){ }

    @Then("Generate Confirmation Payment Code")
    public void step5() {}

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //            Set placeholder for BillPaymentCode to check invoice amount
    // ─────────────────────────────────────────────────────────────────────────────────────//
    @Then("Set BillPaymentCode placeholder")
    public void setBillPaymentCodePlaceholder()
    {
        if (currentRowIndex >= excelData.size()) return;
        ussdRatePlanPropertiesUtilities .setProperty("<BillPaymentCode>", currentBillPaymentCode);
    }

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //                          Generate Bill Payment USSD Code
    // ─────────────────────────────────────────────────────────────────────────────────────//
    public String GenerateBillPaymentCode(CategoryType category) {
        switch (category) {
            case ELECTRICITY:
                return "4," + category.getCode() + "," + companyCode + "," + billingAccountNumber + ",1";
            case WATER:
                return "4," + category.getCode() + "," + companyCode + "," + billingAccountNumber + ",1";
            case GAS:
                return "4," + category.getCode() + "," + companyCode + "," + billingAccountNumber + ",1" ;
            default:
                return "";
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //            Set placeholder for  Confirmation Bill Payment Ussd Code
    // ─────────────────────────────────────────────────────────────────────────────────────//
    @Then("Set ConfirmationPaymentCode placeholder")
    public void setConfirmationPaymentCodePlaceholder()
    {
        if (currentRowIndex >= excelData.size()) return;
        ussdConfirmationPaymentCode .setProperty("<confirmationpaymentcode>", confirmationPaymentCode);
    }

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //                          Generate Confirmation Payment USSD Code
    // ─────────────────────────────────────────────────────────────────────────────────────//
    public String GenerateConfirmationPaymentCode(CategoryType category) {
        switch (category) {
            case ELECTRICITY:
                return "4," + category.getCode() + "," + companyCode + "," + billingAccountNumber + ",1" + ",100100";
            case WATER:
                return "4," + category.getCode() + "," + companyCode + "," + billingAccountNumber + ",1" + ",100100";
            case GAS:
                return "4," + category.getCode() + "," + companyCode + "," + billingAccountNumber + ",1" + "100100" ;
            default:
                return "";
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────────────//
    //                                EXECUTE EACH STEP
    // ─────────────────────────────────────────────────────────────────────────────────────//
    private boolean executeStep(int stepNumber) {
        try {

            switch (stepNumber)
            {
                // ─────────────────────────────────────────────────────────────────────
                //                     Step 1— Load company name
                // ─────────────────────────────────────────────────────────────────────
                case 1:
                    String btcName = currentRow[1];
                    loadCompanyData(btcName);
                    billingAccountNumber = currentRow[10];
                    System.out.println("Billing Account Number: " + billingAccountNumber);
                    System.out.println("Step 1 PASSED - Loaded Company: " + btcName);
                    return true;


                // ─────────────────────────────────────────────────────────────────────
                //       Step 2- Check 3 bill "Electricity , Water , Gas" paid or not
                // ──────────────────────────────────────────────────────────────────────
                case 2:
                    System.out.println("Step 3 running... Checking bill types.");
                    if (ElectricityBill && WaterBill && GasBill) {
                        System.out.println("All 3 bills already paid → skipping Excel rows and continuing scenario.");
                        stopExcelLoop = true;
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
                    System.out.println("Step 2 executed (No payment logic applied yet)");
                    return true;

                // ─────────────────────────────────────────────────────────────────────
                //                     Step 3— Generate Bill Payment Ussd Code
                // ─────────────────────────────────────────────────────────────────────
                case 3:
                    currentBillPaymentCode = GenerateBillPaymentCode(companyCategory);
                    return true;
                // ─────────────────────────────────────────────────────────────────────
                //             Step 4— Set placeholder for USSD options dynamically
                // ─────────────────────────────────────────────────────────────────────
                case 4:
                    setBillPaymentCodePlaceholder();
                    return true;

                // ─────────────────────────────────────────────────────────────────────
                //               Step 5— Generate Confirmation Payment Ussd Code
                // ─────────────────────────────────────────────────────────────────────
                case 5:
                    confirmationPaymentCode = GenerateConfirmationPaymentCode(companyCategory);
                    return true;
                // ────────────────────────────────────────────────────────────────────────────────
                // Step 6— Set placeholder for Confirmation Payment USSD options dynamically
                // ────────────────────────────────────────────────────────────────────────────────
                case 6:
                    setConfirmationPaymentCodePlaceholder();
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