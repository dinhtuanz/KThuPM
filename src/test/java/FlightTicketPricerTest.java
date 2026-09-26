import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.FlightTicketPricer;
import com.example.LoyaltyMember;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTicketPricerTest {

    private FlightTicketPricer pricer;
    private LoyaltyMember standardMember;
    private double baseFare = 1000000.0; // Giả định giá vé gốc là 1 triệu VNĐ

    @BeforeEach
    public void setUp() {
        pricer = new FlightTicketPricer();
        // Khởi tạo member với 0 dặm bay, 0 chuyến, tài khoản ACTIVE -> Hạng STANDARD
        standardMember = new LoyaltyMember(0, 0, LoyaltyMember.AccountStatus.ACTIVE);
    }

    @ParameterizedTest(name = "TId {0}: Age={1}, Hours={2}, Baggage={3} -> Expected: {4}")
    @CsvSource({
            // Test cases thay đổi biến Age (Tuổi)
            "1, 0, 4400, 16.0, 400000.0",
            "2, 1, 4400, 16.0, 400000.0",
            "3, 2, 4400, 16.0, 600000.0",
            "4, 3, 4400, 16.0, 600000.0",
            "5, 11, 4400, 16.0, 600000.0",
            "6, 12, 4400, 16.0, 800000.0",
            "7, 13, 4400, 16.0, 800000.0",
            "8, 99, 4400, 16.0, 800000.0",
            "9, 100, 4400, 16.0, 400000.0",
            "10, 101, 4400, 16.0, 400000.0",
            "11, 119, 4400, 16.0, 400000.0",
            "12, 120, 4400, 16.0, 400000.0",
            
            // Test cases thay đổi biến Hours (Giờ đặt vé)
            "13, 60, 2, 16.0, 1000000.0",
            "14, 60, 3, 16.0, 1000000.0",
            "15, 60, 23, 16.0, 1000000.0",
            "16, 60, 24, 16.0, 1000000.0",
            "17, 60, 25, 16.0, 800000.0",
            "18, 60, 8759, 16.0, 800000.0",
            "19, 60, 8760, 16.0, 800000.0",
            
            // Test cases thay đổi biến Baggage (Hành lý ký gửi)
            "20, 60, 4400, 0.0, 800000.0",
            "21, 60, 4400, 0.1, 800000.0",
            "22, 60, 4400, 19.9, 800000.0",
            "23, 60, 4400, 20.0, 800000.0",
            "24, 60, 4400, 20.1, 815000.0",   // Phạt 0.1kg * 150k = 15k
            "25, 60, 4400, 31.9, 2585000.0",  // Phạt 11.9kg * 150k = 1.785k
            "26, 60, 4400, 32.0, 2600000.0",  // Phạt 12.0kg * 150k = 1.800k
            
            // Base Case (Ca cơ sở - Các giá trị Medium)
            "27, 60, 4400, 16.0, 800000.0"
    })
    public void testFlightTicketPricingBoundaries(int tid, int age, int hours, double baggage, double expectedOutput) {
        double actualPrice = pricer.calculateFinalPrice(standardMember.isVip(), age, hours, baggage, baseFare);
        
        // Dùng delta = 0.01 để so sánh số thực, phòng sai số dấu phẩy động
        assertEquals(expectedOutput, actualPrice, 0.01, "Sai giá vé ở TId " + tid);
    }
}