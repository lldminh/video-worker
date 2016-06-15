package com.golftec.teaching.server.networking.util;

import java.io.*;

public class ByteObjectCommon {

    public static byte[] getObjectBytes(Serializable object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(baos);
            ooStream.writeObject(object);
            ooStream.flush();
            byte[] objectBytes = baos.toByteArray();
            baos.close();
            ooStream.close();
            return objectBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getObjectSize(Serializable object) {
        return getObjectBytes(object).length;
    }

    public static Object getObject(byte[] objectBytes) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(objectBytes);
        ObjectInputStream objectStream = new ObjectInputStream(stream);
        Object o = objectStream.readObject();
        objectStream.close();
        objectStream = null;
        return o;
    }

}
