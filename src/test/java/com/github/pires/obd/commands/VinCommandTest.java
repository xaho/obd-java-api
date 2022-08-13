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

import com.github.pires.obd.commands.control.VinCommand;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.powermock.api.easymock.PowerMock.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests for VinCommand class.
 */
public class VinCommandTest {
    private VinCommand command;
    private InputStream mockIn;

    /**
     * @throws Exception
     */
    @BeforeMethod
    public void setUp() throws Exception {
        command = new VinCommand();
        // mock InputStream read
        mockIn = createMock(InputStream.class);
        mockIn.read();
    }

    /**
     * Clear resources.
     */
    @AfterClass
    public void tearDown() {
        command = null;
        mockIn = null;
    }

    /**
     * Test VIN CAN (ISO-15765) format
     *
     * @throws IOException
     */
    @Test
    public void vinCAN() throws IOException {
        byte[] v = ("014\n" +
                "0: 49 02 01 57 50 30\n" +
                "1: 5A 5A 5A 39 39 5A 54\n" +
                "2: 53 33 39 32 31 32 34>").getBytes();
        for (byte b : v) {
            expectLastCall().andReturn(b);
        }
        replayAll();
        String res = "WP0ZZZ99ZTS392124";

        // call the method to test
        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);

        verifyAll();
    }

    /**
     * Test VIN ISO9141-2, KWP2000 Fast and KWP2000 5Kbps (ISO15031) format
     *
     * @throws IOException
     */
    @Test
    public void vin() throws IOException {
        byte[] v = ("49 02 01 00 00 00 57\n" +
                "49 02 02 50 30 5A 5A\n" +
                "49 02 03 5A 39 39 5A\n" +
                "49 02 04 54 53 33 39\n" +
                "49 02 05 32 31 32 34>").getBytes();
        for (byte b : v) {
            expectLastCall().andReturn(b);
        }

        replayAll();
        String res = "WP0ZZZ99ZTS392124";

        // call the method to test
        command.readResult(mockIn);

        assertEquals(command.getFormattedResult(), res);

        verifyAll();
    }
}
