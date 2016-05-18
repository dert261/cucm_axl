package ru.obelisk.cucmaxl.ftp.csv;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

@Data
public class CsvCucmCdr {
		
	@Parsed(field="cdrRecordType")
	private Integer cdrRecordType;

	@Parsed(field="globalCallID_callManagerId")
	private Integer globalCallIdCallManagerId;

	@Parsed(field="globalCallID_callId")
	private Integer globalCallIdCallId;

	@Parsed(field="origLegCallIdentifier")
	private Integer origLegCallIdentifier;

	@Parsed(field="dateTimeOrigination")
	private Integer dateTimeOrigination;

	@Parsed(field="origNodeId")
	private Integer origNodeId;

	@Parsed(field="origSpan")
	private Integer origSpan;

	@Parsed(field="origIpAddr")
	private Integer origIpAddr;

	@Parsed(field="callingPartyNumber")
	private String callingPartyNumber;

	@Parsed(field="callingPartyUnicodeLoginUserID")
	private String callingPartyUnicodeLoginUserId;

	@Parsed(field="origCause_location")
	private Integer origCauseLocation;

	@Parsed(field="origCause_value")
	private Integer origCauseValue;

	@Parsed(field="origPrecedenceLevel")
	private Integer origPrecedenceLevel;

	@Parsed(field="origMediaTransportAddress_IP")
	private Integer origMediaTransportAddressIP;

	@Parsed(field="origMediaTransportAddress_Port")
	private Integer origMediaTransportAddressPort;

	@Parsed(field="origMediaCap_payloadCapability")
	private Integer origMediaCapPayloadCapability;

	@Parsed(field="origMediaCap_maxFramesPerPacket")
	private Integer origMediaCapMaxFramesPerPacket;

	@Parsed(field="origMediaCap_g723BitRate")
	private Integer origMediaCapG723BitRate;

	@Parsed(field="origVideoCap_Codec")
	private Integer origVideoCapCodec;

	@Parsed(field="origVideoCap_Bandwidth")
	private Integer origVideoCapBandwidth;

	@Parsed(field="origVideoCap_Resolution")
	private Integer origVideoCapResolution;

	@Parsed(field="origVideoTransportAddress_IP")
	private Integer origVideoTransportAddressIp;

	@Parsed(field="origVideoTransportAddress_Port")
	private Integer origVideoTransportAddressPort;

	@Parsed(field="origRSVPAudioStat")
	private String origRSVPAudioStat;

	@Parsed(field="origRSVPVideoStat")
	private String origRSVPVideoStat;

	@Parsed(field="destLegIdentifier")
	private Integer destLegIdentifier;

	@Parsed(field="destNodeId")
	private Integer destNodeId;

	@Parsed(field="destSpan")
	private Integer destSpan;

	@Parsed(field="destIpAddr")
	private Integer destIpAddr;

	@Parsed(field="originalCalledPartyNumber")
	private String originalCalledPartyNumber;

	@Parsed(field="finalCalledPartyNumber")
	private String finalCalledPartyNumber;

	@Parsed(field="finalCalledPartyUnicodeLoginUserID")
	private String finalCalledPartyUnicodeLoginUserId;

	@Parsed(field="destCause_location")
	private Integer destCauseLocation;

	@Parsed(field="destCause_value")
	private Integer destCauseValue;

	@Parsed(field="destPrecedenceLevel")
	private Integer destPrecedenceLevel;

	@Parsed(field="destMediaTransportAddress_IP")
	private Integer destMediaTransportAddressIp;

	@Parsed(field="destMediaTransportAddress_Port")
	private Integer destMediaTransportAddressPort;

	@Parsed(field="destMediaCap_payloadCapability")
	private Integer destMediaCapPayloadCapability;

	@Parsed(field="destMediaCap_maxFramesPerPacket")
	private Integer destMediaCapMaxFramesPerPacket;

	@Parsed(field="destMediaCap_g723BitRate")
	private Integer destMediaCapG723BitRate;

	@Parsed(field="destVideoCap_Codec")
	private Integer destVideoCapCodec;

	@Parsed(field="destVideoCap_Bandwidth")
	private Integer destVideoCapBandwidth;

