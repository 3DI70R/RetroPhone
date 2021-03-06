package ru.threedisevenzeror.retrophone;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Three on 04.11.2016.
 */
public interface ResourceProvider {

    ResourceProvider Null = new ResourceProvider() {
        @Override
        public InputStream open(String name) throws IOException {
            throw new IOException("Null resource provider");
        }
    };

    InputStream open(String name) throws IOException;
}
