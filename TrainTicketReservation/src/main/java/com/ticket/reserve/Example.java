package com.ticket.reserve;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Example {

	public static void main(String[] args) {
		Random rand = new Random();
		int randomInt = rand.nextInt(2);
		//System.out.println(randomInt);
		System.out.println(ThreadLocalRandom.current().nextInt(4));

	}

}
