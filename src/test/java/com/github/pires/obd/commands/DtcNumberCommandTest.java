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

import com.github.pires.obd.commands.control.DtcNumberCommand;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.*;

/**
 * Tests for DtcNumberCommand class.
 */
@PrepareForTest(InputStream.class)
public class DtcNumberCommandTest {

    private DtcNumberCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new DtcNumberCommand();
    }

    /**
     * Test for valid InputStream read, MIL on.
     *
     * @throws IOException
     */
    @Test
    public void testMILOn() throws IOException {
        mockIn = mockInputStreamRead("41 01 9F>");
        command.readResult(mockIn);
        command.getFormattedResult();
        assertTrue(command.getMilOn());
        assertEquals(command.getTotalAvailableCodes(), 31);
    }

    /**
     * Test for valid InputStream read, MIL off.
     *
     * @throws IOException
     */
    @Test
    public void testMILOff() throws IOException {
        mockIn = mockInputStreamRead("41 01 0F>");
        command.readResult(mockIn);
        command.getFormattedResult();
        assertFalse(command.getMilOn());
        assertEquals(command.getTotalAvailableCodes(), 15);
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
