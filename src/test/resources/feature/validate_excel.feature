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
      | <billpaymentcode> |


    # public class DynamicUnicodeMessage {
    #    public static void main(String[] args) {
    #        double amount = 2469.0;    // المبلغ اللي بيتغير
    #        double serviceFee = 18.0;  // رسوم الخدمة اللي بتتغير
    #
    #        // نص ثابت مقسم قبل وبعد الأرقام
    #        String messageTemplate = "\u062a\u0623\u0643\u064a\u062f \u062f\u0641\u0639 \u0645\u0628\u0644\u063a %s \u062c \u0648 \u0631\u0633\u0648\u0645 \u0627\u0644\u062e\u062f\u0645\u0629 %s\u062c \u0627\u062f\u062e\u0644 \u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0633\u0631\u0649";
    #
    #        // استبدال الأرقام بالقيم المتغيرة
    #        String message = String.format(messageTemplate, amount, serviceFee);
    #
    #        System.out.println(message);
    #    }
    #}