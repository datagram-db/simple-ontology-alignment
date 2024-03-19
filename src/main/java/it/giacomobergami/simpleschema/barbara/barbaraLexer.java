package it.giacomobergami.simpleschema.barbara;// Generated from barbara.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class barbaraLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, EscapedString=15, SPACE=16, 
		LIST=17, COMMENT=18, LINE_COMMENT=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "EscapedString", "SPACE", 
			"LIST", "COMMENT", "LINE_COMMENT"
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


	public barbaraLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "barbara.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0013\u00a4\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0005\u000ey\b\u000e\n\u000e\f\u000e|\t\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0004\u000f\u0081\b\u000f\u000b\u000f\f"+
		"\u000f\u0082\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0005\u0011\u0090\b\u0011\n\u0011\f\u0011\u0093\t\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0005\u0012\u009e\b\u0012\n\u0012\f\u0012\u00a1\t\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0091\u0000\u0013\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012%\u0013\u0001\u0000\u0003\u0002\u0000\"\"\\\\\u0003\u0000\t\n\r"+
		"\r  \u0002\u0000\n\n\r\r\u00a8\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001"+
		"\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001"+
		"\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001"+
		"\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000"+
		"\u0000\u0000%\u0001\u0000\u0000\u0000\u0001\'\u0001\u0000\u0000\u0000"+
		"\u0003)\u0001\u0000\u0000\u0000\u0005+\u0001\u0000\u0000\u0000\u00073"+
		"\u0001\u0000\u0000\u0000\t5\u0001\u0000\u0000\u0000\u000b<\u0001\u0000"+
		"\u0000\u0000\rC\u0001\u0000\u0000\u0000\u000fK\u0001\u0000\u0000\u0000"+
		"\u0011S\u0001\u0000\u0000\u0000\u0013X\u0001\u0000\u0000\u0000\u0015]"+
		"\u0001\u0000\u0000\u0000\u0017c\u0001\u0000\u0000\u0000\u0019g\u0001\u0000"+
		"\u0000\u0000\u001bl\u0001\u0000\u0000\u0000\u001dt\u0001\u0000\u0000\u0000"+
		"\u001f\u0080\u0001\u0000\u0000\u0000!\u0086\u0001\u0000\u0000\u0000#\u008b"+
		"\u0001\u0000\u0000\u0000%\u0099\u0001\u0000\u0000\u0000\'(\u0005{\u0000"+
		"\u0000(\u0002\u0001\u0000\u0000\u0000)*\u0005}\u0000\u0000*\u0004\u0001"+
		"\u0000\u0000\u0000+,\u0005c\u0000\u0000,-\u0005o\u0000\u0000-.\u0005n"+
		"\u0000\u0000./\u0005c\u0000\u0000/0\u0005e\u0000\u000001\u0005p\u0000"+
		"\u000012\u0005t\u0000\u00002\u0006\u0001\u0000\u0000\u000034\u0005:\u0000"+
		"\u00004\b\u0001\u0000\u0000\u000056\u0005S\u0000\u000067\u0005T\u0000"+
		"\u000078\u0005R\u0000\u000089\u0005I\u0000\u00009:\u0005N\u0000\u0000"+
		":;\u0005G\u0000\u0000;\n\u0001\u0000\u0000\u0000<=\u0005D\u0000\u0000"+
		"=>\u0005O\u0000\u0000>?\u0005U\u0000\u0000?@\u0005B\u0000\u0000@A\u0005"+
		"L\u0000\u0000AB\u0005E\u0000\u0000B\f\u0001\u0000\u0000\u0000CD\u0005"+
		"I\u0000\u0000DE\u0005N\u0000\u0000EF\u0005T\u0000\u0000FG\u0005E\u0000"+
		"\u0000GH\u0005G\u0000\u0000HI\u0005E\u0000\u0000IJ\u0005R\u0000\u0000"+
		"J\u000e\u0001\u0000\u0000\u0000KL\u0005B\u0000\u0000LM\u0005O\u0000\u0000"+
		"MN\u0005O\u0000\u0000NO\u0005L\u0000\u0000OP\u0005E\u0000\u0000PQ\u0005"+
		"A\u0000\u0000QR\u0005N\u0000\u0000R\u0010\u0001\u0000\u0000\u0000ST\u0005"+
		"D\u0000\u0000TU\u0005A\u0000\u0000UV\u0005T\u0000\u0000VW\u0005E\u0000"+
		"\u0000W\u0012\u0001\u0000\u0000\u0000XY\u0005Y\u0000\u0000YZ\u0005E\u0000"+
		"\u0000Z[\u0005A\u0000\u0000[\\\u0005R\u0000\u0000\\\u0014\u0001\u0000"+
		"\u0000\u0000]^\u0005M\u0000\u0000^_\u0005O\u0000\u0000_`\u0005N\u0000"+
		"\u0000`a\u0005T\u0000\u0000ab\u0005H\u0000\u0000b\u0016\u0001\u0000\u0000"+
		"\u0000cd\u0005D\u0000\u0000de\u0005A\u0000\u0000ef\u0005Y\u0000\u0000"+
		"f\u0018\u0001\u0000\u0000\u0000gh\u0005W\u0000\u0000hi\u0005E\u0000\u0000"+
		"ij\u0005E\u0000\u0000jk\u0005K\u0000\u0000k\u001a\u0001\u0000\u0000\u0000"+
		"lm\u0005C\u0000\u0000mn\u0005O\u0000\u0000no\u0005N\u0000\u0000op\u0005"+
		"C\u0000\u0000pq\u0005E\u0000\u0000qr\u0005P\u0000\u0000rs\u0005T\u0000"+
		"\u0000s\u001c\u0001\u0000\u0000\u0000tz\u0005\"\u0000\u0000uy\b\u0000"+
		"\u0000\u0000vw\u0005\\\u0000\u0000wy\u0007\u0000\u0000\u0000xu\u0001\u0000"+
		"\u0000\u0000xv\u0001\u0000\u0000\u0000y|\u0001\u0000\u0000\u0000zx\u0001"+
		"\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{}\u0001\u0000\u0000\u0000"+
		"|z\u0001\u0000\u0000\u0000}~\u0005\"\u0000\u0000~\u001e\u0001\u0000\u0000"+
		"\u0000\u007f\u0081\u0007\u0001\u0000\u0000\u0080\u007f\u0001\u0000\u0000"+
		"\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000"+
		"\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0084\u0001\u0000\u0000"+
		"\u0000\u0084\u0085\u0006\u000f\u0000\u0000\u0085 \u0001\u0000\u0000\u0000"+
		"\u0086\u0087\u0005L\u0000\u0000\u0087\u0088\u0005I\u0000\u0000\u0088\u0089"+
		"\u0005S\u0000\u0000\u0089\u008a\u0005T\u0000\u0000\u008a\"\u0001\u0000"+
		"\u0000\u0000\u008b\u008c\u0005/\u0000\u0000\u008c\u008d\u0005*\u0000\u0000"+
		"\u008d\u0091\u0001\u0000\u0000\u0000\u008e\u0090\t\u0000\u0000\u0000\u008f"+
		"\u008e\u0001\u0000\u0000\u0000\u0090\u0093\u0001\u0000\u0000\u0000\u0091"+
		"\u0092\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0092"+
		"\u0094\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0094"+
		"\u0095\u0005*\u0000\u0000\u0095\u0096\u0005/\u0000\u0000\u0096\u0097\u0001"+
		"\u0000\u0000\u0000\u0097\u0098\u0006\u0011\u0000\u0000\u0098$\u0001\u0000"+
		"\u0000\u0000\u0099\u009a\u0005/\u0000\u0000\u009a\u009b\u0005/\u0000\u0000"+
		"\u009b\u009f\u0001\u0000\u0000\u0000\u009c\u009e\b\u0002\u0000\u0000\u009d"+
		"\u009c\u0001\u0000\u0000\u0000\u009e\u00a1\u0001\u0000\u0000\u0000\u009f"+
		"\u009d\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0"+
		"\u00a2\u0001\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a2"+
		"\u00a3\u0006\u0012\u0000\u0000\u00a3&\u0001\u0000\u0000\u0000\u0006\u0000"+
		"xz\u0082\u0091\u009f\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}