package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "cucm_data_view", catalog = "adsync", schema="public")
public class DataView implements Serializable {
	
	private static final long serialVersionUID = 4195901231230708792L;

	public final static String[] header = {
			"Device Name",
			"Description",
			"Device Pool",
			"Phone Button Template",
			"CSS",
			"Location",
			"Softkey Template",
			"Device Type",
			"User ID 1",
			"Common Phone Profile",
			"Owner User ID",
			"Device Presence Group",
			"Device Security Profile",
			"Device Subscribe CSS",
			"Device Protocol",
			"SIP Profile",
			"Common Device Configuration",
			"Directory Number 1",
			"Route Partition 1",
			"Line CSS 1",
			"External Phone Number Mask 1",
			
			"Forward All CSS 1",
			"Forward All Destination 1",
			"Forward All Voice Mail 1",
			
			"Forward Busy Internal Voice Mail 1",
			"Forward Busy Internal Destination 1",
			"Forward Busy Internal CSS 1",
			"Forward Busy External Voice Mail 1",
			"Forward Busy External Destination 1",
			"Forward Busy External CSS 1",
			"Forward No Answer Internal Voice Mail 1",
			"Forward No Answer Internal Destination 1",
			"Forward No Answer Internal CSS 1",
			"Forward No Answer External Voice Mail 1",
			"Forward No Answer External Destination 1",
			"Forward No Answer External CSS 1",
			"Forward No Answer Ring Duration 1",
			"Forward No Coverage Internal Voice Mail 1",
			"Forward No Coverage Internal Destination 1",
			"Forward No Coverage Internal CSS 1",
			"Forward No Coverage External Voice Mail 1",
			"Forward No Coverage External Destination 1",
			"Forward No Coverage External CSS 1",
			"Forward Unregistered Internal Voice Mail 1",
			"Forward Unregistered Internal Destination 1",
			"Forward Unregistered Internal CSS 1",
			"Forward Unregistered External Voice Mail 1",
			"Forward Unregistered External Destination 1",
			"Forward Unregistered External CSS 1",
			"Forward on CTI Failure Voice Mail 1",
			"Forward on CTI Failure Destination 1",
			"Forward on CTI Failure CSS 1",
			"Call Pickup Group 1",
			"Line Text Label 1",
			"Maximum Number of Calls 1",
			"Busy Trigger 1",
			"Line Description 1",
			"Alerting Name 1",
			"ASCII Alerting Name 1",
			"Display 1",
			"ASCII Display 1",
			"Recording Profile 1",
			"Recording Media Source 1",
			"Recording Option 1"
			
	}; 
		
	@GenericGenerator(name = "generator", strategy = "foreign", 
			parameters = @Parameter(name = "property", value = "device_name"))
	@Id
	@SequenceGenerator(sequenceName = "cucm_data_view_id_seq", name = "DataViewIdSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DataViewIdSequence")
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Column(name = "device_name", length = 50)
	private String device_name;

	@Column(name = "description")
	private String description;

	@Column(name = "device_pool")
	private String device_pool;

	@Column(name = "phone_button_template")
	private String phone_button_template;

	@Column(name = "css")
	private String css;

	@Column(name = "location")
	private String location;

	@Column(name = "softkey_template")
	private String softkey_template;

	@Column(name = "device_type")
	private String device_type;

	@Column(name = "user_id_1")
	private String user_id_1;

	@Column(name = "common_phone_profile")
	private String common_phone_profile;

	@Column(name = "owner_user_id")
	private String owner_user_id;

	@Column(name = "device_presence_group")
	private String device_presence_group;

	@Column(name = "device_security_profile")
	private String device_security_profile;

	@Column(name = "device_subscribe_css")
	private String device_subscribe_css;

	@Column(name = "device_protocol")
	private String device_protocol;

	@Column(name = "sip_profile")
	private String sip_profile;

	@Column(name = "common_device_configuration")
	private String common_device_configuration;

	@Column(name = "directory_number_1")
	private String directory_number_1;

	@Column(name = "route_partition_1")
	private String route_partition_1;

