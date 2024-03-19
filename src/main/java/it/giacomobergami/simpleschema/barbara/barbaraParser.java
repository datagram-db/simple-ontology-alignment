package it.giacomobergami.simpleschema.barbara;// Generated from barbara.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class barbaraParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, EscapedString=15, SPACE=16, 
		LIST=17, COMMENT=18, LINE_COMMENT=19;
	public static final int
		RULE_ontology = 0, RULE_concept = 1, RULE_properties = 2, RULE_native_type = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"ontology", "concept", "properties", "native_type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'concept'", "':'", "'STRING'", "'DOUBLE'", "'INTEGER'", 
			"'BOOLEAN'", "'DATE'", "'YEAR'", "'MONTH'", "'DAY'", "'WEEK'", "'CONCEPT'", 
			null, null, "'LIST'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "EscapedString", "SPACE", "LIST", "COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "barbara.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public barbaraParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OntologyContext extends ParserRuleContext {
		public Token name;
		public TerminalNode EscapedString() { return getToken(barbaraParser.EscapedString, 0); }
		public List<ConceptContext> concept() {
			return getRuleContexts(ConceptContext.class);
		}
		public ConceptContext concept(int i) {
			return getRuleContext(ConceptContext.class,i);
		}
		public OntologyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ontology; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).enterOntology(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).exitOntology(this);
		}
	}

	public final OntologyContext ontology() throws RecognitionException {
		OntologyContext _localctx = new OntologyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_ontology);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			((OntologyContext)_localctx).name = match(EscapedString);
			setState(9);
			match(T__0);
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(10);
				concept();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(16);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConceptContext extends ParserRuleContext {
		public Token name;
		public TerminalNode EscapedString() { return getToken(barbaraParser.EscapedString, 0); }
		public List<PropertiesContext> properties() {
			return getRuleContexts(PropertiesContext.class);
		}
		public PropertiesContext properties(int i) {
			return getRuleContext(PropertiesContext.class,i);
		}
		public ConceptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_concept; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).enterConcept(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).exitConcept(this);
		}
	}

	public final ConceptContext concept() throws RecognitionException {
		ConceptContext _localctx = new ConceptContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_concept);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(T__2);
			setState(19);
			((ConceptContext)_localctx).name = match(EscapedString);
			setState(20);
			match(T__0);
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EscapedString) {
				{
				{
				setState(21);
				properties();
				}
				}
				setState(26);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(27);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertiesContext extends ParserRuleContext {
		public TerminalNode EscapedString() { return getToken(barbaraParser.EscapedString, 0); }
		public Native_typeContext native_type() {
			return getRuleContext(Native_typeContext.class,0);
		}
		public TerminalNode LIST() { return getToken(barbaraParser.LIST, 0); }
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).enterProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).exitProperties(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_properties);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			match(EscapedString);
			setState(30);
			match(T__3);
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIST) {
				{
				setState(31);
				match(LIST);
				}
			}

			setState(34);
			native_type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Native_typeContext extends ParserRuleContext {
		public TerminalNode EscapedString() { return getToken(barbaraParser.EscapedString, 0); }
		public Native_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).enterNative_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof barbaraListener ) ((barbaraListener)listener).exitNative_type(this);
		}
	}

	public final Native_typeContext native_type() throws RecognitionException {
		Native_typeContext _localctx = new Native_typeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_native_type);
		try {
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				match(T__4);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(37);
				match(T__5);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(38);
				match(T__6);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(39);
				match(T__7);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(40);
				match(T__8);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(41);
				match(T__9);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(42);
				match(T__10);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(43);
				match(T__11);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(44);
				match(T__12);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(45);
				match(T__9);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(46);
				match(T__13);
				setState(47);
				match(EscapedString);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u00133\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0005\u0000\f\b\u0000\n\u0000\f\u0000\u000f\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"\u0017\b\u0001\n\u0001\f\u0001\u001a\t\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002!\b\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u00031\b\u0003\u0001\u0003\u0000\u0000\u0004\u0000\u0002"+
		"\u0004\u0006\u0000\u0000;\u0000\b\u0001\u0000\u0000\u0000\u0002\u0012"+
		"\u0001\u0000\u0000\u0000\u0004\u001d\u0001\u0000\u0000\u0000\u00060\u0001"+
		"\u0000\u0000\u0000\b\t\u0005\u000f\u0000\u0000\t\r\u0005\u0001\u0000\u0000"+
		"\n\f\u0003\u0002\u0001\u0000\u000b\n\u0001\u0000\u0000\u0000\f\u000f\u0001"+
		"\u0000\u0000\u0000\r\u000b\u0001\u0000\u0000\u0000\r\u000e\u0001\u0000"+
		"\u0000\u0000\u000e\u0010\u0001\u0000\u0000\u0000\u000f\r\u0001\u0000\u0000"+
		"\u0000\u0010\u0011\u0005\u0002\u0000\u0000\u0011\u0001\u0001\u0000\u0000"+
		"\u0000\u0012\u0013\u0005\u0003\u0000\u0000\u0013\u0014\u0005\u000f\u0000"+
		"\u0000\u0014\u0018\u0005\u0001\u0000\u0000\u0015\u0017\u0003\u0004\u0002"+
		"\u0000\u0016\u0015\u0001\u0000\u0000\u0000\u0017\u001a\u0001\u0000\u0000"+
		"\u0000\u0018\u0016\u0001\u0000\u0000\u0000\u0018\u0019\u0001\u0000\u0000"+
		"\u0000\u0019\u001b\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000\u0000"+
		"\u0000\u001b\u001c\u0005\u0002\u0000\u0000\u001c\u0003\u0001\u0000\u0000"+
		"\u0000\u001d\u001e\u0005\u000f\u0000\u0000\u001e \u0005\u0004\u0000\u0000"+
		"\u001f!\u0005\u0011\u0000\u0000 \u001f\u0001\u0000\u0000\u0000 !\u0001"+
		"\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\"#\u0003\u0006\u0003\u0000"+
		"#\u0005\u0001\u0000\u0000\u0000$1\u0005\u0005\u0000\u0000%1\u0005\u0006"+
		"\u0000\u0000&1\u0005\u0007\u0000\u0000\'1\u0005\b\u0000\u0000(1\u0005"+
		"\t\u0000\u0000)1\u0005\n\u0000\u0000*1\u0005\u000b\u0000\u0000+1\u0005"+
		"\f\u0000\u0000,1\u0005\r\u0000\u0000-1\u0005\n\u0000\u0000./\u0005\u000e"+
		"\u0000\u0000/1\u0005\u000f\u0000\u00000$\u0001\u0000\u0000\u00000%\u0001"+
		"\u0000\u0000\u00000&\u0001\u0000\u0000\u00000\'\u0001\u0000\u0000\u0000"+
		"0(\u0001\u0000\u0000\u00000)\u0001\u0000\u0000\u00000*\u0001\u0000\u0000"+
		"\u00000+\u0001\u0000\u0000\u00000,\u0001\u0000\u0000\u00000-\u0001\u0000"+
		"\u0000\u00000.\u0001\u0000\u0000\u00001\u0007\u0001\u0000\u0000\u0000"+
		"\u0004\r\u0018 0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}