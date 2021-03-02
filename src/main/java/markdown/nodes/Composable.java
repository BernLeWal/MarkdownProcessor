package markdown.nodes;

import java.util.List;

public interface Composable<T> {
    T getParent();
    void setParent(T parent);

    List<T> getChildren();
    void addChild(T child);
    boolean removeChild(T child);

    default boolean isRoot() { return getParent()==null; }
    default boolean isLeaf() { return getChildren().size()==0; }
}
