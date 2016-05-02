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

package org.mongodb.morphia.mapping.cache;


import org.mongodb.morphia.Key;


/**
 * This is the default EntityCache for Morphia
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class NoOpCache implements EntityCache {

    @SuppressWarnings({"rawtypes", "unchecked"})
    private final EntityCacheStatistics stats = new EntityCacheStatistics();

    @Override
    public Boolean exists(final Key<?> k) {
        return false;
    }

    @Override
    public void flush() {
        stats.reset();
    }

    @Override
    public <T> T getEntity(final Key<T> k) {
        return null;
    }

    @Override
    public <T> T getProxy(final Key<T> k) {
        return null;
    }

    @Override
    public void notifyExists(final Key<?> k, final boolean exists) {
    }

    @Override
    public <T> void putEntity(final Key<T> k, final T t) {
    }

    @Override
    public <T> void putProxy(final Key<T> k, final T t) {
    }

    @Override
    public EntityCacheStatistics stats() {
        return stats.copy();
    }

}
