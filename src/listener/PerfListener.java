package listener;

import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.util.UUID;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PerfListener implements UpdateListener {
	private String eventType;
	private StringBuilder str;
	public PerfListener(String eventType) {
		super();
		this.eventType = eventType;		
		this.str = new StringBuilder(808);
	}
	
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		
		if(newEvents != null){
			str = new StringBuilder("초당 처리량: ");
			for(EventBean event : newEvents){
				System.out.println("logDate=" + event.get("logDate"));		


//				ChannelHandlerContext ctx = (ChannelHandlerContext) event.get("ctx" );
//				ctx.write(event.get("logDate"));
//				ctx.flush();
//				
//				System.out.println(event.get("logDate"));
				
//				System.out.println("engineURI="        + event.get("engineURI")        + ",\t" + 
//								   "timestamp="        + event.get("timestamp")        + ",\t" + 			
//								   "statementName="    + event.get("statementName")    + ",\t" + 
//								   "cpuTime="          + event.get("cpuTime")          + ",\t" + 
//								   "wallTime="         + event.get("wallTime")         + ",\t" + 
//								   "numInput="         + event.get("numInput")         + ",\t" + 
//								   "numOutputIStream=" + event.get("numOutputIStream") + ",\t" + 
//								   "numOutputRStream=" + event.get("numOutputRStream")  
//						           );
				
//				System.out.println("engineURI=" + event.get("engineURI")       + ",\t" + 
//						   "inputCount="        + event.get("inputCount")      + ",\t" + 
//						   "inputCountDelta="   + event.get("inputCountDelta") + ",\t" + 
//						   "scheduleDepth="     + event.get("scheduleDepth")   + ",\t" + 
//						   "timestamp="         + event.get("timestamp")
//				           );
			}
		}
	}
}