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

package com.naijun.rhino.test;

import com.naijun.rhino.NativeJava;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NativeJavaTest {

    private Context cx;
    private ScriptableObject scope;

    @BeforeAll
    public void setUp() {
        cx = Context.enter();
        cx.setLanguageVersion(Context.VERSION_ES6);
        scope = cx.initStandardObjects();
        NativeJava.init(scope, false);
    }

    @AfterAll
    public void tearDown() {
        Context.exit();
    }

    @Test
    public void testJavaToString() {
        Object result =
                cx.evaluateString(
                        scope,
                        "Object.prototype.toString.call(Java)",
                        "test",
                        1,
                        null);

        assertEquals("[object Java]", result);
    }

    @Test
    public void testJavaType() {
        Object result =
                cx.evaluateString(
                        scope,
                        "Java.type('int[]')",
                        "test",
                        1,
                        null);

        assertEquals("[object Java]", result);
    }

    @Test
    public void testJavaIsScriptObject() {
        Object result =
                cx.evaluateString(
                        scope,
                        "Java.isScriptObject({})",
                        "test",
                        1,
                        null);

        assertEquals(true, result);
    }

}
