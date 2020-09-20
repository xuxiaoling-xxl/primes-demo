package com.demo;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 计算质数之和
 *
 * @author xuxiaoling
 * @since 2020-09-19
 */
@Component
class SumOfPrimes implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入质数求和范围：");
		int inputNum = sc.nextInt();
		planA(inputNum);
		planB(inputNum);
	}
	
	//方案一：减少循环
	public void planA(int inputNum) throws Exception {
		long startTime = System.currentTimeMillis();
		long sum = 2;
		long sumStep = 0; // 统计迭代次数

	    for (int i = 3; i < inputNum; i += 2) {
	        boolean flag = true;    
	        for (int j = 3; j <= (int)Math.sqrt((double)i); j += 2) {
	            sumStep += 1;
	            if (i % j == 0) { 
	                flag = false;
	                break;
	            }
	        }
	        if (flag) sum += i;
	    }
		long endTime = System.currentTimeMillis();
		System.out.println("方案一：减少循环次数");
		System.out.println(inputNum+"以下的质数之和为："+sum);
        System.out.println("迭代次数："+sumStep);
        System.out.println("计算耗时(ms)："+(endTime-startTime));
	}
	
	//方案二：提高速率
	public void planB(int inputNum) throws Exception {
		long startTime = System.currentTimeMillis();
		int cpuCoreNum = 4;
        int number = inputNum/cpuCoreNum;
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
        
        MyTask task1 = new MyTask(3, number);
        MyTask task2 = new MyTask(number+1, number*2);
        MyTask task3 = new MyTask(number*2+1, number*3);
        MyTask task4 = new MyTask(number*3+1, inputNum);
        
        Future<Long> f1 = service.submit(task1);
        Future<Long> f2 = service.submit(task2);
        Future<Long> f3 = service.submit(task3);
        Future<Long> f4 = service.submit(task4);
        
        Long sum = 2+f1.get() +f2.get()+f3.get()+f4.get();
        long endTime = System.currentTimeMillis();
        System.out.println("方案二：求和范围高的情况下提高计算速率");
        System.out.println(inputNum+"以下的质数之和为："+sum);
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
