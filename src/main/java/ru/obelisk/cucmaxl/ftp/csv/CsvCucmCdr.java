package ru.obelisk.cucmaxl.ftp.csv;

import com.univocity.parsers.annotations.Parsed;

import lombok.Data;
import ru.obelisk.cucmaxl.ftp.annotation.TableColumn;
import ru.obelisk.cucmaxl.ftp.annotation.TableName;

@Data
@TableName(name="cucm_cdr")
public class CsvCucmCdr {
		
	@Parsed(field="cdrRecordType")
	@TableColumn(name="cdr_record_type")
	private Integer cdrRecordType;

	@Parsed(field="globalCallID_callManagerId")
	@TableColumn(name="global_call_id_call_manager_id")
	private Integer globalCallIdCallManagerId;
	
	@Parsed(field="globalCallID_callId")
	@TableColumn(name="global_call_id_call_id")
	private Integer globalCallIdCallId;

	@Parsed(field="origLegCallIdentifier")
	@TableColumn(name="orig_leg_call_identifier")
	private Integer origLegCallIdentifier;

	@Parsed(field="dateTimeOrigination")
	@TableColumn(name="date_time_origination")
	private Integer dateTimeOrigination;

	@Parsed(field="origNodeId")
	@TableColumn(name="orig_node_id")
	private Integer origNodeId;

	@Parsed(field="origSpan")
	@TableColumn(name="orig_span")
	private Integer origSpan;

	@Parsed(field="origIpAddr")
	@TableColumn(name="orig_ip_addr")
	private Integer origIpAddr;

	@Parsed(field="callingPartyNumber")
	@TableColumn(name="calling_party_number")
	private String callingPartyNumber;

	@Parsed(field="callingPartyUnicodeLoginUserID")
	@TableColumn(name="calling_party_unicode_login_user_id")
	private String callingPartyUnicodeLoginUserId;

	@Parsed(field="origCause_location")
	@TableColumn(name="orig_cause_location")
	private Integer origCauseLocation;

	@Parsed(field="origCause_value")
	@TableColumn(name="orig_cause_value")
	private Integer origCauseValue;

	@Parsed(field="origPrecedenceLevel")
	@TableColumn(name="orig_precedence_level")
	private Integer origPrecedenceLevel;

	@Parsed(field="origMediaTransportAddress_IP")
	@TableColumn(name="orig_media_transport_address_ip")
	private Integer origMediaTransportAddressIP;

	@Parsed(field="origMediaTransportAddress_Port")
	@TableColumn(name="orig_media_transport_address_port")
	private Integer origMediaTransportAddressPort;

	@Parsed(field="origMediaCap_payloadCapability")
	@TableColumn(name="orig_media_cap_payload_capability")
	private Integer origMediaCapPayloadCapability;

	@Parsed(field="origMediaCap_maxFramesPerPacket")
	@TableColumn(name="orig_media_cap_max_frames_per_packet")
	private Integer origMediaCapMaxFramesPerPacket;

	@Parsed(field="origMediaCap_g723BitRate")
	@TableColumn(name="orig_media_cap_g723bit_rate")
	private Integer origMediaCapG723BitRate;

	@Parsed(field="origVideoCap_Codec")
	@TableColumn(name="orig_video_cap_codec")
	private Integer origVideoCapCodec;

	@Parsed(field="origVideoCap_Bandwidth")
	@TableColumn(name="orig_video_cap_bandwidth")
	private Integer origVideoCapBandwidth;

	@Parsed(field="origVideoCap_Resolution")
	@TableColumn(name="orig_video_cap_resolution")
	private Integer origVideoCapResolution;
	
	@Parsed(field="origVideoTransportAddress_IP")
	@TableColumn(name="orig_video_transport_address_ip")
	private Integer origVideoTransportAddressIp;

	@Parsed(field="origVideoTransportAddress_Port")
	@TableColumn(name="orig_video_transport_address_port")
	private Integer origVideoTransportAddressPort;

	@Parsed(field="origRSVPAudioStat")
	@TableColumn(name="orig_rsvp_audio_stat")
	private String origRSVPAudioStat;

