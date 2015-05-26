package listener;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;



public class ScoreListener implements UpdateListener{
	private class RuleResultSum {
		public String logID;
		public String logDateTime;
		public String userID;
		public String ruleID;		
		public String ruleName;
		public String ruleGroupName;
		public String ruleType;
		public int    scoreSum = 0;
		public String severityMax;
		public String resposeCodeMax;
	}
	
	private String logID;
	private String ruleID;
	private String ruleName;
	private String ruleGroupName;
	private String ruleType;
	private int score;
	private String severity;
	private String resposeCode;
	
	private DatagramSocket ds;
	private DatagramPacket dp;
	private InetAddress ia;
	private SimpleDateFormat df;
	
	public ScoreListener() {
		super();

		try {
			this.ia = InetAddress.getByName("192.168.0.8");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			this.ds = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	}
	
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		String userID = null;
		long scoreSum;
		long minDetectNanoTime;
		long maxDetectNanoTime;
		long avgElapsedNanoTime;
		long logCount;
		
		if(newEvents != null){			
			long receiveNanoTime = 0;
			String logDateTime;
			String responseCode = "";	
			
			HashMap<String, RuleResultSum> mapRuleResultSum = new HashMap<String, RuleResultSum>(); 			
			
			
			for(EventBean event : newEvents) { 
				ChannelHandlerContext ctx = (ChannelHandlerContext) event.get("ctx");
				userID   = event.get("userID" ).toString();
				scoreSum = Long.parseLong(event.get("scoreSum").toString());
				minDetectNanoTime = Long.parseLong(event.get("minDetectNanoTime").toString());
				maxDetectNanoTime = Long.parseLong(event.get("maxDetectNanoTime").toString());
				avgElapsedNanoTime = Long.parseLong(event.get("avgElapsedNanoTime").toString());
				logCount  = Long.parseLong(event.get("logCount").toString());
				
				//receiveNanoTime = (long)event.get("receiveNanoTime");
				//logDateTime = event.get("logDateTime").toString();	
				
				if (scoreSum < 60) {
					responseCode = "PASS";
				}else if ((60 <= scoreSum) && (scoreSum < 80)) {
					responseCode = "AUTH";
				}else if (80 <= scoreSum){
					responseCode = "BC";
				}
				
				String fds_score = "";
				fds_score = userID             + "^" +
							scoreSum           + "^" +										
							minDetectNanoTime  + "^" +
							maxDetectNanoTime  + "^" +
							logCount           + "^" +
							responseCode       + "^" +
							avgElapsedNanoTime ; //+ "\n";
            	
				
//				ctx.write(fds_score);
//				ctx.flush();
				System.out.println("\t\tFDS_SCORE-->" +  fds_score);
				
//				PrintWriter os;
//				try {
//					os = new PrintWriter(soc.getOutputStream(), false);
//					os.print(fds_score); 
//					os.flush();
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
				
				//System.out.println(">>Result:Detected: " + msg); 
				
//				if (mapRuleResultSum.containsKey(logID)) {
//					//logID가 이미 존재 --> sum(score), max(responseCode) 	
//					RuleResultSum resultSum = mapRuleResultSum.get(logID);
//					resultSum.scoreSum += Integer.parseInt(event.get("score").toString());					
//				} else {
//					//logID가 없음 --> 단순 생성	
//					RuleResultSum resultSum= new RuleResultSum();
//					resultSum.scoreSum = Integer.parseInt(event.get("score").toString());	
//					mapRuleResultSum.put(logID, resultSum);
//				}
				
//				this.dp = new DatagramPacket(fds_score.getBytes(Charset.forName("UTF-8")), fds_score.getBytes(Charset.forName("UTF-8")).length, ia, 514);
//				try {
//					ds.send(dp);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}								
			}
//						
//			Iterator it = mapRuleResultSum.keySet().iterator();
//			while(it.hasNext()){
//				logID = (String) it.next();
//				int scoreSum = mapRuleResultSum.get(logID).scoreSum;				
//				String responseCode = "";
//				if (scoreSum < 60) {
//					responseCode = "PASS";
//				}else if ((60 <= scoreSum) && (scoreSum < 80)) {
//					responseCode = "AUTH";
//				}else if (80 <= scoreSum){
//					responseCode = "BC";
//				}
//				
//			double elapsedMilliTime = (System.nanoTime() - receiveNanoTime) / 1000000.0;
//			System.out.println(elapsedMilliTime + " : userID=" + userID +" :mapRuleResultSum.get( " + logID + " ).scoreSum == " + scoreSum + ", responseCode=" + responseCode + ", 개수=" + (newEvents.length - 1));
//			
//			}
			
		}
	}
}