package com.golftec.video.production.videoproduction.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;

public class ByteCompressor {


    public static byte[] compress(Object object) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        Deflater compressor = new Deflater();
        compressor.setInput(b.toByteArray());
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Compress the data
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        o.close();
        byte[] bytes = bos.toByteArray();
        bos.reset();
        bos.close();
        return bytes;
    }

    public static byte[] compress(byte[] byteArray) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        Deflater compressor = new Deflater();
        compressor.setInput(byteArray);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Compress the data
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        o.close();
        byte[] bytes = bos.toByteArray();
        bos.reset();
        bos.close();
        return bytes;
    }

}
