package uk.gov.nationalarchives.ttt.discovery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.nationalarchives.ttt.discovery.dao.GraphRepository;

import java.util.Map;

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
    public Iterable<Map<String, Object>> findRelatedDocuments(
            @RequestParam(value = "ref") String ref,
            @RequestParam (value = "scoreThreshold", defaultValue = "5", required = false) Double scoreThreshold,
            @RequestParam(value = "isPathReturned", defaultValue = "false", required = false) boolean isPathReturned) {
        return graphRepository.findRelatedDocuments(ref, scoreThreshold, isPathReturned);
    }
}
