/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2019 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.functions.bind;

import io.questdb.cairo.sql.Record;
import io.questdb.griffin.engine.functions.StatelessFunction;
import io.questdb.griffin.engine.functions.StrFunction;
import io.questdb.std.str.CharSink;
import io.questdb.std.str.StringSink;

class StrBindVariable extends StrFunction implements StatelessFunction {
    private final StringSink sink = new StringSink();
    private boolean isNull = false;

    public StrBindVariable(CharSequence value) {
        super(0);
        if (value == null) {
            isNull = true;
        } else {
            sink.put(value);
        }
    }

    @Override
    public CharSequence getStr(Record rec) {
        return isNull ? null : sink;
    }

    @Override
    public CharSequence getStrB(Record rec) {
        return isNull ? null : sink;
    }

    @Override
    public void getStr(Record rec, CharSink sink) {
        if (isNull) {
            sink.put((CharSequence) null);
        } else {
            sink.put(this.sink);
        }
    }

    @Override
    public int getStrLen(Record rec) {
        if (isNull) {
            return -1;
        }
        return sink.length();
    }

    public void setValue(CharSequence value) {
        sink.clear();
        if (value == null) {
            isNull = true;
        } else {
            isNull = false;
            sink.put(value);
        }
    }
}