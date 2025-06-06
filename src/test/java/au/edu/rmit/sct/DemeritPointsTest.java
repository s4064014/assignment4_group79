package au.edu.rmit.sct;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DemeritPointsTest {

    // --- Valid Offense Date Format ---
    @Test
    public void testValidOffenseDate1() {
        demeritPoints dp = new demeritPoints("56sd%&fAB", "15-11-2023", 1, "05-01-1995");
        assertEquals("Success", dp.addDemeritPoints("56sd%&fAB", "15-11-2023", 1, "05-01-1995"));
    }

    @Test
    public void testValidOffenseDate2() {
        demeritPoints dp = new demeritPoints("29#%_rt&ZX", "01-01-2024", 3, "02-08-1998");
        assertEquals("Success", dp.addDemeritPoints("29#%_rt&ZX", "01-01-2024", 3, "02-08-1998"));
    }

    @Test
    public void testValidOffenseDate3() {
        demeritPoints dp = new demeritPoints("89x@!gt^OW", "10-10-2022", 6, "16-07-2005");
        assertEquals("Success", dp.addDemeritPoints("89x@!gt^OW", "10-10-2022", 6, "16-07-2005"));
    }

    // --- Invalid Offense Date Format ---
    @Test
    public void testSlashesInsteadOfDashes() {
        demeritPoints dp = new demeritPoints("75sq_&fBS", "15/11/2023", 2, "03-01-1998");
        assertEquals("Failed", dp.addDemeritPoints("75sq_&fBS", "15/11/2023", 2, "03-01-1998"));
    }

    @Test
    public void testWrongDateOrder() {
        demeritPoints dp = new demeritPoints("89x@!gt^OW", "2023-11-15", 2, "15-06-1990");
        assertEquals("Failed", dp.addDemeritPoints("89x@!gt^OW", "2023-11-15", 2, "15-06-1990"));
    }

    @Test
    public void testInvalidCalendarDate() {
        demeritPoints dp = new demeritPoints("29#%_rt&ZX", "31-02-2023", 4, "22-08-2000");
        assertEquals("Failed", dp.addDemeritPoints("29#%_rt&ZX", "31-02-2023", 4, "22-08-2000"));
    }

    // --- Valid Demerit Points ---
    @Test
    public void testValidDemeritPoints1() {
        demeritPoints dp = new demeritPoints("75@t$*aWDC", "30-01-2025", 2, "06-06-1999");
        assertEquals("Success", dp.addDemeritPoints("75@t$*aWDC", "30-01-2025", 2, "06-06-1999"));
    }

    @Test
    public void testValidDemeritPoints2() {
        demeritPoints dp = new demeritPoints("34!@r%AXNC", "12-02-2024", 6, "13-01-2007");
        assertEquals("Success", dp.addDemeritPoints("34!@r%AXNC", "12-02-2024", 6, "13-01-2007"));
    }

    @Test
    public void testValidDemeritPoints3() {
        demeritPoints dp = new demeritPoints("67_h!&*aNW", "04-03-2025", 4, "12-07-1995");
        assertEquals("Success", dp.addDemeritPoints("67_h!&*aNW", "04-03-2025", 4, "12-07-1995"));
    }

    // --- Invalid Demerit Points ---
    @Test
    public void testNegativeDemeritPoints() {
        demeritPoints dp = new demeritPoints("67_h!&*aNW", "04-03-2025", -2, "12-07-1995");
        assertEquals("Failed", dp.addDemeritPoints("67_h!&*aNW", "04-03-2025", -2, "12-07-1995"));
    }

    @Test
    public void testPointsOutOfRangeLow() {
        demeritPoints dp = new demeritPoints("49r&!iz^PO", "22-11-2001", 0, "04-05-1980");
        assertEquals("Failed", dp.addDemeritPoints("49r&!iz^PO", "22-11-2001", 0, "04-05-1980"));
    }

    @Test
    public void testPointsOutOfRangeHigh() {
        demeritPoints dp = new demeritPoints("33q*!sr%ZS", "09-08-2002", 8, "04-05-1980");
        assertEquals("Failed", dp.addDemeritPoints("33q*!sr%ZS", "09-08-2002", 8, "04-05-1980"));
    }

    // --- Suspension Logic: under-21 exceeds 6 points ---
    @Test
    public void testUnder21SuspensionCase1() {
        demeritPoints dp1 = new demeritPoints("12ab$#XYz", "01-06-2024", 4, "05-06-2005");
        assertEquals("Success", dp1.addDemeritPoints("12ab$#XYz", "01-06-2024", 4, "05-06-2005"));

        demeritPoints dp2 = new demeritPoints("12ab$#XYz", "01-05-2025", 4, "05-06-2005");
        assertEquals("Success", dp2.addDemeritPoints("12ab$#XYz", "01-05-2025", 4, "05-06-2005"));
    }

    @Test
    public void testUnder21SuspensionCase2() {
        demeritPoints dp1 = new demeritPoints("45xy!@ZRT", "12-03-2024", 4, "20-07-2006");
        assertEquals("Success", dp1.addDemeritPoints("45xy!@ZRT", "12-03-2024", 4, "20-07-2006"));

        demeritPoints dp2 = new demeritPoints("45xy!@ZRT", "10-02-2025", 3, "20-07-2006");
        assertEquals("Success", dp2.addDemeritPoints("45xy!@ZRT", "10-02-2025", 3, "20-07-2006"));
    }
}
