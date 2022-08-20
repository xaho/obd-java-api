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

import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.assertEquals;

/**
 * Tests for TemperatureCommand sub-classes.
 */
@PrepareForTest(InputStream.class)
public class AirIntakeTempCommandTest {

    private AirIntakeTemperatureCommand command = null;
    private InputStream mockIn = null;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new AirIntakeTemperatureCommand();
    }

    /**
     * Test for valid InputStream read, 24ºC
     *
     * @throws IOException
     */
    @Test
    public void testValidTemperatureCelsius() throws IOException {
        mockIn = mockInputStreamRead("41 0F 40>");
        command.readResult(mockIn);
        assertEquals(command.getTemperature(), 24f);
    }

    /**
     * Test for valid InputStream read, 75.2F
     *
     * @throws IOException
     */
    @Test
    public void testValidTemperatureFahrenheit() throws IOException {
        mockIn = mockInputStreamRead("41 0F 45>");
        command.readResult(mockIn);
        command.useImperialUnits = true;
        assertEquals(command.getImperialUnit(), 84.2f);
    }

    /**
     * Test for valid InputStream read, 0ºC
     *
     * @throws IOException
     */
    @Test
    public void testValidTemperatureZeroCelsius() throws IOException {
        mockIn = mockInputStreamRead("41 0F 28>");
        command.readResult(mockIn);
        assertEquals(command.getTemperature(), 0f);
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
