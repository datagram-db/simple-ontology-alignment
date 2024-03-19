package it.giacomobergami.simpleschema.barbara;// Generated from barbara.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link barbaraParser}.
 */
public interface barbaraListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link barbaraParser#ontology}.
	 * @param ctx the parse tree
	 */
	void enterOntology(barbaraParser.OntologyContext ctx);
	/**
	 * Exit a parse tree produced by {@link barbaraParser#ontology}.
	 * @param ctx the parse tree
	 */
	void exitOntology(barbaraParser.OntologyContext ctx);
	/**
	 * Enter a parse tree produced by {@link barbaraParser#concept}.
	 * @param ctx the parse tree
	 */
	void enterConcept(barbaraParser.ConceptContext ctx);
	/**
	 * Exit a parse tree produced by {@link barbaraParser#concept}.
	 * @param ctx the parse tree
	 */
	void exitConcept(barbaraParser.ConceptContext ctx);
	/**
	 * Enter a parse tree produced by {@link barbaraParser#properties}.
	 * @param ctx the parse tree
	 */
	void enterProperties(barbaraParser.PropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link barbaraParser#properties}.
	 * @param ctx the parse tree
	 */
	void exitProperties(barbaraParser.PropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link barbaraParser#native_type}.
	 * @param ctx the parse tree
	 */
	void enterNative_type(barbaraParser.Native_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link barbaraParser#native_type}.
	 * @param ctx the parse tree
	 */
	void exitNative_type(barbaraParser.Native_typeContext ctx);
}