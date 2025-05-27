package swiss.sib.rdf.sparql.examples;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import swiss.sib.rdf.sparql.examples.vocabularies.SIB;

public class ModularizerTest {
	String input = """
			select ?s
			where {
				?s a <http://example.org/Example> .
			}
			""";
	
	@Test
	void simpleModule() {
		String baseIri = "https://example.org/base/";
		TreeModel model = new TreeModel();
		Modularizer.modularizeQuery(baseIri, input, model);
		
		assertNotNull(model);
		assertFalse(model.isEmpty());
		
		for (var statement : model) {
			System.out.println(statement);
		}
		
	}
	
	String inputRename = """
			select (?s AS ?y)
			where {
				?s a <http://example.org/Example>.
			}
			""";
	
	@Test
	void simpleModuleRenamed() {
		String baseIri = "https://example.org/base/";
		TreeModel model = new TreeModel();
		Modularizer.modularizeQuery(baseIri, inputRename, model);
		
		assertNotNull(model);
		assertFalse(model.isEmpty());
		Spliterator<Statement> statements = model.getStatements(null, RDF.TYPE, SIB.PORT).spliterator();
		Stream<Statement> ss = StreamSupport.stream(statements, false);
		assertEquals(2L, ss.count());
		
		
		for (var statement : model) {
			System.out.println(statement);
		}
	}


	String inputoption = """
			select (?s AS ?y) ?type
			where {
				?s a ?type.
			}
			""";
	
	@Test
	void simpleModuleOption() {
		String baseIri = "https://example.org/base/";
		TreeModel model = new TreeModel();
		Modularizer.modularizeQuery(baseIri, inputoption, model);
		
		assertNotNull(model);
		assertFalse(model.isEmpty());
		Spliterator<Statement> statements = model.getStatements(null, RDF.TYPE, SIB.PORT).spliterator();
		Stream<Statement> ss = StreamSupport.stream(statements, false);
		assertEquals(3L, ss.count());
		
		
		for (var statement : model) {
			System.out.println(statement);
		}
	}
}
