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

import static java.util.Collections.singletonList;
import static org.mongodb.morphia.aggregation.AggregationField.field;

public class Dates {
    public static Expression month(final String field) {
        return new Operation("$month", singletonList(field(field)));
    }

    public static Expression dayOfYear(final String field) {
        return new Operation("$dayOfYear", singletonList(field(field)));
    }

    public static Expression year(final String field) {
        return new Operation("$year", singletonList(field(field)));
    }

}
