package ru.obelisk.cucmaxl.cucm.utils;

import ru.obelisk.database.models.entity.CucmAxlPort;

public class CucmRow {
	
	private CucmAxlPort port;
	private String devicePkid;
	private String deviceName;
	private String deviceDescription;
	private String deviceTypeClassName;
	private String deviceTypeDeviceProtocolName;
	private String deviceTypeModelName;
	private String endUserPkid;
	private String endUserUserId;
	private String endUserFirstName;
	private String endUserLastName;
	private String endUserMiddleName;
	private String endUserStatus;
	private String endUserUniqueIdentifier;
	private String endUserConnectedLdapHost;
	private String endUserLdapSynchronizationBase;
	private String lineIndex;
	private String linePkid;
	private String linePattern;
	private String lineDescription;
	private String linePartition;
		
	public String getDevicePkid() {
		return devicePkid;
	}
	public void setDevicePkid(String devicePkid) {
		this.devicePkid = devicePkid;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceDescription() {
		return deviceDescription;
	}
	public void setDeviceDescription(String deviceDescription) {
		this.deviceDescription = deviceDescription;
	}
	public String getDeviceTypeClassName() {
		return deviceTypeClassName;
	}
	public void setDeviceTypeClassName(String deviceTypeClassName) {
		this.deviceTypeClassName = deviceTypeClassName;
	}
	public String getDeviceTypeDeviceProtocolName() {
		return deviceTypeDeviceProtocolName;
	}
	public void setDeviceTypeDeviceProtocolName(String deviceTypeDeviceProtocolName) {
		this.deviceTypeDeviceProtocolName = deviceTypeDeviceProtocolName;
	}
	public String getDeviceTypeModelName() {
		return deviceTypeModelName;
	}
	public void setDeviceTypeModelName(String deviceTypeModelName) {
		this.deviceTypeModelName = deviceTypeModelName;
	}
	public String getEndUserPkid() {
		return endUserPkid;
	}
	public void setEndUserPkid(String endUserPkid) {
		this.endUserPkid = endUserPkid;
	}
	public String getEndUserUserId() {
		return endUserUserId;
	}
	public void setEndUserUserId(String endUserUserId) {
		this.endUserUserId = endUserUserId;
	}
	public String getEndUserFirstName() {
		return endUserFirstName;
	}
	public void setEndUserFirstName(String endUserFirstName) {
		this.endUserFirstName = endUserFirstName;
	}
	public String getEndUserLastName() {
		return endUserLastName;
	}
	public void setEndUserLastName(String endUserLastName) {
		this.endUserLastName = endUserLastName;
	}
	public String getEndUserMiddleName() {
		return endUserMiddleName;
	}
	public void setEndUserMiddleName(String endUserMiddleName) {
		this.endUserMiddleName = endUserMiddleName;
	}
	public String getEndUserStatus() {
		return endUserStatus;
	}
	public void setEndUserStatus(String endUserStatus) {
		this.endUserStatus = endUserStatus;
	}
	public String getEndUserUniqueIdentifier() {
		return endUserUniqueIdentifier;
	}
	public void setEndUserUniqueIdentifier(String endUserUniqueIdentifier) {
		this.endUserUniqueIdentifier = endUserUniqueIdentifier;
	}
	public String getEndUserLdapSynchronizationBase() {
		return endUserLdapSynchronizationBase;
	}
	public void setEndUserLdapSynchronizationBase(String endUserLdapSynchronizationBase) {
		this.endUserLdapSynchronizationBase = endUserLdapSynchronizationBase;
	}
	public String getLineIndex() {
		return lineIndex;
	}
	public void setLineIndex(String lineIndex) {
		this.lineIndex = lineIndex;
	}
	public String getLinePkid() {
		return linePkid;
	}
	public void setLinePkid(String linePkid) {
		this.linePkid = linePkid;
	}
	public String getLinePattern() {
		return linePattern;
	}
	public void setLinePattern(String linePattern) {
		this.linePattern = linePattern;
	}
	public String getLineDescription() {
		return lineDescription;
	}
	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}
	public String getLinePartition() {
		return linePartition;
	}
	public void setLinePartition(String linePartition) {
		this.linePartition = linePartition;
	}
	public CucmAxlPort getPort() {
		return port;
	}
	public void setPort(CucmAxlPort port) {
		this.port = port;
	}
	public String getEndUserConnectedLdapHost() {
		return endUserConnectedLdapHost;
	}
	public void setEndUserConnectedLdapHost(String endUserConnectedLdapHost) {
		this.endUserConnectedLdapHost = endUserConnectedLdapHost;
	}
	@Override
	public String toString() {
		return "CucmRow [port=" + port + ", devicePkid=" + devicePkid + ", deviceName=" + deviceName
				+ ", deviceDescription=" + deviceDescription + ", deviceTypeClassName=" + deviceTypeClassName
				+ ", deviceTypeDeviceProtocolName=" + deviceTypeDeviceProtocolName + ", deviceTypeModelName="
				+ deviceTypeModelName + ", endUserPkid=" + endUserPkid + ", endUserUserId=" + endUserUserId
				+ ", endUserFirstName=" + endUserFirstName + ", endUserLastName=" + endUserLastName
				+ ", endUserMiddleName=" + endUserMiddleName + ", endUserStatus=" + endUserStatus
				+ ", endUserUniqueIdentifier=" + endUserUniqueIdentifier + ", endUserConnectedLdapHost="
				+ endUserConnectedLdapHost + ", lineIndex=" + lineIndex + ", linePkid=" + linePkid + ", linePattern="
				+ linePattern + ", lineDescription=" + lineDescription + ", linePartition=" + linePartition + "]";
	}
	
}
