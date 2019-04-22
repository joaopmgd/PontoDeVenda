package com.zedelivery.pontoDeVenda.repository;

import com.zedelivery.pontoDeVenda.domain.Pdv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Pdv entity.
 */
@Repository
public interface PdvRepository extends MongoRepository<Pdv, String> {

}
