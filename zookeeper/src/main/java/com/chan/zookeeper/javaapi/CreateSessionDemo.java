package com.chan.zookeeper.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ChenMP on 2017/12/28.
 */
public class CreateSessionDemo {
    private final static String CONNECTSTRING= "192.168.182.130:2181,192.168.182.131:2181,192.168.182.132:2181,192.168.182.133:2181";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("watchdEvend ->" + watchedEvent.getState());
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        System.out.println("end ->" + zooKeeper.getState());
    }
}
