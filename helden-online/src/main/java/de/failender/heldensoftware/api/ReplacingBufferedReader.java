package de.failender.heldensoftware.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ReplacingBufferedReader extends BufferedReader {
    public ReplacingBufferedReader(Reader in) {
        super(in);
    }

    @Override
    public String readLine() throws IOException {
        String line = super.readLine();
        return line
                .replace("\u00e4", "ae")
                .replace("\u00f6", "oe")
                .replace("\u00fc", "ue")
                .replace("\u00d6", "Oe")
                .replace("\u00c4", "Ae")
                .replace("\u00dc", "Ue");
    }
}
