package com.chenwen.datastruct;

import lombok.Data;

import java.util.HashMap;

/**
 * 地铁路线最优寻路
 *
 * @author chen.jw
 * @date 2021/9/9 18:11
 */
public class SubWayMinDistance {


    final static SubWayLine A = new SubWayLine("A");
    final static SubWayLine B = new SubWayLine("B");
    final static SubWayLine C = new SubWayLine("C");
    final static SubWayLine D = new SubWayLine("D");
    final static SubWayLine E = new SubWayLine("E");
    /**
     * 站点有交叉站
     * A,B
     * B,C
     */

    static HashMap<String, String> wayBillRelation = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            A.wayBillSites.put("A站" + i, i);
            B.wayBillSites.put("B站" + i, 100 + i);
            C.wayBillSites.put("C站" + i, 100 + i);
            D.wayBillSites.put("D站" + i, 1000 + i);
            E.wayBillSites.put("E站" + i, 10000 + i);
        }
        wayBillRelation.put("A", "B");
    }


    @Data
    public static class SubWayLine {
        private String id;
        private HashMap<String, Integer> wayBillSites;

        public SubWayLine(String id) {
            this.id = id;
            wayBillSites = new HashMap<>();
        }
    }

}
