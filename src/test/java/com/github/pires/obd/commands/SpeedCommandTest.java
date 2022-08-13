/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.pires.obd.commands;

import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Tests for ObdSpeedCommand class.
 */
@PrepareForTest(InputStream.class)
public class SpeedCommandTest {

    private SpeedCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new SpeedCommand();
    }

    public void mockInputStreamRead(InputStream in, String string) throws IOException {
        OngoingStubbing<Integer> stub = when(in.read());
        for (byte b : string.getBytes()) {
            stub = stub.thenReturn((int)b);
        }
    }

    @DataProvider
    public static Object[][] SpeedCommandGetMetricSpeedTestData() {
        return new Object[][] {
                new Object[] {"41 0D 00>", 0},
                new Object[] {"41 0D 40>", 64},
        };
    }
    @DataProvider
    public static Object[][] SpeedCommandGetImperialSpeedTestData() {
        return new Object[][] {
                new Object[] {"41 0D 00>", 0},
                new Object[] {"41 0D 45>", 42.874615f},
        };
    }

    @Test(dataProvider = "SpeedCommandGetMetricSpeedTestData")
    public void testValidMetricSpeed(String response, int n) throws IOException {
        mockIn = Mockito.mock(InputStream.class);
        mockInputStreamRead(mockIn, response);

        // call the method to test
        command.readResult(mockIn);
        command.getFormattedResult();
        assertEquals(command.getMetricSpeed(), n);
    }

    @Test(dataProvider = "SpeedCommandGetImperialSpeedTestData")
    public void testValidImperialSpeed(String response, float n) throws IOException {
        mockIn = Mockito.mock(InputStream.class);
        mockInputStreamRead(mockIn, response);

        // call the method to test
        command.readResult(mockIn);
        command.useImperialUnits = true;
        command.getFormattedResult();
        assertEquals(command.getImperialSpeed(), n);
    }

    /**
     * Clear resources.
     */
    @AfterClass
    public void tearDown() {
        command = null;
        mockIn = null;
    }

}
