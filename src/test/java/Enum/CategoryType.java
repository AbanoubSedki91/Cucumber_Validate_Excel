package Enum;

public enum CategoryType {

    ELECTRICITY("Electricity ",  "1"),
    WATER("Water", "2"),
    GAS("Gas", "3");



    private final String name;
    private final String code;

    CategoryType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() { return name; }
    public String getCode() { return code; }
}




