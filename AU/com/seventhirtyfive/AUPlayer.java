package com.seventhirtyfive;

import java.io.BufferedOutputStream;
import java.util.Vector;

public class AUPlayer extends Player {
    /**
     * Number of years to store in mYears.
     */
    public static final int NUMBER_OF_YEARS = 3;

    /**
     * Year constants
     */
    public static final int YEAR_2009 		= 0;
    public static final int YEAR_2008 		= 1;
    public static final int YEAR_2007 		= 2;
    public static final int YEAR_ALL		= 3;	// this should equal NUMBER_OF_YEARS
    
    /**
     * Current statistical year
     */
    protected int mCurrentYear = YEAR_2009;
    
    private boolean mIsRookie = false;
    
    public boolean isRookie() {
    	return mIsRookie;
    }
    
    public void setRookie(boolean val) {
    	mIsRookie = val;
    }

    public int getYear() {
    	return mCurrentYear;
    }
    
    public void setYear(final int year){
    	mCurrentYear = year;
    }
    
    public static final String PITCHER = "P";
    public static final String STARTING_PITCHER = "SP";
    public static final String RELIEF_PITCHER = "RP";
    public static final String FIRST_BASE = "1B";
    public static final String SECOND_BASE = "2B";
    public static final String THIRD_BASE = "3B";
    public static final String SHORT_STOP = "SS";
    public static final String CATCHER = "C";
    public static final String OUTFIELD = "OF";
    public static final String LEFTFIELD = "LF";
    public static final String CENTERFIELD = "CF";
    public static final String RIGHTFIELD = "RF";
    public static final String DESIGNATED_HITTER = "DH";

    protected Vector<AUPlayerPosition> mPositionElig = new Vector<AUPlayerPosition>();
    private Debug735 mDebug = null;

    public boolean isEligible(int position) {
        for (int i = 0; i < mPositionElig.size(); i++) {
            // TODO: This is terrible, REDO ME
            if ((mPositionElig.get(i).mIPosition & position) == position) {
                return true;
            }
        }
        return false;
    }
    
