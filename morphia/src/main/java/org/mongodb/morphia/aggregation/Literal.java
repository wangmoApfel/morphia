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

/**
 * Defines a literal expression in an aggregation pipeline
 */
public final class Literal implements Expression {
    private final Object value;

    private Literal(final Object value) {
        this.value = value;
    }

    /**
     * Creates an literal expression with a value.
     *
     * @param value the literal value
     * @return the new expression
     */
    public static Literal literal(final Object value) {
        return new Literal(value);
    }

    @Override
    public Object toDatabase() {
        return value;
    }
}
