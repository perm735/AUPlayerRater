/**
 * AU team totals
 */

package com.seventhirtyfive;

public class AUTeam {
	private String mTeamName = "Default";
	private AUBatterStats [] mBatterStats;
	private AUPitcherStats [] mPitcherStats;
	private int mCurrentYear = AUPlayer.YEAR_2009;

	AUTeam(String name) {
		mTeamName = name;
		mBatterStats = new AUBatterStats[AUPlayer.YEAR_ALL];
		for (int i = 0; i < AUPlayer.YEAR_ALL; i++) {
			mBatterStats[i] = new AUBatterStats();
		}
		mPitcherStats = new AUPitcherStats[AUPlayer.YEAR_ALL];
		for (int i = 0; i < AUPlayer.YEAR_ALL; i++) {
			mPitcherStats[i] = new AUPitcherStats();
		}
	}
	
	public String getName()						{	return mTeamName;					}
	public AUBatterStats getBatterStats()		{	return mBatterStats[mCurrentYear];	}
	public AUPitcherStats getPitcherStats()		{	return mPitcherStats[mCurrentYear];	}
	
    public static final String getBatterHeader() {
        return String.format("%-15.15s \t %-25.25s \t %-4.4s \t %-4.4s \t %3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s \t %-3.3s", 
                "Team", "", "","Team", "avg", "Runs", "HR", "RBI", "BB", "SB", "OPS");
    }
    
    public String getBatterLine() {
    	AUBatterStats stats = mBatterStats[mCurrentYear];
        return String.format("%-15.15s \t %-25.25s \t %-4.4s \t %-4.4s \t %3.4f \t %-4.3s \t %-3.3s \t %-4.3s \t %-4.3s \t %-3.3s \t %-3.4f", 
                mTeamName, "", "",/*"Team"*/"", stats.getAvg(), stats.getRuns(), stats.getHr(), stats.getRbi(), 
                stats.getBb(), stats.getSb(), stats.getOps());
    }

    public static final String getPitcherHeader() {
        return String.format("%-15.15s \t %-25.25s \t %-4.4s \t %-4.4s \t %3.4s \t %-3.6s \t %-3.6s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4s", 
                "Team", "", "","Team", "Inn", "Wins", "Losses", "Saves", "K", "BB", "ERA", "Whip");
    }
    public String getPitcherLine() {
    	AUPitcherStats stats = mPitcherStats[mCurrentYear];
    	return String.format("%-15.15s \t %-25.25s \t %-4.4s \t %-4.4s \t %3.1f \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4s \t %-3.4f \t %-3.4f", 
                mTeamName, "", "",/*Team*/"", stats.getInnings(), stats.getWins(), stats.getLosses(), 
                stats.getSaves(), stats.getStrikeouts(), stats.getWalks(), stats.getEra(), stats.getWhip());
    }
}