	@Parsed(field="origRSVPVideoStat")
	@TableColumn(name="orig_rsvp_video_stat")
	private String origRSVPVideoStat;

	@Parsed(field="destLegIdentifier")
	@TableColumn(name="dest_leg_identifier")
	private Integer destLegIdentifier;

	@Parsed(field="destNodeId")
	@TableColumn(name="dest_node_id")
	private Integer destNodeId;

	@Parsed(field="destSpan")
	@TableColumn(name="dest_span")
	private Integer destSpan;

	@Parsed(field="destIpAddr")
	@TableColumn(name="dest_ip_addr")
	private Integer destIpAddr;

	@Parsed(field="originalCalledPartyNumber")
	@TableColumn(name="original_called_party_number")
	private String originalCalledPartyNumber;

	@Parsed(field="finalCalledPartyNumber")
	@TableColumn(name="final_called_party_number")
	private String finalCalledPartyNumber;

	@Parsed(field="finalCalledPartyUnicodeLoginUserID")
	@TableColumn(name="final_called_party_unicode_login_user_id")
	private String finalCalledPartyUnicodeLoginUserId;

	@Parsed(field="destCause_location")
	@TableColumn(name="dest_cause_location")
	private Integer destCauseLocation;

	@Parsed(field="destCause_value")
	@TableColumn(name="dest_cause_value")
	private Integer destCauseValue;

	@Parsed(field="destPrecedenceLevel")
	@TableColumn(name="dest_precedence_level")
	private Integer destPrecedenceLevel;

	@Parsed(field="destMediaTransportAddress_IP")
	@TableColumn(name="dest_media_transport_address_ip")
	private Integer destMediaTransportAddressIp;

	@Parsed(field="destMediaTransportAddress_Port")
	@TableColumn(name="dest_media_transport_address_port")
	private Integer destMediaTransportAddressPort;

	@Parsed(field="destMediaCap_payloadCapability")
	@TableColumn(name="dest_media_cap_payload_capability")
	private Integer destMediaCapPayloadCapability;

	@Parsed(field="destMediaCap_maxFramesPerPacket")
	@TableColumn(name="dest_media_cap_max_frames_per_packet")
	private Integer destMediaCapMaxFramesPerPacket;

	@Parsed(field="destMediaCap_g723BitRate")
	@TableColumn(name="dest_media_cap_g723bit_rate")
	private Integer destMediaCapG723BitRate;
	
	@Parsed(field="destVideoCap_Codec")
	@TableColumn(name="dest_video_cap_codec")
	private Integer destVideoCapCodec;

	@Parsed(field="destVideoCap_Bandwidth")
	@TableColumn(name="dest_video_cap_bandwidth")
	private Integer destVideoCapBandwidth;

	@Parsed(field="destVideoCap_Resolution")
	@TableColumn(name="dest_video_cap_resolution")
	private Integer destVideoCapResolution;

	@Parsed(field="destVideoTransportAddress_IP")
	@TableColumn(name="dest_video_transport_address_ip")
	private Integer destVideoTransportAddressIp;

	@Parsed(field="destVideoTransportAddress_Port")
	@TableColumn(name="dest_video_transport_address_port")
	private Integer destVideoTransportAddressPort;

	@Parsed(field="destRSVPAudioStat")
	@TableColumn(name="dest_rsvp_audio_stat")
	private String destRSVPAudioStat;

	@Parsed(field="destRSVPVideoStat")
	@TableColumn(name="dest_rsvp_video_stat")
	private String destRSVPVideoStat;

	@Parsed(field="dateTimeConnect")
	@TableColumn(name="date_time_connect")
	private Integer dateTimeConnect;

	@Parsed(field="dateTimeDisconnect")
	@TableColumn(name="date_time_disconnect")
	private Integer dateTimeDisconnect;

	@Parsed(field="lastRedirectDn")
	@TableColumn(name="last_redirect_dn")
	private String lastRedirectDn;

	@Parsed(field="pkid")
	@TableColumn(name="pkid")
	private String pkid;

