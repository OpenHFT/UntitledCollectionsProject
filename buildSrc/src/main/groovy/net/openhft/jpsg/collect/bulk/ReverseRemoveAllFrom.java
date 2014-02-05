/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.jpsg.collect.bulk;

import net.openhft.jpsg.PrimitiveType;


public class ReverseRemoveAllFrom extends BulkMethod {

    @Override
    public EntryType entryType() {
        return EntryType.REUSABLE;
    }

    @Override
    public boolean withInternalVersion() {
        return true;
    }

    @Override
    public String collectionArgName() {
        return "s";
    }

    @Override
    public void beginning() {
        gen.lines(
                "if (isEmpty() || s.isEmpty())",
                "    return false;",
                "boolean changed = false;"
        );
    }

    @Override
    public void loopBody() {
        String methodName = "remove";
        if (cxt.isPrimitiveView()) {
            PrimitiveType viewType = (PrimitiveType) cxt.viewOption();
            methodName += viewType.title;
        }
        gen.lines("changed |= s." + methodName + "(" + gen.viewElem() + ");");
    }

    @Override
    public void end() {
        gen.ret("changed");
    }
}
