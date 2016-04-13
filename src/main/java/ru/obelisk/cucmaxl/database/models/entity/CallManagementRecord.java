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
@Table(name = "cucm_cmr", catalog="adsync", schema="public")
@Data
public class CallManagementRecord implements Serializable{
	private static final long serialVersionUID = -6543286419770684317L;

	@Id
	@SequenceGenerator(sequenceName = "cucm_cmr_id_seq", name = "CucmCmrIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmCmrIdSequence")
    @Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name="cdr_record_type")
	private Integer cdrRecordType;

	@Column(name="global_call_id_call_manager_id")
	private Integer globalCallIdCallManagerId;

	@Column(name="global_call_id_call_id")
	private Integer globalCallIdCallId;

	@Column(name="node_id")
	private Integer nodeId;

	@Column(name="directory_num")
	private String directoryNum;

	@Column(name="call_identifier")
	private Integer callIdentifier;

	@Transient
	private Integer dateTimeStamp;
	
	@Column(name="date_time_stamp")
	private LocalDateTime dateTimeStampDb;

	@Column(name="number_packets_sent")
	private Integer numberPacketsSent;

	@Column(name="number_octets_sent")
	private Integer numberOctetsSent;

	@Column(name="number_packets_received")
	private Integer numberPacketsReceived;

	@Column(name="number_octets_received")
	private Integer numberOctetsReceived;

	@Column(name="number_packets_lost")
	private Integer numberPacketsLost;

	@Column(name="jitter")
	private Integer jitter;

	@Column(name="latency")
	private Integer latency;

	@Column(name="pkid")
	private Integer pkid;

	@Column(name="directory_num_partition")
	private String directoryNumPartition;

	@Column(name="global_call_id_cluster_id")
	private String globalCallIdClusterId;

	@Column(name="device_name")
	private String deviceName;

	@Column(name="var_vq_metrics")
	private String varVQMetrics;

	@Column(name="duration")
	private Integer duration;

	@Column(name="video_content_type")
	private String videoContentType;

	@Column(name="video_duration")
	private Integer videoDuration;

	@Column(name="number_video_packets_sent")
	private Integer numberVideoPacketsSent;

	@Column(name="number_video_octets_sent")
	private Integer numberVideoOctetsSent;

	@Column(name="number_video_packets_received")
	private Integer numberVideoPacketsReceived;

	@Column(name="number_video_octets_received")
	private Integer numberVideoOctetsReceived;

	@Column(name="number_video_packets_lost")
	private Integer numberVideoPacketsLost;

	@Column(name="video_average_jitter")
	private Integer videoAverageJitter;

	@Column(name="video_round_trip_time")
	private Integer videoRoundTripTime;

	@Column(name="video_one_way_delay")
	private Integer videoOneWayDelay;

	@Column(name="video_reception_metrics")
	private String videoReceptionMetrics;

	@Column(name="video_transmission_metrics")
	private String videoTransmissionMetrics;

	@Column(name="video_content_type_channel2")
	private String videoContentTypeChannel2;

	@Column(name="video_duration_channel2")
	private Integer videoDurationChannel2;

	@Column(name="number_video_packets_sent_channel2")
	private Integer numberVideoPacketsSentChannel2;

	@Column(name="number_video_octets_sent_channel2")
	private Integer numberVideoOctetsSentChannel2;

	@Column(name="number_video_packets_received_channel2")
	private Integer numberVideoPacketsReceivedChannel2;

	@Column(name="number_video_octets_received_channel2")
	private Integer numberVideoOctetsReceivedChannel2;

	@Column(name="number_video_packets_lost_channel2")
	private Integer numberVideoPacketsLostChannel2;

	@Column(name="video_average_jitter_channel2")
	private Integer videoAverageJitterChannel2;

	@Column(name="video_round_trip_time_channel2")
	private Integer videoRoundTripTimeChannel2;

	@Column(name="video_one_way_delay_channel2")
	private Integer videoOneWayDelayChannel2;

	@Column(name="video_reception_metrics_channel2")
	private String videoReceptionMetricsChannel2;

	@Column(name="video_transmission_metrics_channel2")
	private String videoTransmissionMetricsChannel2;
}
