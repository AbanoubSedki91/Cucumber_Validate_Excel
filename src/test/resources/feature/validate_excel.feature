Feature: validate_excel


  # The Following Steps just for testing if any step fail scenario run automatically from first #
  #--------------------------------------------------------------------------------------------#

  Scenario Outline: Validate Amount and Generate Bill Payment Code
    Given Read Data Testing From Excel Sheet
    Then Load Company Data
    Then IsBillPaid
    Then Generate bill payment code for current category
    Then Set BillPaymentCode placeholder
    Then Print "<BillPaymentCode>"


    Examples:
      | BillPaymentCode  |
      | billpaymentcode |

