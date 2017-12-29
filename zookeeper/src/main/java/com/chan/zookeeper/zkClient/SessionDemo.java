package com.chan.zookeeper.zkClient;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by ChenMP on 2017/12/28.
 */
public class SessionDemo {
    private final static String CONNECTSTRING= "192.168.182.130:2181,192.168.182.131:2181,192.168.182.132:2181,192.168.182.133:2181";

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(CONNECTSTRING,4000);

        System.out.println(zkClient + " -> success");
    }
}