	@Parsed(field="originalCalledPartyNumberPartition")
	@TableColumn(name="original_called_party_number_partition")
	private String originalCalledPartyNumberPartition;

	@Parsed(field="callingPartyNumberPartition")
	@TableColumn(name="calling_party_number_partition")
	private String callingPartyNumberPartition;

	@Parsed(field="finalCalledPartyNumberPartition")
	@TableColumn(name="final_called_party_number_partition")
	private String finalCalledPartyNumberPartition;

	@Parsed(field="lastRedirectDnPartition")
	@TableColumn(name="last_redirect_dn_partition")
	private String lastRedirectDnPartition;

	@Parsed(field="duration")
	@TableColumn(name="duration")
	private Integer duration;

	@Parsed(field="origDeviceName")
	@TableColumn(name="orig_device_name")
	private String origDeviceName;

	@Parsed(field="destDeviceName")
	@TableColumn(name="dest_device_name")
	private String destDeviceName;

	@Parsed(field="origCallTerminationOnBehalfOf")
	@TableColumn(name="orig_call_termination_on_behalf_of")
	private Integer origCallTerminationOnBehalfOf;

	@Parsed(field="destCallTerminationOnBehalfOf")
	@TableColumn(name="dest_call_termination_on_behalf_of")
	private Integer destCallTerminationOnBehalfOf;

	@Parsed(field="origCalledPartyRedirectOnBehalfOf")
	@TableColumn(name="orig_called_party_redirect_on_behalf_of")
	private Integer origCalledPartyRedirectOnBehalfOf;

	@Parsed(field="lastRedirectRedirectOnBehalfOf")
	@TableColumn(name="last_redirect_redirect_on_behalf_of")
	private Integer lastRedirectRedirectOnBehalfOf;

	@Parsed(field="origCalledPartyRedirectReason")
	@TableColumn(name="orig_called_party_redirect_reason")
	private Integer origCalledPartyRedirectReason;

	@Parsed(field="lastRedirectRedirectReason")
	@TableColumn(name="last_redirect_redirect_reason")
	private Integer lastRedirectRedirectReason;

	@Parsed(field="destConversationId")
	@TableColumn(name="dest_conversation_id")
	private Integer destConversationId;

	@Parsed(field="globalCallId_ClusterID")
	@TableColumn(name="global_call_id_cluster_id")
	private String globalCallIdClusterId;

	@Parsed(field="joinOnBehalfOf")
	@TableColumn(name="join_on_behalf_of")
	private Integer joinOnBehalfOf;

	@Parsed(field="comment")
	@TableColumn(name="comment")
	private String comment;

	@Parsed(field="authCodeDescription")
	@TableColumn(name="auth_code_description")
	private String authCodeDescription;

	@Parsed(field="authorizationLevel")
	@TableColumn(name="authorization_level")
	private Integer authorizationLevel;

	@Parsed(field="clientMatterCode")
	@TableColumn(name="client_matter_code")
	private String clientMatterCode;

	@Parsed(field="origDTMFMethod")
	@TableColumn(name="orig_dtmf_method")
	private Integer origDTMFMethod;

	@Parsed(field="destDTMFMethod")
	@TableColumn(name="dest_dtmf_method")
	private Integer destDTMFMethod;

	@Parsed(field="callSecuredStatus")
	@TableColumn(name="call_secured_status")
	private Integer callSecuredStatus;

	@Parsed(field="origConversationId")
	@TableColumn(name="orig_conversation_id")
	private Integer origConversationId;

	@Parsed(field="origMediaCap_Bandwidth")
	@TableColumn(name="orig_media_cap_bandwidth")
	private Integer origMediaCapBandwidth;

	@Parsed(field="destMediaCap_Bandwidth")
	@TableColumn(name="dest_media_cap_bandwidth")
	private Integer destMediaCapBandwidth;

	@Parsed(field="authorizationCodeValue")
	@TableColumn(name="authorization_code_value")
	private String authorizationCodeValue;

