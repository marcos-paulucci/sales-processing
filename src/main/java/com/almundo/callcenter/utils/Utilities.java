package com.almundo.callcenter.utils;

import java.util.Random;

/**
 * Created by Marcos on 12/1/2017.
 */
public class Utilities {

	public static int getRandomCallTime(){
		Configuration cfg = new Configuration();
		Random r = new Random();
		int Low = cfg.getMinRandCallTime();
		int High = cfg.getMaxRandCallTime();
		int result = r.nextInt(High-Low) + Low;
		return result;
	}
}
