package main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import listener.RuleListener;
import listener.ScoreListener;

import org.apache.log4j.BasicConfigurator;

import com.espertech.esper.client.EPStatement;

public class FdsPilotMain {
	private static final int FDS_STREAM_PORT = 10001;
	private static final int API_PORT = 10002;
	
    static final boolean SSL = System.getProperty("ssl") != null;
	
	private static final String[] eventField = {
												"logDate", 		    // 0 . logDate;         ( 8  ) //순번. 필드명 (크기)
												"logTime",          // 1 . logTime;         ( 6  )
									            "userID",           // 2 . userID;          ( 13 )
									            "userName",         // 3 . userName;        ( 30 )
									            "jumin",            // 4 . jumin;           ( 13 )
									            "hddSerial",        // 5 . hddSerial;       ( 30 )
									            "macAddr",          // 6 . macAddr;         ( 39 )
									            "realIP",           // 7 . realIP;          ( 15 )
									            "mediaType",        // 8 . mediaType        ( 10 )
									            "transTarget",      // 9 . transTarget      ( 1  )
									            "isKeyboardBypass", // 10. isKeyboardBypass ( 1  )
									            "textSvcCode",      // 11. textSvcCode      ( 10 )
									            "tmaxSvcCode",      // 12. tmaxSvcCode      ( 10 )
									            "tranxCode",        // 13. tranxCode        ( 10 )
									            "filterSpace",      // 14. filterSpace      ( 10 )
									            "amount",           // 15. amount           ( 20 )
									            "publicIP_1",       // 16. publicIP_1       ( 15 )
									            "publicIP_2",       // 17. publicIP_2       ( 15 )
									            "publicIP_3",       // 18. publicIP_3       ( 15 )
									            "privateIP_1",      // 19. privateIP_1      ( 15 )
									            "privateIP_2",      // 20. privateIP_2      ( 15 )
									            "privateIP_3",      // 21. privateIP_3      ( 15 )
									            "isProxy",          // 22. isProxy          ( 1  )
									            "proxyIP_1",        // 23. proxyIP_1        ( 15 )
									            "proxyIP_2",        // 24. proxyIP_2        ( 15 )
									            "isVPN",            // 25. isVPN            ( 1  )
									            "vpnIP_1",          // 26. vpnIP_1          ( 15 )
									            "vpnIP_2",          // 27. vpnIP_2          ( 15 )
									            "macAddr_1",        // 28. macAddr_1        ( 17 )
									            "macAddr_2",        // 29. macAddr_2        ( 17 )
									            "macAddr_3",        // 30. macAddr_3        ( 17 )
									            "logicalMAC_1",     // 31. logicalMAC_1     ( 17 )
									            "logicalMAC_2",     // 32. logicalMAC_2     ( 17 )
									            "logicalMAC_3",     // 33. logicalMAC_3     ( 17 )
									            "hddSerial_1",      // 34. hddSerial_1      ( 40 )
									            "hddSerial_2",      // 35. hddSerial_2      ( 40 )
									            "hddSerial_3",      // 36. hddSerial_3      ( 40 )
									            "cpuID",            // 37. cpuID            ( 40 )
									            "mbSN",             // 38. mbSN             ( 20 ) - 2014.06.02 변경 40 --> 20
									            "osVersion",        // 39. osVersion        ( 40 )
									            "IsVirtual",        // 40. isVirtual        ( 1  )
									            "virtualToolName",  // 41. virtualToolName  ( 20 ) - 2014.06.02 변경 10 --> 20
									            "gatewayMAC",       // 42. gatewayMAC       ( 17 )
									            "gatewayIP",        // 43. gatewayIP        ( 15 )
									            "hddVolumeID",      // 44. hddVolumeID      ( 15 ) - 2014.06.02 변경 20 --> 15
									            "remoteToolName"    // 45. remoteToolName   ( 40 ) - 2014.06.02 변경  1 --> 40
								            };

