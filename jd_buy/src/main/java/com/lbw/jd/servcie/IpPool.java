package com.lbw.jd.servcie;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/7/30 16:16
 * @description：Ip池
 */
public class IpPool {
    private Hashtable<String, Long> ipMap = new Hashtable<>();
    private static IpPool ipPool;
    private static Long limitDate = (long) (1000 * 60 * 30);

    public Hashtable<String, Long> getIpMap() {
        return ipMap;
    }

    public void setIpMap(Hashtable<String, Long> ipMap) {
        this.ipMap = ipMap;
    }

    public synchronized static IpPool getIpPool() throws Exception {
        if (ipPool == null) {
            ipPool = new IpPool();
            Hashtable<String, Long> ipMap = ipPool.getIpMap();
            String ipStr = ConfigService.getProperties("ips");
            String[] ips = ipStr.split(",");
            for (String ip : ips) {
                ipMap.put(ip, 0L);
                ipMap.put(ip, 0L);
            }

            ipPool.setIpMap(ipMap);
        }
        return ipPool;
    }

    /**
     * 获取可用IP
     */
    public synchronized String getIp() {
        Hashtable<String, Long> ipMap = ipPool.getIpMap();
        for (Map.Entry<String, Long> entry : ipMap.entrySet()) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - entry.getValue() > limitDate) {
                return entry.getKey();
            }
        }
        return null;
    }

    public synchronized void disableIp(String ip) {
        Hashtable<String, Long> ipMap = ipPool.getIpMap();
        if (ipMap.containsKey(ip))
        {
            long currentTimeMillis = System.currentTimeMillis();
            Long time = ipMap.get(ip);
            if (currentTimeMillis-time<limitDate)
            {
                return;
            }
        }
        ipMap.put(ip, System.currentTimeMillis());
    }

}
