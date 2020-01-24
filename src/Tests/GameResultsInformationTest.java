package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.GameResultsInformation;

/**
 * In this test I check if the information is good from the server,
 *  there may be errors in the server jamming status
 * 
 */
class GameResultsInformationTest {

	@SuppressWarnings("deprecation")
	@Test
	void testNumGamePlay() { 
		GameResultsInformation a=new GameResultsInformation();
		a.printLog(304939648);
		//a.allUsers(id2);
		int playTody=a.getNumPlayTody();
		assertEquals(playTody>0, true);
	}
	

	@SuppressWarnings("deprecation")
	@Test

	void testMyBest() {
		GameResultsInformation a=new GameResultsInformation();
		a.printLog(304939648);
		//a.allUsers(id2);

		HashMap<Integer, Integer> arrMyBest=a.getArrMyBest();
		assertEquals(arrMyBest.size()>5, true);
		assertEquals(arrMyBest.get(0), 147);
		
	}

	

	@SuppressWarnings("deprecation")
	@Test

	void testAllBest() {
		GameResultsInformation a=new GameResultsInformation();
		a.printLog(304939648);
		//a.allUsers(id2);

		HashMap<Integer, ArrayList<Integer[]>> arrAllBest=a.getArrAllBest();
		int count =0;
		for (int i = 0; i < arrAllBest.get(0).size(); i++) {
			if(arrAllBest.get(0).get(i)[0]==304939648) count++;
		}
		assertEquals(count, 1);
		assertEquals(arrAllBest.get(3).get(5)[2]<581, true);
		assertEquals(arrAllBest.get(11).get(5)[2]<581, true);
		assertEquals(arrAllBest.get(5).get(5)[2]<500, true);
	}
	
}
