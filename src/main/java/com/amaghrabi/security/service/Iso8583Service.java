package com.amaghrabi.security.service;

import jakarta.annotation.PostConstruct;
import org.jpos.iso.*;
import org.springframework.stereotype.Service;
import org.jpos.util.*;
import org.jpos.iso.channel.*;
import org.jpos.iso.packager.XMLPackager;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class Iso8583Service {

    public Map<String, Object> processIso8583Message() {
        XMLChannel clientChannel = null;
        try {
            // Create a client channel with the custom packager
            Logger logger = new Logger();
            logger.addListener(new SimpleLogListener(System.out));

            clientChannel = new XMLChannel("localhost", 8000, new XMLPackager());
            ((LogSource) clientChannel).setLogger(logger, "client-channel");

            // Connect to the server
            clientChannel.connect();

            // Create and pack a new ISO 8583 message
            ISOMsg requestMsg = getIsoMsg();

            // Send the message
            clientChannel.send(requestMsg);

            // Receive and unpack the response
            ISOMsg responseMsg = clientChannel.receive();

            // Convert ISOMsg to map
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("MTI", responseMsg.getMTI());
            for (int i = 0; i <= responseMsg.getMaxField(); i++) {
                if (responseMsg.hasField(i)) {
                    responseMap.put("Field" + i, responseMsg.getString(i));
                }
            }

            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (clientChannel != null && clientChannel.isConnected()) {
                try {
                    clientChannel.disconnect();
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

    @PostConstruct
    public void startIso8583Server() {
        try {
            // Create a logger for the server
            Logger logger = new Logger();
            // Add a SimpleLogListener to the logger to output log messages to the console
            logger.addListener(new SimpleLogListener(System.out));

            // Create a server channel with the XML packager
            ServerChannel serverChannel = new XMLChannel(new XMLPackager());
            // Configure logging for the server channel
            ((LogSource) serverChannel).setLogger(logger, "server-channel");

            // Create an ISOServer instance listening on port 8000 with the server channel
            ISOServer server = new ISOServer(8000, serverChannel, null);
            // Set logger for the ISOServer
            server.setLogger(logger, "iso-server");

            // Add a listener to handle incoming ISO requests
            server.addISORequestListener((source, m) -> {
                try {
                    // Clone the received message to create a response message
                    ISOMsg response = (ISOMsg) m.clone();
                    // Set MTI (Message Type Indicator) to indicate a response message
                    response.setMTI("0210");
                    // Set field 39 (Response Code) to "00" for a successful transaction
                    response.set(39, "00");
                    // Send the response back to the client
                    source.send(response);
                    return true;
                } catch (ISOException | IOException e) {
                    e.printStackTrace();
                    return false;
                }
            });

            // Start the server in a new thread to handle incoming requests concurrently
            new Thread(server).start();
        } catch (ISOException | IOException e) {
            e.printStackTrace();
        }
    }

}
