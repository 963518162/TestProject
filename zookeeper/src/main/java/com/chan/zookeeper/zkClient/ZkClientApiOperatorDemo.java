package com.chan.zookeeper.zkClient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by ChenMP on 2017/12/28.
 */
public class ZkClientApiOperatorDemo {
    private final static String CONNECTSTRING= "192.168.182.130:2181,192.168.182.131:2181,192.168.182.132:2181,192.168.182.133:2181";

    private static ZkClient getInstance() {
        return new ZkClient(CONNECTSTRING,4000);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = getInstance();

        zkClient.createPersistent("/zkClient/childNode",true);
        System.out.println("success");

        zkClient.subscribeDataChanges("/zkClient", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath + "->" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });
        zkClient.writeData("/zkClient","123");
        TimeUnit.SECONDS.sleep(1);
    }
}
