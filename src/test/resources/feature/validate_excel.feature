Feature: validate_excel


  # The Following Steps just for testing if any step fail scenario run automatically from first #
  #--------------------------------------------------------------------------------------------#
  Scenario: Validate Amount for each Excel row
    Given Read Data Testing From Excel Sheet
    Then Load Company Data
    Then IsBillPaid
    Then Step 2
    Then Step 3
    Then Step 4
