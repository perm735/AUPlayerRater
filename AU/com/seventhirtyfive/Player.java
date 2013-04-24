package com.seventhirtyfive;

/**
 * AU Player interface.
 */
public abstract class Player {
	/// First name of player
	protected String mFirstName = null;
	
	/// Last name of player
	protected String mLastName = null;
	
	protected AUMlbTeam mTeam = null;
	
	public Player( String first, String last ){
	    mFirstName = first;
	    mLastName = last;
	}
	
	public Player(){
        this("","");
    }

	public String getFirstName() {
	    return mFirstName;
	}
	
	public String getLastName() {
        return mLastName;
	}
	
	public AUMlbTeam getTeam() {
	    return mTeam;
	}
	
    public void setTeam(AUMlbTeam t) {
	    mTeam = t;
	}
	
	public String toString() {
	    return "Player: "+mFirstName+", "+mLastName+" "+mTeam;
	}
}
