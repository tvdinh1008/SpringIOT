package com.iot.config;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.authentication.JwtTokenProvider;
import com.iot.dao.IDeviceDao;
import com.iot.entity.DeviceEntity;
import com.iot.mqtt.CollectDataModel;
import com.iot.payloads.JwtAuthRequest;
import com.iot.payloads.JwtAuthResponse;
import com.iot.service.ISensorService;

@Configuration
@EnableAsync
@EnableScheduling
public class MQTTConfig {
	private static final Logger logger = Logger.getLogger(MQTTConfig.class);
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private ISensorService sensorService;

	// Of course , you can define the Executor too
	@Bean
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setServerURIs(new String[] { "tcp://mqtt.eclipse.org:1883" });
		factory.setUserName("username");
		factory.setPassword("password");
		factory.setCleanSession(true);
		factory.setKeepAliveInterval(10);

		return factory;
	}

	// subscriber
	@Bean
	public MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("clientId",
				mqttClientFactory(), "iot/authentication", "iot/collectdata");
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(2);
		adapter.setOutputChannel(outbount());
		adapter.setAutoStartup(true);
		adapter.setTaskScheduler(taskScheduler()); // cái hay nhất là ở đây vì khi chạy mqtt subscriber cần tạo 1 task
													// để chạy nhé !!!
		return adapter;
	}

	// callback subscribe
	@Bean
	public PublishSubscribeChannel outbount() {
		PublishSubscribeChannel psc = new PublishSubscribeChannel();
		psc.subscribe(new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				String received_topic = (String) message.getHeaders().get(MqttHeaders.TOPIC);
				// Xử lý topic nhận được ở đây
				System.out.println(
						"**************** Message : " + message.getPayload() + " Headers:" + message.getHeaders());
				if (received_topic.equals("iot/authentication")) {
					// bao gồm user_token(Người tạo device) và deviceId;
					Long deviceID = 0l;
					try {
						JwtAuthRequest authModel = new ObjectMapper().readValue(message.getPayload().toString(),
								JwtAuthRequest.class);
						deviceID = authModel.getId();
						if (StringUtils.isNotBlank(authModel.getUser_token())
								&& tokenProvider.validateJwtToken(authModel.getUser_token())) {
							String username = tokenProvider.getUserNameFromJwtToken(authModel.getUser_token());
							// Sau khi xác thực thì ta sẽ chuyển alive của device=1 tức là nó sẵn sàng
							DeviceEntity device = deviceDao.findByIdAndUsername(username, authModel.getId());
							if (device != null) {
								String topic_result = "iot/authentication_result_" + device.getId();
								String jwt = tokenProvider.generateToken(device.getUser().getId(), device.getId());
								System.out.println("Device token:" + jwt);
								device.setAlive(1);
								if (deviceDao.update(device) != null) {
									// nếu xác thực thành công
									JwtAuthResponse data = new JwtAuthResponse();
									data.setDevice_token(jwt);
									data.setId(device.getId());
									String jsonData = new ObjectMapper().writeValueAsString(data);
									Message<String> messageauth = MessageBuilder.withPayload(jsonData)
											.setHeader(MqttHeaders.TOPIC, topic_result).build();
									mqqtMessageHandler().handleMessage(messageauth);
									return;
								}

							}

						}

					} catch (IOException e) {
						logger.error(e.getMessage());
					}
					String topic_result = "iot/authentication_result_" + deviceID;
					String data = "false";
					Message<String> messageauth = MessageBuilder.withPayload(data)
							.setHeader(MqttHeaders.TOPIC, topic_result).build();
					mqqtMessageHandler().handleMessage(messageauth);
					return;
				}
				if (received_topic.equals("iot/collectdata")) {
					try {
						CollectDataModel collectData = new ObjectMapper().readValue(message.getPayload().toString(),
								CollectDataModel.class);

						if (StringUtils.isNotBlank(collectData.getDevice_token())
								&& tokenProvider.validateJwtToken(collectData.getDevice_token())) {

							List<Long> ids = tokenProvider.parseToken(collectData.getDevice_token());
							if (ids != null && ids.size() == 2) {
								if (ids.get(1) == collectData.getId()) {
									sensorService.saveCollectData(collectData);
								}

							}
						}

					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		});
		return psc;
	}

	// publisher
	@Bean
	public MqttPahoMessageHandler mqqtMessageHandler() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("clientId", mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("demo01");
		return messageHandler;
	}

}
