package com.victor.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
@EnableMongoRepositories(basePackages = "com.easipass.api.dao.clusterEight",
		mongoTemplateRef = "primaryMongoTemplate")
public class MBCluster8DataSourceConfig {
}
