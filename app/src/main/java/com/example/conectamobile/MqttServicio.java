package com.example.conectamobile;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttServicio {

    private MqttClient client;
    private MqttConnectOptions connectOptions;

    public MqttServicio(String brokerUrl, String clientId) {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(brokerUrl, clientId, persistence);
            connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
        } catch (MqttException e) {
            throw new RuntimeException("Error al crear el cliente MQTT", e);
        }
    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }

    public void connect() {
        try {
            if (!client.isConnected()) {
                client.connect(connectOptions);
                System.out.println("Conexión exitosa al broker MQTT");
            }
        } catch (MqttException e) {
            throw new RuntimeException("Error al conectar al broker MQTT", e);
        }
    }

    public void disconnect() {
        try {
            if (client.isConnected()) {
                client.disconnect();
                System.out.println("Desconexión exitosa del broker MQTT");
            }
        } catch (MqttException e) {
            throw new RuntimeException("Error al desconectar del broker MQTT", e);
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1); // Establecer el QoS
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            throw new RuntimeException("Error al publicar el mensaje", e);
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            throw new RuntimeException("Error al suscribirse al tópico", e);
        }
    }

    public boolean isConnected() {
        return client.isConnected();
    }
}