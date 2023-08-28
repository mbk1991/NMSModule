package perf.DevICMP;

public class PerfICMPMIB {

    // 취급 MIB , 230825 권마빈
    //  1.3.6.1.2.1.4 : root.iso(1).org(3).dod(6).internet(1).mgmt(2).mib-2(1).ip(4)
    //	1.3.6.1.2.1.5 : root.iso(1).org(3).dod(6).internet(1).mgmt(2).mib-2(1).icmp(5)

    public static final String MIB_icmp = ".1.3.6.1.2.1.5";

    public static final String MIB_icmpInMsgs = "1.3.6.1.2.1.5.1.0";
    public static final String MIB_icmpInErrors = "1.3.6.1.2.1.5.2.0";
    public static final String MIB_icmpInDestUnreachs = "1.3.6.1.2.1.5.3.0";
    public static final String MIB_icmpInTimeExcds = "1.3.6.1.2.1.5.4.0";
    public static final String MIB_icmpOutMsgs = "1.3.6.1.2.1.5.14.0";
    public static final String MIB_icmpOutErrors = "1.3.6.1.2.1.5.15.0";
    public static final String MIB_icmpOutDestUnreachs = "1.3.6.1.2.1.5.16.0";
    public static final String MIB_icmpOutTimeExcds = "1.3.6.1.2.1.5.17.0";

    public static final String MIB_ip = ".1.3.6.1.2.1.4";

    public static final String MIB_ipInReceives = "1.3.6.1.2.1.4.3.0";
    public static final String MIB_ipInHdrErrors = "1.3.6.1.2.1.4.4.0";
    public static final String MIB_ipInAddrErrors = "1.3.6.1.2.1.4.5.0";
    public static final String MIB_ipForwDatagrams = "1.3.6.1.2.1.4.6.0";
    public static final String MIB_ipInUnknownProtos = "1.3.6.1.2.1.4.7.0";
    public static final String MIB_ipInDiscards = "1.3.6.1.2.1.4.8.0";
    public static final String MIB_ipInDelivers = "1.3.6.1.2.1.4.9.0";
    public static final String MIB_ipOutRequests = "1.3.6.1.2.1.4.10.0";
    public static final String MIB_ipOutDiscards = "1.3.6.1.2.1.4.11.0";
    public static final String MIB_ipOutNoRoutes = "1.3.6.1.2.1.4.12.0";
    public static final String MIB_ipRoutingDiscards = "1.3.6.1.2.1.4.23.0";

}
