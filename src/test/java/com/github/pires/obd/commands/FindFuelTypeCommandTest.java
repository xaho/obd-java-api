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

import com.github.pires.obd.commands.fuel.FindFuelTypeCommand;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.assertEquals;

/**
 * Tests for FindFuelTypeCommand class.
 */
public class FindFuelTypeCommandTest {

    private FindFuelTypeCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new FindFuelTypeCommand();
    }

    /**
     * Test for valid InputStream read, Gasoline
     *
     * @throws IOException
     */
    @Test
    public void testFindGasoline() throws IOException {
        mockIn = mockInputStreamRead("41 51 01>");
        command.readResult(mockIn);
        assertEquals(command.getFormattedResult(), "Gasoline");
    }

    /**
     * Test for valid InputStream read, Diesel
     *
     * @throws IOException
     */
    @Test
    public void testDiesel() throws IOException {
        mockIn = mockInputStreamRead("41 51 04>");
        command.readResult(mockIn);
        assertEquals(command.getFormattedResult(), "Diesel");
    }

    /**
     * Test for valid InputStream read, Ethanol
     *
     * @throws IOException
     */
    @Test
    public void testHybridEthanol() throws IOException {
        mockIn = mockInputStreamRead("41 51 12>");
        command.readResult(mockIn);
        assertEquals(command.getFormattedResult(), "Hybrid Ethanol");
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
