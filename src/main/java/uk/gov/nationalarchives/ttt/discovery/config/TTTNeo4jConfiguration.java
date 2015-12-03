/** 
 * Copyright (c) 2015, The National Archives <digitalpreservation@nationalarchives.gov.uk> 
 * http://www.nationalarchives.gov.uk 
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 */
package uk.gov.nationalarchives.ttt.discovery.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("uk.gov.nationalarchives.ttt.neo4j.dao.neo4j")
@EnableTransactionManagement
public class TTTNeo4jConfiguration extends Neo4jConfiguration {

    public static final String HOST = "http://***REMOVED***:8990";
    public static final String PORT = "7474";
    public static final String USER = "neo4j";
    public static final String PASSWORD = "ttt";

    @Bean
    public Neo4jServer neo4jServer() {
        return new RemoteServer(HOST+":"+PORT, USER, PASSWORD);
    }

    @Bean
    public SessionFactory getSessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory("uk.gov.nationalarchives.ttt.neo4j.domain.graphperson");
    }

//    // needed for session in view in web-applications
    @Bean
//    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
