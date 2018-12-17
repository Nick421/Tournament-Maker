import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
/* ---------------------------------------------
 * Cases to fix
 * 1) error check when entering winning team
 * 2) teams not power of 2? do what?
 ----------------------------------------------*/

/* ---------------------------------------------
 * Alternative better implementation
 * https://www.clear.rice.edu/comp212/02-spring/projects/tournament/
 * we could figure out how many rounds and the tree level is always n-1
 * make a tree with unknown team nodes with the correct level
 * recurse down to leaf and insert teams randomly.
 * play the round
 * replace the unknown root with the winner
 ----------------------------------------------*/

public class TournamentMaker {
	
	public static ArrayList<Team> loadTeams(String fileName) throws FileNotFoundException {
		int teamCount = 0;
		ArrayList<Team> teams = new ArrayList<Team>();
		Scanner scan = new Scanner(new File(fileName));
		while(scan.hasNextLine()){
			
		    String line = scan.nextLine();
		    // put in ArrayList<Team> 
		    Team newTeam = new Team(line);
		    teams.add(newTeam);
		    teamCount++;
		      
		}
	
		scan.close();
		if(teamCount%2 != 0){
			System.out.println("Number of teams is not power of 2, some teams might be left out!");
		}
		return teams;
	}
	public static ArrayList<Match> makeRound(ArrayList<Team> list){
		/*
		 * select 2 random teams from List
		 * pair them to play(might use the match class object)
		 * remove them from list
		 * loop again, make sure to check null
		 * probably randomize by array index, only need in first round
		 * should take in a list of teams and maybe round number?
		 * if we have a list of matches, could pick winner teams
		 * then we get a list of winning teams
		 */
		ArrayList<Match> matches = new ArrayList<Match>();
		while(!list.isEmpty()){
			Team team1 = null;
			Team team2 = null;
			
			while(team1 == team2 ){
				
				team1 = getRandomItem(list);
				team2 = getRandomItem(list);
			}
			list.remove(team1);
			list.remove(team2);
			
			Match m = new Match(team1, team2);
			matches.add(m);
		}
		return matches;
	}
	public static ArrayList<Match> nextRound(ArrayList<Match> matches){
		/*
		 * we have a list of winning teams, no need to randomize anymore
		 * if we assumed the list of winning teams is ordered from left to right
		 * we can just pair up wining team 1 & 2, 3 & 4,..... so on
		 * repeat till 1 team left. Winner of tournament.
		 */
		ArrayList<Match> nextRound = new ArrayList<Match>();
		int i = 0;
		while(!matches.isEmpty()){
			Team team1 = null;
			Team team2 = null;
	
			if(i != matches.size()){
				team1 = matches.get(i).getWinningTeam();
				team2 = matches.get(i+1).getWinningTeam();
				matches.remove(team1);
				matches.remove(team2);
			}else{
				break;
			}

			Match m = new Match(team1, team2);
			nextRound.add(m);
			i = i+2;
		}
		return nextRound;
		
	}
	public static void play(ArrayList<Match> m){
		for(int i = 0; i < m.size(); i++){
			m.get(i).enterWinningTeam();
		}
	}
	public static Object roundHelper(ArrayList<Match> r){
		ArrayList<Match> nextRound = nextRound(r);
		printRound(nextRound);
		play(nextRound);
		if(nextRound.get(0).getWinningTeam() != null && nextRound.size() == 1){
			endTour(nextRound.get(0).getWinningTeam());
			return null;
		}
		return roundHelper(nextRound);
	}
	public static void endTour(Team t){
		System.out.println("The winner of this tournament is: "+ t.getName() + "!");
	}
	
	public static void printRound(ArrayList<Match> m){
		for(Match x: m){
			System.out.println(x.matchName());
		}
	}
	
	private static <T> T getRandomItem(List<T> list)
	{
	    Random random = new Random();
	    int listSize = list.size();
	    int randomIndex = random.nextInt(listSize);
	    return list.get(randomIndex);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//@param args pass in txt file
		String fileName = args[0];
		ArrayList<Team> teams = null;
		try {
			teams = loadTeams(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Initialising teams from txt file......");
		
		if(teams != null && teams.size() != 2){
			
			ArrayList<Match> round1 = makeRound(teams);
			printRound(round1);
			play(round1);
			roundHelper(round1);
	
		}
		else if(teams.size() == 2){
			ArrayList<Match> round1 = makeRound(teams);
			printRound(round1);
			play(round1);
			endTour(round1.get(0).getWinningTeam());
		}else{
			System.out.println("No teams!");
		}
	}

}
