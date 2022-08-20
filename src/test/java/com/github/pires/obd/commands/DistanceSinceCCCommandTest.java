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

import com.github.pires.obd.commands.control.DistanceSinceCCCommand;
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
public class DistanceSinceCCCommandTest {

    private DistanceSinceCCCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new DistanceSinceCCCommand();
    }

    /**
     * Test for valid InputStream read, 65535 km.
     *
     * @throws IOException
     */
    @Test
    public void testMaxDistanceValue() throws IOException {
        mockIn = mockInputStreamRead("41 31 FF FF>");
        command.readResult(mockIn);
        assertEquals(command.getKm(), 65535);
    }

    /**
     * Test for valid InputStream read, 17731 kms
     *
     * @throws IOException
     */
    @Test
    public void testSomeRuntimeValue() throws IOException {
        mockIn = mockInputStreamRead("41 31 45 43>");
        command.readResult(mockIn);
        assertEquals(command.getKm(), 17731);
    }

    /**
     * Test for valid InputStream read, 0 km.
     *
     * @throws IOException
     */
    @Test
    public void testMinRuntimeValue() throws IOException {
        mockIn = mockInputStreamRead("41 31 00 00>");
        command.readResult(mockIn);
        assertEquals(command.getKm(), 0);
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
