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

import com.github.pires.obd.commands.engine.RPMCommand;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.assertEquals;

/**
 * Tests for RPMCommand class.
 */
@PrepareForTest(InputStream.class)
public class RPMCommandTest {

    private RPMCommand command = null;
    private InputStream mockIn = null;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new RPMCommand();
    }

    /**
     * Test for valid InputStream read, max RPM
     *
     * @throws IOException
     */
    @Test
    public void testMaximumRPMValue() throws IOException {
        mockIn = mockInputStreamRead("41 0C FF FF>");
        command.readResult(mockIn);
        assertEquals(command.getRPM(), 16383);
    }

    /**
     * Test for valid InputStream read
     *
     * @throws IOException
     */
    @Test
    public void testHighRPM() throws IOException {
        mockIn = mockInputStreamRead("41 0C 28 3C>");
        command.readResult(mockIn);
        assertEquals(command.getRPM(), 2575);
    }

    /**
     * Test for valid InputStream read
     *
     * @throws IOException
     */
    @Test
    public void testLowRPM() throws IOException {
        mockIn = mockInputStreamRead("41 0C 0A 00>");
        command.readResult(mockIn);
        assertEquals(command.getRPM(), 640);
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
