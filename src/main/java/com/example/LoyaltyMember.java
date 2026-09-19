package com.example;
public class LoyaltyMember {
    private int milesFlown;
    private int flightSegments;

    public LoyaltyMember(int milesFlown, int flightSegments) {
        if (milesFlown < 0 || flightSegments < 0) {
            throw new IllegalArgumentException("Dặm bay và số chuyến bay không được âm.");
        }
        this.milesFlown = milesFlown;
        this.flightSegments = flightSegments;
    }

    public enum Tier {
        STANDARD, SILVER, GOLD, PLATINUM
    }

   
    public Tier calculateTier() {
        if (milesFlown >= 100000 || flightSegments >= 100) {
            return Tier.PLATINUM;
        } else if (milesFlown >= 50000 || flightSegments >= 50) {
            return Tier.GOLD;
        } else if (milesFlown >= 20000 || flightSegments >= 20) {
            return Tier.SILVER;
        } else {
            return Tier.STANDARD;
        }
    }
}