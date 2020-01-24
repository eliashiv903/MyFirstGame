package gameClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class GameResultsInformation {
	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";
	private  int numPlayTody;
	private  HashMap<Integer, Integer> arrMove=new HashMap<Integer, Integer>();
	private  HashMap<Integer, Integer> arrMyBest=new HashMap<Integer, Integer>();
	private  HashMap<Integer, ArrayList<Integer[]>> arrAllBest=new HashMap<Integer, ArrayList<Integer[]>>();




	public static void main(String[] args) {
		int id1 = 304939648;  // "dummy existing ID  
		int level = 0;
		GameResultsInformation a=new GameResultsInformation();
		//a.allUsers(id1);
		a.printLog(304939648);
		//a.notSameId();
		int numPlayTody=a.getNumPlayTody();

		HashMap<Integer, Integer> arrMyBest=a.getArrMyBest();
		HashMap<Integer, ArrayList<Integer[]>> arrAllBest=a.getArrAllBest();
		System.out.println(arrAllBest.get(24));
		String numPlay="youPlay:"+numPlayTody+" in the game";
		int b[]= {0,1,3,5,9,11,13,16,19,20,23};
		String arrBest="";
		for (int i = 0; i < b.length; i++) {
			arrBest+="in level:"+b[i]+"- you best score:"+arrMyBest.get(b[i])+".\n";	 
		}
		String arrAll="";
		for (int i = 0; i < b.length; i++) {

			arrAll+="in level:"+b[i]+"- the 10 best score:\n";
			for (int j = 0; j < 10&&j<arrAllBest.get(b[i]).size(); j++) {
				int place=j+1;
				arrAll+="         place"+place+"- id:"+arrAllBest.get(b[i]).get(j)[0]+" score:"+arrAllBest.get(b[i]).get(j)[1]+"\n";
			}

		}
		System.out.println(arrBest);
		//	System.out.println(arrAll+"mmm");
		System.out.println(numPlay+"llllllfffffffffffffffffffffffffffffffff");


		//			String kml = getKML(id1,level);
		//			System.out.println("***** KML file example: ******");
		//			System.out.println(kml);
	}










	public  HashMap<Integer, Integer> getArrMyBest(){
		return arrMyBest;
	}
	public  HashMap<Integer, ArrayList<Integer[]>> getArrAllBest(){
		return arrAllBest;
	}
	public int getNumPlayTody() {
		return numPlayTody;
	}
	public GameResultsInformation(){
		for (int i = 0; i < 24; i++) {
			ArrayList<Integer[]> a=new ArrayList<Integer[]>();
			arrAllBest.put(i, a);
		}
		arrMove.put(2, 2000);
		arrMove.put(4, 2000);
		arrMove.put(6, 2000);
		arrMove.put(7, 2000);
		arrMove.put(8, 2000);
		arrMove.put(10, 2000);
		arrMove.put(12, 2000);
		arrMove.put(14, 2000);
		arrMove.put(15, 2000);
		arrMove.put(17, 2000);
		arrMove.put(18, 2000);
		arrMove.put(21, 2000);
		arrMove.put(22, 2000);
		arrMove.put(0, 290);
		arrMove.put(1, 580);
		arrMove.put(3, 580);
		arrMove.put(5, 500);
		arrMove.put(9, 580);
		arrMove.put(11, 580);
		arrMove.put(13, 580);
		arrMove.put(16, 290);
		arrMove.put(19, 580);
		arrMove.put(20, 290);
		arrMove.put(23, 1140);
	}



	public  void printLog(int id1) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next())
			{
				int ID=resultSet.getInt("UserID");
				int L=resultSet.getInt("levelID");
				int MOVE=resultSet.getInt("moves");
				int SCORE=resultSet.getInt("score");
				//	System.out.println("Id: " + ID+","+L+","+MOVE+","+resultSet.getDate("time")+", "+SCORE);
				if(id1==ID) {
					//System.out.println("jj");
					numPlayTody++;
				}
				theBest(ID,L,MOVE,SCORE);
				if(id1==ID)MyBest(L,MOVE,SCORE);
			}
			resultSet.close();
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * this function returns the KML string as stored in the database (userID, level);
	 * @param id
	 * @param level
	 * @return
	 */
	public static String getKML(int id, int level) {
		String ans = null;
		String allCustomersQuery = "SELECT * FROM Users where userID="+id+";";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			if(resultSet!=null && resultSet.next()) {
				ans = resultSet.getString("kml_"+level);
			}
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}
	public  int allUsers(int id1) {
		int ans = 0;
		String allCustomersQuery = "SELECT * FROM Users;";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next()) {
				int ID=resultSet.getInt("UserID");
				int L=resultSet.getInt("levelID");
				int MOVE=resultSet.getInt("moves");
				int SCORE=resultSet.getInt("score");
				//System.out.println("Id: " + ID+","+L+","+MOVE+","+resultSet.getDate("time")+", "+SCORE);
				if(id1==ID)numPlayTody++;
				theBest(ID,L,MOVE,SCORE);
				if(id1==ID)MyBest(L,MOVE,SCORE);
				ans++;
			}
			resultSet.close();
			statement.close();		
			connection.close();
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}
	private void theBest(int iD, int l, int mOVE, int sCORE) {
		if(l>=0&&l<=23&&iD>1000000) {
			if(mOVE>arrMove.get(l) )return;
			for (int i = 0; i < 10  ; i++) {
				if(arrAllBest.get(l).size()<=i ||sCORE>arrAllBest.get(l).get(i)[1]) {
					Integer b[]=new Integer[3];
					b[0]=iD;
					b[1]=sCORE;
					b[2]=mOVE;
					arrAllBest.get(l).add(i, b);
					for(int j=i+1;j<10&&j<arrAllBest.get(l).size();j++)
						if(arrAllBest.get(l).get(j)[0]==iD)arrAllBest.get(l).remove(j);
					break;
				}
				else if(iD==arrAllBest.get(l).get(i)[0])break;;
			
			}
		}

	}

	private void MyBest(int l, int mOVE, int sCORE) {
		if(l<0&&l>23) return;
		if(mOVE<=arrMove.get(l) && (arrMyBest.get(l)==null||arrMyBest.get(l)<sCORE))
			arrMyBest.put(l, sCORE);
	}
}

