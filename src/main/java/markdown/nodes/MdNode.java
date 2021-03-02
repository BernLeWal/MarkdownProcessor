package markdown.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MdNode implements Composable<MdNode> {
    private MdNode parent;
    private List<MdNode> children = new ArrayList<>();

    private String value;


    public MdNode() {
    }

    public MdNode(String value) {
        this.value = value;
    }


    @Override
    public MdNode getParent() {
        return parent;
    }

    @Override
    public void setParent(MdNode parent) {
        this.parent = parent;
    }


    @Override
    public List<MdNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(MdNode child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public boolean removeChild(MdNode child) {
        if (children.remove(child)) {
            child.setParent(null);
            return true;
        } else
            return false;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{}";
    }

    public String toStringRecursive(boolean showType) {
        StringBuilder sb = new StringBuilder();
        if( showType )
            sb.append( toString() );
        if( !isLeaf() ) {
            sb.append("[");
            boolean isFirst = true;
            for (MdNode child : children) {
                if (isFirst)
                    isFirst = false;
                else
                    sb.append(", ");
                sb.append(child.toStringRecursive(true));
            }
            sb.append("] ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MdNode mdNode = (MdNode) o;
        return children.equals(mdNode.children) && Objects.equals(value, mdNode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, value);
    }
}