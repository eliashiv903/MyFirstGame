package gameClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Gets information about the games and the results and arranges the, 
 * information that can be displayed in a user-friendly way.
 * I have built two functions that conveniently arrange useful information.
 */

public class GameResultsInformation {
	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";
	private  int numPlayTody;
	private  HashMap<Integer, Integer> arrMove=new HashMap<Integer, Integer>();
	private  HashMap<Integer, Integer> arrMyBest=new HashMap<Integer, Integer>();
	private  HashMap<Integer, ArrayList<Integer[]>> arrAllBest=new HashMap<Integer, ArrayList<Integer[]>>();


	public  HashMap<Integer, Integer> getArrMyBest(){
		return arrMyBest;
	}
	public  HashMap<Integer, ArrayList<Integer[]>> getArrAllBest(){
		return arrAllBest;
	}
	public int getNumPlayTody() {
		return numPlayTody;
	}

	/**
	 *A constructor that initializes the lists and builds a list according to the steps of limiting steps,
	 * in unlimited graphs I decided to keep information of less than 2000 steps.
	 * Every time I get given one game and I sort the data
	 */
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
	/**
	Builds a list of 350 people with the best result in HashMap
	 * According to the levels that everyone has a list of high results (350)
	 */

	private void theBest(int iD, int l, int mOVE, int sCORE) {
		if(l>=0&&l<=23&&iD>99999999) {
			if(mOVE>arrMove.get(l) )return;
			for (int i = 0; i < 350  ; i++) {
				if(arrAllBest.get(l).size()<=i ||sCORE>arrAllBest.get(l).get(i)[1]) {
					Integer b[]=new Integer[3];
					b[0]=iD;
					b[1]=sCORE;
					b[2]=mOVE;
					arrAllBest.get(l).add(i, b);
					for(int j=i+1;j<350&&j<arrAllBest.get(l).size();j++)
						if(arrAllBest.get(l).get(j)[0]==iD)arrAllBest.get(l).remove(j);
					break;
				}
				else if(iD==arrAllBest.get(l).get(i)[0])break;;

			}
		}

	}

	//Finds the best result for the ID at the required stage
	private void MyBest(int l, int mOVE, int sCORE) {
		if(l<0&&l>23) return;
		if(mOVE<=arrMove.get(l) && (arrMyBest.get(l)==null||arrMyBest.get(l)<sCORE))
			arrMyBest.put(l, sCORE);
	}


	/** simply prints part the games as played by the users (in the database).
	 * 
	 */
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
				if(id1==ID&&id1>99999999)MyBest(L,MOVE,SCORE);
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
	/** simply prints all the games as played by the users (in the database).
	 * 
	 */
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
				if(id1==ID&&id1>99999999)MyBest(L,MOVE,SCORE);
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

}

