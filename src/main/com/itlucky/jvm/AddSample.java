package main.com.itlucky.jvm;

/**
 * @Author: itlucky
 * @Date: 2020/5/15 3:17 下午
 * @Description: 并发编程  (需要debug执行查看结果，run执行没有结果，不知道为何？)
 *
 * 执行结果count输出的值并不是1000
 * 程序说明：
 * 1.主线程里面启动了100个线程来执行 ；
 * 2.每个线程都对count执行10次++操作；
 * 3.由于每个线程都有可能访问到共享变量，比如说count为10的时候被两个线程读取到，
 *      并且放在各自的缓存中，并且都执行了++操作，结果将count的值变为11。
 *      按理说两个线程都执行了++操作，count的值应该是12，这就是并发编程存在的问题。
 *
 *
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
//        System.out.println(Thread.activeCount());
        while (Thread.activeCount() > 1) {  //让启动的100个线程都结束，线程保证策略
            Thread.yield();  //线程交出cpu执行权
        }
//        System.out.println(Thread.activeCount());
        System.out.println(addSample.count);
    }
}
