/*
 * Copyright (c) 2008-2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.morphia.aggregation;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mongodb.morphia.aggregation.AggregationField.field;
import static org.mongodb.morphia.aggregation.Literal.literal;

/**
 * Defines an accumulator for use in an aggregation pipeline.
 */
public class Accumulator extends Operation {
    /**
     * Defines an accumulator for use in an aggregation pipeline.
     *
     * @param operation the accumulator operation
     * @param name      the field to use
     */
    public Accumulator(final String operation, final String name) {
        this(operation, singletonList(field(name)));
    }

    /**
     * Defines an accumulator for use in an aggregation pipeline.
     *
     * @param operation the accumulator operation
     * @param operands  the field to use
     */
    Accumulator(final String operation, final List<Expression> operands) {
        super(operation, operands);
    }

    /**
     * Returns a sum of numerical values. Ignores non-numeric values.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/sum $sum
     * @since 1.3
     */
    public static Accumulator sum(final Expression... expressions) {
        return new Accumulator("$sum", asList(expressions));
    }

    /**
     * Returns a sum of numerical values. Ignores non-numeric values.
     *
     * @param number the value to sum
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/sum $sum
     * @since 1.3
     */
    public static Accumulator sum(final Number number) {
        return new Accumulator("$sum", singletonList((Expression) literal(number)));
    }

    /**
     * Returns an average of numerical values. Ignores non-numeric values.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/avg $avg
     * @since 1.3
     */
    public static Accumulator avg(final Expression... expressions) {
        return new Accumulator("$avg", asList(expressions));
    }

    /**
     * Returns a value from the first document for each group. Order is only defined if the documents are in a defined order.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/first $first
     * @since 1.3
     */
    public static Accumulator first(final Expression... expressions) {
        return new Accumulator("$first", asList(expressions));
    }

    /**
     * Returns a value from the last document for each group. Order is only defined if the documents are in a defined order.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/last $last
     * @since 1.3
     */
    public static Accumulator last(final Expression... expressions) {
        return new Accumulator("$last", asList(expressions));
    }

    /**
     * Returns the highest expression value for each group.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/max $max
     * @since 1.3
     */
    public static Accumulator max(final Expression... expressions) {
        return new Accumulator("$max", asList(expressions));
    }

    /**
     * Returns the lowest expression value for each group.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/min $min
     * @since 1.3
     */
    public static Accumulator min(final Expression... expressions) {
        return new Accumulator("$min", asList(expressions));
    }

    /**
     * Returns an array of expression values for each group.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/push $push
     * @since 1.3
     */
    public static Accumulator push(final Expression... expressions) {
        return new Accumulator("$push", asList(expressions));
    }

    /**
     * Returns an array of unique expression values for each group. Order of the array elements is undefined.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/addToSet $addToSet
     * @since 1.3
     */
    public static Accumulator addToSet(final Expression... expressions) {
        return new Accumulator("$addToSet", asList(expressions));
    }

    /**
     * Returns the population standard deviation of the input values.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/stdDevPop $stdDevPop
     * @since 1.3
     */
    public static Accumulator stdDevPop(final Expression... expressions) {
        return new Accumulator("$stdDevPop", asList(expressions));
    }

    /**
     * Returns the sample standard deviation of the input values.
     *
     * @param expressions the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/stdDevSamp $stdDevSamp
     * @since 1.3
     */
    public static Accumulator stdDevSamp(final Expression... expressions) {
        return new Accumulator("$stdDevSamp", asList(expressions));
    }
}
