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

import com.github.pires.obd.commands.engine.ThrottlePositionCommand;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.assertEquals;

/**
 * Tests for ThrottlePositionCommand class.
 */
@PrepareForTest(InputStream.class)
public class ThrottleCommandTest {
    private ThrottlePositionCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new ThrottlePositionCommand();
    }

    /**
     * Test for valid InputStream read, maximum value of 100%
     *
     * @throws IOException
     */
    @Test
    public void testMaxThrottlePositionValue() throws IOException {
        mockIn = mockInputStreamRead("41 11 FF>");
        command.readResult(mockIn);
        assertEquals(command.getPercentage(), 100f);
    }

    /**
     * Test for valid InputStream read, 58.4%
     *
     * @throws IOException
     */
    @Test
    public void testSomeThrottlePositionValue() throws IOException {
        mockIn = mockInputStreamRead("41 11 95>");
        command.readResult(mockIn);
        assertEquals(command.getPercentage(), 58.431374f);
    }

    /**
     * Test for valid InputStream read, minimum value 0%
     *
     * @throws IOException
     */
    @Test
    public void testMinThrottlePositionValue() throws IOException {
        mockIn = mockInputStreamRead("41 11 00>");
        command.readResult(mockIn);
        assertEquals(command.getPercentage(), 0f);
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
