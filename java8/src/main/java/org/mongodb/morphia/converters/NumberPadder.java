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

package org.mongodb.morphia.converters;

class NumberPadder {
    private final long[] paddings;
    private long value;

    NumberPadder(final int... paddings) {
        this.paddings = new long[paddings.length];
        for (int i = 0; i < paddings.length; i++) {
            this.paddings[i] = padValue(paddings[i]);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    private long padValue(final int padding) {
        return (long) Math.pow(10, padding);
    }

    long pad(final long initial, final long... values) {
        value = initial;
        for (int i = 0; i < values.length; i++) {
            value *= paddings[i];
            value += values[i];
        }
        return value;
    }

    long[] extract(final long value) {
        final long[] results = new long[paddings.length + 1];
        long temp = value;
        for (int i = results.length - 1; i > 0; i--) {
            results[i] = temp % paddings[i - 1];
            temp /= paddings[i - 1];
        }
        results[0] = temp;
        return results;
    }
}
