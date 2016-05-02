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

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.TestBase;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.query.MorphiaIterator;
import org.slf4j.Logger;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

public class EntityCacheTest extends TestBase {
    private static final Logger LOG = getLogger(EntityCacheTest.class);

    public static void main(final String[] args) throws InterruptedException {
        final EntityCacheTest test = new EntityCacheTest();
        test.setUp();
        test.caching();
    }

    public void caching() throws InterruptedException {
        LOG.info(new Date().toString());
        int count = 1200000;
        getMorphia().map(Cached.class);
        final Datastore ds = getDs();
        Cached last = null;
        for (int i = 0; i < count; i++) {
            final long millis = System.currentTimeMillis();
            Cached entity = new Cached(millis + "", millis, last);
            ds.save(entity);
            last = entity;
        }

        LOG.info("Done writing.  Querying now...");
        Thread.sleep(10000);
        final MorphiaIterator<Cached, Cached> cacheds = ds.find(Cached.class).fetch();
        final EntityCache cache = cacheds.getCache();
        for (int i = 0; i < count; i++) {
            if (i % 10000 == 0) {
                LOG.info("************ cache.stats() = " + cache.stats());
                LOG.info("************ cached[%s] = %s\n", i, cacheds.next());
            }
        }
        //        query.asList();
        LOG.info(new Date().toString());
    }

    private static class Cached {
        @Id
        private ObjectId id;
        private String name;
        private Long number;
        @Reference(lazy = true, idOnly = true)
        private Cached previous;

        public Cached() {
        }

        public Cached(final String name, final Long number, final Cached previous) {
            this.name = name;
            this.number = number;
            //            this.previous = previous;
        }

        @Override
        public String toString() {
            return String.format("Cached{id=%s, name='%s', number=%d, previous=%s}", id, name, number, previous);
        }
    }
}
