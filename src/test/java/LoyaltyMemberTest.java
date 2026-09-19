import com.example.LoyaltyMember;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoyaltyMemberTest {

    @ParameterizedTest(name = "milesFlown={0}, flightSegments={1} -> Mức thẻ: {2}")
    @CsvSource({
            "0, 10, STANDARD",
            "1, 10, STANDARD",
            "19999, 10, STANDARD",
            "20000, 10, SILVER",
            "20001, 10, SILVER",
            "49999, 10, SILVER",
            "50000, 10, GOLD",
            "50001, 10, GOLD",
            "99999, 10, GOLD",
            "100000, 10, PLATINUM",
            "100001, 10, PLATINUM"
    })
    public void testMilesFlownBoundaries(int milesFlown, int flightSegments, LoyaltyMember.Tier expectedTier) {
        LoyaltyMember member = new LoyaltyMember(milesFlown, flightSegments, LoyaltyMember.AccountStatus.ACTIVE);
        assertEquals(expectedTier, member.calculateTier(), 
            "Sai mức thẻ khi milesFlown = " + milesFlown);
    }

    // Nhóm 2: Kiểm thử biên cho flightSegments
    @ParameterizedTest(name = "milesFlown={0}, flightSegments={1} -> Mức thẻ: {2}")
    @CsvSource({
            "10000, 0, STANDARD",
            "10000, 1, STANDARD",
            "10000, 19, STANDARD",
            "10000, 20, SILVER",
            "10000, 21, SILVER",
            "10000, 49, SILVER",
            "10000, 50, GOLD",
            "10000, 51, GOLD",
            "10000, 99, GOLD",
            "10000, 100, PLATINUM",
            "10000, 101, PLATINUM"
    })
    public void testFlightSegmentsBoundaries(int milesFlown, int flightSegments, LoyaltyMember.Tier expectedTier) {
        LoyaltyMember member = new LoyaltyMember(milesFlown, flightSegments, LoyaltyMember.AccountStatus.ACTIVE);
        assertEquals(expectedTier, member.calculateTier(), 
            "Sai mức thẻ khi flightSegments = " + flightSegments);
    }

    @ParameterizedTest(name = "Trạng thái thẻ {0} -> Mức thẻ: {1}")
    @CsvSource({
            "ACTIVE, PLATINUM",
            "SUSPENDED, STANDARD",
            "CLOSED, STANDARD"
    })
    public void testAccountStatusEquivalencePartitions(LoyaltyMember.AccountStatus status, LoyaltyMember.Tier expectedTier) {
        // Cố định điểm ở mức rất cao (đủ điều kiện Platinum)
        int milesFlown = 100000;
        int flightSegments = 100;
        
        LoyaltyMember member = new LoyaltyMember(milesFlown, flightSegments, status);
        
        assertEquals(expectedTier, member.calculateTier(), 
            "Sai mức thẻ khi kiểm tra trạng thái: " + status);
    }
}