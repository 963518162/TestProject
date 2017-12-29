package com.chan.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by ChenMP on 2017/12/29.
 * 这是curator 3.0以下的处理方式
 */
public class CuratorCreateSessionDemo {
    private final static String CONNECTSTRING= "192.168.182.130:2181,192.168.182.131:2181,192.168.182.132:2181,192.168.182.133:2181";

    public static void main(String[] args) {
        //创建会话的两种方式 normal
        CuratorFramework curatorFramework= CuratorFrameworkFactory.
                newClient(CONNECTSTRING,5000,5000,
                        new ExponentialBackoffRetry(1000,3));//重试策略
        curatorFramework.start(); //start方法启动连接

        //fluent风格
        CuratorFramework curatorFramework1=CuratorFrameworkFactory.builder().connectString(CONNECTSTRING).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();

        curatorFramework1.start();
        System.out.println("success");
    }
}
