package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.AlgoGameRooboteStart;

class AlgoGameRooboteStartTest {

	@SuppressWarnings("deprecation")
	@Test

	void testLevel17() {
		game_service game = Game_Server.getServer(17);
		AlgoGameRooboteStart a=new AlgoGameRooboteStart(game, 17);
		assertEquals(a.getEdgedataMaxVal()[0].getSrc(), 12);
		assertEquals(a.getEdgedataMaxVal()[1].getSrc(), 16);
		assertEquals(a.getEdgedataMaxVal()[2].getSrc(), 10);
	}


	@SuppressWarnings("deprecation")
	@Test

	void testLevel0() {
		game_service game = Game_Server.getServer(0);
		AlgoGameRooboteStart a=new AlgoGameRooboteStart(game, 0);
		assertEquals(a.getEdgedataMaxVal()[0].getSrc(), 9);
		assertEquals(a.getEdgedataMaxVal()[0].getDest(), 8);
	}

	@SuppressWarnings("deprecation")
	@Test

	void testLevel22() {
		game_service game = Game_Server.getServer(22);
		AlgoGameRooboteStart a=new AlgoGameRooboteStart(game, 22);
		assertEquals(a.getEdgedataMaxVal()[0].getSrc(), 14);
		assertEquals(a.getEdgedataMaxVal()[0].getDest(), 38);
		assertEquals(a.getEdgedataMaxVal()[1].getSrc(), 12);
		assertEquals(a.getEdgedataMaxVal()[1].getDest(), 3);
	}
}
