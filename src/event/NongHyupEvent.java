package event;

import io.netty.channel.ChannelHandlerContext;

import java.net.Socket;

public class NongHyupEvent {
	private ChannelHandlerContext ctx;
	private String logID;            // 0 
	private String logDateTime; 
	private long   receiveNanoTime;
	
	private String logDate;          // 0 . logDate         ( 8  ) //순번. 필드명 (크기)
	private String logTime;          // 1 . logTime         ( 6  )
	private String userID;           // 2 . userID          ( 13 )
	private String userName;         // 3 . userName        ( 30 )
	private String jumin;            // 4 . jumin           ( 13 )
	private String hddSerial;        // 5 . hddSerial       ( 30 )
	private String macAddr;          // 6 . macAddr         ( 39 )
	private String realIP;           // 7 . realIP          ( 15 )
	private String mediaType;        // 8 . mediaType       ( 10 )
	private String transTarget;      // 9 . transTarget     ( 1  )
	private String isKeyboardBypass; // 10. isKeyboardBypas ( 1  )
	private String textSvcCode;      // 11. textSvcCode     ( 10 )
	private String tmaxSvcCode;      // 12. tmaxSvcCode     ( 10 )
	private String tranxCode;        // 13. tranxCode       ( 10 )
	private String filterSpace;      // 14. filterSpace     ( 10 )
	private   long amount;           // 15. amount          ( 20 )
	private String publicIP_1;       // 16. publicIP_1      ( 15 )
	private String publicIP_2;       // 17. publicIP_2      ( 15 )
	private String publicIP_3;       // 18. publicIP_3      ( 15 )
	private String privateIP_1;      // 19. privateIP_1     ( 15 )
	private String privateIP_2;      // 20. privateIP_2     ( 15 )
	private String privateIP_3;      // 21. privateIP_3     ( 15 )
	private String isProxy;          // 22. isProxy         ( 1  )
	private String proxyIP_1;        // 23. proxyIP_1       ( 15 )
	private String proxyIP_2;        // 24. proxyIP_2       ( 15 )
	private String isVPN;            // 25. isVPN           ( 1  )
	private String vpnIP_1;          // 26. vpnIP_1         ( 15 )
	private String vpnIP_2;          // 27. vpnIP_2         ( 15 )
	private String macAddr_1;        // 28. macAddr_1       ( 17 )
	private String macAddr_2;        // 29. macAddr_2       ( 17 )
	private String macAddr_3;        // 30. macAddr_3       ( 17 )
	private String logicalMAC_1;     // 31. logicalMAC_1    ( 17 )
	private String logicalMAC_2;     // 32. logicalMAC_2    ( 17 )
	private String logicalMAC_3;     // 33. logicalMAC_3    ( 17 )
	private String hddSerial_1;      // 34. hddSerial_1     ( 40 )
	private String hddSerial_2;      // 35. hddSerial_2     ( 40 )
	private String hddSerial_3;      // 36. hddSerial_3     ( 40 )
	private String cpuID;            // 37. cpuID           ( 40 )
	private String mbSN;             // 38. mbSN            ( 40 ) - 2014.06.02 변경(KTB), 크기: 40 --> 20
	private String osVersion;        // 39. osVersion       ( 40 )
	private String isVirtual;        // 40. isVirtual       ( 1  )
	private String virtualToolName;  // 41. virtualToolName ( 10 ) - 2014.06.02 변경(KTB), 크기: 10 --> 20
	private String gatewayMAC;       // 42. gatewayMAC      ( 17 )
	private String gatewayIP;        // 43. gatewayIP       ( 15 )
  //private String isDirtyMAC;       //   . isDirtyMAC      ( 1  ) - 2014.06.02 삭제(KTB)
	private String hddVolumeID;      // 44. hddVolumeID     ( 20 ) - 2014.06.02 변경(KTB), 크기: 20 --> 15
	private String remoteToolName;   // 45. IsRDP           ( 40 ) - 2014.06.02 변경(KTB), 크기:  1 --> 40

