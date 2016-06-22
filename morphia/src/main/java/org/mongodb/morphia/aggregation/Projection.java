package org.mongodb.morphia.aggregation;


import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Defines a projection for use in aggregation
 *
 * @mongodb.driver.manual reference/operator/aggregation/project/ $project
 */
public class Projection {

    private String target;
    private String source;
    private List<Projection> projections;
    private List<Object> arguments;
    private boolean suppressed = false;

    private Projection() {
    }

    private Projection(final String field, final String source) {
        this.target = field;
        this.source = source.startsWith("$") ? source : "$" + source;
    }

    private Projection(final String field, final Projection projection, final Projection... subsequent) {
        this(field);
        this.projections = new ArrayList<Projection>();
        projections.add(projection);
        projections.addAll(Arrays.asList(subsequent));
    }

    private Projection(final String field) {
        this.target = field;
        source = null;
    }

    private Projection(final String expression, final Object... args) {
        this(expression);
        this.arguments = Arrays.asList(args);
    }

    /**
     * Define fields for inclusion in a $project
     *
     * @param field  the field to include
     * @param fields optional additional fields to include
     * @return the new Projection
     */
    public static Projection include(final String field, final String... fields) {
        return new Includes(field, fields);
    }

    /**
     * Define fields for exclusion in a $project
     *
     * @param field  the field to exclude
     * @param fields optional additional fields to exclude
     * @return the new Projection
     */
    public static Projection exclude(final String field, final String... fields) {
        return new Excludes(field, fields);
    }

    /**
     * Defines a computed field
     *
     * @param field      the field to exclude
     * @param expression the expression to compute the value of the new field
     * @return the new Projection
     */
    public static Projection compute(final String field, final Expression expression) {
        return new Computed(field, expression);
    }

    /**
     * Renames a field
     *
     * @param field       the new field name
     * @param sourceField the source field to rename.  If the field name does not start with "$" it will be prepended
     * @return the new Projection
     */
    public static Projection rename(final String field, final String sourceField) {
        return new Projection(field, sourceField);
    }

    /**
     * Creates a projection on a field
     *
     * @param field the field
     * @return the projection
     * @deprecated use {@link #include(String, String...)}
     */
    @Deprecated
    public static Projection projection(final String field) {
        return new Projection(field);
    }

    /**
     * Creates a projection on a field and renames it
     *
     * @param field          the field
     * @param projectedField the new field name
     * @return the projection
     * @deprecated use {@link #rename(String, String)}
     */
    @Deprecated
    public static Projection projection(final String field, final String projectedField) {
        return new Projection(field, projectedField);
    }

    /**
     * Creates a projection on a field with subsequent projects applied.
     *
     * @param field      the field
     * @param projection the project to apply
     * @param subsequent the other projections to apply
     * @return the projection
     */
    public static Projection projection(final String field, final Projection projection, final Projection... subsequent) {
        return new Projection(field, projection, subsequent);
    }

    /**
     * Provides access to arbitrary expressions taking an array of arguments, such as $concat
     *
     * @param operator the operator for the projection
     * @param args     the projection arguments
     * @return the projection
     * @deprecated use {@link Expression} instead
     */
    @Deprecated
    public static Projection expression(final String operator, final Object... args) {
        return new Projection(operator, args);
    }

    /**
     * Creates a list projection
     *
     * @param args the projection arguments
     * @return the projection
     */
    public static Projection list(final Object... args) {
        return new Projection(null, args);
    }

    /**
     * Creates an addition projection
     *
     * @param args the projection arguments
     * @return the projection
     * @mongodb.driver.manual reference/operator/aggregation/add $add
     * @deprecated use {@link Expression#add(Expression, Expression...)} instead
     */
    public static Projection add(final Object... args) {
        return expression("$add", args);
    }

    /**
     * Creates a subtraction projection
     *
     * @param arg1 subtraction argument
     * @param arg2 subtraction argument
     * @return the projection
     * @mongodb.driver.manual reference/operator/aggregation/subtract $subtract
     * @deprecated use {@link Expression#substract(Expression, Expression...)} instead
     */
    public static Projection subtract(final Object arg1, final Object arg2) {
        return expression("$subtract", arg1, arg2);
    }

    /**
     * Creates a multiplication projection
     *
     * @param args the projection arguments
     * @return the projection
     * @mongodb.driver.manual reference/operator/aggregation/multiply $multiply
     * @deprecated use {@link Expression#multiply(Expression, Expression...)} instead
     */
    public static Projection multiply(final Object... args) {
        return expression("$multiply", args);
    }

