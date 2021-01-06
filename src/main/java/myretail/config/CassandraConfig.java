package myretail.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Slf4j
@Configuration
@EnableCassandraRepositories(basePackages = {"myretail.repository.impl"})
public class CassandraConfig {

    private PropertyConfig propertyConfig;

    @Autowired
    public CassandraConfig(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        log.info("Cassandra Host IP :: {}", propertyConfig.getCassandraHostIP());
        cluster.setContactPoints(propertyConfig.getCassandraHostIP());
        cluster.setPort(9042);
        cluster.setJmxReportingEnabled(false);
        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() {
        return new BasicCassandraMappingContext();
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session() throws Exception {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName("myretail_keyspace");
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.NONE);
        return session;
    }

    @Bean
    @Primary
    public CassandraOperations cassandraTemplateCart() throws Exception {
        return new CassandraTemplate(session().getObject());
    }
}
