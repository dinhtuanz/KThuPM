import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.FlightTicketPricer;
import com.example.LoyaltyMember;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTicketPricerTest {

    private FlightTicketPricer pricer;
    
    private LoyaltyMember standardMember; 
    private double baseFare = 1000000.0; 

    @BeforeEach
    public void setUp() {
        pricer = new FlightTicketPricer();
        standardMember = new LoyaltyMember(0, 0); // Khởi tạo hạng STANDARD
    }

    // Nhóm 1: Biến chạy là Age
    @ParameterizedTest(name = "Test Age {0} -> Expected: {3}")
    @CsvSource({
            "0, 4400, 16.0, 100000.0",
            "1, 4400, 16.0, 100000.0",
            "2, 4400, 16.0, 100000.0",
            "3, 4400, 16.0, 750000.0",
            "11, 4400, 16.0, 750000.0",
            "12, 4400, 16.0, 750000.0",
            "13, 4400, 16.0, 1000000.0",
            "119, 4400, 16.0, 1000000.0",
            "120, 4400, 16.0, 1000000.0"
    })
    public void testAgeBoundaries(int age, int hours, double baggage, double expectedOutput) {
        // 2. Hàm đã được sửa để truyền standardMember vào vị trí đầu tiên
        double actualPrice = pricer.calculateFinalPrice(standardMember, age, hours, baggage, baseFare);
        assertEquals(expectedOutput, actualPrice, 0.01, "Sai giá vé ở biến Age: " + age);
    }

    @ParameterizedTest(name = "Test Hours {1} -> Expected: {3}")
    @CsvSource({
            "60, 2, 16.0, 1200000.0",
            "60, 3, 16.0, 1200000.0",
            "60, 23, 16.0, 1200000.0",
            "60, 24, 16.0, 1200000.0",
            "60, 25, 16.0, 1000000.0",
            "60, 8759, 16.0, 1000000.0",
            "60, 8760, 16.0, 1000000.0"
    })
    public void testHoursBoundaries(int age, int hours, double baggage, double expectedOutput) {
        double actualPrice = pricer.calculateFinalPrice(standardMember, age, hours, baggage, baseFare);
        assertEquals(expectedOutput, actualPrice, 0.01, "Sai giá vé ở biến Hours: " + hours);
    }

    @ParameterizedTest(name = "Test Baggage {2} -> Expected: {3}")
    @CsvSource({
            "60, 4400, 0.0, 1000000.0",
            "60, 4400, 0.1, 1000000.0",
            "60, 4400, 19.9, 1000000.0",
            "60, 4400, 20.0, 1000000.0",
            "60, 4400, 20.1, 1015000.0",
            "60, 4400, 31.9, 2785000.0",
            "60, 4400, 32.0, 2800000.0"
    })
    public void testBaggageBoundaries(int age, int hours, double baggage, double expectedOutput) {
        double actualPrice = pricer.calculateFinalPrice(standardMember, age, hours, baggage, baseFare);
        assertEquals(expectedOutput, actualPrice, 0.01, "Sai giá vé ở biến Baggage: " + baggage);
    }
}