package com.enjoy.enjoymmkv;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        float i = 1.1f;
        int j = Float.floatToIntBits(i);
        float k = Float.intBitsToFloat(j);
        System.out.println(k);

    }
}