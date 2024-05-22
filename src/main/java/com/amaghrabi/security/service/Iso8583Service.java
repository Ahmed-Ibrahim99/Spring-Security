package com.amaghrabi.security.service;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.ASCIIChannel;
import org.springframework.stereotype.Service;
import org.jpos.util.*;
import org.jpos.iso.packager.*;


import java.io.IOException;

@Service
public class Iso8583Service {

    public String processIso8583Message() {
        ISOChannel channel = null;
        try {
            // Create a channel with the custom packager
            Logger logger = new Logger();
            logger.addListener (new SimpleLogListener (System.out));
            channel = new ASCIIChannel("localhost", 8000, new ISO87APackager());
            ((LogSource) channel).setLogger(logger, "test-channel");
            channel.connect();

            // Create and pack a new ISO 8583 message
            ISOMsg requestMsg = getIsoMsg();

            // Send the message
            channel.send(requestMsg);

            // Receive and unpack the response
            ISOMsg responseMsg = channel.receive();

            // Print the response message fields
            System.out.println("Response MTI: " + responseMsg.getMTI());
            for (int i = 0; i <= responseMsg.getMaxField(); i++) {
                if (responseMsg.hasField(i)) {
                    System.out.println("Field (" + i + "): " + responseMsg.getString(i));
                }
            }

            return "ISO 8583 message processed successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing ISO 8583 message: " + e.getMessage();
        } finally {
            if (channel != null && channel.isConnected()) {
                try {
                    channel.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ISOMsg getIsoMsg() throws ISOException {
        ISOMsg requestMsg = new ISOMsg();
        requestMsg.setMTI("0200");
        requestMsg.set(2, "1234567890123456");
        requestMsg.set(3, "000000");
        requestMsg.set(4, "1000");
        requestMsg.set(7, "0101010101");
        requestMsg.set(11, "123456");
        requestMsg.set(12, "123456");
        requestMsg.set(13, "0101");
        requestMsg.set(37, "123456789012");
        requestMsg.set(41, "12345678");
        return requestMsg;
    }
}
