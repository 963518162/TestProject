package com.chan.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ChenMP on 2017/12/29.
 */
public class CuratorOperatorDemo {
    public static void main(String[] args) throws InterruptedException {
        CuratorFramework curatorFramework = CuratorClientUtils.getInstance();
        System.out.println("连接成功！");

        //创建节点
        try {
            String result = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                    forPath("/curator/curator1-1", "123".getBytes());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查询节点
        try {
            Stat stat = new Stat();
            byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/curator/curator1-1");
            System.out.println("data -> " + new String(bytes) + "&&stat -> " + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //修改节点
        try {
            Stat stat = curatorFramework.setData().forPath("/curator/curator1-1", "321".getBytes());
            System.out.println("stat -> " + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //删除节点
        try {
            Void result = curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //异步操作
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                    inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                            System.out.println(Thread.currentThread().getName()+"->"+"resultCode:"+event.getResultCode()+"->"+event.getType());
                            countDownLatch.countDown();
                        }
                    },executorService).forPath("/curator/curator1-2","123".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await();
        executorService.shutdown();

        //事务操作 curator独有的



    }
}
