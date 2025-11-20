package Enum;



public enum CompanyType
{
    // Electricity Companies and their USSD Code //
    South_Cairo_Electricity_Bill("South Cairo Electricity Bill", "1", CategoryType.ELECTRICITY),
    North_Cairo_Electricity_Bill("North Cairo Electricity Bill", "2", CategoryType.ELECTRICITY),
    Alexandria_Electricity_Bill ("Alexandria Electricity Bill", "3", CategoryType.ELECTRICITY),
    Canal_Electricity ("Canal Electricity", "4", CategoryType.ELECTRICITY),
    ElBeheira_Electricity ("El Beheira Electricity ", "5", CategoryType.ELECTRICITY),
    South_Delta_Electricity_Bill ("South Delta Electricity Bill ", "6", CategoryType.ELECTRICITY),
    North_Delta_Electricity_Bill  ("North Delta Electricity - Bill", "7", CategoryType.ELECTRICITY),
    Middle_Egypt_Bill ("Middle Egypt - Bill", "8", CategoryType.ELECTRICITY),
    Upper_Egypt_Electricity ("Upper Egypt Electricity", "9", CategoryType.ELECTRICITY),
    //------------------------------------------------------------------------------------------------------------//

    // Water Companies and their USSD Code //
    Cairo_Water("Cairo Water", "1", CategoryType.WATER),
    Giza_Water_Company ("Giza Water Company", "2", CategoryType.WATER),
    Alexandria_Water ("Alexandria Water", "3", CategoryType.WATER),
    Matrouh_Water ("Matrouh Water", "4", CategoryType.WATER),
    Beni_Suef_Water_Company ("Beni Suef Water Company", "5", CategoryType.WATER),
    Sohag_Water ("Sohag Water", "6", CategoryType.WATER),
    Red_Sea_Water("Red Sea Water", "7", CategoryType.WATER),
    //------------------------------------------------------------------------------------------------------------//

    // Gas Companies and their USSD Code //
    // ON opencode Natgas USSD CODE is "2" But we have two names in testing excel sheet//
    Bill_Payment_Natgas("Bill Payment Natgas", "2", CategoryType.GAS),
    Bills_Payment_natgas("Bills Payment natgas", "2", CategoryType.GAS),
    // ON opencode Natgas USSD CODE is "2" But we have two names in testing excel sheet//
    Petrotrade_Bill_Payment ("Petrotrade Bill Payment", "3", CategoryType.GAS),
    Petrotrade_Bills_Payment ("Petrotrade Bills Payment", "3", CategoryType.GAS);
    //------------------------------------------------------------------------------------------------------------//
    private final String name;
    private final String code;
    private final CategoryType category;

    CompanyType(String name, String code, CategoryType category) {
        this.name = name;
        this.code = code;
        this.category = category;
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public CategoryType getCategory() { return category; }

    // Helper to get company by name
    public static CompanyType fromName(String name) {

        if (name == null) return null;

        // Normalize Excel name
        String excelName = name.trim()
                .replace("_", "")
                .replace(" ", "")
                .replace("-", "")
                .toLowerCase();

        for (CompanyType c : values()) {

            // Normalize Enum Name
            String enumName = c.getName().trim()
                    .replace("_", "")
                    .replace(" ", "")
                    .replace("-", "")
                    .toLowerCase();

            if (excelName.equals(enumName)) {
                return c;
            }
        }

        return null;
    }

}

