import java.util.*;
public class Match {
	private Team t1;
	private Team t2;
	private Team winningTeam;

	public Match(Team t1, Team t2){
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public Team getWinningTeam(){
		return this.winningTeam;
	}
	
	public String matchName(){
		
		return t1.getName() +" vs. "+ t2.getName();
	}
	
	public void enterWinningTeam(){
		
		
		System.out.println("Please enter the winning team.");
		System.out.println("Enter L for "+t1.getName());
		System.out.println("Enter R for "+t2.getName());
		
		while(true){
			Scanner scan = new Scanner(System.in);
			String response = scan.next();
			if(response.equals("L")){
				this.winningTeam = t1;
				break;
			}else if(response.equals("R")){
				this.winningTeam = t2;
				break;
			}else{
				//some bugs here with error caching
				System.out.println("error");
				System.out.println("Please enter L or R.");
			}
			scan.close();
		}
		
	}
	
	
}
