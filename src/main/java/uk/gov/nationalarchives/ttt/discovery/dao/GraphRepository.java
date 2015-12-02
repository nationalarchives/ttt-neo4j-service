package uk.gov.nationalarchives.ttt.discovery.dao;

import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jcharlet on 16/11/15.
 */
@Repository
public class GraphRepository {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private final Session session;

    @Autowired
    public GraphRepository(Session session) {
        this.session=session;
    }

    public String findRelatedDocuments(String ref, Double scoreThreshold) {
        return ref + " " + scoreThreshold;
    }


}
