package com.example.service1.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public SnapshotTriggerDefinition app1SnapshotTrigger(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 100);
    }

    @Bean
    public SpringAggregateSnapshotter snapshotter(ParameterResolverFactory parameterResolverFactory, EventStore eventStore,
            TransactionManager transactionManager) {
        Executor executor = Executors.newFixedThreadPool(10);
        return SpringAggregateSnapshotter.builder().eventStore(eventStore).transactionManager(transactionManager)
                .parameterResolverFactory(parameterResolverFactory).executor(executor).build();
    }
}
