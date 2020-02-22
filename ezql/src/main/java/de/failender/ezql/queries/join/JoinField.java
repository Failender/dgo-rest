package de.failender.ezql.queries.join;

public class JoinField{

    private final JoinCondition source;
    private final JoinCondition target;
    private final JoinType joinType;

    public JoinField(JoinCondition source, JoinCondition target, JoinType joinType) {
        this.source = source;
        this.target = target;
        this.joinType = joinType;
    }


    public JoinType getJoinType() {
        return joinType;
    }

    public JoinCondition getSource() {
        return source;
    }

    public JoinCondition getTarget() {
        return target;
    }
}
