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

import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.exceptions.NoDataException;
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
public class PendingTroubleCodesCommandTest {
    private PendingTroubleCodesCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new PendingTroubleCodesCommand();
    }

    /**
     * Test for two frames with four dtc
     *
     * @throws java.io.IOException
     */
    @Test
    public void twoFramesWithFourDTC() throws IOException {
        mockIn = mockInputStreamRead("47 00 03 51 04 A1 AB\n47 F1 06 00 00 00 00>");
        String res = "P0003\n" +
                "C1104\n" +
                "B21AB\n" +
                "U3106\n";

        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);
    }

    /**
     * Test for one frame with three dtc
     *
     * @throws java.io.IOException
     */
    @Test
    public void oneFrameWithThreeDTC() throws IOException {
        mockIn = mockInputStreamRead("47 01 03 01 04 01 05>");
        String res = "P0103\n" +
                "P0104\n" +
                "P0105\n";

        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);
    }

    /**
     * Test for one frame with two dtc
     *
     * @throws java.io.IOException
     */
    @Test
    public void oneFrameWithTwoDTC() throws IOException {
        mockIn = mockInputStreamRead("47 01 03 01 04 00 00>");
        String res = "P0103\n" + "P0104\n";

        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);
    }

    /**
     * Test for two frames with four dtc CAN (ISO-15765) format
     *
     * @throws IOException
     */
    @Test
    public void twoFramesWithFourDTCCAN() throws IOException {
        mockIn = mockInputStreamRead("00A\n0: 47 04 01 08 01 18\n1: 01 19 01 20 00 00>");
        String res = "P0108\n" +
                "P0118\n" +
                "P0119\n" +
                "P0120\n";

        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);
    }

    /**
     * Test for one frames with two dtc CAN (ISO-15765) format
     *
     * @throws IOException
     */
    @Test
    public void oneFrameWithTwoDTCCAN() throws IOException {
        mockIn = mockInputStreamRead("47 02 01 20 01 21>");
        String res = "P0120\n" + "P0121\n";

        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);
    }

    /**
     * Test for no data
     *
     * @throws java.io.IOException
     */
    @Test(expectedExceptions = NoDataException.class)
    public void noData() throws IOException {
        mockIn = mockInputStreamRead("47 NO DATA>");
        command.readResult(mockIn);
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
