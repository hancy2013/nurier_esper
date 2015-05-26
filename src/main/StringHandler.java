package main;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import event.NongHyupEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class StringHandler extends SimpleChannelInboundHandler<String> {

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
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		ByteBuf in = (ByteBuf) message;
		long receiveNanoTime;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
        try {
        	String line = in.toString(Charset.forName("UTF-8"));
        	
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

					System.out.println("FDS_MST-->" + fds_mst);	
					
//					dp = new DatagramPacket(fds_mst.getBytes(Charset.forName("UTF-8")), fds_mst.getBytes(Charset.forName("UTF-8")).length, ia, 500);
//					try {
//						ds.send(dp);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}				
					
					NongHyupEvent event = new NongHyupEvent(ctx, logID, logDateTime, receiveNanoTime,
                            NongHyupLog[0 ], NongHyupLog[1 ], NongHyupLog[2 ], NongHyupLog[3 ], NongHyupLog[4 ],
							NongHyupLog[5 ], NongHyupLog[6 ], NongHyupLog[7 ], NongHyupLog[8 ], NongHyupLog[9 ],
							NongHyupLog[10], NongHyupLog[11], NongHyupLog[12], NongHyupLog[13], NongHyupLog[14],
							NongHyupLog[15], NongHyupLog[16], NongHyupLog[17], NongHyupLog[18], NongHyupLog[19],
							NongHyupLog[20], NongHyupLog[21], NongHyupLog[22], NongHyupLog[23], NongHyupLog[24],
							NongHyupLog[25], NongHyupLog[26], NongHyupLog[27], NongHyupLog[28], NongHyupLog[29],
							NongHyupLog[30], NongHyupLog[31], NongHyupLog[32], NongHyupLog[33], NongHyupLog[34],
							NongHyupLog[35], NongHyupLog[36], NongHyupLog[37], NongHyupLog[38], NongHyupLog[39],
							NongHyupLog[40], NongHyupLog[41], NongHyupLog[42], NongHyupLog[43], NongHyupLog[44],
							NongHyupLog[45]
							);

					FdsPilotMain.getEngine().getEngine().getEPRuntime().sendEvent(event);
					
//					long detectMilliTime = System.currentTimeMillis();
//					while (System.currentTimeMillis() - detectMilliTime < 10) {
//						
//					}
					
					//Object retv = new String("aaa");
					ctx.write(message);
					ctx.flush();
					
					
//					Gson gson = new Gson();
//					String json = gson.toJson(event.getCpuID());  
//					System.out.println(json);
					//couch_connection.set(logID, gson.toJson(event).getBytes(Charset.forName("UTF-8")));


				}else {
					//os.print("전문포맷 오류(전문길이==" + line.length()+ "): " + line);
					//os.flush( );
					//System.out.println("전문포맷 오류(전문길이==" + line.length()+ "): " + line);
				}
        	
        }
        catch(Exception e){
            System.out.println("---------------- Reading Exception ----------------");
            e.printStackTrace();
        }finally{
            //ReferenceCountUtil.release(message);
        }
	}
		
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
//    }

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}


