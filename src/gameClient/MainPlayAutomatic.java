package gameClient;

public class MainPlayAutomatic {
	
	public static void main(String[] args) {
		WindowMange window = new WindowMange();
		window.setVisible(true);
		while(window.getId()==-1) {
			System.out.println();;
		}
		int numGraph=window.getLevelPlay();
		if((numGraph>=0 &&numGraph<=10)||numGraph==12||numGraph==15||numGraph==18||numGraph==21) {
			AutomaticPlay play1 =new AutomaticPlay();
			play1.test1(window);
		}
		else if(numGraph==11||numGraph==17||numGraph==20||numGraph==23||numGraph==14) {
			AutomaticPlay3 play3 =new AutomaticPlay3();
			play3.test3(window);
		}
		else {
			AutomaticPlay2 play2 =new AutomaticPlay2();
			play2.test2(window);
		}
	}

}
