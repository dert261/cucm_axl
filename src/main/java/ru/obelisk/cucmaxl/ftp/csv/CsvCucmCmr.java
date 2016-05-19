package ru.obelisk.cucmaxl.ftp.csv;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import ru.obelisk.cucmaxl.ftp.annotation.TableColumn;
import ru.obelisk.cucmaxl.ftp.annotation.TableName;

@Data
@TableName(name="cucm_cmr")
public class CsvCucmCmr {
	
	@Parsed(field="cdrRecordType")
	@TableColumn(name="cdr_record_type")
	private Integer cdrRecordType;

	@Parsed(field="globalCallID_callManagerId")
	@TableColumn(name="global_call_id_call_manager_id")
	private Integer globalCallIdCallManagerId;

	@Parsed(field="globalCallID_callId")
	@TableColumn(name="global_call_id_call_id")
	private Integer globalCallIdCallId;

	@Parsed(field="nodeId")
	@TableColumn(name="node_id")
	private Integer nodeId;

	@Parsed(field="directoryNum")
	@TableColumn(name="directory_num")
	private String directoryNum;

	@Parsed(field="callIdentifier")
	@TableColumn(name="call_identifier")
	private Integer callIdentifier;

	@Parsed(field="dateTimeStamp")
	@TableColumn(name="date_time_stamp")
	private Integer dateTimeStamp;

	@Parsed(field="numberPacketsSent")
	@TableColumn(name="number_packets_sent")
	private Integer numberPacketsSent;

	@Parsed(field="numberOctetsSent")
	@TableColumn(name="number_octets_sent")
	private Integer numberOctetsSent;

	@Parsed(field="numberPacketsReceived")
	@TableColumn(name="number_packets_received")
	private Integer numberPacketsReceived;

	@Parsed(field="numberOctetsReceived")
	@TableColumn(name="number_octets_received")
	private Integer numberOctetsReceived;

	@Parsed(field="numberPacketsLost")
	@TableColumn(name="number_packets_lost")
	private Integer numberPacketsLost;

	@Parsed(field="jitter")
	@TableColumn(name="jitter")
	private Integer jitter;

	@Parsed(field="latency")
	@TableColumn(name="latency")
	private Integer latency;

	@Parsed(field="pkid")
	@TableColumn(name="pkid")
	private String pkid;

	@Parsed(field="directoryNumPartition")
	@TableColumn(name="directory_num_partition")
	private String directoryNumPartition;

	@Parsed(field="globalCallId_ClusterID")
	@TableColumn(name="global_call_id_cluster_id")
	private String globalCallIdClusterId;

	@Parsed(field="deviceName")
	@TableColumn(name="device_name")
	private String deviceName;

	@Parsed(field="varVQMetrics")
	@TableColumn(name="var_vq_metrics")
	private String varVQMetrics;

	@Parsed(field="duration")
	@TableColumn(name="duration")
	private Integer duration;

	@Parsed(field="videoContentType")
	@TableColumn(name="video_content_type")
	private String videoContentType;

	@Parsed(field="videoDuration")
	@TableColumn(name="video_duration")
	private Integer videoDuration;

	@Parsed(field="numberVideoPacketsSent")
	@TableColumn(name="number_video_packets_sent")
	private Integer numberVideoPacketsSent;

	@Parsed(field="numberVideoOctetsSent")
	@TableColumn(name="number_video_octets_sent")
	private Integer numberVideoOctetsSent;

	@Parsed(field="numberVideoPacketsReceived")
	@TableColumn(name="number_video_packets_received")
	private Integer numberVideoPacketsReceived;

	@Parsed(field="numberVideoOctetsReceived")
	@TableColumn(name="number_video_octets_received")
	private Integer numberVideoOctetsReceived;

	@Parsed(field="numberVideoPacketsLost")
	@TableColumn(name="number_video_packets_lost")
	private Integer numberVideoPacketsLost;

	@Parsed(field="videoAverageJitter")
	@TableColumn(name="video_average_jitter")
	private Integer videoAverageJitter;

	@Parsed(field="videoRoundTripTime")
	@TableColumn(name="video_round_trip_time")
	private Integer videoRoundTripTime;

	@Parsed(field="videoOneWayDelay")
	@TableColumn(name="video_one_way_delay")
	private Integer videoOneWayDelay;

	@Parsed(field="videoReceptionMetrics")
	@TableColumn(name="video_reception_metrics")
	private String videoReceptionMetrics;

	@Parsed(field="videoTransmissionMetrics")
	@TableColumn(name="video_transmission_metrics")
	private String videoTransmissionMetrics;

	@Parsed(field="videoContentTypeChannel2")
	@TableColumn(name="video_content_type_channel2")
	private String videoContentTypeChannel2;

	@Parsed(field="videoDuration_channel2")
	@TableColumn(name="video_duration_channel2")
	private Integer videoDurationChannel2;

	@Parsed(field="numberVideoPacketsSent_channel2")
	@TableColumn(name="number_video_packets_sent_channel2")
	private Integer numberVideoPacketsSentChannel2;

	@Parsed(field="numberVideoOctetsSent_channel2")
	@TableColumn(name="number_video_octets_sent_channel2")
	private Integer numberVideoOctetsSentChannel2;

	@Parsed(field="numberVideoPacketsReceived_channel2")
	@TableColumn(name="number_video_packets_received_channel2")
	private Integer numberVideoPacketsReceivedChannel2;

	@Parsed(field="numberVideoOctetsReceived_channel2")
	@TableColumn(name="number_video_octets_received_channel2")
	private Integer numberVideoOctetsReceivedChannel2;

	@Parsed(field="numberVideoPacketsLost_channel2")
	@TableColumn(name="number_video_packets_lost_channel2")
	private Integer numberVideoPacketsLostChannel2;

	@Parsed(field="videoAverageJitter_channel2")
	@TableColumn(name="video_average_jitter_channel2")
	private Integer videoAverageJitterChannel2;

	@Parsed(field="videoRoundTripTime_channel2")
	@TableColumn(name="video_round_trip_time_channel2")
	private Integer videoRoundTripTimeChannel2;

	@Parsed(field="videoOneWayDelay_channel2")
	@TableColumn(name="video_one_way_delay_channel2")
	private Integer videoOneWayDelayChannel2;

	@Parsed(field="videoReceptionMetrics_channel2")
	@TableColumn(name="video_reception_metrics_channel2")
	private String videoReceptionMetricsChannel2;

	@Parsed(field="videoTransmissionMetrics_channel2")
	@TableColumn(name="video_transmission_metrics_channel2")
	private String videoTransmissionMetricsChannel2;

}
