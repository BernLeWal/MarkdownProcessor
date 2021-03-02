package markdown.interpreter;

import java.util.List;

/**
 *
 *
 * Interpreter-Pattern see https://www.baeldung.com/java-interpreter-pattern
 */
public interface Interpretable<T, C> {
    List<T> interpret(C context);
}
