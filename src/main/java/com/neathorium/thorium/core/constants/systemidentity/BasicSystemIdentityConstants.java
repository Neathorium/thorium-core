package com.neathorium.thorium.core.constants.systemidentity;

public abstract class BasicSystemIdentityConstants {
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String PROPERTY_HOSTNAME = System.getenv("HOSTNAME");
    public static final String PROPERTY_COMPUTERNAME = System.getenv("COMPUTERNAME");

    public static final String MAC_OSX_NAME = "Mac OS X";
    public static final String ETHERNET_0 = "en0";
    public static final String INTERNET_URI = "google.com";
    public static final String ROUTING_IP = "8.8.8.8";
    public static final int INTERNET_PORT = 80;
    public static final int ROUTING_PORT = 10002;
}
