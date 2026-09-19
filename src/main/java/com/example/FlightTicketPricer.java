package com.example;

public class FlightTicketPricer {

    public double calculateFinalPrice(LoyaltyMember member, int age, int hoursToDeparture, 
                                      double baggageWeightKg, double baseFare) {
        
        if (age < 0 || age > 120) throw new IllegalArgumentException("Tuổi không hợp lệ.");
        if (baseFare <= 0) throw new IllegalArgumentException("Giá vé gốc phải > 0.");
        if (hoursToDeparture < 2 || hoursToDeparture > 8760) {
            throw new IllegalStateException("Ngoài khung thời gian xuất vé (2h - 8760h).");
        }
        if (baggageWeightKg < 0) throw new IllegalArgumentException("Hành lý không được âm.");
        if (baggageWeightKg > 32.0) {
            throw new IllegalStateException("Quy tắc an toàn: Không chấp nhận kiện hành lý quá 32kg.");
        }

        double currentFare;
        if (age <= 2) {
            currentFare = baseFare * 0.10;
        } else if (age <= 12) {
            currentFare = baseFare * 0.75;
        } else {
            currentFare = baseFare;
        }

        if (hoursToDeparture <= 24) {
            currentFare = currentFare * 1.20;
        }

        LoyaltyMember.Tier tier = member.calculateTier();
        
        switch (tier) {
            case SILVER: currentFare *= 0.95; break;
            case GOLD: currentFare *= 0.90; break;
            case PLATINUM: currentFare *= 0.85; break;
            case STANDARD: default: break;
        }

        double freeAllowance = 20.0;
        if (tier == LoyaltyMember.Tier.SILVER) freeAllowance = 25.0;
        else if (tier == LoyaltyMember.Tier.GOLD) freeAllowance = 30.0;
        else if (tier == LoyaltyMember.Tier.PLATINUM) freeAllowance = 40.0;

        double excessBaggageFee = 0.0;
        if (baggageWeightKg > freeAllowance) {
            excessBaggageFee = (baggageWeightKg - freeAllowance) * 150000.0;
        }

        return currentFare + excessBaggageFee;
    }
}