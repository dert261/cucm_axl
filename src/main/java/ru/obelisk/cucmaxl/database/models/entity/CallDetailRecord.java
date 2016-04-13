package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


@Entity
@Table(name = "cucm_cdr", catalog="adsync", schema="public")
@Data
public class CallDetailRecord implements Serializable{
	private static final long serialVersionUID = -525156342280168916L;

	@Id
	@SequenceGenerator(sequenceName = "cucm_cdr_id_seq", name = "CucmCdrIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmCdrIdSequence")
    @Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name="cdr_record_type")
	private Integer cdrRecordType;

	@Column(name="global_call_id_call_manager_id")
	private Integer globalCallIdCallManagerId;

	@Column(name="global_call_id_call_id")
	private Integer globalCallIdCallId;

	@Column(name="orig_leg_call_identifier")
	private Integer origLegCallIdentifier;

	@Transient
	private Integer dateTimeOrigination;
	
	@Column(name="date_time_origination")
	private LocalDateTime dateTimeOriginationDb;

	@Column(name="orig_node_id")
	private Integer origNodeId;

	@Column(name="orig_span")
	private Integer origSpan;

	@Column(name="orig_ip_addr")
	private Integer origIpAddr;

	@Column(name="calling_party_number")
	private String callingPartyNumber;

	@Column(name="calling_party_unicode_login_user_id")
	private String callingPartyUnicodeLoginUserId;

	@Column(name="orig_cause_location")
	private Integer origCauseLocation;

	@Column(name="orig_cause_value")
	private Integer origCauseValue;

	@Column(name="orig_precedence_level")
	private Integer origPrecedenceLevel;

	@Column(name="orig_media_transport_address_ip")
	private Integer origMediaTransportAddressIP;

	@Column(name="orig_media_transport_address_port")
	private Integer origMediaTransportAddressPort;

	@Column(name="orig_media_cap_payload_capability")
	private Integer origMediaCapPayloadCapability;

	@Column(name="orig_media_cap_max_frames_per_packet")
	private Integer origMediaCapMaxFramesPerPacket;

	@Column(name="orig_media_cap_g723bit_rate")
	private Integer origMediaCapG723BitRate;

	@Column(name="orig_video_cap_codec")
	private Integer origVideoCapCodec;

	@Column(name="orig_video_cap_bandwidth")
	private Integer origVideoCapBandwidth;

	@Column(name="orig_video_cap_resolution")
	private Integer origVideoCapResolution;

	@Column(name="orig_video_transport_address_ip")
	private Integer origVideoTransportAddressIP;

	@Column(name="orig_video_transport_address_port")
	private Integer origVideoTransportAddressPort;

	@Column(name="orig_rsvp_audio_stat")
	private String origRSVPAudioStat;

	@Column(name="orig_rsvp_video_stat")
	private String origRSVPVideoStat;

	@Column(name="dest_leg_identifier")
	private Integer destLegIdentifier;

	@Column(name="dest_node_id")
	private Integer destNodeId;

	@Column(name="dest_span")
	private Integer destSpan;

	@Transient
	private Integer destIpAddr;
	
	@Column(name="dest_ip_addr")
	private String destIpAddrDb;

	@Column(name="original_called_party_number")
	private String originalCalledPartyNumber;

	@Column(name="final_called_party_number")
	private String finalCalledPartyNumber;

	@Column(name="final_called_party_unicode_login_user_id")
	private String finalCalledPartyUnicodeLoginUserId;

	@Column(name="dest_cause_location")
	private Integer destCauseLocation;

	@Column(name="dest_cause_value")
	private Integer destCauseValue;

	@Column(name="dest_precedence_level")
	private Integer destPrecedenceLevel;

	@Transient
	private Integer destMediaTransportAddressIp;
	
	@Column(name="dest_media_transport_address_ip")
	private String destMediaTransportAddressIpDb;

	@Column(name="dest_media_transport_address_port")
	private Integer destMediaTransportAddressPort;

	@Column(name="dest_media_cap_payload_capability")
	private Integer destMediaCapPayloadCapability;

	@Column(name="dest_media_cap_max_frames_per_packet")
	private Integer destMediaCapMaxFramesPerPacket;

	@Column(name="dest_media_cap_g723bit_rate")
	private Integer destMediaCapG723BitRate;

	@Column(name="dest_video_cap_codec")
	private Integer destVideoCapCodec;

	@Column(name="dest_video_cap_bandwidth")
	private Integer destVideoCapBandwidth;

	@Column(name="dest_video_cap_resolution")
	private Integer destVideoCapResolution;

	@Column(name="dest_video_transport_address_ip")
	private Integer destVideoTransportAddressIp;

	@Column(name="dest_video_transport_address_port")
	private Integer destVideoTransportAddressPort;

	@Column(name="dest_rsvp_audio_stat")
	private String destRSVPAudioStat;

	@Column(name="dest_rsvp_video_stat")
	private String destRSVPVideoStat;

	@Transient
	private Integer dateTimeConnect;
	
	@Column(name="date_time_connect")
	private LocalDateTime dateTimeConnectDb;

	@Transient
	private Integer dateTimeDisconnect;
	
	@Column(name="date_time_disconnect")
	private LocalDateTime dateTimeDisconnectDb;

	@Column(name="last_redirect_dn")
	private String lastRedirectDn;

	@Column(name="pkid")
	private String pkid;

	@Column(name="original_called_party_number_partition")
	private String originalCalledPartyNumberPartition;

	@Column(name="calling_party_number_partition")
	private String callingPartyNumberPartition;

	@Column(name="final_called_party_number_partition")
	private String finalCalledPartyNumberPartition;

	@Column(name="last_redirect_dn_partition")
	private String lastRedirectDnPartition;

	@Column(name="duration")
	private Integer duration;

	@Column(name="orig_device_name")
	private String origDeviceName;

	@Column(name="dest_device_name")
	private String destDeviceName;

	@Column(name="orig_call_termination_on_behalf_of")
	private Integer origCallTerminationOnBehalfOf;

	@Column(name="dest_call_termination_on_behalf_of")
	private Integer destCallTerminationOnBehalfOf;

	@Column(name="orig_called_party_redirect_on_behalf_of")
	private Integer origCalledPartyRedirectOnBehalfOf;

	@Column(name="last_redirect_redirect_on_behalf_of")
	private Integer lastRedirectRedirectOnBehalfOf;

	@Column(name="orig_called_party_redirect_reason")
	private Integer origCalledPartyRedirectReason;

	@Column(name="last_redirect_redirect_reason")
	private Integer lastRedirectRedirectReason;

	@Column(name="dest_conversation_id")
	private Integer destConversationId;

	@Column(name="global_call_id_cluster_id")
	private String globalCallIdClusterId;

	@Column(name="join_on_behalf_of")
	private Integer joinOnBehalfOf;

	@Column(name="comment")
	private String comment;

	@Column(name="auth_code_description")
	private String authCodeDescription;

	@Column(name="authorization_level")
	private Integer authorizationLevel;

	@Column(name="client_matter_code")
	private String clientMatterCode;

	@Column(name="orig_dtmf_method")
	private Integer origDTMFMethod;

	@Column(name="dest_dtmf_method")
	private Integer destDTMFMethod;

	@Column(name="call_secured_status")
	private Integer callSecuredStatus;

	@Column(name="orig_conversation_id")
	private Integer origConversationId;

	@Column(name="orig_media_cap_bandwidth")
	private Integer origMediaCapBandwidth;

	@Column(name="dest_media_cap_bandwidth")
	private Integer destMediaCapBandwidth;

	@Column(name="authorization_code_value")
	private String authorizationCodeValue;

	@Column(name="outpulsed_calling_party_number")
	private String outpulsedCallingPartyNumber;

	@Column(name="outpulsed_called_party_number")
	private String outpulsedCalledPartyNumber;

	@Column(name="orig_ipv4v6_addr")
	private String origIpv4v6Addr;

	@Column(name="dest_ipv4v6_addr")
	private String destIpv4v6Addr;

	@Column(name="orig_video_cap_codec_channel2")
	private Integer origVideoCapCodecChannel2;

	@Column(name="orig_video_cap_bandwidth_channel2")
	private Integer origVideoCapBandwidthChannel2;

	@Column(name="orig_video_cap_resolution_channel2")
	private Integer origVideoCapResolutionChannel2;

	@Column(name="orig_video_transport_address_ip_channel2")
	private Integer origVideoTransportAddressIPChannel2;

	@Column(name="orig_video_transport_address_port_channel2")
	private Integer origVideoTransportAddressPortChannel2;

	@Column(name="orig_video_channel_role_channel2")
	private Integer origVideoChannelRoleChannel2;

	@Column(name="dest_video_cap_codec_channel2")
	private Integer destVideoCapCodecChannel2;

	@Column(name="dest_video_cap_bandwidth_channel2")
	private Integer destVideoCapBandwidthChannel2;

	@Column(name="dest_video_cap_resolution_channel2")
	private Integer destVideoCapResolutionChannel2;

	@Column(name="dest_video_transport_address_ip_channel2")
	private Integer destVideoTransportAddressIpChannel2;

	@Column(name="dest_video_transport_address_port_channel2")
	private Integer destVideoTransportAddressPortChannel2;

	@Column(name="dest_video_channel_role_channel2")
	private Integer destVideoChannelRoleChannel2;

	@Column(name="incoming_protocol_id")
	private Integer incomingProtocolId;

	@Column(name="incoming_protocol_call_ref")
	private String incomingProtocolCallRef;

	@Column(name="outgoing_protocol_id")
	private Integer outgoingProtocolId;

	@Column(name="outgoing_protocol_call_ref")
	private String outgoingProtocolCallRef;

	@Column(name="current_routing_reason")
	private Integer currentRoutingReason;

	@Column(name="orig_routing_reason")
	private Integer origRoutingReason;

	@Column(name="last_redirecting_routing_reason")
	private Integer lastRedirectingRoutingReason;

	@Column(name="hunt_pilot_partition")
	private String huntPilotPartition;

	@Column(name="hunt_pilot_dn")
	private String huntPilotDn;

	@Column(name="called_party_pattern_usage")
	private Integer calledPartyPatternUsage;

	@Column(name="incoming_icid")
	private String incomingICId;

	@Column(name="incoming_orig_ioi")
	private String incomingOrigIOI;

	@Column(name="incoming_term_ioi")
	private String incomingTermIOI;

	@Column(name="outgoing_icid")
	private String outgoingICId;

	@Column(name="outgoing_orig_ioi")
	private String outgoingOrigIOI;

	@Column(name="outgoing_term_ioi")
	private String outgoingTermIOI;

	@Column(name="outpulsed_original_called_party_number")
	private String outpulsedOriginalCalledPartyNumber;

	@Column(name="outpulsed_last_redirecting_number")
	private String outpulsedLastRedirectingNumber;

	@Column(name="was_call_queued")
	private Integer wasCallQueued;

	@Column(name="total_wait_time_in_queue")
	private Integer totalWaitTimeInQueue;

	@Column(name="calling_party_number_uri")
	private String callingPartyNumberUri;

	@Column(name="original_called_party_number_uri")
	private String originalCalledPartyNumberUri;

	@Column(name="final_called_party_number_uri")
	private String finalCalledPartyNumberUri;

	@Column(name="last_redirect_dn_uri")
	private String lastRedirectDnUri;

	@Column(name="mobile_calling_party_number")
	private String mobileCallingPartyNumber;

	@Column(name="final_mobile_called_party_number")
	private String finalMobileCalledPartyNumber;

	@Column(name="orig_mobile_device_name")
	private String origMobileDeviceName;

	@Column(name="dest_mobile_device_name")
	private String destMobileDeviceName;

	@Column(name="orig_mobile_call_duration")
	private Integer origMobileCallDuration;

	@Column(name="dest_mobile_call_duration")
	private Integer destMobileCallDuration;

	@Column(name="mobile_call_type")
	private Integer mobileCallType;

	@Column(name="original_called_party_pattern")
	private String originalCalledPartyPattern;

	@Column(name="final_called_party_pattern")
	private String finalCalledPartyPattern;

	@Column(name="last_redirecting_party_pattern")
	private String lastRedirectingPartyPattern;

	@Column(name="hunt_pilot_pattern")
	private String huntPilotPattern;
}
