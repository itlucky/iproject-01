package main.com.itlucky.jvm;

/**
 * @Author: itlucky
 * @Date: 2020/5/15 3:17 下午
 * @Description: 并发编程
 * @Version: V1.0
 **/
public class AddSample {

    private volatile Integer count = 0;

    public void add() {
        count++;
    }

    public static void main(String[] args) {
        final AddSample addSample = new AddSample();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    addSample.add();
                }
            }).start();
        }
        while (Thread.activeCount() > 1) {  //让启动的100个线程都结束
            Thread.yield();
        }
        System.out.println(addSample.count);
    }
}
