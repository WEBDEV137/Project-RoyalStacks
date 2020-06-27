package royalstacks.app.model;

public enum Sector {

    AGRICULTURE ("Agriculture"),
    BUSINESS_SERVICES ("Business services"),
    CONSTRUCTION ("Construction"),
    CULTURE_SPORTS_AND_RECREATION ("Culture, sports and recreation"),
    ENERGY_WATER_AND_ENVIRONMENT ("Energy, water and environment"),
    FINANCIAL_SERVICES ("Financial services"),
    HEALTHCARE ("Healthcare"),
    HOSPITALITY ("Hospitality"),
    ICT_AND_MEDIA("ICT and media"),
    INDUSTRY ("Industry"),
    LOGISTICS("Logistics"),
    RETAIL ("Retail"),
    WHOLESALE ("Wholesale");

    //CONSTRUCTOR
    private final String displayValue;
    private Sector(String displayValue){
        this.displayValue = displayValue;
    }

    //GETTER
    public String getDisplayValue() {
        return displayValue;
    }
}