    public void addPosition(String p) {
    	if (p==null) {
    		mPositionElig.addElement(new AUPlayerPosition(
                    AUPlayerPosition.BATTER));
    	} else {
            if (p.compareTo(PITCHER) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.PITCHER));
            } else if (p.compareTo(STARTING_PITCHER) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.STARTING_PITCHER));
            } else if (p.compareTo(RELIEF_PITCHER) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.RELIEF_PITCHER));
            } else if (p.compareTo(FIRST_BASE) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.FIRSTBASE));
            } else if (p.compareTo(SECOND_BASE) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.SECONDBASE));
            } else if (p.compareTo(THIRD_BASE) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.THIRDBASE));
            } else if (p.compareTo(SHORT_STOP) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.SHORTSTOP));
            } else if (p.compareTo(OUTFIELD) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.OUTFIELD));
            } else if (p.compareTo(CATCHER) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.CATCHER));
            } else if (p.compareTo(CENTERFIELD) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.CENTERFIELD));
            } else if (p.compareTo(RIGHTFIELD) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.RIGHTFIELD));
            } else if (p.compareTo(LEFTFIELD) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.LEFTFIELD));
            } else if (p.compareTo(DESIGNATED_HITTER) == 0) {
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.DESIGNATEDHITTER));
            } else {
                mDebug.log("Position not found: " + p);
                mPositionElig.addElement(new AUPlayerPosition(
                        AUPlayerPosition.BATTER));
            }
    	}
    }

    public AUPlayer(String first, String last, String position) {
        super(first, last);
        if (mDebug == null) {
            mDebug = PlayerRater.getDebugger();
        }
        addPosition(position);
    }

    protected void writePlayer( BufferedOutputStream os, double price ) {
    	try {
    		String elig = mPositionElig.get(0).toString();
    		for (int i = 1; i < mPositionElig.size(); i++) {
    			elig = elig.concat(" "+mPositionElig.get(i));
    			System.out.println("Found multi elig: " + mFirstName +" "+mLastName+":"+elig);
    		}
    		String strToWrite = String.format("%s %s, %s, $%2.2f", mFirstName,mLastName,elig,price);
    		if (mIsRookie) {
    			strToWrite = strToWrite.concat(", (R) $10.00");
    		}
    		strToWrite = strToWrite.concat("\n");
    		os.write(strToWrite.getBytes());
    	}catch (Exception e) {
    		System.out.println(this+": could not write");
    	}
    }

    public static boolean isStringPosition(String s) {
        if (   s.compareTo(AUPlayer.CATCHER) == 0
            || s.compareTo(AUPlayer.PITCHER) == 0
            || s.compareTo(AUPlayer.STARTING_PITCHER) == 0
            || s.compareTo(AUPlayer.RELIEF_PITCHER) == 0
            || s.compareTo(AUPlayer.DESIGNATED_HITTER) == 0
            || s.compareTo(AUPlayer.FIRST_BASE) == 0
            || s.compareTo(AUPlayer.SECOND_BASE) == 0
            || s.compareTo(AUPlayer.SHORT_STOP) == 0
            || s.compareTo(AUPlayer.THIRD_BASE) == 0
            || s.compareTo(AUPlayer.OUTFIELD) == 0
            || s.compareTo(AUPlayer.LEFTFIELD) == 0
            || s.compareTo(AUPlayer.RIGHTFIELD) == 0
            || s.compareTo(AUPlayer.CENTERFIELD) == 0) {
            return true;
        }
        return false;
    }

    public class AUPlayerPosition {
        // or'd values of player eligibility
        public final int mIPosition;

        // OR'd fields that designate eligibility
        public static final int PITCHER = 0x00000001;
        public static final int CATCHER = 0x00000002;
        public static final int FIRSTBASE = 0x00000004;
        public static final int SECONDBASE = 0x00000008;
        public static final int THIRDBASE = 0x00000010;
        public static final int SHORTSTOP = 0x00000020;
        public static final int LEFTFIELD = 0x00000040;
        public static final int CENTERFIELD = 0x00000080;
        public static final int RIGHTFIELD = 0x00000100;
        public static final int STARTING_PITCHER = 0x00000200;
        public static final int RELIEF_PITCHER = 0x00000400;
        public static final int OUTFIELD = 0x00000800;
        public static final int DESIGNATEDHITTER = 0x00001000;
        public static final int BATTER = 0x10000000;

        public String toString() {
            switch (mIPosition) {
            case PITCHER:
                return "P";
            case CATCHER:
                return "C";
            case FIRSTBASE:
                return "1B";
            case SECONDBASE:
                return "2B";
            case THIRDBASE:
                return "3B";
            case SHORTSTOP:
                return "SS";
            case CENTERFIELD:
            case RIGHTFIELD:
            case LEFTFIELD:
            case OUTFIELD:
                return "OF";
            case STARTING_PITCHER:
                return "SP";
            case RELIEF_PITCHER:
                return "RP";
            case DESIGNATEDHITTER:
                return "DH";
            default:
                return "";
            }
        }

        public AUPlayerPosition(int position) {
            mIPosition = position;
        }

        public boolean isPitcher() {
            return (isStartingPitcher() || isReliefPitcher());
        }

        public boolean isStartingPitcher() {
            return ((mIPosition & STARTING_PITCHER) == STARTING_PITCHER);
        }

        public boolean isReliefPitcher() {
            return ((mIPosition & RELIEF_PITCHER)== RELIEF_PITCHER);
        }

        public boolean isCatcher() {
            return ((mIPosition & CATCHER) == CATCHER);
        }

        public boolean isFirstBase() {
            return ((mIPosition & THIRDBASE) == FIRSTBASE);
        }

        public boolean isThirdBase() {
            return ((mIPosition & THIRDBASE) == THIRDBASE);
        }

        public boolean isSecondBase() {
            return ((mIPosition & SECONDBASE) == SECONDBASE);
        }

        public boolean isShortStop() {
            return ((mIPosition & SHORTSTOP) == SHORTSTOP);
        }
        
        public boolean isDesignatedHitter() {
            return ((mIPosition & DESIGNATEDHITTER) == DESIGNATEDHITTER);
        }

        public boolean isOutfield() {
            return ((mIPosition & CENTERFIELD) == CENTERFIELD || (mIPosition & LEFTFIELD) == LEFTFIELD
                    || (mIPosition & RIGHTFIELD) == RIGHTFIELD || (mIPosition & OUTFIELD) == OUTFIELD);
        }
    }

}
