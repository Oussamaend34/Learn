package com.ensah.demo;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.cassandra.CassandraVectorStore;
import org.springframework.context.annotation.Configuration;

import com.datastax.oss.driver.api.core.CqlSession;

@Configuration
public class VectorStoreConfig {

    public VectorStore appVectorStore(CqlSession session, EmbeddingModel embeddingModel) {
        return CassandraVectorStore.builder(embeddingModel)
            .session(session)
            .keyspace("my_keyspace")
            .table("my_vectors")
            .build();
    }

}
