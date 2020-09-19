package com.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 计算200000以下的质数之和（减少循环次数）
 *
 * @author xuxiaoling
 * @since 2020-09-19
 */
@Component
public class SumOfPrimes implements ApplicationRunner,Ordered{

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		long startTime = System.currentTimeMillis();
		long sum = 2;
		long sumStep = 0; // 统计迭代次数

	    for (int i = 3; i < 200000; i += 2) {
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
		System.out.println("方案二：考虑减少循环次数");
		System.out.println("200000以下的质数之和为："+sum);
        System.out.println("迭代次数："+sumStep);
        System.out.println("计算耗时(ms)："+(endTime-startTime));
	}
}
