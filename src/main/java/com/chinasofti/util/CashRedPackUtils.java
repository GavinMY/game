package com.chinasofti.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 红包分发工具类
 *
 */
public class CashRedPackUtils {
	/**
	 * 一个红包最少拆分数量
	 */
	public static final int REDPACK_MIN_QUANTITY = 1;
	/**
	 * 一个红包最多拆分数量
	 */
	public static final int REDPACK_MAX_QUANTITY = 100;
	/**
	 * 小数位长度
	 */
	private static final int SCALE = 2;
	/**
	 * 舍弃的小数位处理方式
	 */
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
	/**
	 * 红包放大倍数
	 */
	private static final BigDecimal TIMES = new BigDecimal("3");
	/**
	 * 单个红包最小金额
	 */
	public static final BigDecimal SINGLE_RED_MIN_MONEY = new BigDecimal("0.01");
	/**
	 * 单个红包最大金额
	 */
//	 private static final BigDecimal SINGLE_RED_MAX_MONEY = new
//	 BigDecimal("10");
	/**
	 * 递归计算红包金额时连续错误最大值，超过此次数将返回最小值
	 */
	private static final int ERROR_MAX_NUM = 5;
	/**
	 * 递归计算红包金额连续错误次数初始值
	 */
	private static final int ERROR_INIT_NUM = 1;
	/**
	 * 计算金额时的小数位，1000代表3位小数
	 */
	private static final int FRACTION_LENGTH = 1000;

	/**
	 * 分发红包
	 * 
	 * @param redMoney
	 *            红包总金额
	 * @param num
	 *            红包数量
	 * @return
	 */
	public static List<BigDecimal> SplitRedPackes(BigDecimal redMoney, int num) {
		List<BigDecimal> redInfoList = new ArrayList<BigDecimal>();
		// 红包有误
		if (num < REDPACK_MIN_QUANTITY || num > REDPACK_MAX_QUANTITY)
			return redInfoList;

		// 校验：金额大于0
		if (redMoney.compareTo(BigDecimal.ZERO) != 1) {
			return redInfoList;
		}

		if (num <= 1) {
			redInfoList.add(redMoney);
			return redInfoList;
		}
		Random random = new Random();
		for (int i = 0; i < num; i++) {
			// System.out.println("\n" + (i+1) + "个红包信息：");
			int surplusNum = num - i;// 未分配金额红包数量
			BigDecimal curRedMoney = fightLuckRedPacked(redMoney, surplusNum, random, ERROR_INIT_NUM);
			redInfoList.add(curRedMoney);
			redMoney = redMoney.subtract(curRedMoney);
			// MandoAssert.notTrue(redMoney.compareTo(BigDecimal.ZERO) == -1,
			// "红包金额有误");
			// System.out.println("红包金额：" + curRedMoney + ",剩余：" + redMoney);
		}
		return redInfoList;
	}

	/**
	 * 拼手气红包
	 * 
	 * @param redMoney
	 *            红包金额
	 * @param num
	 *            红包数量
	 * @param random
	 *            随机数生成对象
	 * @param errorNum
	 *            错误测试
	 * @return 单个红包金额
	 */
	private static BigDecimal fightLuckRedPacked(BigDecimal redMoney, int num, Random random, int errorNum) {
		System.out.println("redMoney:"+redMoney);
		System.out.println("num:"+num);		
		System.out.println("errorNum:"+errorNum);
		if (num <= 1) {
			return redMoney;
		}
		if (errorNum > ERROR_MAX_NUM) {
			// 随机金额产生错误次数超过上限，返回最小值
			return SINGLE_RED_MIN_MONEY;
		}
		// 每个红包最大金额 = 剩余总金额 / 未分配金额红包数量 * 红包放大倍数
		int avgRedMaxMoney = redMoney.divide(new BigDecimal(num),2, ROUNDING_MODE).multiply(TIMES).intValue()* FRACTION_LENGTH;
				
		System.out.println("每个红包最大金额 "+avgRedMaxMoney);
		
		
		BigDecimal curRedMoney = new BigDecimal(random.nextInt(avgRedMaxMoney) * 1.00 / FRACTION_LENGTH + "")
				.setScale(SCALE, ROUNDING_MODE);
		
		
		
		if (curRedMoney.compareTo(SINGLE_RED_MIN_MONEY) == -1) {
			// 红包最小值判断：小于最小红包金额，重新计算
			return fightLuckRedPacked(redMoney, num, random, ++errorNum);
		}
		
//		  if(curRedMoney.compareTo(SINGLE_RED_MAX_MONEY) == 1) { //红包最大值判断
//		  return fightLuckRedPacked(curRedMoney, num, random, ++errorNum); }
//		 

		// 最少保留红包金额
		BigDecimal surplusMinRedMoney = SINGLE_RED_MIN_MONEY.multiply(new BigDecimal(num - 1));
		// 除当前红包剩余金额
		BigDecimal surplusRedMoney = redMoney.subtract(curRedMoney);
		if (surplusMinRedMoney.compareTo(surplusRedMoney) == 1) {
			return fightLuckRedPacked(redMoney, num, random, ++errorNum);
		}
		return curRedMoney;
	}

	public static void main(String[] args) {

		
//		BigDecimal a=new BigDecimal("3");
//		System.out.println(a.divide(new BigDecimal(10), RoundingMode.HALF_EVEN));
//		System.out.println(a.divide(new BigDecimal(10),2, RoundingMode.HALF_EVEN));
		System.out.println(SplitRedPackes(new BigDecimal("3"),10));
		
	}

}
