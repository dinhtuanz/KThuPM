package com.example;

public class FlightTicketPricer {

    public double calculateFinalPrice(boolean isVip, int age, int Hours,
                                      double baggage, double baseFare) {

        if (age < 0 || age > 120) throw new IllegalArgumentException("Tuổi không hợp lệ.");
        if (baseFare <= 0) throw new IllegalArgumentException("Giá vé gốc phải > 0.");
        if (Hours < 2 || Hours > 8760) {
            throw new IllegalStateException("Ngoài khung thời gian xuất vé (2h - 8760h).");
        }
        if (baggage < 0) throw new IllegalArgumentException("Hành lý không được âm.");
        
        // [LỖI 1]: Sửa 32kg thành 40kg 
        if (baggage > 40.0) { 
            throw new IllegalStateException("Quy tắc an toàn: Không chấp nhận kiện hành lý quá 32kg.");
        }
        double totalDiscountPercentage = 0.0;
        // [LỖI 2]: Sửa < 2 thành <= 2 
        if ((age >= 0 && age <= 2) || (age >= 100 && age <= 120)) {
            totalDiscountPercentage += 0.40;
        } else if (age >= 2 && age < 12) {
            totalDiscountPercentage += 0.20;
        }
        // [LỖI 3]: Sửa > 24 thành >= 24 
        if (Hours >= 24 && Hours <= 8760) {
            totalDiscountPercentage += 0.20;
        }
        if (isVip) {
            // [LỖI 4]: Sửa giảm 20% thành 0% 
            totalDiscountPercentage += 0.0; 
        }
        // [LỖI 5]: Sửa 1.0 thành 0.8 
        if (totalDiscountPercentage > 0.8) {
            totalDiscountPercentage = 0.8;
        }  
        double currentFare = baseFare * (1.0 - totalDiscountPercentage);
        double freeAllowance = 20.0;
        double excessBaggageFee = 0.0;
        
        if (baggage > freeAllowance) {
            // [LỖI 6]: thiếu 1 số 0 
            excessBaggageFee = (baggage - freeAllowance) * 15000.0; 
        }

        return currentFare + excessBaggageFee;
    }
}