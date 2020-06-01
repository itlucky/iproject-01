package main.com.itlucky.jvm;

/**
 * @Author: itlucky
 * @Date: 2020/5/14 9:33 下午
 * @Description: 指令重排
 *          如果是多线程执行，这里出现的最终结果是i=0。
 *          下面程序其实还是单线程执行的。
 *
 *          也就是说指令重排之后的执行顺序可能是这样的：2 -> 3 -> 4  -> 1
 *
 *          编译器和处理器可能会对操作做重排序。编译器和处理器在重排序时,会遵守数据依赖性,
 *      编译器和处理器不会改变存在数据依赖关系的两个操作的执行顺序。
 *          注意,这里所说的数据依赖性仅针对单个处理器中执行的指令序列和单个线程中执行的操作，
 *      不同处理器之间和不同线程之间的数据依赖性不被编译器和处理器考
 * 虑。
 * @Version: V1.0
 **/
public class CpuReOrder {

    private int a = 0;
    private boolean flag = false;
    public void write(){
        System.out.println("++++write...");
        a = 1; //1
        flag = true;  //2
    }

    public void read(){
        System.out.println("++++read...");
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
