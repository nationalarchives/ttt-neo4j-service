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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TTTNeo4jConfiguration extends Neo4jConfiguration {

    @Autowired
    private Neo4jProperties neo4jProperties;

    @Bean
    public Neo4jServer neo4jServer() {
        return new RemoteServer(neo4jProperties.getHost()+":"+neo4jProperties.getPort(), neo4jProperties.getUser(),
                neo4jProperties.getPassword());
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory();
    }

    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
