package event;

import io.netty.channel.ChannelHandlerContext;

public class ScoreEvent {
	private ChannelHandlerContext ctx;
	private String logID;
	private long receiveNanoTime;
	private long detectNanoTime;
	private long elapsedNanoTime;
	private String logDateTime;
	private String userID;	
	private String ruleID;
	private String ruleGroupName;	
	private String ruleName;
	private String ruleType;
	private int score;
	private String severity;
	private String resposeCode;		

	public ScoreEvent(  ChannelHandlerContext ctx,
						String logID,           //  0.
						String logDateTime,     //  1.
						String detectDateTime,  //  2.						                                 
				  		long receiveNanoTime,   //  3.
				  		long detectNanoTime,    //  4.
						long elapsedNanoTime,   //  5.
						String userID,          //  6.
						String ruleID,          //  7.
						String ruleGroupName,   //  8.
						String ruleName,        //  9.
						String ruleType,        // 10.
						int score,              // 11.
						String severity,        // 12.
						String resposeCode      // 13.
		     ) {
		//super();
		this.ctx = ctx;
		this.logID = logID;
		this.receiveNanoTime = receiveNanoTime;
		this.detectNanoTime = detectNanoTime;
		this.elapsedNanoTime = elapsedNanoTime;
		this.logDateTime = logDateTime;
		this.userID = userID;
		this.ruleID = ruleID;
		this.ruleName = ruleName;
		this.ruleGroupName = ruleGroupName;
		this.ruleType = ruleType;
		this.score = score;
		this.severity = severity;
		this.resposeCode = resposeCode;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setSoc(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public long getReceiveNanoTime() {
		return receiveNanoTime;
	}

	public void setReceiveNanoTime(long receiveNanoTime) {
		this.receiveNanoTime = receiveNanoTime;
	}

	public long getDetectNanoTime() {
		return detectNanoTime;
	}

	public void setDetectNanoTime(long detectNanoTime) {
		this.detectNanoTime = detectNanoTime;
	}

	public long getElapsedNanoTime() {
		return elapsedNanoTime;
	}

	public void setElapsedNanoTime(long elapsedNanoTime) {
		this.elapsedNanoTime = elapsedNanoTime;
	}

	public String getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(String logDateTime) {
		this.logDateTime = logDateTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleGroupName() {
		return ruleGroupName;
	}

	public void setRuleGroupName(String ruleGroupName) {
		this.ruleGroupName = ruleGroupName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public int getScore() {
		return score;
	}

	public void setScoreSum(int score) {
		this.score = score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getResposeCode() {
		return resposeCode;
	}

	public void setResposeCode(String resposeCode) {
		this.resposeCode = resposeCode;
	}		
}
