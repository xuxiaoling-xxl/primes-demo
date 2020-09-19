package com.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * 计算200000以下的质数之和（高效）
 *
 * @author xuxiaoling
 * @since 2020-09-19
 */
@SpringBootTest
class QuickPrimesTest {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
        final int cpuCoreNum = 4;
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
 
        MyTask task1 = new MyTask(3, 80000);
        MyTask task2 = new MyTask(80001, 130000);
        MyTask task3 = new MyTask(130001, 170000);
        MyTask task4 = new MyTask(170001, 200000);
        
        Future<Long> f1 = service.submit(task1);
        Future<Long> f2 = service.submit(task2);
        Future<Long> f3 = service.submit(task3);
        Future<Long> f4 = service.submit(task4);
        
        Long sum = 2+f1.get() +f2.get()+f3.get()+f4.get();
        long endTime = System.currentTimeMillis();
        System.out.println("200000以下的质数之和为："+sum);
        System.out.println("并行计算耗时(ms)："+(endTime-startTime));
    }
 
    static class MyTask implements Callable<Long>{
        int startPos,endPos;
 
        public MyTask(int startPos, int endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }
 
        @Override
        public Long call() throws Exception {
        	Long list = getSumOfPrime(startPos, endPos);
            return list;
        }
    }
 
    //计算质数之和
    static Long getSumOfPrime(int start,int end){
    	long sum = 0;
        for (int i = start; i < end; i += 2) {
	        boolean flag = true;    
	        for (int j = 3; j <= (int)Math.sqrt((double)i); j += 2) {
	            if (i%j == 0) { 
	                flag = false;
	                break;
	            }
	        }
	        if (flag) sum += i;
	    }
        return sum;
    }

}
