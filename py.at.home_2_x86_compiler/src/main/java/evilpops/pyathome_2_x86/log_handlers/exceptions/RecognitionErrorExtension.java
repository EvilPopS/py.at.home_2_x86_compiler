package main.java.evilpops.pyathome_2_x86.log_handlers.exceptions;

import org.antlr.v4.runtime.*;

public class RecognitionErrorExtension extends RecognitionException {

    public RecognitionErrorExtension(String message, Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx, Token offendingTk) {
        super(message, recognizer, input, ctx);
        this.setOffendingToken(offendingTk);
    }
}
