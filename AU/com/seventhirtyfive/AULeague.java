package com.seventhirtyfive;

import java.util.Vector;

/**
 * @author mschassberger
 *
 * This class is a collection of AU teams.  It will hold the list of teams
 * and provide statistical information about the league
 */
public class AULeague {
    public static final int NUMBER_OF_PLAYERS = 23;
    public static final int NUMBER_OF_HITTERS = 13;
    public static final int NUMBER_OF_PITCHERS = 10;
    
	private Vector<AUTeam> mTeamList = new Vector<AUTeam>();
	private static AULeague mSingleton = null;
    
    // This number is used to determine player values.  We actually use 
    // 300 as a cap value, but the salaries have traditionally been determined
    // based on a 260 salary cap value.
    public static final int SALARY_CAP = 260;
    //public static final int BATTER_MONEY = 260;
    public static final int BATTER_MONEY = 260;		// needed to adjust this after finding a bug in the weighted average
    public static final int PITCHER_MONEY = 80;
    
    public static AULeague getInstance() {
    	if (mSingleton == null) {
    		mSingleton = new AULeague();
    	}
    	return mSingleton;
    }
    
	public AULeague() {
		
	}
	
	/**
	 * Add a team to the league
	 * @param team
	 */
	public void addTeam( AUTeam team ) {
		mTeamList.add(team);
	}
	
	/**
	 * Get a team based on team name.
	 * @param teamName
	 * @return
	 */
	public AUTeam getTeam( String teamName ) {
		for (int i = 0; i < mTeamList.size(); i++) {
			if (mTeamList.elementAt(i).getName().equals(teamName)) {
				return mTeamList.elementAt(i);
			}
		}
		return null;
	}
	
	/**
	 * Get the number of teams in the league
	 * @return
	 */
	public int getNumberOfTeams() {
		return mTeamList.size();
	}
	
	/**
	 * Spit out the batter stats for the team specified
	 * @param index
	 * @return
	 */
	public String getBatterLine( int index ) {
		return mTeamList.get(index).getBatterLine();
	}
	
	/**
	 * Spit out the pitcher stats for the team specified
	 * @param index
	 * @return
	 */
	public String getPitcherLine( int index ) {
		return mTeamList.get(index).getPitcherLine();
	}

