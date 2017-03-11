package com.penpi.server.utils;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtils {

	protected static final Logger LOG = LoggerFactory
			.getLogger(JPushUtils.class);

	private static final String masterSecret = "6fad0e2c9501ddaebb8c79fd";
	private static final String appKey = "13be36cf0e5b0452678218f3";

	public static JPushClient jpushClient = null;

	@Test
	public void test() {
		push("alert", "title", "content");
	}

	@SuppressWarnings("unchecked")
	public static void push(String alert, String title, String content) {

		jpushClient = new JPushClient(masterSecret, appKey, null,
				ClientConfig.getInstance());
		Map<String, String> map = null;
		if (content != null) {
			map = new HashedMap();
			map.put("content", content);
		}

		PushPayload payload = buildPushObject_all_all_alert(alert, title, map);

		try {
			LOG.info("Got result - " + payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	public static PushPayload buildPushObject_all_all_alert(String alert,
			String title, Map<String, String> map) {

		return PushPayload
				.newBuilder()
				// 设置接受的平台
				.setPlatform(Platform.all())
				// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setAudience(Audience.all())
				.setNotification(Notification.android(alert, title, map))
				.build();
	}
}