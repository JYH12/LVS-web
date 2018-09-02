package com.unnet.triangle.lvs.master.constants;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;


public final class Constants {

	public static final String APP = "triangle-lvs-service";

	public static final String DATE_FORMAT = "yyyy-MM-dd-HH:mm:ss";

	public static final String NULL = "NULL";

	public static final String ENCODE = "utf-8";

	public static final String DEFAULT_SECRET_KEY = "ywOVVAKVuTnAJPVGHPUFxZCHAxeGSuEZMBoaHQBvtUk";

	public static final String V1_URI_API_PREFIX = "/api/lvs/v1/";

	public static final AtomicLong TASK_COUNTER = new AtomicLong(0);

	//public static final String DEFAULT_TEMPLATE_RELATIVE_PATH = "/template/lvs";

	/**
	 * callback
	 */
	public static final String DASHBOARD_API = StringUtils.isEmpty(System.getenv("DASHBOARD_ADDRESS"))
			? "http://127.0.0.1:8081"
			: System.getenv("DASHBOARD_ADDRESS");
	public static final String DASHBOARD_WEBHOOK_PATH = StringUtils.isEmpty(System.getenv("DASHBOARD_WEBHOOKPATH"))
			? "/_/webhook/policyhook"
			: System.getenv("DASHBOARD_WEBHOOKPATH");

	/**
	 * when task counter reach this value, will cause clean progress
	 */
	public static final long TASK_COUNTER_THRESHOLD = 32;

	/**
	 * Configure encrypt default key
	 */
	public static final String ENCRYPT_KEY = "ry^it#rl7iiUricl";

	public static final String OBJECT_CHECKSUM_FIELD_NAME = "checksum";

	/**
	 * Monitor part
	 */
	public static String MONITOR_WORKGROUP_ID = "workgroup_id";
	public static String MONITOR_WORKGROUP_NAME = "workgroup_name";
	public static String MONITOR_DATE = "date";
	public static String MONITOR_TIMESTAMP = "timestamp";
	public static String MONITOR_TYPE = "type";
	//public static String MONITOR_UNLAYER_TYPE = "unlayer";
	//public static String MONITOR_UNLAYER_MONITOR_TYPE = "unlayermonitor";
	//public static String MONITOR_UNLAYER_LOG_TYPE = "unlayerlog";
	public static String MONITOR_ELASTICSEARCH_OUTFLOW = "elasticsearch";
	public static String MONITOR_KAFKA_OUTFLOW = "kafka";

	/**
	 * Task part
	 */
	public static final String TASK_STATUS_PENDING = "pending";
	public static final String TASK_STATUS_RUNNING = "running";
	public static final String TASK_STATUS_SUCCESS = "success";
	public static final String TASK_STATUS_FAILED = "failed";
	public static final String TASK_TTL_TIMEOUT = "timeout";

	public static final String TASK_TYPE_MOD_CONFIG = "modconfig";
	public static final String TASK_TYPE_MOD_WORKGROUP = "modworkgroup";
	public static final String TASK_TYPE_TEST_CONFIG = "testconfig";
	public static final String TASK_TYPE_START = "start";
	public static final String TASK_TYPE_STOP = "stop";
	public static final String TASK_TYPE_DEPLOY = "deploy";
	public static final String TASK_TYPE_CONNECTIVITY = "connectivity";
	public static final String TASK_TYPE_HARDWAREUSAGE = "hardwareusage";

}
