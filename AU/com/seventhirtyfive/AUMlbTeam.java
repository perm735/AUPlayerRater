package com.seventhirtyfive;

public class AUMlbTeam {
	public String mTeamName = "";
	public String mShortTeamName = "";
	
	private Debug735 mDebug = null;
	private boolean mIsNational = true;
	public boolean isNationalLeague()	{	return mIsNational; }
	public boolean isAmericanLeague()	{	return ( !isNationalLeague() ); }

	public AUMlbTeam(){
		// do nothing, take default values
		init();
	}
	
	private void init() {
		if (mDebug == null) {
			mDebug = PlayerRater.getDebugger();
		}
	}
	
	public AUMlbTeam( String longname, String shortname, boolean isNational )
	{
		init();
		
		mIsNational = isNational;
		mTeamName = longname;
		mShortTeamName = shortname;
	}
	
	public String toString(){
		return( mShortTeamName );
	}
}