	private String ruleID;
	private String perfThroughput;
	
	public NongHyupEvent(   ChannelHandlerContext ctx,
			                String logID,
			                String logDateTime,
			                  long receiveNanoTime,			              
							String logDate,
							String logTime,
							String userID,
							String userName,
							String jumin,
							String hddSerial,
							String macAddr,
							String realIP,
							String mediaType,
							String transTarget,
							String isKeyboardBypass,
							String textSvcCode,
							String tmaxSvcCode,
							String tranxCode,
							String filterSpace,
							String amount,
							String publicIP_1,
							String publicIP_2,
							String publicIP_3,
							String privateIP_1,
							String privateIP_2,
							String privateIP_3,
							String isProxy,
							String proxyIP_1,
							String proxyIP_2,
							String isVPN,
							String vpnIP_1,
							String vpnIP_2,
							String macAddr_1,
							String macAddr_2,
							String macAddr_3,
							String logicalMAC_1,
							String logicalMAC_2,
							String logicalMAC_3,
							String hddSerial_1,
							String hddSerial_2,
							String hddSerial_3,
							String cpuID,
							String mbSN,
							String osVersion,
							String isVirtual,
							String virtualToolName,
							String gatewayMAC,
							String gatewayIP,
							String hddVolumeID,
							String remoteToolName
							) {
		this.ctx = ctx;
		this.logID              = logID;
		this.logDateTime        = logDateTime;
		this.receiveNanoTime    = receiveNanoTime;
		this.logDate 			= logDate.replace("_", "").trim();
		this.logTime 			= logTime.replace("_", "").trim();
		this.userID 			= userID.replace("_", "").trim();
		this.userName 			= userName.replace("_", "").trim();
		this.jumin 				= jumin.replace("_", "").trim();
		this.hddSerial 			= hddSerial.replace("_", "").trim();
		this.macAddr 			= macAddr.replace("_", "").trim();
		this.realIP 			= realIP.replace("_", "").trim();
		this.mediaType 			= mediaType.replace("_", "").trim();
		this.transTarget 		= transTarget.replace("_", "").trim();
		this.isKeyboardBypass 	= isKeyboardBypass.replace("_", "").trim();
		this.textSvcCode 		= textSvcCode.replace("_", "").trim();
		this.tmaxSvcCode 		= tmaxSvcCode.replace("_", "").trim();
		this.tranxCode 			= tranxCode.replace("_", "").trim();
		this.filterSpace 		= filterSpace.replace("_", "").trim();
		this.amount 			= Long.parseLong(amount.replace("_", "").trim());
		this.publicIP_1 		= publicIP_1.replace("_", "").trim();
		this.publicIP_2 		= publicIP_2.replace("_", "").trim();
		this.publicIP_3 		= publicIP_3.replace("_", "").trim();
		this.privateIP_1 		= privateIP_1.replace("_", "").trim();
		this.privateIP_2 		= privateIP_2.replace("_", "").trim();
		this.privateIP_3 		= privateIP_3.replace("_", "").trim();
		this.isProxy 			= isProxy.replace("_", "").trim();
		this.proxyIP_1 			= proxyIP_1.replace("_", "").trim();
		this.proxyIP_2 			= proxyIP_2.replace("_", "").trim();
		this.isVPN 				= isVPN.replace("_", "").trim();
		this.vpnIP_1 			= vpnIP_1.replace("_", "").trim();
		this.vpnIP_2 			= vpnIP_2.replace("_", "").trim();
		this.macAddr_1	 		= macAddr_1.replace("_", "").trim();
		this.macAddr_2 			= macAddr_2.replace("_", "").trim();
		this.macAddr_3 			= macAddr_3.replace("_", "").trim();
		this.logicalMAC_1 		= logicalMAC_1.replace("_", "").trim();
		this.logicalMAC_2 		= logicalMAC_2.replace("_", "").trim();
		this.logicalMAC_3 		= logicalMAC_3.replace("_", "").trim();
		this.hddSerial_1 		= hddSerial_1.replace("_", "").trim();
		this.hddSerial_2		= hddSerial_2.replace("_", "").trim();
		this.hddSerial_3 		= hddSerial_3.replace("_", "").trim();
		this.cpuID 				= cpuID.replace("_", "").trim();
		this.mbSN 				= mbSN.replace("_", "").trim();
		this.osVersion 			= osVersion.replace("_", "").trim();
		this.isVirtual 			= isVirtual.replace("_", "").trim();
		this.virtualToolName 	= virtualToolName.replace("_", "").trim();
		this.gatewayMAC 		= gatewayMAC.replace("_", "").trim();
		this.gatewayIP 			= gatewayIP.replace("_", "").trim();
		this.hddVolumeID 		= hddVolumeID.replace("_", "").trim();
		this.remoteToolName 	= remoteToolName.replace("_", "").trim();
		}
	
	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public String getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(String logDateTime) {
		this.logDateTime = logDateTime;
	}

