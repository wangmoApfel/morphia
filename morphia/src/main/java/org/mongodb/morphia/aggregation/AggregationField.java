/*
 * Copyright (c) 2008-2016 MongoDB, Inc.
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

import java.util.ArrayList;
import java.util.List;

public class AggregationField implements Expression {
    private final String name;

    public AggregationField(final String name) {
        this.name = name.startsWith("$") ? name : "$" + name;
    }

    /**
     * Creates an expression with just a field name.
     * If the field names do not begin with '$' it will be prepended to name given
     *
     * @param name the field name
     * @return the new expression
     */
    public static Expression field(final String name) {
        return new AggregationField(name);
    }


    /**
     * Creates a List of expressions with just a field name.
     * If the field names do not begin with '$' it will be prepended to name given
     *
     * @param names the field names
     * @return the new list
     */
    public static Expression[] fields(final String... names) {
        List<Expression> expressions = new ArrayList<Expression>();
        for (String name : names) {
            expressions.add(field(name));
        }

        return expressions.toArray(new Expression[0]);
    }


    @Override
    public Object toDatabase() {
        return name;
    }
}