	@Column(name = "line_css_1")
	private String line_css_1;
	
	@Column(name = "e164_number_mask_1")
	private String e164_number_mask_1;
	
	@Column(name = "forward_all_voice_mail_1")
	private String forward_all_voice_mail_1;

	@Column(name = "forward_all_destination_1")
	private String forward_all_destination_1;

	@Column(name = "forward_all_css_1")
	private String forward_all_css_1;
	
	@Column(name = "forward_busy_internal_voice_mail_1")
	private String forward_busy_internal_voice_mail_1;

	@Column(name = "forward_busy_internal_destination_1")
	private String forward_busy_internal_destination_1;

	@Column(name = "forward_busy_internal_css_1")
	private String forward_busy_internal_css_1;

	@Column(name = "forward_busy_external_voice_mail_1")
	private String forward_busy_external_voice_mail_1;

	@Column(name = "forward_busy_external_destination_1")
	private String forward_busy_external_destination_1;

	@Column(name = "forward_busy_external_css_1")
	private String forward_busy_external_css_1;

	@Column(name = "forward_no_answer_internal_voice_mail_1")
	private String forward_no_answer_internal_voice_mail_1;

	@Column(name = "forward_no_answer_internal_destination_1")
	private String forward_no_answer_internal_destination_1;

	@Column(name = "forward_no_answer_internal_css_1")
	private String forward_no_answer_internal_css_1;

	@Column(name = "forward_no_answer_external_voice_mail_1")
	private String forward_no_answer_external_voice_mail_1;

	@Column(name = "forward_no_answer_external_destination_1")
	private String forward_no_answer_external_destination_1;

	@Column(name = "forward_no_answer_external_css_1")
	private String forward_no_answer_external_css_1;

	@Column(name = "forward_no_coverage_internal_voice_mail_1")
	private String forward_no_coverage_internal_voice_mail_1;

	@Column(name = "forward_no_coverage_internal_destination_1")
	private String forward_no_coverage_internal_destination_1;

	@Column(name = "forward_no_coverage_internal_css_1")
	private String forward_no_coverage_internal_css_1;

	@Column(name = "forward_no_coverage_external_voice_mail_1")
	private String forward_no_coverage_external_voice_mail_1;

	@Column(name = "forward_no_coverage_external_destination_1")
	private String forward_no_coverage_external_destination_1;

	@Column(name = "forward_no_coverage_external_css_1")
	private String forward_no_coverage_external_css_1;

	@Column(name = "forward_no_answer_ring_duration_1")
	private String forward_no_answer_ring_duration_1;

	@Column(name = "call_pickup_group_1")
	private String call_pickup_group_1;

	@Column(name = "line_text_label_1")
	private String line_text_label_1;

	@Column(name = "maximum_number_of_calls_1")
	private String maximum_number_of_calls_1;

	@Column(name = "busy_trigger_1")
	private String busy_trigger_1;

	@Column(name = "line_description_1")
	private String line_description_1;

	@Column(name = "alerting_name_1")
	private String alerting_name_1;

	@Column(name = "ascii_alerting_name_1")
	private String ascii_alerting_name_1;

	@Column(name = "forward_on_cti_failure_voice_mail_1")
	private String forward_on_cti_failure_voice_mail_1;

	@Column(name = "forward_on_cti_failure_destination_1")
	private String forward_on_cti_failure_destination_1;

	@Column(name = "forward_on_cti_failure_css_1")
	private String forward_on_cti_failure_css_1;

	@Column(name = "display_1")
	private String display_1;

	@Column(name = "ascii_display_1")
	private String ascii_display_1;

	@Column(name = "forward_unregistered_internal_voice_mail_1")
	private String forward_unregistered_internal_voice_mail_1;

	@Column(name = "forward_unregistered_internal_destination_1")
	private String forward_unregistered_internal_destination_1;

	@Column(name = "forward_unregistered_internal_css_1")
	private String forward_unregistered_internal_css_1;

	@Column(name = "forward_unregistered_external_voice_mail_1")
	private String forward_unregistered_external_voice_mail_1;

