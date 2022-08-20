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

import com.github.pires.obd.commands.fuel.FuelLevelCommand;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.assertEquals;

/**
 * Tests for FuelLevelCommand class.
 */
public class FuelLevelCommandTest {
    private FuelLevelCommand command = null;
    private InputStream mockIn = null;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new FuelLevelCommand();
    }

    /**
     * Test for valid InputStream read, full tank
     *
     * @throws IOException
     */
    @Test
    public void testFullTank() throws IOException {
        mockIn = mockInputStreamRead("41 2F FF>");
        command.readResult(mockIn);
        assertEquals(command.getFuelLevel(), 100f);
    }

    /**
     * Test for valid InputStream read. 78.4%
     *
     * @throws IOException
     */
    @Test
    public void testSomeValue() throws IOException {
        mockIn = mockInputStreamRead("41 2F C8>");
        command.readResult(mockIn);
        assertEquals(command.getFuelLevel(), 78.43137f);
    }

    /**
     * Test for valid InputStream read, empty tank
     *
     * @throws IOException
     */
    @Test
    public void testEmptyTank() throws IOException {
        mockIn = mockInputStreamRead("41 2F 00>");
        command.readResult(mockIn);
        assertEquals(command.getFuelLevel(), 0f);
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
