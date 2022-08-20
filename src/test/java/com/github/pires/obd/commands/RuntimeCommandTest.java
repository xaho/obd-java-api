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

import com.github.pires.obd.commands.engine.RuntimeCommand;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.pires.obd.TestUtils.mockInputStreamRead;
import static org.testng.Assert.assertEquals;

/**
 * Runtime since engine start in seconds, with a maximum value of 65535.
 */
@PrepareForTest(InputStream.class)
public class RuntimeCommandTest {

    private RuntimeCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new RuntimeCommand();
    }

    /**
     * Test for valid InputStream read, 65535 seconds.
     *
     * @throws IOException
     */
    @Test
    public void testMaxRuntimeValue() throws IOException {
        mockIn = mockInputStreamRead("41 1F FF FF>");
        command.readResult(mockIn);
        assertEquals(command.getFormattedResult(), "18:12:15");
    }

    /**
     * Test for valid InputStream read, 67 seconds
     *
     * @throws IOException
     */
    @Test
    public void testSomeRuntimeValue() throws IOException {
        mockIn = mockInputStreamRead("41 1F 45 43>");
        command.readResult(mockIn);
        assertEquals(command.getFormattedResult(), "04:55:31");
    }

    /**
     * Test for valid InputStream read, 0 seconds.
     *
     * @throws IOException
     */
    @Test
    public void testMinRuntimeValue() throws IOException {
        mockIn = mockInputStreamRead("41 1F 00 00>");
        command.readResult(mockIn);
        assertEquals(command.getFormattedResult(), "00:00:00");
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
