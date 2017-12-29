package com.chan.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by ChenMP on 2017/12/29.
 */
public class CuratorClientUtils {
    private final static String CONNECTSTRING= "192.168.182.130:2181,192.168.182.131:2181,192.168.182.132:2181,192.168.182.133:2181";
    private static CuratorFramework curatorFramework;

    public static CuratorFramework getInstance() {
        curatorFramework= CuratorFrameworkFactory.
                newClient(CONNECTSTRING,5000,5000,
                        new ExponentialBackoffRetry(1000,3));//重试策略
        curatorFramework.start(); //start方法启动连接
        return curatorFramework;
    }
}
