package com.amaghrabi.security.jpos;

import org.jpos.iso.IFA_LLNUM;
import org.jpos.iso.IFA_NUMERIC;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;

public class CustomISO8583Packager extends ISOBasePackager {
    private static final ISOFieldPackager[] fieldPackagers = new ISOFieldPackager[128];

    static {
        fieldPackagers[0] = new IFA_NUMERIC(4, "MTI");
        fieldPackagers[2] = new IFA_LLNUM(19, "PAN");
        fieldPackagers[3] = new IFA_NUMERIC(6, "Processing Code");
        fieldPackagers[4] = new IFA_NUMERIC(12, "Amount");
        fieldPackagers[7] = new IFA_NUMERIC(10, "Transmission Date and Time");
        fieldPackagers[11] = new IFA_NUMERIC(6, "System Trace Audit Number");
        fieldPackagers[12] = new IFA_NUMERIC(6, "Local Transaction Time");
        fieldPackagers[13] = new IFA_NUMERIC(4, "Local Transaction Date");
        fieldPackagers[37] = new IFA_NUMERIC(12, "Retrieval Reference Number");
        fieldPackagers[39] = new IFA_NUMERIC(2, "Response Code");
        fieldPackagers[41] = new IFA_NUMERIC(8, "Card Acceptor Terminal ID");
    }

    public CustomISO8583Packager() {
        super();
        setFieldPackager(fieldPackagers);
    }
}
