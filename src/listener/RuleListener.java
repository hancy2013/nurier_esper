package listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import event.NongHyupEvent;
import event.ScoreEvent;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

import main.FdsPilotMain;
import main.RealtimeEngine;

public class RuleListener implements UpdateListener {
	private RealtimeEngine engine;
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
	
	public RuleListener(RealtimeEngine engine, 
							    String ruleID, 
							    String ruleName, 
								String ruleGroupName, 
								String ruleType, 
								   int score, 
								   int secerityID,
								String severity,
								   int resposeCodeID,
								String resposeCode) {
		super();
		this.engine = engine;
		
		this.ruleID = ruleID;
		this.ruleName = ruleName;		
		this.ruleGroupName = ruleGroupName;		
		this.ruleType = ruleType;
		this.score = score;
		this.severity = severity;
		this.resposeCode = resposeCode;			
		this.ruleType = ruleType;	
		
		try {
			this.ia = InetAddress.getByName("192.168.0.111");
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
		if(newEvents != null){
			Date now = new Date();
			String detectDateTime = df.format(now);		
			String logID;
			String logDateTime;
			String userID;
			long receiveNanoTime = 0;
			
			for(EventBean event : newEvents){				
				logID  = event.get("logID" ).toString();	
				userID = event.get("userID").toString();
				logDateTime = event.get("logDateTime").toString();
				receiveNanoTime = Long.parseLong(event.get("receiveNanoTime").toString());
				long detectNanoTime = System.nanoTime();
				long elapsedNanoTime = (detectNanoTime - receiveNanoTime);

				ChannelHandlerContext ctx = (ChannelHandlerContext) event.get("ctx" );
				
//				PrintWriter os;
//				try {
//					os = new PrintWriter(soc.getOutputStream( ), true);
//					os.print(userID + "^" + logID + "^" + ruleName + "\n"); 
//					os.flush( );
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				String detectID = UUID.randomUUID().toString();
				
				String fds_dtl = ";" + 
				                 detectID        + ";" + //  0.		8.
						         logID           + ";" + //  1.  	9.
				                 logDateTime     + ";" + //  2. 	10.
						         detectDateTime  + ";" + //  3. 	11.
						         receiveNanoTime + ";" + //  4. 	12.
						         detectNanoTime  + ";" + //  5.		13.
						         elapsedNanoTime + ";" + //  6.		14.
							     userID          + ";" + //  7.		15.
							     ruleID          + ";" + //  8.		16.
							     ruleGroupName   + ";" + //  9.		17.
							     ruleName        + ";" + // 10.		18.
							     ruleType        + ";" + // 11.		19.
							     score           + ";" + // 12.		20.
							     severity        + ";" + // 13.		21.
							     resposeCode     + ";" ; // 14.		22.
			
				System.out.println("\tFDS_DTL-->" + fds_dtl); 
				//ctx.write(fds_dtl);
				
				
				ScoreEvent score_event = new ScoreEvent( ctx,
														 logID,           //  0.
						                                 logDateTime,     //  1.
						                                 detectDateTime,  //  2.						                                 
						                                 receiveNanoTime, //  3.
						                                 detectNanoTime,  //  4.
						                                 elapsedNanoTime, //  5.
													     userID,          //  6.
													     ruleID,          //  7.
													     ruleGroupName,   //  8.
													     ruleName,        //  9.
													     ruleType,        // 10.
													     score,           // 11.
													     severity,        // 12.
													     resposeCode      // 13.
														);	

				engine.getEngine().getEPRuntime().sendEvent(score_event);

//				this.dp = new DatagramPacket(fds_dtl.getBytes(Charset.forName("UTF-8")),fds_dtl.getBytes(Charset.forName("UTF-8")).length, ia, 501);
//				try {
//					ds.send(dp);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}								
			}
		}
	}
}