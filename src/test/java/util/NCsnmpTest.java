package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NCsnmpTest {
    private NCsnmp ncsnmp = new NCsnmp();
    private final String address = "127.0.0.1";
    private final String port = "161";
    private final String community = "ncnms";
    private final int timeout = 1000;
    private final int retries = 2;
    private final int version = SnmpConstants.version2c;
    private final String leafOID = "1.3.6.1.2.1.1.1.0";
    private final String rootOID = "1.3.6.1.2.1.5";

    @Test
    void snmp_get_테스트() {

        String s = ncsnmp.snmp_get(leafOID, address, community, version, timeout, retries);
        System.out.println(s);
    }

    @Test
    void snmp_walk_테스트() {

        Snmp snmp = new Snmp();
        try {

            //1. Snmp Session 및 UDP 세팅
            TransportMapping transportMapping = new DefaultUdpTransportMapping();
            Snmp snmpSession = new Snmp(transportMapping);
            snmpSession.listen();

            //2. 헤더 세팅
            Address addr = GenericAddress.parse(address);
            CommunityTarget communityTarget = new CommunityTarget(addr, new OctetString(community));
            communityTarget.setTimeout(timeout);
            communityTarget.setRetries(retries);
            communityTarget.setVersion(version);

            //3.페이로드 세팅
            OID rOID = new OID(rootOID);
            PDU requestPDU = new PDU();
            requestPDU.setType(PDU.GETNEXT);
            requestPDU.add(new VariableBinding(rOID));

            TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
            List<TreeEvent> events = treeUtils.getSubtree(communityTarget, rOID);
            System.out.println("events.toString() = " + events.toString());

            //예외처리
            if (events == null || events.isEmpty()) {
                snmp.close();
                transportMapping.close();
                return;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public boolean snmp_walk_테스트2() {

        try {
            Address targetAddress = GenericAddress.parse("udp:" + address + "/" + port);
            TransportMapping transport = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transport);
            transport.listen();

            // setting up target
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setAddress(targetAddress);
            target.setRetries(retries);
            target.setTimeout(timeout);
            target.setVersion(version);

            System.out.println("===============");

            OID oid = null;
            try {
                oid = new OID(rootOID);
            } catch (RuntimeException ex) {

                System.out.println("OID is not specified correctly.");
                transport.close();
                snmp.close();
                return false;
            }

            TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
            List<TreeEvent> events = treeUtils.getSubtree(target, oid);


            System.out.println("events.toString() = " + events.toString());

            if (events == null || events.size() == 0) {

//				System.out.println("No result returned.");

                snmp.close();
                transport.close();
                return false;
            }

            // Get snmpwalk result.
            for (TreeEvent event : events) {
                if (event != null) {
                    if (event.isError()) {
                        transport.close();
                        snmp.close();
                        return false;
                    }

                    VariableBinding[] varBindings = event.getVariableBindings();
                    if (varBindings == null) {
//				    	System.out.println("No result returned.");
                        transport.close();
                        snmp.close();
                        return false;
                    }
                    /*
                     * SNMP NO SUCH 결과 값 체크 안 함
                     */
//				    if (varBindings == null || varBindings.length == 0) {
//				    	System.out.println("No result returned.");
//				    	transport.close();
//				    	snmp.close();
//				    	return false;
//				    }

                    for (VariableBinding varBinding : varBindings) {
                        System.out.println(varBinding.getOid() + " : " + varBinding.getVariable());
                    }
                }
            }
            transport.close();
            snmp.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
        }
        return true;
    }

}