	@Parsed(field="destVideoCap_Resolution")
	private Integer destVideoCapResolution;

	@Parsed(field="destVideoTransportAddress_IP")
	private Integer destVideoTransportAddressIp;

	@Parsed(field="destVideoTransportAddress_Port")
	private Integer destVideoTransportAddressPort;

	@Parsed(field="destRSVPAudioStat")
	private String destRSVPAudioStat;

	@Parsed(field="destRSVPVideoStat")
	private String destRSVPVideoStat;

	@Parsed(field="dateTimeConnect")
	private Integer dateTimeConnect;

	@Parsed(field="dateTimeDisconnect")
	private Integer dateTimeDisconnect;

	@Parsed(field="lastRedirectDn")
	private String lastRedirectDn;

	@Parsed(field="pkid")
	private String pkid;

	@Parsed(field="originalCalledPartyNumberPartition")
	private String originalCalledPartyNumberPartition;

	@Parsed(field="callingPartyNumberPartition")
	private String callingPartyNumberPartition;

	@Parsed(field="finalCalledPartyNumberPartition")
	private String finalCalledPartyNumberPartition;

	@Parsed(field="lastRedirectDnPartition")
	private String lastRedirectDnPartition;

	@Parsed(field="duration")
	private Integer duration;

	@Parsed(field="origDeviceName")
	private String origDeviceName;

	@Parsed(field="destDeviceName")
	private String destDeviceName;

	@Parsed(field="origCallTerminationOnBehalfOf")
	private Integer origCallTerminationOnBehalfOf;

	@Parsed(field="destCallTerminationOnBehalfOf")
	private Integer destCallTerminationOnBehalfOf;

	@Parsed(field="origCalledPartyRedirectOnBehalfOf")
	private Integer origCalledPartyRedirectOnBehalfOf;

	@Parsed(field="lastRedirectRedirectOnBehalfOf")
	private Integer lastRedirectRedirectOnBehalfOf;

	@Parsed(field="origCalledPartyRedirectReason")
	private Integer origCalledPartyRedirectReason;

	@Parsed(field="lastRedirectRedirectReason")
	private Integer lastRedirectRedirectReason;

	@Parsed(field="destConversationId")
	private Integer destConversationId;

	@Parsed(field="globalCallId_ClusterID")
	private String globalCallIdClusterId;

	@Parsed(field="joinOnBehalfOf")
	private Integer joinOnBehalfOf;

	@Parsed(field="comment")
	private String comment;

	@Parsed(field="authCodeDescription")
	private String authCodeDescription;

	@Parsed(field="authorizationLevel")
	private Integer authorizationLevel;

	@Parsed(field="clientMatterCode")
	private String clientMatterCode;

	@Parsed(field="origDTMFMethod")
	private Integer origDTMFMethod;

	@Parsed(field="destDTMFMethod")
	private Integer destDTMFMethod;

	@Parsed(field="callSecuredStatus")
	private Integer callSecuredStatus;

	@Parsed(field="origConversationId")
	private Integer origConversationId;

	@Parsed(field="origMediaCap_Bandwidth")
	private Integer origMediaCapBandwidth;

	@Parsed(field="destMediaCap_Bandwidth")
	private Integer destMediaCapBandwidth;

	@Parsed(field="authorizationCodeValue")
	private String authorizationCodeValue;

	@Parsed(field="outpulsedCallingPartyNumber")
	private String outpulsedCallingPartyNumber;

	@Parsed(field="outpulsedCalledPartyNumber")
	private String outpulsedCalledPartyNumber;

	@Parsed(field="origIpv4v6Addr")
	private String origIpv4v6Addr;

	@Parsed(field="destIpv4v6Addr")
	private String destIpv4v6Addr;

	@Parsed(field="origVideoCap_Codec_Channel2")
	private Integer origVideoCapCodecChannel2;

	@Parsed(field="origVideoCap_Bandwidth_Channel2")
	private Integer origVideoCapBandwidthChannel2;

	@Parsed(field="origVideoCap_Resolution_Channel2")
	private Integer origVideoCapResolutionChannel2;

	@Parsed(field="origVideoTransportAddress_IP_Channel2")
	private Integer origVideoTransportAddressIPChannel2;

