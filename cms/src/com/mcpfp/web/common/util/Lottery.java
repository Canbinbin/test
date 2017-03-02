package com.mcpfp.web.common.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Lottery {
	
	private static final Logger logger = LoggerFactory.getLogger(Lottery.class);
	
	public Lottery() {
	}
	
	/**
	 * 抽奖并返回奖项
	 * 
	 * @param Object[][position【位置】,award【奖项ID】,rate【中奖率】] 
	 * @return Object[位置,奖项ID] 获得奖项
	 */
	public Object[] getAward(Object[][] awardArray) {
		Integer[] obj = new Integer[awardArray.length]; // 概率数组
		for (int i = 0; i < awardArray.length; i++) {
			obj[i] = (Integer) awardArray[i][2];
		}
		Integer awardId = getRand(obj); // 根据概率获取奖项id
		if (awardId == null) {
			return new Object[] { "-1", "-1", "" };
		}
		String position = awardArray[awardId][0].toString();// 转盘所在的位置
		String prizeId = awardArray[awardId][1].toString();// 奖项ID
		return new Object[] { position, prizeId };
	}
	
	
	/**
	 * 根据概率获取奖项
	 * 
	 * @param rates 概率数组
	 * @param sumRate 总概率
	 * @return 获得奖项
	 */
	public Integer getRand(Integer[] rates) {
		Integer result = null;
		try {
			int sum = 0; // 概率数组的总概率精度
			for (int i = 0; i < rates.length; i++) {
				sum += rates[i];
			}
			for (int i = 0; i < rates.length; i++) { // 概率数组循环
				int randomNum = new Random().nextInt(sum); // 随机生成[0,sum)的整数
				if (randomNum < rates[i]) {// 中奖
					result = i;
					break;
				} else {
					sum -= rates[i];
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		//奖品概率数组
		Object[][] originAwardArray = new  Object[][]{ 
			    {1, "2",  100},
				{2, "3",  30},
				{3, "4",  10},
				{4, "5",  5},
				{5, "6",  5},
				{6, "7",  5},
				{7, "8",  10},
				{8, "9",  10},
				{-1,"-1",825}
		};
		
		int rate = 1000;
		for (int i = 0; i < originAwardArray.length; i++) {
			rate -= Integer.parseInt(originAwardArray[i][2].toString());
		}
		

		Lottery lottery = new Lottery();
		
		int count = 0;
		for (int i = 0; i < 10000; i++) {
			Object result[] = lottery.getAward(originAwardArray);//抽奖后返回奖品ID
			if(Integer.parseInt(result[1].toString()) > 0){
				count ++;
			}
			System.out.println("position:" + result[0] + "，prizeId:" +  result[1]);
		}
		System.out.println("获奖数：" + count);
	}
}