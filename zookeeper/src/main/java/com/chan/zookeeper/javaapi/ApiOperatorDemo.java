package com.chan.zookeeper.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChenMP on 2017/12/28.
 *
 * TODO 存疑：Event.EventType.NodeChildrenChanged不生效
 */
public class ApiOperatorDemo implements Watcher {
    private final static String CONNECTSTRING= "192.168.182.130:2181,192.168.182.131:2181,192.168.182.132:2181,192.168.182.133:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat(); //数据节点信息

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(CONNECTSTRING, 5000, new ApiOperatorDemo());
        countDownLatch.await();
        System.out.println("zooKeeper.getState() ->" + zooKeeper.getState());

        String result = zooKeeper.create("/chen", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//ALC  节点属性
        System.out.println("创建成功 ->" + result + ":" + new String(zooKeeper.getData("/chen",true,stat)));//设置监听

        zooKeeper.setData("/chen","321".getBytes(),-1);
        TimeUnit.SECONDS.sleep(2); //watch是异步操作

        Stat exists = zooKeeper.exists("/chen/node1", true);
        if (null == exists) {//表示节点不存在
            zooKeeper.create("/chen/node1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(2);
        }

        zooKeeper.setData("/chen/node1","321".getBytes(),-1);
        TimeUnit.SECONDS.sleep(2);

        //获取节点下的子节点
        List<String> children = zooKeeper.getChildren("/chen", true);
    }

    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                System.out.println("event.getState() ->" + event.getState());
                countDownLatch.countDown();
            }else if (event.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println("NodeDataChanged -> event.getPath():" + event.getPath() + "|getData():"+ new String(zooKeeper.getData(event.getPath(),true,stat)));
                } catch (KeeperException|InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("NodeChildrenChanged -> event.getPath():" + event.getPath() + "|getData():"+ new String(zooKeeper.getData(event.getPath(),true,stat)));
                } catch (KeeperException|InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (event.getType() == Event.EventType.NodeCreated) {
                try {
                    System.out.println("NodeCreated -> event.getPath():" + event.getPath() + "|getData():"+ new String(zooKeeper.getData(event.getPath(),true,stat)));
                } catch (KeeperException|InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (event.getType() == Event.EventType.NodeDeleted) {

            }
        }
    }
}
