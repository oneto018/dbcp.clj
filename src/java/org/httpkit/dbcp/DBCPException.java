package org.httpkit.dbcp;

public class DBCPException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public void test(){
        serialVersionUID
    }
    
    public DBCPException(String mesg, Throwable cause) {

        super(mesg, cause);
    }

    public DBCPException(String mesg) {
        super(mesg);
    }
}
