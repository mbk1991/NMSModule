package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
            communityTarget.setVersion(SnmpConstants.version1);

            //3.페이로드 세팅
            OID rOID = new OID(leafOID);
            PDU requestPDU = new PDU();
            requestPDU.setType(PDU.GETNEXT);
            requestPDU.add(new VariableBinding(rOID));

            ResponseEvent responseEvent = snmp.send(requestPDU, communityTarget);

            PDU responsePDU = responseEvent.getResponse();
            Vector variableBindings = responsePDU.getVariableBindings();

            if(variableBindings.isEmpty()){
                Iterator iterator = variableBindings.iterator();
                while(iterator.hasNext()){
                    Object o = iterator.next();
                    if(o instanceof VariableBinding){
                        VariableBinding v = (VariableBinding) o;
                        System.out.println(v.getVariable());
                    }
                }
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

    @Test
    void treeUtil_테스트() throws IOException {
        // SNMP agent information
        String targetAddressStr = "udp:127.0.0.1/161";  // Change this to the actual agent address
        String communityString = "ncnms";  // Change this to the actual community string

        // Create target address
        Address targetAddress = GenericAddress.parse(targetAddressStr);
        TransportMapping transport = new DefaultUdpTransportMapping();
        Snmp snmp = new Snmp(transport);
        transport.listen();

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(communityString));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);

        // OID for the subtree you want to retrieve
        OID subtreeOID = new OID("1.3.6.1.2.1.5");  // Change this to the actual OID

        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        List<TreeEvent> events = treeUtils.getSubtree(target, subtreeOID);

        if (events == null || events.isEmpty()) {
            System.out.println("No result returned.");
            return;
        }

        // Process the depth-first tree events
        for (TreeEvent event : events) {
            if (event == null) {
                continue;
            }
            if (event.isError()) {
                System.err.println("Error: " + event.getErrorMessage());
                continue;
            }

            VariableBinding[] varBindings = event.getVariableBindings();
            if (varBindings == null) {
                continue;
            }

            for (VariableBinding v : varBindings) {
                System.out.println(v.getOid() + " = " + v.getVariable());
            }
        }
        snmp.close();
    }

    @Test
    void SNMP_BULK_테스트() {
        try {
            // SNMP 정보 설정
            String ipAddress = "udp:127.0.0.1/161";
            String community = "ncnms";
            int retries = 2;
            int timeout = 1500;

            // SNMP PDU 생성
            PDU pdu = new PDU();
            pdu.setType(PDU.GETBULK);
            pdu.setMaxRepetitions(10); // Max repetitions 설정

            // OID 목록
            OID oid = new OID("1.3.6.1.2.1.1"); // 예시 OID
            pdu.add(new VariableBinding(oid));

            // SNMP 연결 설정
            Address targetAddress = GenericAddress.parse(ipAddress);
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setAddress(targetAddress);
            target.setRetries(retries);
            target.setTimeout(timeout);
            target.setVersion(SnmpConstants.version2c);

            // SNMP 연결 생성
            TransportMapping transport = null;
            transport = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transport);
            snmp.listen();

            // 요청 보내기
            ResponseEvent responseEvent = snmp.send(pdu, target);
            PDU response = responseEvent.getResponse();

            if (response != null) {

                Vector<? extends VariableBinding> variableBindings = response.getVariableBindings();
                // 결과 처리
                for (VariableBinding vb : variableBindings) {
                    System.out.println(vb.getOid() + " = " + vb.getVariable());
                }
            } else {
                System.out.println("No response received.");
            }
            // 연결 닫기
            snmp.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void snmp_treeEvent_테스트() {
        try {
            Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString("ncnms"));
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version2c);

            TransportMapping transport = null;
            transport = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transport);
            transport.listen();

            TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
            OID rootOID = new OID("1.3.6.1.2.1"); // Example root OID

            List<TreeEvent> treeEvents = treeUtils.getSubtree(target, rootOID);
            if (treeEvents != null) {
                for (TreeEvent t : treeEvents) {
                    VariableBinding[] variableBindings = t.getVariableBindings();

                    for (VariableBinding v : variableBindings) {
                        System.out.println(v.getOid() + " " + v.getVariable());
                    }
                }
            } else {
                System.err.println("TreeEvent is null.");
            }

            snmp.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
