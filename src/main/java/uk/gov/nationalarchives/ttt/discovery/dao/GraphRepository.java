package uk.gov.nationalarchives.ttt.discovery.dao;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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

    public Iterable<Map<String, Object>> findRelatedDocuments(String ref, Double scoreThreshold, Boolean
            isPathReturned) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ref", ref);
        parameters.put("scoreThreshold", scoreThreshold);

        //API: get related documents to doc ref
        String cypherQuery="MATCH (d:Document) " +
        "WHERE d.genre={ref} "+
        "MATCH path=(d:Document)<-[:IN_CONTAINER]-(p:Person)-[rels:LINK*1..7]-(p2:Person)-[:IN_CONTAINER]-> "  +
                "(d2:Document)"+
        "WHERE ALL(r in rels WHERE has(r.score) AND r.score>{scoreThreshold}) "+
        "WITH extract(x in rels| x.score) as scores,path,d,p,rels,p2,d2 "+
        "WITH reduce(res=1, x in scores| res * x/{scoreThreshold}) as totalScore,scores,path,d,p,rels,p2,d2 "+
        "WHERE totalScore>1 "+
        "WITH length(path)-2 as sizeOfPeopleChain,totalScore,scores,path,d,p,rels,p2,d2 "+
        "WITH d,d2,collect({totalScore:totalScore, sizeOfPeopleChain:sizeOfPeopleChain,p:p,p2:p2";

        cypherQuery+="}) as rs,max "  +
                "(totalScore) as maxTotalScore "+
        "WITH d,d2,maxTotalScore,filter(x in rs where x.totalScore=maxTotalScore) as rsFilter unwind rsFilter as rs ";

        cypherQuery+="return DISTINCT d as originalDocument,rs.p as originalPerson,rs.p2 as relatedPerson,d2 as relatedDocument,rs" +
                ".totalScore as totalScore,rs.sizeOfPeopleChain as sizeOfPeopleChain ";

        cypherQuery+="ORDER by totalScore DESC";

        String output="";

        Result result = session.query(
                cypherQuery,
                parameters);

        return result.queryResults();
    }


}