	@Column(name = "forward_unregistered_external_destination_1")
	private String forward_unregistered_external_destination_1;

	@Column(name = "forward_unregistered_external_css_1")
	private String forward_unregistered_external_css_1;
	
	
	@Column(name = "recording_profile_1")
	private String recording_profile_1;

	@Column(name = "recording_media_source_1")
	private String recording_media_source_1;

	@Column(name = "recording_option_1")
	private String recording_option_1;
	
	@Column(name = "custom_maybe_owner_user_id")
	private String custom_maybe_owner_user_id;

	@Column(name = "custom_use_flag")
	private String custom_use_flag;

	@Column(name = "custom_public_en")
	private String custom_public_en;

	@Column(name = "custom_public_ru")
	private String custom_public_ru;
	
	@Column(name = "custom_e164_mask")
	private String custom_e164_mask;
	
	public DataView (){
		
	}
	
	public boolean isNew(){
		return this.id==null;
	}
	
	public String[] toStringArray() {
        return new String[] {
        		this.getDevice_name(),
        		this.getDescription(),
        		this.getDevice_pool(),
        		this.getPhone_button_template(),
        		this.getCss(),
        		this.getLocation(),
        		this.getSoftkey_template(),
        		this.getDevice_type(),
        		this.getUser_id_1(),
        		this.getCommon_phone_profile(),
        		this.getOwner_user_id(),
        		this.getDevice_presence_group(),
        		this.getDevice_security_profile(),
        		this.getDevice_subscribe_css(),
        		this.getDevice_protocol(),
        		this.getSip_profile(),
        		this.getCommon_device_configuration(),
        		this.getDirectory_number_1(),
        		this.getRoute_partition_1(),
        		this.getLine_css_1(),
        		this.getE164_number_mask_1(),
        		this.getForward_all_css_1(),
        		this.getForward_all_destination_1(),
        		this.getForward_all_voice_mail_1(),
        		this.getForward_busy_internal_voice_mail_1(),
        		this.getForward_busy_internal_destination_1(),
        		this.getForward_busy_internal_css_1(),
        		this.getForward_busy_external_voice_mail_1(),
        		this.getForward_busy_external_destination_1(),
        		this.getForward_busy_external_css_1(),
        		this.getForward_no_answer_internal_voice_mail_1(),
        		this.getForward_no_answer_internal_destination_1(),
        		this.getForward_no_answer_internal_css_1(),
        		this.getForward_no_answer_external_voice_mail_1(),
        		this.getForward_no_answer_external_destination_1(),
        		this.getForward_no_answer_external_css_1(),
        		this.getForward_no_answer_ring_duration_1(),
        		this.getForward_no_coverage_internal_voice_mail_1(),
        		this.getForward_no_coverage_internal_destination_1(),
        		this.getForward_no_coverage_internal_css_1(),
        		this.getForward_no_coverage_external_voice_mail_1(),
        		this.getForward_no_coverage_external_destination_1(),
        		this.getForward_no_coverage_external_css_1(),
        		this.getForward_unregistered_internal_voice_mail_1(),
        		this.getForward_unregistered_internal_destination_1(),
        		this.getForward_unregistered_internal_css_1(),
        		this.getForward_unregistered_external_voice_mail_1(),
        		this.getForward_unregistered_external_destination_1(),
        		this.getForward_unregistered_external_css_1(),
        		this.getForward_on_cti_failure_voice_mail_1(),
        		this.getForward_on_cti_failure_destination_1(),
        		this.getForward_on_cti_failure_css_1(),
        		this.getCall_pickup_group_1(),
        		this.getLine_text_label_1(),
        		this.getMaximum_number_of_calls_1(),
        		this.getBusy_trigger_1(),
        		this.getLine_description_1(),
        		this.getAlerting_name_1(),
        		this.getAscii_alerting_name_1(),
        		this.getDisplay_1(),
        		this.getAscii_display_1(),
        		this.getRecording_profile_1(),
        		this.getRecording_media_source_1(),
        		this.getRecording_option_1()
         };
    }
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDevice_pool() {
		return device_pool;
	}
	public void setDevice_pool(String device_pool) {
		this.device_pool = device_pool;
	}