    /**
     * Creates a division projection
     *
     * @param arg1 subtraction argument
     * @param arg2 subtraction argument
     * @return the projection
     * @mongodb.driver.manual reference/operator/aggregation/divide $divide
     * @deprecated use {@link Expression#divide(Expression, Expression...)} instead
     */
    public static Projection divide(final Object arg1, final Object arg2) {
        return expression("$divide", arg1, arg2);
    }

    /**
     * Creates a modulo projection
     *
     * @param arg1 subtraction argument
     * @param arg2 subtraction argument
     * @return the projection
     * @mongodb.driver.manual reference/operator/aggregation/mod $mod
     * @deprecated use {@link Expression#mod(Expression, Expression...)} instead
     */
    public static Projection mod(final Object arg1, final Object arg2) {
        return expression("$mod", arg1, arg2);
    }

    /**
     * @return the arguments for the projection
     */
    public List<Object> getArguments() {
        return arguments;
    }

    /**
     * @return any projections applied to this field
     */
    public List<Projection> getProjections() {
        return projections;
    }

    /**
     * @return the projected field name
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the source field of the projection
     */
    public String getTarget() {
        return target;
    }

    /**
     * @return true if this field is suppressed from the output
     * @deprecated use {@link #include(String, String...)} or {@link #exclude(String, String...)} instead
     */
    @Deprecated
    public boolean isSuppressed() {
        return suppressed;
    }

    /**
     * Marks this field to be suppressed from the output of this stage
     *
     * @return this
     * @deprecated use {@link #include(String, String...)} or {@link #exclude(String, String...)} instead
     */
    @Deprecated
    public Projection suppress() {
        suppressed = true;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Projection{projectedField='%s', sourceField='%s', projections=%s, suppressed=%s}",
                             source, target, projections, suppressed);
    }

    private DBObject toExpressionArgs(final List<Object> args) {
        BasicDBList result = new BasicDBList();
        for (Object arg : args) {
            if (arg instanceof Projection) {
                Projection projection = (Projection) arg;
                if (projection.getArguments() != null || projection.getProjections() != null || projection.getSource() != null) {
                    result.add(projection.toDatabase());
                } else {
                    String target = projection.getTarget();
                    result.add(target != null ? "$" + target : null);
                }
            } else {
                result.add(arg);
            }
        }
        return result.size() == 1 ? (DBObject) result.get(0) : result;
    }

    static class Includes extends Projection {

        private List<String> fields = new ArrayList<String>();

        Includes(final String field, final String[] fields) {
            this.fields.add(field);
            Collections.addAll(this.fields, fields);
        }

        @Override
        public DBObject toDatabase() {
            BasicDBObject dbObject = new BasicDBObject();
            for (String field : fields) {
                dbObject.put(field, 1);
            }
            return dbObject;
        }

    }

    static class Excludes extends Projection {
        private List<String> fields = new ArrayList<String>();

        Excludes(final String field, final String[] fields) {
            this.fields.add(field);
            Collections.addAll(this.fields, fields);
        }

        @Override
        public DBObject toDatabase() {
            BasicDBObject dbObject = new BasicDBObject();
            for (String field : fields) {
                dbObject.put(field, 0);
            }
            return dbObject;
        }
    }

    private static class Computed extends Projection {
        private final String name;
        private final Expression expression;

        Computed(final String name, final Expression expression) {
            this.name = name;
            this.expression = expression;
        }

        @Override
        public DBObject toDatabase() {
            return new BasicDBObject(name, expression.toDatabase());
        }

    }

    @SuppressWarnings("unchecked")
    DBObject toDatabase() {
        String target = getTarget();

        if (getProjections() != null) {
            List<Projection> list = getProjections();
            DBObject projections = new BasicDBObject();
            for (Projection subProjection : list) {
                projections.putAll(subProjection.toDatabase());
            }
            return new BasicDBObject(target, projections);
        } else if (getSource() != null) {
            return new BasicDBObject(target, getSource());
        } else if (getArguments() != null) {
            if (target == null) {
                return toExpressionArgs(getArguments());
            } else {
                return new BasicDBObject(target, toExpressionArgs(getArguments()));
            }
        } else {
            return new BasicDBObject(target, isSuppressed() ? 0 : 1);
        }
    }
}
