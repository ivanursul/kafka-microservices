# Kafka Microservices

This is an example repo showing how we can use Kafka to built a scalable microservices

### Installing

cd to kafka directory

Start Zookeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka Node

```
bin/kafka-server-start.sh config/server.properties
```
Start Order and Customer Service

Explore commands in Postman Collection kafka-microservices.postman_collection

## Authors

* **Ivan Ursul** - [ivanursul.com](https://ivanursul.com)
