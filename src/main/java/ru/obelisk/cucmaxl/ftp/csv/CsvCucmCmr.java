package ru.obelisk.cucmaxl.ftp.csv;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

@Data
public class CsvCucmCmr {
	
	@Parsed(field="cdrRecordType")
	private Integer cdrRecordType;

	@Parsed(field="globalCallID_callManagerId")
	private Integer globalCallIdCallManagerId;

	@Parsed(field="globalCallID_callId")
	private Integer globalCallIdCallId;

	@Parsed(field="nodeId")
	private Integer nodeId;

	@Parsed(field="directoryNum")
	private String directoryNum;

	@Parsed(field="callIdentifier")
	private Integer callIdentifier;

	@Parsed(field="dateTimeStamp")
	private Integer dateTimeStamp;

	@Parsed(field="numberPacketsSent")
	private Integer numberPacketsSent;

	@Parsed(field="numberOctetsSent")
	private Integer numberOctetsSent;

	@Parsed(field="numberPacketsReceived")
	private Integer numberPacketsReceived;

	@Parsed(field="numberOctetsReceived")
	private Integer numberOctetsReceived;

	@Parsed(field="numberPacketsLost")
	private Integer numberPacketsLost;

	@Parsed(field="jitter")
	private Integer jitter;

	@Parsed(field="latency")
	private Integer latency;

	@Parsed(field="pkid")
	private String pkid;

	@Parsed(field="directoryNumPartition")
	private String directoryNumPartition;

	@Parsed(field="globalCallId_ClusterID")
	private String globalCallIdClusterId;

	@Parsed(field="deviceName")
	private String deviceName;

	@Parsed(field="varVQMetrics")
	private String varVQMetrics;

	@Parsed(field="duration")
	private Integer duration;

	@Parsed(field="videoContentType")
	private String videoContentType;

	@Parsed(field="videoDuration")
	private Integer videoDuration;

	@Parsed(field="numberVideoPacketsSent")
	private Integer numberVideoPacketsSent;

	@Parsed(field="numberVideoOctetsSent")
	private Integer numberVideoOctetsSent;

	@Parsed(field="numberVideoPacketsReceived")
	private Integer numberVideoPacketsReceived;

	@Parsed(field="numberVideoOctetsReceived")
	private Integer numberVideoOctetsReceived;

	@Parsed(field="numberVideoPacketsLost")
	private Integer numberVideoPacketsLost;

	@Parsed(field="videoAverageJitter")
	private Integer videoAverageJitter;

	@Parsed(field="videoRoundTripTime")
	private Integer videoRoundTripTime;

	@Parsed(field="videoOneWayDelay")
	private Integer videoOneWayDelay;

	@Parsed(field="videoReceptionMetrics")
	private String videoReceptionMetrics;

	@Parsed(field="videoTransmissionMetrics")
	private String videoTransmissionMetrics;

	@Parsed(field="videoContentTypeChannel2")
	private String videoContentTypeChannel2;

	@Parsed(field="videoDuration_channel2")
	private Integer videoDurationChannel2;

	@Parsed(field="numberVideoPacketsSent_channel2")
	private Integer numberVideoPacketsSentChannel2;

	@Parsed(field="numberVideoOctetsSent_channel2")
	private Integer numberVideoOctetsSentChannel2;

	@Parsed(field="numberVideoPacketsReceived_channel2")
	private Integer numberVideoPacketsReceivedChannel2;

	@Parsed(field="numberVideoOctetsReceived_channel2")
	private Integer numberVideoOctetsReceivedChannel2;

	@Parsed(field="numberVideoPacketsLost_channel2")
	private Integer numberVideoPacketsLostChannel2;

	@Parsed(field="videoAverageJitter_channel2")
	private Integer videoAverageJitterChannel2;

	@Parsed(field="videoRoundTripTime_channel2")
	private Integer videoRoundTripTimeChannel2;

	@Parsed(field="videoOneWayDelay_channel2")
	private Integer videoOneWayDelayChannel2;

	@Parsed(field="videoReceptionMetrics_channel2")
	private String videoReceptionMetricsChannel2;

	@Parsed(field="videoTransmissionMetrics_channel2")
	private String videoTransmissionMetricsChannel2;

}
