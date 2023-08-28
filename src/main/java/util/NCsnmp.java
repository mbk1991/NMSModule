package util;

import perf.DevICMP.PerfICMPMIB;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;


/**
 * SNMP4J
 * 네트워크 자원 관리를 위한 애플리케이션계층 프로토콜인 SNMP의 메시지를 생성, 전송, 수신하는 기능을 제공하는 Java 클래스 및 인터페이스 집합 라이브러리
 * <p>
 * *SNMP Message = Message Header + Payload ( PDU: Protocol Data Unit )
 * <p>
 * snmp 메시지 교환을 위해 필요한 것 = 원격 시스템 식별 정보 ( IP, Port ) , 통신에 대한 정책 정보, 요청 정보 OID
 * <p>
 * -공통      : IP, PORT, OID, TIMEOUT POLICY etc..     *v3는 Community 문자열을 사용하지 않는다.
 * -v1, v2c  : CommunityTarget을 사용한다. ( COMMUNITY 정보 제공 )
 * -v3       : UserTarget을 사용한다. ( SecureTarget 추상클래스를 extends. 보안이름, 보안수준, 보안모델(User based Secure Model) 권한보유 엔진ID 등 사용자 정보 제공 )
 * <p>
 * <p>
 * SNMP는 매니저(관리자)와 에이전트로 구분하여 매니저가 에이전트의 네트워크 관련 정보를 수집한다.
 * 에이전트를 원격 시스템( remote system ) 이라고 할 때,
 * 원격 시스템은 SNMP4J에서 Target 인스턴스로 표현된다.
 * <p>
 * SNMP MESSAGE 는 Message Header와 Payload(PDU)로 구성되는데 SNMP4J에서
 * Message Headersms Target 인스턴스로, Payload는 PDU 인스턴스로 표현한다.
 * <p>
 * - Message Header의 표현 : Target 인스턴스 ( Message Header 에 원격 시스템에 대한 정보가 있는 것)
 * - PDU의 표현 : PDUv1 (SNMPv1),  PDU (SNMPv2c)  ,ScopedPDU (SNMPv3)
 * <p>
 * ★SNMP4J로 SNMP Message를 전송하기 위해서는 Target 인스턴스와 PDU 인스턴스가 필수로 필요하다★
 * <p>
 * SNMP Message는 SNMP4j의 SNMP Session 인스턴스를 통해 전송된다.
 * SNMP Session의 기본 구현 클래스는 Snmp 이다.
 * Snmp의 세팅은 TransportMapping인스턴스를 생성자 파라미터에 전달하는 것으로 처리할 수 있다.
 * <p>
 * TransportMapping은 remote system과 Message를 주고 받기 위해  전송 계층의 UDP 프로토콜 등을 이용하는 객체이다.
 * <p>
 * SNMP4J를 이용하면 SNMP Message를 동기, 비동기 식으로 전송할 수 있다.
 *
 *
 * MIB 트리에서
 * 마지막이 0으로 끝나는 경우 leaf 정보를 가지고 있는 leaf 노드라고 판단할 수 있다.
 *
 * leaf 노드인 경우 get으로 단일 정보를 조회한다.
 * 상위 노드인 경우 walk (getnext) 로 여러 정보를 조회한다.
 *
 */
public class NCsnmp {

    Snmp snmp = new Snmp();
    private static int secure_level;
    private static final String PORT = "161";

    public void close() {
        try {
            snmp.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //SNMPv2. get은 해당하는 OID의 value 하나만 가져온다. ( 단일 정보 )
    public String snmp_get(String oid, String ip, String community, int version, int timeout, int retries) {
        String result = "";

        try {
            TransportMapping transportMapping = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transportMapping);
            snmp.listen();

            Address address = GenericAddress.parse(ip + "/" + PORT);
            CommunityTarget communityTarget = new CommunityTarget();
            communityTarget.setAddress(address);
            communityTarget.setCommunity(new OctetString(community));
            communityTarget.setVersion(version);
            communityTarget.setTimeout(timeout);
            communityTarget.setRetries(retries);

            PDU requestPDU = new PDU();
            requestPDU.add(new VariableBinding(new OID(oid)));
            requestPDU.setType(PDU.GET);

            ResponseEvent responseEvent = snmp.send(requestPDU, communityTarget);

            if (responseEvent != null) {

                PDU responsePDU = responseEvent.getResponse();
                if (responsePDU != null) {
                    result = responsePDU.getVariableBindings().firstElement().toString();
                    if( result.contains("=")){
                        int len = result.indexOf("=");
                        result = result.substring(len + 2);
                    }
                }
            }
            snmp.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
        }
        return result;
    }





    public static void main(String[] args) {


    }
}
