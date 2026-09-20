package com.example;

public class LoyaltyMember {
    private int milesFlown;
    private int flightSegments;
    private AccountStatus status;

    public enum Tier {
        STANDARD, SILVER, GOLD, PLATINUM
    }

    // Khai báo Enum trạng thái
    public enum AccountStatus {
        ACTIVE, SUSPENDED, CLOSED
    }

    public LoyaltyMember(int milesFlown, int flightSegments, AccountStatus status) {
        if (milesFlown < 0 || flightSegments < 0) {
            throw new IllegalArgumentException("Dặm bay và số chuyến bay không được âm.");
        }
        this.milesFlown = milesFlown;
        this.flightSegments = flightSegments;
        this.status = status;
    }
   
    public Tier calculateTier() {
        if (status == AccountStatus.SUSPENDED || status == AccountStatus.CLOSED) {
            return Tier.STANDARD;
        }

        if (milesFlown >= 100000 || flightSegments >= 100) { 
            return Tier.PLATINUM;
        } else if (milesFlown >= 500000 || flightSegments > 50) { //>=50, 50000
            return Tier.GOLD;
        } else if (milesFlown >= 20000 || flightSegments > 30) { //>=20
            return Tier.SILVER;
        } else {
            return Tier.STANDARD;
        }
    }
}