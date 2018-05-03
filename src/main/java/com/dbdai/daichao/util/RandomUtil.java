package com.dbdai.daichao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: RandomUtil
 * @Description: 随机方法类
 * @author Cloud
 * @date 2018年3月22日
 */
public class RandomUtil {
	public static void shuffle(int[] array, Random random) {
		for (int i = array.length; i >= 1; i--) {
			swap(array, i - 1, random.nextInt(i));
		}
	}

	public static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static List<Integer> randomNum(int[] array) {
		List<Integer> num = new ArrayList<>();
		shuffle(array, new Random());
		for (int i = 0; i < array.length; i++) {
			num.add(array[i]);
		}
		return num;
	}

}
