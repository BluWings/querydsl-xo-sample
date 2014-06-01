package com.smbtec.xo.querydsl.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.neo4j.cypherdsl.CypherQuery.lookup;
import static org.neo4j.cypherdsl.CypherQuery.param;
import static org.neo4j.cypherdsl.CypherQuery.start;
import static org.neo4j.cypherdsl.querydsl.CypherQueryDSL.identifier;

import java.net.URISyntaxException;
import java.util.Collection;

import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.buschmais.xo.api.XOManager;
import com.buschmais.xo.api.bootstrap.XOUnit;
import com.buschmais.xo.neo4j.test.AbstractNeo4jXOManagerTest;
import com.smbtec.xo.querydsl.sample.domain.Group;
import com.smbtec.xo.querydsl.sample.domain.Person;
import com.smbtec.xo.querydsl.sample.domain.QPerson;

import static org.neo4j.cypherdsl.CypherQuery.allNodes;
import static org.neo4j.cypherdsl.CypherQuery.identifier;
import static org.neo4j.cypherdsl.CypherQuery.lookup;
import static org.neo4j.cypherdsl.CypherQuery.nodesById;
import static org.neo4j.cypherdsl.CypherQuery.start;
import static org.neo4j.cypherdsl.CypherQuery.match;
import static org.neo4j.cypherdsl.CypherQuery.node;
import static org.neo4j.cypherdsl.CypherQuery.value;
import static org.neo4j.cypherdsl.CypherQuery.param;
import static org.neo4j.cypherdsl.querydsl.CypherQueryDSL.identifier;
import static org.neo4j.cypherdsl.querydsl.CypherQueryDSL.toBooleanExpression;

import org.neo4j.cypherdsl.Identifier;
import org.neo4j.cypherdsl.grammar.Execute;
import org.neo4j.cypherdsl.grammar.ReturnNext;
import org.neo4j.cypherdsl.grammar.StartNext;
import org.neo4j.cypherdsl.querydsl.CypherQueryDSL;

@RunWith(Parameterized.class)
public class XoCypherQuerydslTest extends AbstractNeo4jXOManagerTest {

    private Person person;

    public XoCypherQuerydslTest(XOUnit xoUnit) {
        super(xoUnit);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getXOUnits() throws URISyntaxException {
        return xoUnits(Person.class, Group.class);
    }

    @Before
    public void setup() {
        final XOManager xoManager = getXoManager();
        xoManager.currentTransaction().begin();
        person = xoManager.create(Person.class);
        person.setName("Morpheus");
        xoManager.currentTransaction().commit();
    }

    @Test
    public void testFindPersonById() {
        QPerson p = QPerson.person;
        Execute execute = start(nodesById(identifier(p), 0)).returns(identifier(p));
        final XOManager xoManager = getXoManager();
        xoManager.currentTransaction().begin();
        Person result = xoManager.createQuery(execute.toString(), Person.class).execute().getSingleResult();
        xoManager.currentTransaction().commit();
        assertThat(person, equalTo(result));
    }

    @Test
    public void testFindPersonByName() {
        QPerson p = QPerson.person;
        Execute execute = start(allNodes(identifier(p))).where(toBooleanExpression(p.name.eq("Morpheus"))).returns(identifier(p));
        final XOManager xoManager = getXoManager();
        xoManager.currentTransaction().begin();
        Person result = xoManager.createQuery(execute.toString(), Person.class).execute().getSingleResult();
        xoManager.currentTransaction().commit();
        assertThat(person, equalTo(result));
    }


}