	private static final int[] eventSize = {	8 , // 0 . logDate;         ( 8  ) //순번. 필드명 (크기)
											  	6 , // 1 . logTime;         ( 6  )
											  	13, // 2 . userID;          ( 13 )
											  	30, // 3 . userName;        ( 30 )
											  	13, // 4 . jumin;           ( 13 )
											  	30, // 5 . hddSerial;       ( 30 )
											  	39, // 6 . macAddr;         ( 39 )
											  	15, // 7 . realIP;          ( 15 )
											  	10, // 8 . mediaType        ( 10 )
											  	1 , // 9 . transTarget      ( 1  )
											  	1 , // 10. isKeyboardBypass ( 1  )
											  	10, // 11. textSvcCode      ( 10 )
											  	10, // 12. tmaxSvcCode      ( 10 )
											  	10, // 13. tranxCode        ( 10 )
											  	10, // 14. filterSpace      ( 10 )
											  	20, // 15. amount           ( 20 )
											  	15, // 16. publicIP_1       ( 15 )
											  	15, // 17. publicIP_2       ( 15 )
											  	15, // 18. publicIP_3       ( 15 )
											  	15, // 19. privateIP_1      ( 15 )
											  	15, // 20. privateIP_2      ( 15 )
											  	15, // 21. privateIP_3      ( 15 )
											  	1 , // 22. isProxy          ( 1  )
											  	15, // 23. proxyIP_1        ( 15 )
											  	15, // 24. proxyIP_2        ( 15 )
											  	1 , // 25. isVPN            ( 1  )
											  	15, // 26. vpnIP_1          ( 15 )
											  	15, // 27. vpnIP_2          ( 15 )
											  	17, // 28. macAddr_1        ( 17 )
											 	17, // 29. macAddr_2        ( 17 )
											 	17, // 30. macAddr_3        ( 17 )
											 	17, // 31. logicalMAC_1     ( 17 )
											 	17, // 32. logicalMAC_2     ( 17 )
											 	17, // 33. logicalMAC_3     ( 17 )
											 	40, // 34. hddSerial_1      ( 40 )
											 	40, // 35. hddSerial_2      ( 40 )
											 	40, // 36. hddSerial_3      ( 40 )
											 	40, // 37. cpuID            ( 40 )
											 	20, // 38. mBSN             ( 20 ) - 2014.06.02 변경 40 --> 20
											 	40, // 39. osVersion        ( 40 )
											 	1 , // 40. isVirtual        ( 1  )
											 	20, // 41. virtualToolName  ( 20 ) - 2014.06.02 변경 10 --> 20
											 	17, // 42. gatewayMAC       ( 17 )
											 	15, // 43. gatewayIP        ( 15 )
											 	15, // 44. hddVolumeID      ( 15 ) - 2014.06.02 변경 20 --> 15
											 	40  // 45. remoteToolName   ( 40 ) - 2014.06.02 변경  1 --> 40
									       	};	

	private static RealtimeEngine engine; 	

	
	public static RealtimeEngine getEngine() {
		return engine;
	}

	public static void setEngine(RealtimeEngine engine) {
		FdsPilotMain.engine = engine;
	}	
	
