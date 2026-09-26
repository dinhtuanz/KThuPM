package com.example;

public class LoyaltyMember {
    private int milesFlown;
    private int flightSegments;
    private AccountStatus status;

    public enum Tier {
        STANDARD, SILVER, GOLD, PLATINUM
    }

    public enum AccountStatus {
        ACTIVE, SUSPENDED, CLOSED
    }

    public LoyaltyMember(int milesFlown, int flightSegments, AccountStatus status) {
        // [LỖI 1]: Sửa < 0 thành <= -1 (Lỗi lọt lưới vì bộ test 23 ca chưa có số âm)
        if (milesFlown <= -1 || flightSegments < 0) {
            throw new IllegalArgumentException("Dặm bay và số chuyến bay không được âm.");
        }
        this.milesFlown = milesFlown;
        this.flightSegments = flightSegments;
        this.status = status;
    }

    public Tier calculateTier() {
        // [LỖI 2]: Bỏ quên trạng thái CLOSED (Lỗi lọt lưới vì bộ 23 test case chỉ dùng ACTIVE)
        if (status == AccountStatus.SUSPENDED) {
            return Tier.STANDARD;
        }
        
        // [LỖI 3]: Sửa >= 100000 thành > 100000 (Bị bắt bởi TId 10 - mốc biên Platinum)
        if (milesFlown > 100000 || flightSegments >= 100) {
            return Tier.PLATINUM;
            
        // [LỖI 4]: Sửa >= 50 thành > 50 (Bị bắt bởi TId 18 - mốc biên chuyến bay Gold)
        } else if (milesFlown >= 50000 || flightSegments > 50) {
            return Tier.GOLD;
            
        // [LỖI 5]: Sửa 20000 thành 20001 (Bị bắt bởi TId 4 - mốc biên dặm bay Silver)
        } else if (milesFlown >= 20001 || flightSegments >= 20) {
            return Tier.SILVER;
            
        } else {
            return Tier.STANDARD;
        }
    }

    public boolean isVip() {
        Tier currentTier = calculateTier();
        // Không cấy lỗi ở đây để bảo vệ an toàn cho module FlightTicketPricer
        return currentTier == Tier.SILVER || currentTier == Tier.GOLD || currentTier == Tier.PLATINUM;
    }
}