	public String getPhone_button_template() {
		return phone_button_template;
	}

	public void setPhone_button_template(String phone_button_template) {
		this.phone_button_template = phone_button_template;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSoftkey_template() {
		return softkey_template;
	}

	public void setSoftkey_template(String softkey_template) {
		this.softkey_template = softkey_template;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getUser_id_1() {
		return user_id_1;
	}

	public void setUser_id_1(String user_id_1) {
		this.user_id_1 = user_id_1;
	}

	public String getCommon_phone_profile() {
		return common_phone_profile;
	}

	public void setCommon_phone_profile(String common_phone_profile) {
		this.common_phone_profile = common_phone_profile;
	}

	public String getOwner_user_id() {
		return owner_user_id;
	}

	public void setOwner_user_id(String owner_user_id) {
		this.owner_user_id = owner_user_id;
	}

	public String getDevice_presence_group() {
		return device_presence_group;
	}

	public void setDevice_presence_group(String device_presence_group) {
		this.device_presence_group = device_presence_group;
	}

	public String getDevice_security_profile() {
		return device_security_profile;
	}

	public void setDevice_security_profile(String device_security_profile) {
		this.device_security_profile = device_security_profile;
	}

	public String getDevice_subscribe_css() {
		return device_subscribe_css;
	}

	public void setDevice_subscribe_css(String device_subscribe_css) {
		this.device_subscribe_css = device_subscribe_css;
	}

	public String getDevice_protocol() {
		return device_protocol;
	}

	public void setDevice_protocol(String device_protocol) {
		this.device_protocol = device_protocol;
	}

	public String getSip_profile() {
		return sip_profile;
	}

	public void setSip_profile(String sip_profile) {
		this.sip_profile = sip_profile;
	}

	public String getCommon_device_configuration() {
		return common_device_configuration;
	}

	public void setCommon_device_configuration(String common_device_configuration) {
		this.common_device_configuration = common_device_configuration;
	}

	public String getDirectory_number_1() {
		return directory_number_1;
	}

	public void setDirectory_number_1(String directory_number_1) {
		this.directory_number_1 = directory_number_1;
	}

	public String getRoute_partition_1() {
		return route_partition_1;
	}

	public void setRoute_partition_1(String route_partition_1) {
		this.route_partition_1 = route_partition_1;
	}

	public String getLine_css_1() {
		return line_css_1;
	}

	public void setLine_css_1(String line_css_1) {
		this.line_css_1 = line_css_1;
	}

	public String getE164_number_mask_1() {
		return e164_number_mask_1;
	}

	public void setE164_number_mask_1(String e164_number_mask_1) {
		this.e164_number_mask_1 = e164_number_mask_1;
	}

	public String getForward_all_voice_mail_1() {
		return forward_all_voice_mail_1;
	}

	public void setForward_all_voice_mail_1(String forward_all_voice_mail_1) {
		this.forward_all_voice_mail_1 = forward_all_voice_mail_1;
	}

	public String getForward_all_destination_1() {
		return forward_all_destination_1;
	}

	public void setForward_all_destination_1(String forward_all_destination_1) {
		this.forward_all_destination_1 = forward_all_destination_1;
	}

	public String getForward_all_css_1() {
		return forward_all_css_1;
	}

	public void setForward_all_css_1(String forward_all_css_1) {
		this.forward_all_css_1 = forward_all_css_1;
	}

	public String getForward_busy_internal_voice_mail_1() {
		return forward_busy_internal_voice_mail_1;
	}

	public void setForward_busy_internal_voice_mail_1(String forward_busy_internal_voice_mail_1) {
		this.forward_busy_internal_voice_mail_1 = forward_busy_internal_voice_mail_1;
	}

	public String getForward_busy_internal_destination_1() {
		return forward_busy_internal_destination_1;
	}

	public void setForward_busy_internal_destination_1(String forward_busy_internal_destination_1) {
		this.forward_busy_internal_destination_1 = forward_busy_internal_destination_1;
	}

	public String getForward_busy_internal_css_1() {
		return forward_busy_internal_css_1;
	}

	public void setForward_busy_internal_css_1(String forward_busy_internal_css_1) {
		this.forward_busy_internal_css_1 = forward_busy_internal_css_1;
	}

	public String getForward_busy_external_voice_mail_1() {
		return forward_busy_external_voice_mail_1;
	}

	public void setForward_busy_external_voice_mail_1(String forward_busy_external_voice_mail_1) {
		this.forward_busy_external_voice_mail_1 = forward_busy_external_voice_mail_1;
	}

	public String getForward_busy_external_destination_1() {
		return forward_busy_external_destination_1;
	}

	public void setForward_busy_external_destination_1(String forward_busy_external_destination_1) {
		this.forward_busy_external_destination_1 = forward_busy_external_destination_1;
	}

	public String getForward_busy_external_css_1() {
		return forward_busy_external_css_1;
	}

	public void setForward_busy_external_css_1(String forward_busy_external_css_1) {
		this.forward_busy_external_css_1 = forward_busy_external_css_1;
	}

	public String getForward_no_answer_internal_voice_mail_1() {
		return forward_no_answer_internal_voice_mail_1;
	}

	public void setForward_no_answer_internal_voice_mail_1(String forward_no_answer_internal_voice_mail_1) {
		this.forward_no_answer_internal_voice_mail_1 = forward_no_answer_internal_voice_mail_1;
	}

	public String getForward_no_answer_internal_destination_1() {
		return forward_no_answer_internal_destination_1;
	}

	public void setForward_no_answer_internal_destination_1(String forward_no_answer_internal_destination_1) {
		this.forward_no_answer_internal_destination_1 = forward_no_answer_internal_destination_1;
	}

	public String getForward_no_answer_internal_css_1() {
		return forward_no_answer_internal_css_1;
	}

	public void setForward_no_answer_internal_css_1(String forward_no_answer_internal_css_1) {
		this.forward_no_answer_internal_css_1 = forward_no_answer_internal_css_1;
	}

	public String getForward_no_answer_external_voice_mail_1() {
		return forward_no_answer_external_voice_mail_1;
	}

	public void setForward_no_answer_external_voice_mail_1(String forward_no_answer_external_voice_mail_1) {
		this.forward_no_answer_external_voice_mail_1 = forward_no_answer_external_voice_mail_1;
	}

	public String getForward_no_answer_external_destination_1() {
		return forward_no_answer_external_destination_1;
	}

	public void setForward_no_answer_external_destination_1(String forward_no_answer_external_destination_1) {
		this.forward_no_answer_external_destination_1 = forward_no_answer_external_destination_1;
	}

	public String getForward_no_answer_external_css_1() {
		return forward_no_answer_external_css_1;
	}

	public void setForward_no_answer_external_css_1(String forward_no_answer_external_css_1) {
		this.forward_no_answer_external_css_1 = forward_no_answer_external_css_1;
	}

	public String getForward_no_coverage_internal_voice_mail_1() {
		return forward_no_coverage_internal_voice_mail_1;
	}

	public void setForward_no_coverage_internal_voice_mail_1(String forward_no_coverage_internal_voice_mail_1) {
		this.forward_no_coverage_internal_voice_mail_1 = forward_no_coverage_internal_voice_mail_1;
	}

	public String getForward_no_coverage_internal_destination_1() {
		return forward_no_coverage_internal_destination_1;
	}

	public void setForward_no_coverage_internal_destination_1(String forward_no_coverage_internal_destination_1) {
		this.forward_no_coverage_internal_destination_1 = forward_no_coverage_internal_destination_1;
	}

	public String getForward_no_coverage_internal_css_1() {
		return forward_no_coverage_internal_css_1;
	}

	public void setForward_no_coverage_internal_css_1(String forward_no_coverage_internal_css_1) {
		this.forward_no_coverage_internal_css_1 = forward_no_coverage_internal_css_1;
	}

	public String getForward_no_coverage_external_voice_mail_1() {
		return forward_no_coverage_external_voice_mail_1;
	}

	public void setForward_no_coverage_external_voice_mail_1(String forward_no_coverage_external_voice_mail_1) {
		this.forward_no_coverage_external_voice_mail_1 = forward_no_coverage_external_voice_mail_1;
	}

	public String getForward_no_coverage_external_destination_1() {
		return forward_no_coverage_external_destination_1;
	}

	public void setForward_no_coverage_external_destination_1(String forward_no_coverage_external_destination_1) {
		this.forward_no_coverage_external_destination_1 = forward_no_coverage_external_destination_1;
	}

	public String getForward_no_coverage_external_css_1() {
		return forward_no_coverage_external_css_1;
	}

	public void setForward_no_coverage_external_css_1(String forward_no_coverage_external_css_1) {
		this.forward_no_coverage_external_css_1 = forward_no_coverage_external_css_1;
	}

	public String getForward_no_answer_ring_duration_1() {
		return forward_no_answer_ring_duration_1;
	}

	public void setForward_no_answer_ring_duration_1(String forward_no_answer_ring_duration_1) {
		this.forward_no_answer_ring_duration_1 = forward_no_answer_ring_duration_1;
	}

	public String getCall_pickup_group_1() {
		return call_pickup_group_1;
	}

	public void setCall_pickup_group_1(String call_pickup_group_1) {
		this.call_pickup_group_1 = call_pickup_group_1;
	}

	public String getLine_text_label_1() {
		return line_text_label_1;
	}

	public void setLine_text_label_1(String line_text_label_1) {
		this.line_text_label_1 = line_text_label_1;
	}

	public String getMaximum_number_of_calls_1() {
		return maximum_number_of_calls_1;
	}

	public void setMaximum_number_of_calls_1(String maximum_number_of_calls_1) {
		this.maximum_number_of_calls_1 = maximum_number_of_calls_1;
	}

	public String getBusy_trigger_1() {
		return busy_trigger_1;
	}

	public void setBusy_trigger_1(String busy_trigger_1) {
		this.busy_trigger_1 = busy_trigger_1;
	}

	public String getLine_description_1() {
		return line_description_1;
	}

	public void setLine_description_1(String line_description_1) {
		this.line_description_1 = line_description_1;
	}

	public String getAlerting_name_1() {
		return alerting_name_1;
	}

	public void setAlerting_name_1(String alerting_name_1) {
		this.alerting_name_1 = alerting_name_1;
	}

	public String getAscii_alerting_name_1() {
		return ascii_alerting_name_1;
	}

	public void setAscii_alerting_name_1(String ascii_alerting_name_1) {
		this.ascii_alerting_name_1 = ascii_alerting_name_1;
	}

	public String getForward_on_cti_failure_voice_mail_1() {
		return forward_on_cti_failure_voice_mail_1;
	}

	public void setForward_on_cti_failure_voice_mail_1(String forward_on_cti_failure_voice_mail_1) {
		this.forward_on_cti_failure_voice_mail_1 = forward_on_cti_failure_voice_mail_1;
	}

	public String getForward_on_cti_failure_destination_1() {
		return forward_on_cti_failure_destination_1;
	}

	public void setForward_on_cti_failure_destination_1(String forward_on_cti_failure_destination_1) {
		this.forward_on_cti_failure_destination_1 = forward_on_cti_failure_destination_1;
	}

	public String getForward_on_cti_failure_css_1() {
		return forward_on_cti_failure_css_1;
	}

	public void setForward_on_cti_failure_css_1(String forward_on_cti_failure_css_1) {
		this.forward_on_cti_failure_css_1 = forward_on_cti_failure_css_1;
	}

	public String getDisplay_1() {
		return display_1;
	}

	public void setDisplay_1(String display_1) {
		this.display_1 = display_1;
	}

	public String getAscii_display_1() {
		return ascii_display_1;
	}

	public void setAscii_display_1(String ascii_display_1) {
		this.ascii_display_1 = ascii_display_1;
	}

	public String getForward_unregistered_internal_voice_mail_1() {
		return forward_unregistered_internal_voice_mail_1;
	}

	public void setForward_unregistered_internal_voice_mail_1(String forward_unregistered_internal_voice_mail_1) {
		this.forward_unregistered_internal_voice_mail_1 = forward_unregistered_internal_voice_mail_1;
	}

	public String getForward_unregistered_internal_destination_1() {
		return forward_unregistered_internal_destination_1;
	}

	public void setForward_unregistered_internal_destination_1(String forward_unregistered_internal_destination_1) {
		this.forward_unregistered_internal_destination_1 = forward_unregistered_internal_destination_1;
	}

	public String getForward_unregistered_internal_css_1() {
		return forward_unregistered_internal_css_1;
	}

	public void setForward_unregistered_internal_css_1(String forward_unregistered_internal_css_1) {
		this.forward_unregistered_internal_css_1 = forward_unregistered_internal_css_1;
	}

	public String getForward_unregistered_external_voice_mail_1() {
		return forward_unregistered_external_voice_mail_1;
	}

	public void setForward_unregistered_external_voice_mail_1(String forward_unregistered_external_voice_mail_1) {
		this.forward_unregistered_external_voice_mail_1 = forward_unregistered_external_voice_mail_1;
	}

	public String getForward_unregistered_external_destination_1() {
		return forward_unregistered_external_destination_1;
	}

	public void setForward_unregistered_external_destination_1(String forward_unregistered_external_destination_1) {
		this.forward_unregistered_external_destination_1 = forward_unregistered_external_destination_1;
	}

	public String getForward_unregistered_external_css_1() {
		return forward_unregistered_external_css_1;
	}

	public void setForward_unregistered_external_css_1(String forward_unregistered_external_css_1) {
		this.forward_unregistered_external_css_1 = forward_unregistered_external_css_1;
	}

	public String getRecording_profile_1() {
		return recording_profile_1;
	}

	public void setRecording_profile_1(String recording_profile_1) {
		this.recording_profile_1 = recording_profile_1;
	}

	public String getRecording_media_source_1() {
		return recording_media_source_1;
	}

	public void setRecording_media_source_1(String recording_media_source_1) {
		this.recording_media_source_1 = recording_media_source_1;
	}

	public String getRecording_option_1() {
		return recording_option_1;
	}

	public void setRecording_option_1(String recording_option_1) {
		this.recording_option_1 = recording_option_1;
	}

	public String getCustom_maybe_owner_user_id() {
		return custom_maybe_owner_user_id;
	}

	public void setCustom_maybe_owner_user_id(String custom_maybe_owner_user_id) {
		this.custom_maybe_owner_user_id = custom_maybe_owner_user_id;
	}

	public String getCustom_use_flag() {
		return custom_use_flag;
	}

	public void setCustom_use_flag(String custom_use_flag) {
		this.custom_use_flag = custom_use_flag;
	}

	public String getCustom_public_en() {
		return custom_public_en;
	}

	public void setCustom_public_en(String custom_public_en) {
		this.custom_public_en = custom_public_en;
	}

	public String getCustom_public_ru() {
		return custom_public_ru;
	}

	public void setCustom_public_ru(String custom_public_ru) {
		this.custom_public_ru = custom_public_ru;
	}
	
	public String getCustom_e164_mask() {
		return custom_e164_mask;
	}

	public void setCustom_e164_mask(String custom_e164_mask) {
		this.custom_e164_mask = custom_e164_mask;
	}

	@Override
	public String toString() {
		return "DataView [id=" + id + ", device_name=" + device_name + ", description=" + description + ", device_pool="
				+ device_pool + ", phone_button_template=" + phone_button_template + ", css=" + css + ", location="
				+ location + ", softkey_template=" + softkey_template + ", device_type=" + device_type + ", user_id_1="
				+ user_id_1 + ", common_phone_profile=" + common_phone_profile + ", owner_user_id=" + owner_user_id
				+ ", device_presence_group=" + device_presence_group + ", device_security_profile="
				+ device_security_profile + ", device_subscribe_css=" + device_subscribe_css + ", device_protocol="
				+ device_protocol + ", sip_profile=" + sip_profile + ", common_device_configuration="
				+ common_device_configuration + ", directory_number_1=" + directory_number_1 + ", route_partition_1="
				+ route_partition_1 + ", line_css_1=" + line_css_1 + ", e164_number_mask_1=" + e164_number_mask_1
				+ ", forward_all_voice_mail_1=" + forward_all_voice_mail_1 + ", forward_all_destination_1="
				+ forward_all_destination_1 + ", forward_all_css_1=" + forward_all_css_1
				+ ", forward_busy_internal_voice_mail_1=" + forward_busy_internal_voice_mail_1
				+ ", forward_busy_internal_destination_1=" + forward_busy_internal_destination_1
				+ ", forward_busy_internal_css_1=" + forward_busy_internal_css_1
				+ ", forward_busy_external_voice_mail_1=" + forward_busy_external_voice_mail_1
				+ ", forward_busy_external_destination_1=" + forward_busy_external_destination_1
				+ ", forward_busy_external_css_1=" + forward_busy_external_css_1
				+ ", forward_no_answer_internal_voice_mail_1=" + forward_no_answer_internal_voice_mail_1
				+ ", forward_no_answer_internal_destination_1=" + forward_no_answer_internal_destination_1
				+ ", forward_no_answer_internal_css_1=" + forward_no_answer_internal_css_1
				+ ", forward_no_answer_external_voice_mail_1=" + forward_no_answer_external_voice_mail_1
				+ ", forward_no_answer_external_destination_1=" + forward_no_answer_external_destination_1
				+ ", forward_no_answer_external_css_1=" + forward_no_answer_external_css_1
				+ ", forward_no_coverage_internal_voice_mail_1=" + forward_no_coverage_internal_voice_mail_1
				+ ", forward_no_coverage_internal_destination_1=" + forward_no_coverage_internal_destination_1
				+ ", forward_no_coverage_internal_css_1=" + forward_no_coverage_internal_css_1
				+ ", forward_no_coverage_external_voice_mail_1=" + forward_no_coverage_external_voice_mail_1
				+ ", forward_no_coverage_external_destination_1=" + forward_no_coverage_external_destination_1
				+ ", forward_no_coverage_external_css_1=" + forward_no_coverage_external_css_1
				+ ", forward_no_answer_ring_duration_1=" + forward_no_answer_ring_duration_1 + ", call_pickup_group_1="
				+ call_pickup_group_1 + ", line_text_label_1=" + line_text_label_1 + ", maximum_number_of_calls_1="
				+ maximum_number_of_calls_1 + ", busy_trigger_1=" + busy_trigger_1 + ", line_description_1="
				+ line_description_1 + ", alerting_name_1=" + alerting_name_1 + ", ascii_alerting_name_1="
				+ ascii_alerting_name_1 + ", forward_on_cti_failure_voice_mail_1=" + forward_on_cti_failure_voice_mail_1
				+ ", forward_on_cti_failure_destination_1=" + forward_on_cti_failure_destination_1
				+ ", forward_on_cti_failure_css_1=" + forward_on_cti_failure_css_1 + ", display_1=" + display_1
				+ ", ascii_display_1=" + ascii_display_1 + ", forward_unregistered_internal_voice_mail_1="
				+ forward_unregistered_internal_voice_mail_1 + ", forward_unregistered_internal_destination_1="
				+ forward_unregistered_internal_destination_1 + ", forward_unregistered_internal_css_1="
				+ forward_unregistered_internal_css_1 + ", forward_unregistered_external_voice_mail_1="
				+ forward_unregistered_external_voice_mail_1 + ", forward_unregistered_external_destination_1="
				+ forward_unregistered_external_destination_1 + ", forward_unregistered_external_css_1="
				+ forward_unregistered_external_css_1 + ", recording_profile_1=" + recording_profile_1
				+ ", recording_media_source_1=" + recording_media_source_1 + ", recording_option_1="
				+ recording_option_1 + ", custom_maybe_owner_user_id=" + custom_maybe_owner_user_id
				+ ", custom_use_flag=" + custom_use_flag + ", custom_public_en=" + custom_public_en
				+ ", custom_public_ru=" + custom_public_ru + "]";
	}
}
