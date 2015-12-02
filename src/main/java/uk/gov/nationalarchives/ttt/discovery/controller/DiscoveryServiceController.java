package uk.gov.nationalarchives.ttt.discovery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.nationalarchives.ttt.discovery.dao.GraphRepository;

/**
 * Created by jcharlet on 12/2/15.
 */
@RestController
public class DiscoveryServiceController {

    private final GraphRepository graphRepository;

    @Autowired
    public DiscoveryServiceController(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    @RequestMapping("/findRelatedDocuments")
        public String findRelatedDocuments(@RequestParam(value="ref") String ref, @RequestParam(value="scoreThreshold") Double scoreThreshold) {
            return graphRepository.findRelatedDocuments(ref,scoreThreshold);
        }
}
