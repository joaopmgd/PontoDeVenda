package com.zedelivery.pontoDeVenda.resource;

import com.zedelivery.pontoDeVenda.domain.Pdv;
import com.zedelivery.pontoDeVenda.resource.util.HeaderUtil;
import com.zedelivery.pontoDeVenda.service.PdvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pdv.
 */
@RestController
@RequestMapping("/api")
public class PdvResource {

    private static final String ENTITY_NAME = "pdv";
    private final Logger log = LoggerFactory.getLogger(PdvResource.class);
    private final PdvService pdvService;

    public PdvResource(PdvService pdvService) {
        this.pdvService = pdvService;
    }

    /**
     * POST  /pdvs : Create a new pdv.
     *
     * @param pdv the pdv to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pdv, or with status 400 (Bad Request) if the pdv has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pdvs")
    public ResponseEntity<Pdv> createPdv(@Valid @RequestBody Pdv pdv) throws URISyntaxException {
        log.debug("REST request to save Pdv : {}", pdv);
        if (pdv.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new pdv cannot be created.");
        }
        Pdv result = pdvService.save(pdv);
        return ResponseEntity.created(new URI("/api/pdvs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
                .body(result);
    }

    /**
     * PUT  /pdvs : Updates an existing pdv.
     *
     * @param pdv the pdv to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pdv,
     * or with status 400 (Bad Request) if the pdv is not valid,
     * or with status 500 (Internal Server Error) if the pdv couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pdvs")
    public ResponseEntity<Pdv> updatePdv(@Valid @RequestBody Pdv pdv) throws URISyntaxException {
        log.debug("REST request to update Pdv : {}", pdv);
        if (pdv.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID, ID not found");
        }
        Pdv result = pdvService.save(pdv);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pdv.getId()))
                .body(result);
    }

    /**
     * GET  /pdvs : get all the pdvs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pdvs in body
     */
    @GetMapping("/pdvs")
    public List<Pdv> getAllPdvs() {
        log.debug("REST request to get all Pdvs");
        return pdvService.findAll();
    }


    /**
     * GET  /pdvs/:id : get the "id" pdv.
     *
     * @param id the id of the pdv to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pdv, or with status 404 (Not Found)
     */
    @GetMapping("/pdvs/{id}")
    public ResponseEntity<Pdv> getPdv(@PathVariable String id) {
        log.debug("REST request to get Pdv : {}", id);
        Optional<Pdv> pdv = pdvService.findOne(id);
        return pdv.map((response) -> {
            return (ResponseEntity.ok()).body(response);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /pdvs/:id : get the "id" pdv.
     *
     * @param lat the latitude of the area searched
     * @param lon the longitude of the area searched
     * @return the ResponseEntity with status 200 (OK) and with body the pdv, or with status 404 (Not Found)
     */
    @GetMapping("/pdvs/closer")
    public ResponseEntity<Pdv> getClosestPdv(@RequestParam(value = "lat") double lat, @RequestParam(value = "lon") double lon) {
        log.debug("REST request to get the closest Pdv to the lat {} lon {}", lat, lon);
        Pdv pdv = pdvService.findClosestOne(lat, lon);
        if (pdv == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pdv.getId()))
                .body(pdv);
    }

    /**
     * DELETE  /pdvs/:id : delete the "id" pdv.
     *
     * @param id the id of the pdv to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pdvs/{id}")
    public ResponseEntity<Void> deletePdv(@PathVariable String id) {
        log.debug("REST request to delete Pdv : {}", id);
        pdvService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
