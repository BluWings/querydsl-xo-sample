package com.smbtec.xo.querydsl.sample.domain;

import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Person")
public interface Person {

    @Indexed
    String getName();


    void setName(String name);

}