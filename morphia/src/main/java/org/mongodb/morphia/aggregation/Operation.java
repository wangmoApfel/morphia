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

import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Operation implements Expression {
    private String operator;
    private List<Expression> operands;

    Operation(final String value, final List<Expression> operands) {
        this.operator = value;
        this.operands = operands;
    }

    /**
     * Creates a $add expression with the given operands
     *
     * @param expressions  the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/add $add
     */
    public static Expression add(final Expression... expressions) {
        return new Operation("$add", asList(expressions));
    }

    /**
     * Creates a $substract expression with the given operands
     *
     * @param expressions  the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/substract $substract
     */
    public static Expression substract(final Expression... expressions) {
        return new Operation("$substract", asList(expressions));
    }

    /**
     * Creates a $multiply expression with the given operands
     *
     * @param expressions  the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/multiply $multiply
     */
    public static Expression multiply(final Expression... expressions) {
        return new Operation("$multiply", asList(expressions));
    }

    /**
     * Creates a $divide expression with the given operands
     *
     * @param expressions  the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/divide $divide
     */
    public static Expression divide(final Expression... expressions) {
        return new Operation("$divide", asList(expressions));
    }

    /**
     * Creates a $mod expression with the given operands
     *
     * @param expressions  the expressions to include
     * @return the new expression
     * @mongodb.driver.manual reference/operator/aggregation/mod $mod
     */
    public static Expression mod(final Expression... expressions) {
        return new Operation("$mod", asList(expressions));
    }

    @Override
    public Object toDatabase() {
        if (operands.size() == 1) {
            return new BasicDBObject(operator, operands.get(0).toDatabase());
        } else {
            List<Object> list = new ArrayList<Object>();
            for (Expression operand : operands) {
                list.add(operand.toDatabase());
            }
            return new BasicDBObject(operator, list);
        }
    }
}