	/**
	 * Get the average K value
	 */
	private double mKAverage = 0.0;
	public double getKAverage() {
		if (mKAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getPitcherStats().getStrikeouts();
			}
			mKAverage = average(a);
		}
		return mKAverage;
	}
	
	/**
	 * Get the average win value
	 */
	private double mWinAverage = 0.0;
	public double getWinAverage() {
		if (mWinAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getPitcherStats().getWins();
			}
			mWinAverage = average(a);
		}
		return mWinAverage;
	}
	
	/**
	 * Get save average
	 */
	private double mSaveAverage = 0.0;
	public double getSaveAverage() {
		if (mSaveAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getPitcherStats().getSaves();
			}
			mSaveAverage = average(a);
		}
		return mSaveAverage;
	}

	/**
	 * Get inning average
	 */
	private double mInnAverage = 0.0;
	public double getInnAverage() {
		if (mInnAverage==0.0) {
			float [] a = new float[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getPitcherStats().getInnings();
			}
			mInnAverage = average(a);
		}
		return mInnAverage;
	}
	
	/**
	 * Get the ERA average
	 */
	private double mEraAverage = 0.0;
	public double getEraAverage() {
		// TODO: we only get each teams ERA and not their total runs, this is
		// inaccurate and should be fixed later
		if (mEraAverage == 0.0) {
			float [] a = new float[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getPitcherStats().getEra();
			}
			mEraAverage = average(a);
		}
		return mEraAverage;
	}
	
	/**
	 * Get the average whip
	 */
	private double mWhipAverage = 0.0;
	public double getWhipAverage() {
		// TODO: we only get each teams whip and not their hits and walks, this is
		// inaccurate and should be fixed later
		if (mWhipAverage==0.0) {
			float [] a = new float[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getPitcherStats().getWhip();
			}
			mWhipAverage = average(a);
		}
		return mWhipAverage;
	}
	
	/**
	 * Get the average AB value
	 * @return
	 */
	private double mAbAverage = 0.0;
	public double getAbAverage() {
		if (mAbAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getBatterStats().getAb();
			}
			mAbAverage = average(a);
		}
		return mAbAverage;
	}
	
	/**
	 * Get the average HR value
	 * @return
	 */
	private double mHrAverage = 0.0;
	public double getHrAverage() {
		if (mHrAverage == 0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getBatterStats().getHr();
			}
			mHrAverage = average(a);
		}
		return mHrAverage;
	}

	/**
	 * Get the average Run Value
	 * @return
	 */
	private double mRunAverage = 0.0;
	public double getRunAverage() {
		if (mRunAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getBatterStats().getRuns();
			}
			mRunAverage = average(a);
		}
		return mRunAverage;
	}

	/**
	 * Get the average RBI value
	 * @return
	 */
	private double mRbiAverage = 0.0;
	public double getRbiAverage() {
		if (mRbiAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getBatterStats().getRbi();
			}
			mRbiAverage = average(a);
		}
		return mRbiAverage;
	}

	/**
	 * Get the average SB value
	 * @return
	 */
	private double mSbAverage = 0.0;
	public double getSbAverage() {
		if (mSbAverage==0.0) {
			int [] a = new int[mTeamList.size()];
			for (int i = 0; i < mTeamList.size(); i++) {
				a[i] = mTeamList.elementAt(i).getBatterStats().getSb();
			}
			mSbAverage = average(a);
		}
		return mSbAverage;
	}
	
	private double mBaAverage = 0.0;
	public double getBaAverage() {
		if (mBaAverage==0.0) {
			mBaAverage = (double)getTotalHits() / (double)getTotalAbs();
		}
		return mBaAverage;
	}
	
	private double mObpAverage = 0.0;
	public double getObpAverage() {
		if (mObpAverage==0.0 ) {
			// OBP is walks + hits / AB's + walks
			int totalWalks = 0;
			for (int i = 0; i < mTeamList.size(); i++) {
				totalWalks += mTeamList.elementAt(i).getBatterStats().getBb();
			}
			mObpAverage =  (
				(double)(totalWalks+getTotalHits()) /
				(double)(totalWalks+getTotalAbs())
			);
		}
		return mObpAverage;
	}
	
	private double mSlgAverage = 0.0;
	public double getSlgAverage() {
		if (mSlgAverage==0.0) {
			// add up the total bases and divide by AB's
			int totalBases = 0;
			for (int i = 0; i < mTeamList.size(); i++) {
				totalBases += mTeamList.elementAt(i).getBatterStats().get1B();
				totalBases += mTeamList.elementAt(i).getBatterStats().get2B()*2;
				totalBases += mTeamList.elementAt(i).getBatterStats().get3B()*3;
				totalBases += mTeamList.elementAt(i).getBatterStats().getHr()*4; 
			}
			mSlgAverage = (double)totalBases / (double)getTotalAbs();
		}
		return mSlgAverage;
	}
	
	private int mTotalHits = 0;
	private int getTotalHits() {
		if (mTotalHits == 0) {
			for (int i = 0; i < mTeamList.size(); i++) {
				mTotalHits += mTeamList.elementAt(i).getBatterStats().get1B();
				mTotalHits += mTeamList.elementAt(i).getBatterStats().get2B();
				mTotalHits += mTeamList.elementAt(i).getBatterStats().get3B();
				mTotalHits += mTeamList.elementAt(i).getBatterStats().getHr();
			}
		}
		return mTotalHits;
	}
	
	// This variable is needed a bunch, so cache it
	private int mTotalAbs = 0;
	private int getTotalAbs() {
		if (mTotalAbs == 0) {
			for (int i = 0; i < mTeamList.size(); i++) {
				mTotalAbs += mTeamList.elementAt(i).getBatterStats().getAb();
			}
		}
		return mTotalAbs;
	}

	/**
	 * Finds the average of a vector of integers
	 * @param v
	 * @return
	 */
	private double average( int [] a ) {
		double average = 0.0;
		if (a.length>0) {
			for (int i = 0; i < a.length; i++) {
				average += (double)a[i];
			}
			average /= a.length;
		}
		return average;
	}
	
	private double average( float [] a ) {
		double average = 0.0;
		if (a.length>0) {
			for (int i = 0; i < a.length; i++) {
				average += (double)a[i];
			}
			average /= a.length;
		}
		return average;
	}
	
	private double median( int [] a ) {
		// sort the vector
		// return the value in the middle
		return 0.0;
	}
}
