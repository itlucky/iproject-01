package main.com.itlucky.jvm;

/**
 * @Author: itlucky
 * @Date: 2020/5/14 9:33 下午
 * @Description: 指令重排
 * @Version: V1.0
 **/
public class CpuReOrder {

    private int a = 0;
    private boolean flag = false;
    public void write(){
        System.out.println("write...");
        a = 1; //1
        flag = true;  //2
    }

    public void read(){
        System.out.println("read...");
        if(flag) {   //3
            int i = a*a;  //4
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        final CpuReOrder cpuReOrder = new CpuReOrder();
        //写线程
        new Thread(()->cpuReOrder.write()).start();
        //读线程
        new Thread(()->cpuReOrder.read()).start();
    }
}
