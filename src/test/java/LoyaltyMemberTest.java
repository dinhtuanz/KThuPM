import com.example.LoyaltyMember;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoyaltyMemberTest {

    @ParameterizedTest(name = "TId {0}: miles={1}, segments={2} -> Mức thẻ: {3}")
    @CsvSource({
            "1, 0, 10, STANDARD",
            "2, 1, 10, STANDARD",
            "3, 19999, 10, STANDARD",
            "4, 20000, 10, SILVER",
            "5, 20001, 10, SILVER",
            "6, 49999, 10, SILVER",
            "7, 50000, 10, GOLD",
            "8, 50001, 10, GOLD",
            "9, 99999, 10, GOLD",
            "10, 100000, 10, PLATINUM",
            "11, 100001, 10, PLATINUM",
            "12, 10000, 0, STANDARD",
            "13, 10000, 1, STANDARD",
            "14, 10000, 19, STANDARD",
            "15, 10000, 20, SILVER",
            "16, 10000, 21, SILVER",
            "17, 10000, 49, SILVER",
            "18, 10000, 50, GOLD",
            "19, 10000, 51, GOLD",
            "20, 10000, 99, GOLD",
            "21, 10000, 100, PLATINUM",
            "22, 10000, 101, PLATINUM",
            "23, 10000, 10, STANDARD"
    })
    public void testLoyaltyMemberTierCalculation(int tid, int milesFlown, int flightSegments, LoyaltyMember.Tier expectedTier) {
        LoyaltyMember member = new LoyaltyMember(milesFlown, flightSegments, LoyaltyMember.AccountStatus.ACTIVE);
        
        assertEquals(expectedTier, member.calculateTier(), 
            "Sai mức thẻ ở TId " + tid);
    }
}