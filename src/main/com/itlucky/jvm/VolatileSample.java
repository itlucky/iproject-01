package main.com.itlucky.jvm;

/**
 * @author hong.jie
 * @version v1.0
 * @date 2020/6/1 21:11
 * @desc volatile关键字
 * @see 。
 *
 *      结果分析：
 * 假设线程A执行write()方法之后,线程B执行read()方法。根据happens before规则,
 * 这个过程建立的happens before关系可以分为两类:
 * 1.根据程序次序规则, 1 happens before 2; 3 happens before 4。
 * 2.根据volatile规则, 2 happens before 3。
 * 3.根据happens before的传递性规则, 1 happens before 4。
 *
 * 注意: 1 happens before 4说明:线程A中a=1, flag=true 对线程B都是可见的。
 *
 */
public class VolatileSample {
    private int a = 0;
    private volatile boolean flag = false;
    public void write(){
        a = 1;                  //1
        flag = true;            //2
    }

    public void read(){
        if(flag){               //3
            int i = a;        //4
            System.out.println("++++i:" + i);
        }
    }

    public static void main(String[] args) {
        final VolatileSample volatileSample = new VolatileSample();

        new Thread(()->volatileSample.write()).start();

        new Thread(()->volatileSample.read()).start();

//        System.out.println(Thread.activeCount());
    }
}
