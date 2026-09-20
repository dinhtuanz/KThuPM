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
        standardMember = new LoyaltyMember(0, 0, LoyaltyMember.AccountStatus.ACTIVE);  
    }

    @ParameterizedTest(name = "TId {0}: Age={1}, Hours={2}, Baggage={3} -> Expected: {4}")
    @CsvSource({
            "1, 0, 4400, 16.0, 100000.0",
            "2, 1, 4400, 16.0, 100000.0",
            "3, 2, 4400, 16.0, 100000.0",
            "4, 3, 4400, 16.0, 750000.0",
            "5, 11, 4400, 16.0, 750000.0",
            "6, 12, 4400, 16.0, 750000.0",
            "7, 13, 4400, 16.0, 1000000.0",
            "8, 119, 4400, 16.0, 1000000.0",
            "9, 120, 4400, 16.0, 1000000.0",
            "10, 60, 2, 16.0, 1200000.0",
            "11, 60, 3, 16.0, 1200000.0",
            "12, 60, 23, 16.0, 1200000.0",
            "13, 60, 24, 16.0, 1200000.0",
            "14, 60, 25, 16.0, 1000000.0",
            "15, 60, 8759, 16.0, 1000000.0",
            "16, 60, 8760, 16.0, 1000000.0",
            "17, 60, 4400, 0.0, 1000000.0",
            "18, 60, 4400, 0.1, 1000000.0",
            "19, 60, 4400, 19.9, 1000000.0",
            "20, 60, 4400, 20.0, 1000000.0",
            "21, 60, 4400, 20.1, 1015000.0",
            "22, 60, 4400, 31.9, 2785000.0",
            "23, 60, 4400, 32.0, 2800000.0",
            "24, 60, 4400, 16.0, 1000000.0"
    })
    public void testFlightTicketPricingBoundaries(int tid, int age, int hours, double baggage, double expectedOutput) {
        double actualPrice = pricer.calculateFinalPrice(standardMember, age, hours, baggage, baseFare);
        assertEquals(expectedOutput, actualPrice, 0.01, "Sai giá vé ở TId " + tid);
    }
}