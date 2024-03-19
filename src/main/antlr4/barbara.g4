grammar barbara;

ontology : name=EscapedString '{' concept* '}';

concept : 'concept' name=EscapedString '{' properties* '}';

properties : EscapedString ':' LIST? native_type;

native_type : 'STRING'
            | 'DOUBLE'
            | 'INTEGER'
            | 'BOOLEAN'
            | 'DATE'
            | 'YEAR'
            | 'MONTH'
            | 'DAY'
            | 'WEEK'
            | 'YEAR'
            | 'CONCEPT' EscapedString
            ;

EscapedString : '"' (~[\\"] | '\\' [\\"])* '"';
SPACE : [ \t\r\n]+ -> skip;
LIST : 'LIST';
COMMENT
    : '/*' .*? '*/' -> skip
;

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
;