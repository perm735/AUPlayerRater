package com.seventhirtyfive;

/**
 * 
 * @author mschassberger
 * 
 * Factory object for managing team objects.  This object is responsible for managing team objects and
 * ensuring that the team is valid
 */
public class AUMlbTeamFactory
{
	private static final int mMlbTeams = 30;
	private static AUMlbTeam mTeams[];
	
	public AUMlbTeamFactory() {
	}
	/**
	 * 
	 * @param isShortName Is the name provide the short name?  ie. SDG
	 * @param name
	 * @return null on Failure, AUMlbTeam if found
	 */
	public static AUMlbTeam getTeam( boolean isShortName, String name )
	{
	    if (mTeams == null) {
	        mTeams = new AUMlbTeam[mMlbTeams];

	        // NL West
	        mTeams[0] = new AUMlbTeam("San Diego", "SD", true );
	        mTeams[1] = new AUMlbTeam("San Francisco", "SF", true );
	        mTeams[2] = new AUMlbTeam("Colorado", "COL", true );
	        mTeams[3] = new AUMlbTeam("Arizona", "ARI", true );
	        mTeams[4] = new AUMlbTeam("Los Angeles", "LA", true );
	        
	        // NL Central
	        mTeams[5] = new AUMlbTeam("St. Louis", "STL", true );
	        mTeams[6] = new AUMlbTeam("Chicago", "CHC", true );
	        mTeams[7] = new AUMlbTeam("Houston", "HOU", true );
	        mTeams[8] = new AUMlbTeam("Pittsburg", "PIT", true );
	        mTeams[9] = new AUMlbTeam("Milwalkee", "MIL", true );
	        mTeams[10] = new AUMlbTeam("Cincinati", "CIN", true );
	        
	        // NL East
	        mTeams[11] = new AUMlbTeam("Florida", "FLA", true );
	        mTeams[12] = new AUMlbTeam("Atlanta", "ATL", true );
	        mTeams[13] = new AUMlbTeam("Washington", "WAS", true );
	        mTeams[14] = new AUMlbTeam("Philly", "PHI", true );
	        mTeams[15] = new AUMlbTeam("New York", "NYM", true );
	        
	        // AL West
	        mTeams[16] = new AUMlbTeam("Los Angeles (Anaheim)", "ANA", false );
	        mTeams[17] = new AUMlbTeam("Texas", "TEX", false );
	        mTeams[18] = new AUMlbTeam("Oakland", "OAK", false );
	        mTeams[19] = new AUMlbTeam("Seattle", "SEA", false );

	        // AL Central
	        mTeams[20] = new AUMlbTeam("Detroit", "DET", false );
	        mTeams[21] = new AUMlbTeam("Minnesota", "MIN", false );
	        mTeams[22] = new AUMlbTeam("Chicago", "CHW", false );
	        mTeams[23] = new AUMlbTeam("Cleveland", "CLE", false );
	        mTeams[24] = new AUMlbTeam("Kansas City", "KC", false );

	        // AL East
	        mTeams[25] = new AUMlbTeam("New York", "NYY", false );
	        mTeams[26] = new AUMlbTeam("Boston", "BOS", false );
	        mTeams[27] = new AUMlbTeam("Tampa Bay", "TB", false );
	        mTeams[28] = new AUMlbTeam("Toronto", "TOR", false );
	        mTeams[29] = new AUMlbTeam("Baltimore", "BAL", false );
	    }
		for( int i = 0; i < mMlbTeams; i++ ){
			if( isShortName ){
				if( name.equals(mTeams[i].mShortTeamName) ){
					return mTeams[i];
				}
			}
			else{
				if( name.equals(mTeams[i].mTeamName) ){
					return mTeams[i];
				}
			}
		}
		return null;
	}
}
