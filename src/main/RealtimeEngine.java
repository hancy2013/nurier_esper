package main;

import java.io.File;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esperio.http.EsperIOHTTPAdapter;
import com.espertech.esperio.http.config.ConfigurationHTTPAdapter;
import com.espertech.esperio.http.config.Request;
import com.espertech.esperio.socket.EsperIOSocketAdapter;
import com.espertech.esperio.socket.config.ConfigurationSocketAdapter;
import com.espertech.esperio.socket.config.DataType;
import com.espertech.esperio.socket.config.SocketConfig;

import event.NongHyupEvent;

public class RealtimeEngine {
	private EPServiceProvider engine;
	//private static final String ENGINE_URI = "http-io-fds-pilot";
	//private static final String REQUEST_URI = "http://192.168.1.190:8877/esper/fds/pilot";

	public RealtimeEngine () {		
		Configuration config = new Configuration();
		config.configure(new File("./nfds.ini"));
		//config.getEngineDefaults().getMetricsReporting().setJmxEngineMetrics(true);
		config.getEngineDefaults().getExecution().setPrioritized(true);				
		
		config.addEventType("NongHyupEvent"  , NongHyupEvent.class.getName());
		config.addEventType("RuleResultEvent", NongHyupEvent.class.getName());
		
		
		this.engine = EPServiceProviderManager.getDefaultProvider(config);
		//this.engine = EPServiceProviderManager.getProvider(ENGINE_URI, config);		
	}
	
	public void startHTTPInputAdapter() {
//		ConfigurationHTTPAdapter httpAdapterConfig = new ConfigurationHTTPAdapter();
//		Request request = new Request();
//		request.setStream("DataStream");
//		request.setUri(REQUEST_URI);
//		httpAdapterConfig.getRequests().add(request);
//		EsperIOHTTPAdapter httpAdapter = new EsperIOHTTPAdapter(httpAdapterConfig, ENGINE_URI);
//		httpAdapter.start();
	}
	
	public void startSocketInputAdapter() {
//		ConfigurationHTTPAdapter httpAdapterConfig = new ConfigurationHTTPAdapter();		
//		Request request = new Request();
//		request.setStream("DataStream");
//		request.setUri(REQUEST_URI);
//		httpAdapterConfig.getRequests().add(request);
//		EsperIOHTTPAdapter httpAdapter = new EsperIOHTTPAdapter(httpAdapterConfig, ENGINE_URI);
//		httpAdapter.start();
		
//		ConfigurationSocketAdapter socketAdapterConfig = new ConfigurationSocketAdapter();
//		SocketConfig socket = new SocketConfig();
//		socket.setDataType(DataType.CSV);
//
//		socket.setPort(1999);
//		socketAdapterConfig.getSockets().put("SocketService", socket);
//		EsperIOSocketAdapter socketAdapter = new EsperIOSocketAdapter(socketAdapterConfig, "engineURI");
//		
//		socketAdapter.start();
		
		
	}
		
	public EPServiceProvider getEngine() {
		return engine;
	}

	public void setEngine(EPServiceProvider engine) {
		this.engine = engine;
	}
}