	@Parsed(field="outpulsedCallingPartyNumber")
	@TableColumn(name="outpulsed_calling_party_number")
	private String outpulsedCallingPartyNumber;

	@Parsed(field="outpulsedCalledPartyNumber")
	@TableColumn(name="outpulsed_called_party_number")
	private String outpulsedCalledPartyNumber;

	@Parsed(field="origIpv4v6Addr")
	@TableColumn(name="orig_ipv4v6_addr")
	private String origIpv4v6Addr;
	
	@Parsed(field="destIpv4v6Addr")
	@TableColumn(name="dest_ipv4v6_addr")
	private String destIpv4v6Addr;

	@Parsed(field="origVideoCap_Codec_Channel2")
	@TableColumn(name="orig_video_cap_codec_channel2")
	private Integer origVideoCapCodecChannel2;

	@Parsed(field="origVideoCap_Bandwidth_Channel2")
	@TableColumn(name="orig_video_cap_bandwidth_channel2")
	private Integer origVideoCapBandwidthChannel2;

	@Parsed(field="origVideoCap_Resolution_Channel2")
	@TableColumn(name="orig_video_cap_resolution_channel2")
	private Integer origVideoCapResolutionChannel2;

	@Parsed(field="origVideoTransportAddress_IP_Channel2")
	@TableColumn(name="orig_video_transport_address_ip_channel2")
	private Integer origVideoTransportAddressIPChannel2;

	@Parsed(field="origVideoTransportAddress_Port_Channel2")
	@TableColumn(name="orig_video_transport_address_port_channel2")
	private Integer origVideoTransportAddressPortChannel2;

	@Parsed(field="origVideoChannel_Role_Channel2")
	@TableColumn(name="orig_video_channel_role_channel2")
	private Integer origVideoChannelRoleChannel2;

	@Parsed(field="destVideoCap_Codec_Channel2")
	@TableColumn(name="dest_video_cap_codec_channel2")
	private Integer destVideoCapCodecChannel2;

	@Parsed(field="destVideoCap_Bandwidth_Channel2")
	@TableColumn(name="dest_video_cap_bandwidth_channel2")
	private Integer destVideoCapBandwidthChannel2;

	@Parsed(field="destVideoCap_Resolution_Channel2")
	@TableColumn(name="dest_video_cap_resolution_channel2")
	private Integer destVideoCapResolutionChannel2;

	@Parsed(field="destVideoTransportAddress_IP_Channel2")
	@TableColumn(name="dest_video_transport_address_ip_channel2")
	private Integer destVideoTransportAddressIpChannel2;

	@Parsed(field="destVideoTransportAddress_Port_Channel2")
	@TableColumn(name="dest_video_transport_address_port_channel2")
	private Integer destVideoTransportAddressPortChannel2;

	@Parsed(field="destVideoChannel_Role_Channel2")
	@TableColumn(name="dest_video_channel_role_channel2")
	private Integer destVideoChannelRoleChannel2;

	@Parsed(field="IncomingProtocolID")
	@TableColumn(name="incoming_protocol_id")
	private Integer incomingProtocolId;

	@Parsed(field="IncomingProtocolCallRef")
	@TableColumn(name="incoming_protocol_call_ref")
	private String incomingProtocolCallRef;

	@Parsed(field="OutgoingProtocolID")
	@TableColumn(name="outgoing_protocol_id")
	private Integer outgoingProtocolId;

	@Parsed(field="OutgoingProtocolCallRef")
	@TableColumn(name="outgoing_protocol_call_ref")
	private String outgoingProtocolCallRef;

	@Parsed(field="currentRoutingReason")
	@TableColumn(name="current_routing_reason")
	private Integer currentRoutingReason;

	@Parsed(field="origRoutingReason")
	@TableColumn(name="orig_routing_reason")
	private Integer origRoutingReason;

	@Parsed(field="lastRedirectingRoutingReason")
	@TableColumn(name="last_redirecting_routing_reason")
	private Integer lastRedirectingRoutingReason;

	@Parsed(field="huntPilotPartition")
	@TableColumn(name="hunt_pilot_partition")
	private String huntPilotPartition;

