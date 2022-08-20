package com.github.pires.obd;

import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.when;

public class TestUtils {
    public static InputStream mockInputStreamRead(String string) throws IOException {
        InputStream in = Mockito.mock(InputStream.class);
        OngoingStubbing<Integer> stub = when(in.read());
        for (byte b : string.getBytes()) {
            stub = stub.thenReturn((int)b);
        }
        return in;
    }
}