	public static void main(String[] args) throws Exception {	
		BasicConfigurator.configure(); //Log4J Configuration
		engine = new RealtimeEngine(); //Set Default Configuration of Esper Engine
		
//		String epl_p = "INSERT INTO DataStream SELECT rstream count(logDate) AS logDate FROM NongHyupEvent.win:time(1 sec)";
////		String epl_p = "@Name('Performance') SELECT * FROM com.espertech.esper.client.metric.EngineMetric";
//		EPStatement stmt_p = engine.getEngine().getEPAdministrator().createEPL(epl_p);		
//		PerfListener pListener = new PerfListener("Performance");
//		stmt_p.addListener(pListener);		

		String epl = "@Drop @Priority(1) @Name('중복 로그인 탐지') SELECT * FROM NongHyupEvent.win:time(300 sec) GROUP BY macAddr_1 HAVING COUNT(*) > 3";
		EPStatement stmt7 = engine.getEngine().getEPAdministrator().createEPL(epl);
		RuleListener rListener7 = new RuleListener(engine, "RC01", "중복 로그인 탐지", "중복/다중 이상거래", "simple", 30, 4, "WARNING", 3, "BC" );
		stmt7.addListener(rListener7);
		
		epl        = "SELECT * FROM NongHyupEvent";
		EPStatement stmt = engine.getEngine().getEPAdministrator().createEPL(epl);		
		RuleListener rListener = new RuleListener(engine, "NO00", "정상", "정상", "simple", 0, 1, "SAFE", 1, "PASS");
		stmt.addListener(rListener);	
		
		epl        = "@Name('가상 OS 사용') SELECT * FROM NongHyupEvent WHERE isVirtual = 'y'";
		EPStatement stmt54 = engine.getEngine().getEPAdministrator().createEPL(epl);		
		RuleListener rListener54 = new RuleListener(engine, "SG01", "가상 OS 사용", "접속단말이상거래", "simple", 20, 5, "CRITICAL", 3, "BC");
		stmt54.addListener(rListener54);	
		
		epl        = "@Name('Proxy 사용') SELECT * FROM NongHyupEvent WHERE isProxy = 'y'";
		EPStatement stmt55 = engine.getEngine().getEPAdministrator().createEPL(epl);		
		RuleListener rListener55 = new RuleListener(engine, "SG02", "Proxy 사용", "접속단말이상거래", "simple", 30, 4, "WARNING", 3, "BC");
		stmt55.addListener(rListener55);		
		
//		epl        = "@Name('VPN 사용') SELECT * FROM NongHyupEvent WHERE isVPN = 'y'";
//		EPStatement stmt56 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener56 = new RuleListener(engine, "SG03", "VPN 사용", "접속단말이상거래", "simple", 40, 4, "WARNING", 3, "BC");
//		stmt56.addListener(rListener56);
//		
//		epl        = "@Name('게이트웨이 MAC 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent WHERE gatewayMAC=''";
//		EPStatement stmt61 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener61 = new RuleListener(engine, "SG08", "게이트웨이 MAC 미추출", "접속단말이상거래", "simple", 30, 4, "WARNING", 2, "AUTH");
//		stmt61.addListener(rListener61);
//		
//		epl        = "@Name('게이트웨이 IP 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent WHERE gatewayIP=''";
//		EPStatement stmt62 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener62 = new RuleListener(engine, "SG09", "게이트웨이 IP 미추출", "접속단말이상거래", "simple", 30, 5, "CRITICAL", 3, "BC");
//		stmt62.addListener(rListener62);
//
//		epl        = "@Name('CPU ID 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent WHERE cpuID=''";
//		EPStatement stmt63 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener63 = new RuleListener(engine, "SG10", "CPU ID 미추출", "접속단말이상거래", "simple", 30, 5, "CRITICAL", 2, "AUTH");
//		stmt63.addListener(rListener63);		
//		
//		epl        = "@Name('물리 MAC 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent WHERE macAddr_1=''";
//		EPStatement stmt64 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener64 = new RuleListener(engine, "SG11", "물리 MAC 미추출", "접속단말이상거래", "simple", 30, 5, "CRITICAL", 3, "BC");
//		stmt64.addListener(rListener64);
//		
//		epl        = "@Name('HDD S/N 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent WHERE hddSerial=''";
//		EPStatement stmt65 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener65 = new RuleListener(engine, "SG12", "HDD S/N 미추출", "접속단말이상거래", "simple", 30, 2, "NORMAL", 3, "BC");
//		stmt65.addListener(rListener65);
//	
//		epl        = "@Name('이체건수 과다') INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(300 sec) WHERE amount >= 900000 GROUP BY userID HAVING COUNT(*) >= 3";
//		EPStatement stmt84 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener84 = new RuleListener(engine, "SH01", "평시대비 이체건수 과다", "이상금융거래", "simple", 50, 5, "CRITICAL", 2, "AUTH");
//		stmt84.addListener(rListener84);
//		
//		epl        = "@Name('이체금액 과도') INSERT INTO DataStream SELECT * FROM NongHyupEvent WHERE amount >= 9000000";
//		EPStatement stmt85 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener85 = new RuleListener(engine, "SH02", "평시대비 이체금액 과도", "이상금융거래", "simple", 30, 5, "CRITICAL", 3, "BC");
//		stmt85.addListener(rListener85);	
		
//		epl        = "INSERT INTO DataStream SELECT * FROM NongHyupEvent, sql:logcenter_builtin_db ['select '] WHERE userID LIKE '%black%'";
//		epl        = "INSERT INTO DataStream SELECT NH.* FROM NongHyupEvent AS NH, sql:logcenter_builtin_db ['select * from lcefdsblacklist where userid = ${userID}']"; // WHERE userID LIKE '%black%'";
//		EPStatement stmt19 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener19 = new RuleListener(engine, "RE01", "블랙리스트", "블랙리스트", "simple", 100, 5, "CRITICAL", 3, "BC");
//		stmt19.addListener(rListener19);
		
//		epl        = "('물리 MAC 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(1 sec) GROUP BY publicIP_1 HAVING COUNT(*) >= 3";
//		EPStatement stmt39 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener39 = new RuleListener(engine, "SD05", "동일IP 초당 3회이상 접속", "시간분석 이상거래", "simple", 30, 5, "CRITICAL", 3, "BC");
//		stmt39.addListener(rListener39);
//		
//		epl        = "('물리 MAC 미추출') INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(60 sec) GROUP BY publicIP_1 HAVING COUNT(*) >= 60";
//		EPStatement stmt40 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener40 = new RuleListener(engine, "SD05", "동일IP 분당 60회이상 접속", "시간분석 이상거래", "simple", 30, 3, "NOTICE", 3, "BC");
//		stmt40.addListener(rListener40);
//		
//		epl        = "INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(3600 sec) GROUP BY publicIP_1 HAVING COUNT(*) >= 600";
//		EPStatement stmt41 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener41 = new RuleListener(engine, "SD05", "동일IP 시간당 600회이상 접속", "시간분석 이상거래", "simple", 30, 4, "WARNING", 3, "BC");
//		stmt41.addListener(rListener41);
//		
//		epl        = "INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(1 sec) GROUP BY userID HAVING COUNT(*) >= 3";
//		EPStatement stmt43 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener43 = new RuleListener(engine, "SD05", "동일ID 초당 3회이상 접속", "시간분석 이상거래", "simple", 30, 5, "CRITICAL", 3, "BC");
//		stmt43.addListener(rListener43);
//		
//		epl        = "INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(60 sec) GROUP BY userID HAVING COUNT(*) >= 60";
//		EPStatement stmt44 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener44 = new RuleListener(engine, "SD05", "동일ID 분당 60회이상 접속", "시간분석 이상거래", "simple", 30, 3, "NORMAL", 3, "BC");
//		stmt44.addListener(rListener44);
//		
//		epl        = "INSERT INTO DataStream SELECT * FROM NongHyupEvent.win:time(3600 sec) GROUP BY userID HAVING COUNT(*) >= 600";
//		EPStatement stmt45 = engine.getEngine().getEPAdministrator().createEPL(epl);		
//		RuleListener rListener45 = new RuleListener(engine, "SD05", "동일ID 시간당 600회이상 접속", "시간분석 이상거래", "simple", 30, 4, "WARNING", 3, "BC");
//		stmt45.addListener(rListener45);		
		
		String epl_r  = "@Name('Score') SELECT ISTREAM ctx, userID, sum(score) AS scoreSum , min(detectNanoTime) AS minDetectNanoTime, max(detectNanoTime) AS maxDetectNanoTime, min(elapsedNanoTime) AS avgElapsedNanoTime, count(logID) AS logCount FROM ScoreEvent.win:time(24 hour) GROUP BY ctx, userID"; // HAVING SUM(score) >= 100";
		EPStatement stmt_r = engine.getEngine().getEPAdministrator().createEPL(epl_r);		
		ScoreListener resultListener = new ScoreListener();
		stmt_r.addListener(resultListener);	
		
		//new FdsPilotMain().runServer();	
		
        

		
		// Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 1000)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }
                     //p.addLast(new LoggingHandler(LogLevel.INFO));
                     p.addLast(new LineBasedFrameDecoder(32768));
                     p.addLast(new StringHandler());
                 }
             });

            // Start the server.
            ChannelFuture f = b.bind(FDS_STREAM_PORT).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }		
		return;
	}
	
	public void runServer() throws Exception	{
		new realtimeEngineServer(FDS_STREAM_PORT).start(); // 실시간탐지엔진 서버 실행
		new apiServer(API_PORT).start();                   // Rest-API 서버 실행
	}
	
	class realtimeEngineServer extends Thread {
		private ServerSocket sock;
		private Socket clientSocket;
		private int serverPort;
		//private CouchbaseClient couch_connection;
		
		
		public realtimeEngineServer(int serverPort) throws Exception  {
			super();
			this.serverPort = serverPort;
			
//			List<URI> hosts = Arrays.asList(new URI("http://localhost:8091/pools"));
//			couch_connection = new CouchbaseClient(hosts, "FDS", "admin12!@");
		}
		
		public void run() {
			try {
				sock = new ServerSocket(serverPort);
				System.out.println("FDS_STREAM_PORT ready for connections.");
			
				while(true){
					clientSocket = sock.accept();
					//new RealtimeEngineHandler(clientSocket, couch_connection).start( );
					new RealtimeEngineHandler(clientSocket).start( );
				}
			} catch(Exception e) {
				System.err.println("Could not accept " + e);
				System.exit(1);
			}			
		}
	}
	
	class RealtimeEngineHandler extends Thread {
		Socket client_soc;
		long receiveNanoTime;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DatagramSocket ds = null;
		DatagramPacket dp;
		InetAddress ia = null;
		//CouchbaseClient couch_connection;
				
		RealtimeEngineHandler(Socket s) throws Exception {
			client_soc = s;
			//couch_connection = c;
			
//			try {
//				ia = InetAddress.getByName("192.168.1.190");
//			} catch (UnknownHostException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//			try {
//				ds = new DatagramSocket();
//			} catch (SocketException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		public void run( )
		{
			System.out.println("Socket starting: " + client_soc);
			try {
				DataInputStream is = new DataInputStream(client_soc.getInputStream( ));
				PrintStream os = new PrintStream(client_soc.getOutputStream( ), true);
			
				String line;
				while ((line = is.readLine()) != null) {
					if (line.length() == 808) {
						byte[] bdata = line.getBytes();
						int eventPos = 0;

						String[] NongHyupLog = new String[eventField.length];

						for(int i=0;i<eventSize.length;i++){
							byte[] temp = new byte[eventSize[i]];

							System.arraycopy(bdata, eventPos, temp, 0, eventSize[i]);
							NongHyupLog[i] =  new String(temp).replace("_", "").trim();
							eventPos += eventSize[i];
						}
						
						String logID = UUID.randomUUID().toString();
						Date now = new Date();
						String logDateTime = df.format(now);

						receiveNanoTime = System.nanoTime();

						String fds_mst = ";" + logID     + ";" +
									     logDateTime     + ";" +
									     receiveNanoTime + ";" +
									     NongHyupLog[2 ] + ";" + NongHyupLog[3 ] + ";" +
										 NongHyupLog[4 ] + ";" + NongHyupLog[5 ] + ";" +
										 NongHyupLog[6 ] + ";" + NongHyupLog[7 ] + ";" +
										 NongHyupLog[8 ] + ";" + NongHyupLog[9 ] + ";" +
										 NongHyupLog[10] + ";" + NongHyupLog[11] + ";" +
										 NongHyupLog[12] + ";" + NongHyupLog[13] + ";" +
										 //NongHyupLog[14] + ";" +
										 NongHyupLog[15] + ";" +
										 NongHyupLog[16] + ";" + NongHyupLog[17] + ";" +
										 NongHyupLog[18] + ";" + NongHyupLog[19] + ";" +
										 NongHyupLog[20] + ";" + NongHyupLog[21] + ";" +
										 NongHyupLog[22] + ";" + NongHyupLog[23] + ";" +
										 NongHyupLog[24] + ";" + NongHyupLog[25] + ";" +
										 NongHyupLog[26] + ";" + NongHyupLog[27] + ";" +
										 NongHyupLog[28] + ";" + NongHyupLog[29] + ";" +
										 NongHyupLog[30] + ";" + NongHyupLog[31] + ";" +
										 NongHyupLog[32] + ";" + NongHyupLog[33] + ";" +
										 NongHyupLog[34] + ";" + NongHyupLog[35] + ";" +
										 NongHyupLog[36] + ";" + NongHyupLog[37] + ";" +
										 NongHyupLog[38] + ";" + NongHyupLog[39] + ";" +
										 NongHyupLog[40] + ";" + NongHyupLog[41] + ";" +
										 NongHyupLog[42] + ";" + NongHyupLog[43] + ";" +
										 NongHyupLog[44] + ";" + NongHyupLog[45] + ";"; // +

//						System.out.println("fds_mst-->" + fds_mst);	
						
//						dp = new DatagramPacket(fds_mst.getBytes(Charset.forName("UTF-8")), fds_mst.getBytes(Charset.forName("UTF-8")).length, ia, 500);
//						try {
//							ds.send(dp);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}

//						NongHyupEvent event = new NongHyupEvent(client_soc, logID, logDateTime, receiveNanoTime,
//                                NongHyupLog[0 ], NongHyupLog[1 ], NongHyupLog[2 ], NongHyupLog[3 ], NongHyupLog[4 ],
//								NongHyupLog[5 ], NongHyupLog[6 ], NongHyupLog[7 ], NongHyupLog[8 ], NongHyupLog[9 ],
//								NongHyupLog[10], NongHyupLog[11], NongHyupLog[12], NongHyupLog[13], NongHyupLog[14],
//								NongHyupLog[15], NongHyupLog[16], NongHyupLog[17], NongHyupLog[18], NongHyupLog[19],
//								NongHyupLog[20], NongHyupLog[21], NongHyupLog[22], NongHyupLog[23], NongHyupLog[24],
//								NongHyupLog[25], NongHyupLog[26], NongHyupLog[27], NongHyupLog[28], NongHyupLog[29],
//								NongHyupLog[30], NongHyupLog[31], NongHyupLog[32], NongHyupLog[33], NongHyupLog[34],
//								NongHyupLog[35], NongHyupLog[36], NongHyupLog[37], NongHyupLog[38], NongHyupLog[39],
//								NongHyupLog[40], NongHyupLog[41], NongHyupLog[42], NongHyupLog[43], NongHyupLog[44],
//								NongHyupLog[45]
//								);

//						engine.getEngine().getEPRuntime().sendEvent(event);
						
						
//						Gson gson = new Gson();
//						String json = gson.toJson(event.getCpuID());  
//						System.out.println(json);
						//couch_connection.set(logID, gson.toJson(event).getBytes(Charset.forName("UTF-8")));


					}else {
						os.print("전문포맷 오류(전문길이==" + line.length()+ "): " + line);
						os.flush( );
						System.out.println("전문포맷 오류(전문길이==" + line.length()+ "): " + line);
					}									
				}
				client_soc.close( );
			} catch (IOException e) {
				System.out.println("IO Error on socket: " + e.getMessage());
				return;
			}
			System.out.println("Socket ENDED: " + client_soc);
		}
	}
	
	class apiServer extends Thread {
		private ServerSocket sock;
		private Socket clientSocket;
		private int serverPort;
		
		public apiServer(int serverPort) {
			super();
			this.serverPort = serverPort;
		}
		
		public void run() {
			try {
				sock = new ServerSocket(serverPort);
				System.out.println("API Server ready for connections.");
			
				while(true){
					clientSocket = sock.accept( );
					new apiHandler(clientSocket).start( );
				}
			} catch(IOException e) {
				System.err.println("Could not accept " + e);
				System.exit(1);
			}			
		}
	}	
		
	class apiHandler extends Thread {
		Socket client_soc;
		long receiveNanoTime;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DatagramSocket ds = null;
		DatagramPacket dp;
		InetAddress ia = null;
		
		apiHandler(Socket s) {
			client_soc = s;
		}
		
		public void run( )
		{
			System.out.println("Socket starting: " + client_soc);
			try {
				DataInputStream is = new DataInputStream(client_soc.getInputStream( ));
				PrintStream os = new PrintStream(client_soc.getOutputStream( ), true);
			
				String line = is.readLine();
				
				System.out.println(line);				
				
				String epl        = "INSERT INTO DataStream SELECT NH.* FROM NongHyupEvent AS NH, sql:logcenter_builtin_db ['select * from lcefdsblacklist where userid = ${userID}']"; // WHERE userID LIKE '%black%'";
				EPStatement stmt19 = engine.getEngine().getEPAdministrator().createEPL(epl);		
				RuleListener rListener19 = new RuleListener(engine, "RE01", "블랙리스트", "블랙리스트", "simple", 100, 5, "CRITICAL", 3, "BC");
				stmt19.addListener(rListener19);				
				
				client_soc.close( );	
			} catch (IOException e) {
				System.out.println("IO Error on socket " + e);
				return;
			}
			System.out.println("Socket ENDED: " + client_soc);
		}
	}
}
