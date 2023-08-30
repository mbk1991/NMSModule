package perf.DevICMP;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Type_Device_ICMP {

    private int DKEY;
    private String SYSOID;
    private String SYSNAME;
    private String HOSTNAME;
    private String SYSUPTIME;
    private int IFCOUNT;
    private String SYSDESCR;
    private String OSVER;
    private String IFIP;
    private String REGDATE;
    private String COMMUNITY;
    private String USERNAME;
    private String AUTHPASS;
    private String PRIVPASS;
    private String AUTH_PROTOCOL;
    private String PRIV_PROTOCOL;
    private int VERSION;
    private int V3_SECURELEVEL;
    private String NETCD;
    private String STATECD;
    private String NETNM;
    private String STATENM;

    /* ICMP 성능값 */
    private long icmpInMsgs;
    private long icmpInErrors;
    private long icmpInDestUnreachs;
    private long icmpInTimeExcds;
    private long icmpOutMsgs;
    private long icmpOutErrors;
    private long icmpOutDestUnreachs;
    private long icmpOutTimeExcds;
    private long ipInReceives;
    private long ipInHdrErrors;
    private long ipInAddrErrors;
    private long ipForwDatagrams;
    private long ipInUnknownProtos;
    private long ipInDiscards;
    private long ipInDelivers;
    private long ipOutRequests;
    private long ipOutDiscards;
    private long ipOutNoRoutes;
    private long ipRoutingDiscards;

    /* 이전 성능 원본값 */
    private long PREV_icmpInMsgs;
    private long PREV_icmpInErrors;
    private long PREV_icmpInDestUnreachs;
    private long PREV_icmpInTimeExcds;
    private long PREV_icmpOutMsgs;
    private long PREV_icmpOutErrors;
    private long PREV_icmpOutDestUnreachs;
    private long PREV_icmpOutTimeExcds;
    private long PREV_ipInReceives;
    private long PREV_ipInHdrErrors;
    private long PREV_ipInAddrErrors;
    private long PREV_ipForwDatagrams;
    private long PREV_ipInUnknownProtos;
    private long PREV_ipInDiscards;
    private long PREV_ipInDelivers;
    private long PREV_ipOutRequests;
    private long PREV_ipOutDiscards;
    private long PREV_ipOutNoRoutes;
    private long PREV_ipRoutingDiscards;
    private String PREV_GETDATE;

    /* 계산된 성능 데이터 */
    private long TRAF_icmpInMsgs;
    private long TRAF_icmpInErrors;
    private long TRAF_icmpInDestUnreachs;
    private long TRAF_icmpInTimeExcds;
    private long TRAF_icmpOutMsgs;
    private long TRAF_icmpOutErrors;
    private long TRAF_icmpOutDestUnreachs;
    private long TRAF_icmpOutTimeExcds;
    private long TRAF_ipInReceives;
    private long TRAF_ipInHdrErrors;
    private long TRAF_ipInAddrErrors;
    private long TRAF_ipForwDatagrams;
    private long TRAF_ipInUnknownProtos;
    private long TRAF_ipInDiscards;
    private long TRAF_ipInDelivers;
    private long TRAF_ipOutRequests;
    private long TRAF_ipOutDiscards;
    private long TRAF_ipOutNoRoutes;
    private long TRAF_ipRoutingDiscards;
    private long TRAF_RTERRRATE;
    private long TRAF_BUFFERRRATE;

    /* 알람 임계치 값 */
    private int RTERRSTATUS;
    private int RTERRTHR;
    private int RTERRLOWTHR;
    private int BUFFERRSTATUS;
    private int BUFFERRTHR;
    private int BUFFERRLOWTHR;



    static class DeviceBuilder{
        private int DKEY;
        private String SYSOID;
        private String IFIP;
        private String COMMUNITY;
        private String USERNAME;
        private String AUTHPASS;
        private String PRIVPASS;
        private String AUTH_PROTOCOL;
        private String PRIV_PROTOCOL;
        private int VERSION;
        private int V3_SECURELEVEL;

        public DeviceBuilder(){};


        public Type_Device_ICMP build(){
            Type_Device_ICMP t = new Type_Device_ICMP();

            t.setIFIP(IFIP);
            t.setVERSION(VERSION);
            t.setCOMMUNITY(COMMUNITY);
            t.setUSERNAME(USERNAME);
            t.setAUTHPASS(AUTHPASS);
            t.setPRIVPASS(PRIVPASS);
            t.setV3_SECURELEVEL(V3_SECURELEVEL);
            t.setSYSOID(SYSOID);
            t.setDKEY(DKEY);
            t.setAUTH_PROTOCOL(AUTH_PROTOCOL);
            t.setPRIV_PROTOCOL(PRIV_PROTOCOL);

            return t;
        }

        public DeviceBuilder IFIP(String val){
            IFIP = val;
            return this;
        }
        public DeviceBuilder VERSION(int val){
            VERSION = val;
            return this;
        }
        public DeviceBuilder COMMUNITY(String val){
            COMMUNITY = val;
            return this;
        }
        public DeviceBuilder USERNAME(String val){
            USERNAME = val;
            return this;
        }
        public DeviceBuilder AUTHPASS(String val){
            AUTHPASS = val;
            return this;
        }
        public DeviceBuilder PRIVPASS(String val){
            PRIVPASS = val;
            return this;
        }
        public DeviceBuilder V3_SECURELEVEL(int val){
            V3_SECURELEVEL = val;
            return this;
        }
        public DeviceBuilder SYSOID(String val){
            SYSOID = val;
            return this;
        }
        public DeviceBuilder DKEY(int val){
            DKEY = val;
            return this;
        }
        public DeviceBuilder AUTH_PROTOCOL(String val){
            AUTH_PROTOCOL = val;
            return this;
        }
        public DeviceBuilder PRIV_PROTOCOL(String val){
            PRIV_PROTOCOL = val;
            return this;
        }
    }
}
