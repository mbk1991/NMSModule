package perf.DevICMP;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    public int selectDevice(ArrayList<Type_Device_ICMP> all_dev){
        // 빌더 테스트 코드
        int rs_getInt = 0;
        
        all_dev.add(new Type_Device_ICMP.DeviceBuilder().IFIP("rs.getString(n)")
                                                        .VERSION(rs_getInt)
                                                        .COMMUNITY("rs.getString(n)")
                                                        .USERNAME("rs.getString(n)")
                                                        .AUTHPASS("rs.getString(n)")
                                                        .PRIVPASS("rs.getString(n)")
                                                        .V3_SECURELEVEL(rs_getInt)
                                                        .SYSOID("rs.getString(n)")
                                                        .DKEY(rs_getInt)
                                                        .AUTH_PROTOCOL("rs.getString(n)")
                                                        .PRIV_PROTOCOL("rs.getString(n)")
                                                        .build());

        return 0;
    }
}