	@Parsed(field="origVideoTransportAddress_Port_Channel2")
	private Integer origVideoTransportAddressPortChannel2;

	@Parsed(field="origVideoChannel_Role_Channel2")
	private Integer origVideoChannelRoleChannel2;

	@Parsed(field="destVideoCap_Codec_Channel2")
	private Integer destVideoCapCodecChannel2;

	@Parsed(field="destVideoCap_Bandwidth_Channel2")
	private Integer destVideoCapBandwidthChannel2;

	@Parsed(field="destVideoCap_Resolution_Channel2")
	private Integer destVideoCapResolutionChannel2;

	@Parsed(field="destVideoTransportAddress_IP_Channel2")
	private Integer destVideoTransportAddressIpChannel2;

	@Parsed(field="destVideoTransportAddress_Port_Channel2")
	private Integer destVideoTransportAddressPortChannel2;

	@Parsed(field="destVideoChannel_Role_Channel2")
	private Integer destVideoChannelRoleChannel2;

	@Parsed(field="IncomingProtocolID")
	private Integer incomingProtocolId;

	@Parsed(field="IncomingProtocolCallRef")
	private String incomingProtocolCallRef;

	@Parsed(field="OutgoingProtocolID")
	private Integer outgoingProtocolId;

	@Parsed(field="OutgoingProtocolCallRef")
	private String outgoingProtocolCallRef;

	@Parsed(field="currentRoutingReason")
	private Integer currentRoutingReason;

	@Parsed(field="origRoutingReason")
	private Integer origRoutingReason;

	@Parsed(field="lastRedirectingRoutingReason")
	private Integer lastRedirectingRoutingReason;

	@Parsed(field="huntPilotPartition")
	private String huntPilotPartition;

	@Parsed(field="huntPilotDN")
	private String huntPilotDn;

	@Parsed(field="calledPartyPatternUsage")
	private Integer calledPartyPatternUsage;

	@Parsed(field="IncomingICID")
	private String incomingICId;

	@Parsed(field="IncomingOrigIOI")
	private String incomingOrigIOI;

	@Parsed(field="IncomingTermIOI")
	private String incomingTermIOI;

	@Parsed(field="OutgoingICID")
	private String outgoingICId;

	@Parsed(field="OutgoingOrigIOI")
	private String outgoingOrigIOI;

	@Parsed(field="OutgoingTermIOI")
	private String outgoingTermIOI;

	@Parsed(field="outpulsedOriginalCalledPartyNumber")
	private String outpulsedOriginalCalledPartyNumber;

	@Parsed(field="outpulsedLastRedirectingNumber")
	private String outpulsedLastRedirectingNumber;

	@Parsed(field="wasCallQueued")
	private Integer wasCallQueued;

	@Parsed(field="totalWaitTimeInQueue")
	private Integer totalWaitTimeInQueue;

	@Parsed(field="callingPartyNumber_uri")
	private String callingPartyNumberUri;

	@Parsed(field="originalCalledPartyNumber_uri")
	private String originalCalledPartyNumberUri;

	@Parsed(field="finalCalledPartyNumber_uri")
	private String finalCalledPartyNumberUri;

	@Parsed(field="lastRedirectDn_uri")
	private String lastRedirectDnUri;

	@Parsed(field="mobileCallingPartyNumber")
	private String mobileCallingPartyNumber;

	@Parsed(field="finalMobileCalledPartyNumber")
	private String finalMobileCalledPartyNumber;

	@Parsed(field="origMobileDeviceName")
	private String origMobileDeviceName;

	@Parsed(field="destMobileDeviceName")
	private String destMobileDeviceName;

	@Parsed(field="origMobileCallDuration")
	private Integer origMobileCallDuration;

	@Parsed(field="destMobileCallDuration")
	private Integer destMobileCallDuration;

	@Parsed(field="mobileCallType")
	private Integer mobileCallType;

	@Parsed(field="originalCalledPartyPattern")
	private String originalCalledPartyPattern;

	@Parsed(field="finalCalledPartyPattern")
	private String finalCalledPartyPattern;

	@Parsed(field="lastRedirectingPartyPattern")
	private String lastRedirectingPartyPattern;

	@Parsed(field="huntPilotPattern")
	private String huntPilotPattern;


}