	@Parsed(field="huntPilotDN")
	@TableColumn(name="hunt_pilot_dn")
	private String huntPilotDn;

	@Parsed(field="calledPartyPatternUsage")
	@TableColumn(name="called_party_pattern_usage")
	private Integer calledPartyPatternUsage;

	@Parsed(field="IncomingICID")
	@TableColumn(name="incoming_icid")
	private String incomingICId;

	@Parsed(field="IncomingOrigIOI")
	@TableColumn(name="incoming_orig_ioi")
	private String incomingOrigIOI;

	@Parsed(field="IncomingTermIOI")
	@TableColumn(name="incoming_term_ioi")
	private String incomingTermIOI;

	@Parsed(field="OutgoingICID")
	@TableColumn(name="outgoing_icid")
	private String outgoingICId;

	@Parsed(field="OutgoingOrigIOI")
	@TableColumn(name="outgoing_orig_ioi")
	private String outgoingOrigIOI;

	@Parsed(field="OutgoingTermIOI")
	@TableColumn(name="outgoing_term_ioi")
	private String outgoingTermIOI;

	@Parsed(field="outpulsedOriginalCalledPartyNumber")
	@TableColumn(name="outpulsed_original_called_party_number")
	private String outpulsedOriginalCalledPartyNumber;

	@Parsed(field="outpulsedLastRedirectingNumber")
	@TableColumn(name="outpulsed_last_redirecting_number")
	private String outpulsedLastRedirectingNumber;

	@Parsed(field="wasCallQueued")
	@TableColumn(name="was_call_queued")
	private Integer wasCallQueued;

	@Parsed(field="totalWaitTimeInQueue")
	@TableColumn(name="total_wait_time_in_queue")
	private Integer totalWaitTimeInQueue;

	@Parsed(field="callingPartyNumber_uri")
	@TableColumn(name="calling_party_number_uri")
	private String callingPartyNumberUri;

	@Parsed(field="originalCalledPartyNumber_uri")
	@TableColumn(name="original_called_party_number_uri")
	private String originalCalledPartyNumberUri;

	@Parsed(field="finalCalledPartyNumber_uri")
	@TableColumn(name="final_called_party_number_uri")
	private String finalCalledPartyNumberUri;

	@Parsed(field="lastRedirectDn_uri")
	@TableColumn(name="last_redirect_dn_uri")
	private String lastRedirectDnUri;

	@Parsed(field="mobileCallingPartyNumber")
	@TableColumn(name="mobile_calling_party_number")
	private String mobileCallingPartyNumber;

	@Parsed(field="finalMobileCalledPartyNumber")
	@TableColumn(name="final_mobile_called_party_number")
	private String finalMobileCalledPartyNumber;

	@Parsed(field="origMobileDeviceName")
	@TableColumn(name="orig_mobile_device_name")
	private String origMobileDeviceName;

	@Parsed(field="destMobileDeviceName")
	@TableColumn(name="dest_mobile_device_name")
	private String destMobileDeviceName;

	@Parsed(field="origMobileCallDuration")
	@TableColumn(name="orig_mobile_call_duration")
	private Integer origMobileCallDuration;

	@Parsed(field="destMobileCallDuration")
	@TableColumn(name="dest_mobile_call_duration")
	private Integer destMobileCallDuration;

	@Parsed(field="mobileCallType")
	@TableColumn(name="mobile_call_type")
	private Integer mobileCallType;

	@Parsed(field="originalCalledPartyPattern")
	@TableColumn(name="original_called_party_pattern")
	private String originalCalledPartyPattern;

	@Parsed(field="finalCalledPartyPattern")
	@TableColumn(name="final_called_party_pattern")
	private String finalCalledPartyPattern;

	@Parsed(field="lastRedirectingPartyPattern")
	@TableColumn(name="last_redirecting_party_pattern")
	private String lastRedirectingPartyPattern;

	@Parsed(field="huntPilotPattern")
	@TableColumn(name="hunt_pilot_pattern")
	private String huntPilotPattern;
}
