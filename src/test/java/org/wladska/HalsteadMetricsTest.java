package org.wladska;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wladska.Main.calculateHalsteadMetrics;

public class HalsteadMetricsTest {

    @Test
    public void testBasicFunctionality() {
        String code = "public class Main {" +
            "public static void main(String[] args) {" +
                "int a = 0;" +
                "int b = 1;" +
                "a += 12;" +
                "a = b + 1;" +
                "--a;" +
                "++a;" +
                "System.out.println(\"Hello world\");" +
            "}" +
        "}";
        HalsteadMetrics metrics = calculateHalsteadMetrics(code);
        DecimalFormat df = new DecimalFormat("#.##");
        assertEquals("18", df.format(metrics.getProgramLength()));
        assertEquals("10", df.format(metrics.getVocabularySize()));
        assertEquals("59,79", df.format(metrics.getProgramVolume()));
        assertEquals("5,5", df.format(metrics.getDifficulty()));
        assertEquals("328,87", df.format(metrics.getEffort()));
        assertEquals("18,27", df.format(metrics.getTimeRequiredToProgram()));
        assertEquals("0,02", df.format(metrics.getNumberOfDeliveredBugs()));
    }
}