	public long getReceiveNanoTime() {
		return receiveNanoTime;
	}

	public void setReceiveNanoTime(long receiveNanoTime) {
		this.receiveNanoTime = receiveNanoTime;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getLogID() {
		return logID;
	}

	public String getMbSN() {
		return mbSN;
	}

	public void setMbSN(String mbSN) {
		this.mbSN = mbSN;
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
	
	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public String getHddSerial() {
		return hddSerial;
	}

	public void setHddSerial(String hddSerial) {
		this.hddSerial = hddSerial;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getRealIP() {
		return realIP;
	}

	public void setRealIP(String realIP) {
		this.realIP = realIP;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getTransTarget() {
		return transTarget;
	}

	public void setTransTarget(String transTarget) {
		this.transTarget = transTarget;
	}

	public String getIsKeyboardBypass() {
		return isKeyboardBypass;
	}

	public void setIsKeyboardBypass(String isKeyboardBypass) {
		this.isKeyboardBypass = isKeyboardBypass;
	}

	public String getTextSvcCode() {
		return textSvcCode;
	}

	public void setTextSvcCode(String textSvcCode) {
		this.textSvcCode = textSvcCode;
	}

	public String getTmaxSvcCode() {
		return tmaxSvcCode;
	}

	public void setTmaxSvcCode(String tmaxSvcCode) {
		this.tmaxSvcCode = tmaxSvcCode;
	}

	public String getTranxCode() {
		return tranxCode;
	}

	public void setTranxCode(String tranxCode) {
		this.tranxCode = tranxCode;
	}

	public String getFilterSpace() {
		return filterSpace;
	}

	public void setFilterSpace(String filterSpace) {
		this.filterSpace = filterSpace;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getPublicIP_1() {
		return publicIP_1;
	}

	public void setPublicIP_1(String publicIP_1) {
		this.publicIP_1 = publicIP_1;
	}

	public String getPublicIP_2() {
		return publicIP_2;
	}

	public void setPublicIP_2(String publicIP_2) {
		this.publicIP_2 = publicIP_2;
	}

	public String getPublicIP_3() {
		return publicIP_3;
	}

	public void setPublicIP_3(String publicIP_3) {
		this.publicIP_3 = publicIP_3;
	}

	public String getPrivateIP_1() {
		return privateIP_1;
	}

	public void setPrivateIP_1(String privateIP_1) {
		this.privateIP_1 = privateIP_1;
	}

	public String getPrivateIP_2() {
		return privateIP_2;
	}

	public void setPrivateIP_2(String privateIP_2) {
		this.privateIP_2 = privateIP_2;
	}

	public String getPrivateIP_3() {
		return privateIP_3;
	}

	public void setPrivateIP_3(String privateIP_3) {
		this.privateIP_3 = privateIP_3;
	}

	public String getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}

	public String getProxyIP_1() {
		return proxyIP_1;
	}

	public void setProxyIP_1(String proxyIP_1) {
		this.proxyIP_1 = proxyIP_1;
	}

	public String getProxyIP_2() {
		return proxyIP_2;
	}

	public void setProxyIP_2(String proxyIP_2) {
		this.proxyIP_2 = proxyIP_2;
	}

	public String getIsVPN() {
		return isVPN;
	}

	public void setIsVPN(String isVPN) {
		this.isVPN = isVPN;
	}

	public String getVpnIP_1() {
		return vpnIP_1;
	}

	public void setVpnIP_1(String vpnIP_1) {
		this.vpnIP_1 = vpnIP_1;
	}

	public String getVpnIP_2() {
		return vpnIP_2;
	}

	public void setVpnIP_2(String vpnIP_2) {
		this.vpnIP_2 = vpnIP_2;
	}

	public String getMacAddr_1() {
		return macAddr_1;
	}

	public void setMacAddr_1(String macAddr_1) {
		this.macAddr_1 = macAddr_1;
	}

	public String getMacAddr_2() {
		return macAddr_2;
	}

	public void setMacAddr_2(String macAddr_2) {
		this.macAddr_2 = macAddr_2;
	}

	public String getMacAddr_3() {
		return macAddr_3;
	}

	public void setMacAddr_3(String macAddr_3) {
		this.macAddr_3 = macAddr_3;
	}

	public String getLogicalMAC_1() {
		return logicalMAC_1;
	}

	public void setLogicalMAC_1(String logicalMAC_1) {
		this.logicalMAC_1 = logicalMAC_1;
	}

	public String getLogicalMAC_2() {
		return logicalMAC_2;
	}

	public void setLogicalMAC_2(String logicalMAC_2) {
		this.logicalMAC_2 = logicalMAC_2;
	}

	public String getLogicalMAC_3() {
		return logicalMAC_3;
	}

	public void setLogicalMAC_3(String logicalMAC_3) {
		this.logicalMAC_3 = logicalMAC_3;
	}

	public String getHddSerial_1() {
		return hddSerial_1;
	}

	public void setHddSerial_1(String hddSerial_1) {
		this.hddSerial_1 = hddSerial_1;
	}

	public String getHddSerial_2() {
		return hddSerial_2;
	}

	public void setHddSerial_2(String hddSerial_2) {
		this.hddSerial_2 = hddSerial_2;
	}

	public String getHddSerial_3() {
		return hddSerial_3;
	}

	public void setHddSerial_3(String hddSerial_3) {
		this.hddSerial_3 = hddSerial_3;
	}

	public String getCpuID() {
		return cpuID;
	}

	public void setCpuID(String cpuID) {
		this.cpuID = cpuID;
	}

	public String getmBSN() {
		return mbSN;
	}

	public void setmBSN(String mbSN) {
		this.mbSN = mbSN;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getVirtualToolName() {
		return virtualToolName;
	}

	public void setVirtualToolName(String virtualToolName) {
		this.virtualToolName = virtualToolName;
	}

	public String getGatewayMAC() {
		return gatewayMAC;
	}

	public void setGatewayMAC(String gatewayMAC) {
		this.gatewayMAC = gatewayMAC;
	}

	public String getGatewayIP() {
		return gatewayIP;
	}

	public void setGatewayIP(String gatewayIP) {
		this.gatewayIP = gatewayIP;
	}

//	public String getIsDirtyMAC() {
//		return isDirtyMAC;
//	}
//
//	public void setIsDirtyMAC(String isDirtyMAC) {
//		this.isDirtyMAC = isDirtyMAC;
//	}

	public String getHddVolumeID() {
		return hddVolumeID;
	}

	public void setHddVolumeID(String hddVolumeID) {
		this.hddVolumeID = hddVolumeID;
	}

	public String getRemoteToolName() {
		return remoteToolName;
	}

	public void setRemoteToolName(String remoteToolName) {
		this.remoteToolName = remoteToolName;
	}

	public String getPerfThroughput() {
		return perfThroughput;
	}

	public void setPerfThroughput(String perfThroughput) {
		this.perfThroughput = perfThroughput;
	}	
}
