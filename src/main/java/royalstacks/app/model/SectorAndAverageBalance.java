package royalstacks.app.model;

public class SectorAndAverageBalance {

    private Sector sector;
    private double totalBalance;

    //CONSTRUCTOR

    public SectorAndAverageBalance(Sector sector, double totalBalance) {
        this.sector = sector;
        this.totalBalance = totalBalance;
    }

    //GETTERS

    public Sector getSector() {
        return sector;
    }

    public double getTotalBalance() {
        return totalBalance;
    }
}
