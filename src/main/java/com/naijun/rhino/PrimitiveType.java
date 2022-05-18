/*
 * MIT License
 *
 * Copyright (c) 2022 naijun0403
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.naijun.rhino;

import java.util.Collection;
import java.util.HashMap;

public class PrimitiveType {

    private static final HashMap<Class<?>, Class<?>> PRIMITIVE_TYPES = createPrimitiveTypes();

    private static final HashMap<String, Class<?>> PRIMITIVE_TYPES_BY_NAME = createClassNameMapping(PRIMITIVE_TYPES.keySet());

    private static HashMap<Class<?>, Class<?>> createPrimitiveTypes() {
        HashMap<Class<?>, Class<?>> types = new HashMap<>();
        types.put(Void.TYPE, Void.class);
        types.put(Boolean.TYPE, Boolean.class);
        types.put(Byte.TYPE, Byte.class);
        types.put(Character.TYPE, Character.class);
        types.put(Short.TYPE, Short.class);
        types.put(Integer.TYPE, Integer.class);
        types.put(Long.TYPE, Long.class);
        types.put(Float.TYPE, Float.class);
        types.put(Double.TYPE, Double.class);
        return types;
    }

    private static HashMap<String, Class<?>> createClassNameMapping(final Collection<Class<?>> classes) {
        HashMap<String, Class<?>> map = new HashMap<>();
        for (Class<?> clazz : classes) {
            map.put(clazz.getName(), clazz);
        }
        return map;
    }

    public static Class<?> getClassByTypeName(String typeName) {
        return PRIMITIVE_TYPES_BY_NAME.get(typeName);
    }

}
