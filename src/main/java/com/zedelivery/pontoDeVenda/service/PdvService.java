package com.zedelivery.pontoDeVenda.service;


import com.zedelivery.pontoDeVenda.domain.Location;
import com.zedelivery.pontoDeVenda.domain.Pdv;
import com.zedelivery.pontoDeVenda.repository.PdvRepository;
import com.zedelivery.pontoDeVenda.resource.util.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Pdv.
 */
@Service
public class PdvService {

    private final Logger log = LoggerFactory.getLogger(PdvService.class);

    private final PdvRepository pdvRepository;

    public PdvService(PdvRepository pdvRepository) {
        this.pdvRepository = pdvRepository;
    }

    /**
     * Save a pdv.
     *
     * @param pdv the entity to save
     * @return the persisted entity
     */
    public Pdv save(Pdv pdv) {
        log.debug("Request to save Pdv : {}", pdv);
        return pdvRepository.save(pdv);
    }

    /**
     * Get all the pdvs.
     *
     * @return the list of entities
     */
    public List<Pdv> findAll() {
        log.debug("Request to get all Pdvs");
        return pdvRepository.findAll();
    }


    /**
     * Get one pdv by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Pdv> findOne(String id) {
        log.debug("Request to get Pdv : {}", id);
        return pdvRepository.findById(id);
    }


    /**
     * Get one pdv by location.
     *
     * @param lat the latitude of the area searched
     * @param lon the longitude of the area searched
     * @return the entity
     */
    public Pdv findClosestOne(Double lat, Double lon) {
        log.debug("Request to get closest Pdv to lat {} and lon {}", lat, lon);
        List<Pdv> pdvList = pdvRepository.findAll();
        if (pdvList.size() == 0) {
            return null;
        }
        Double distance = null;
        Pdv closestPdv = null;
        Location location = new Location(lat, lon);
        for (Pdv pdv : pdvList) {
            Double distanceFromLocation = DistanceCalculator.calculateDistance(location,
                    new Location(pdv.getAddress().getCoordinates()[0], pdv.getAddress().getCoordinates()[1]));
            if (distance == null || distance > distanceFromLocation){
                distance = distanceFromLocation;
                closestPdv = pdv;
            }
        }
        return closestPdv;
    }


    /**
     * Delete the pdv by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Pdv : {}", id);
        pdvRepository.deleteById(id);
    }
}
