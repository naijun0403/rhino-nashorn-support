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

import org.mozilla.javascript.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * Nashorn Java Object
 * @author naijun
 */
public class NativeJava extends IdScriptableObject {

    final static String TO_STRING_TAG = "Java";

    private static Scriptable scope = null;

    public static void init(Scriptable sp, boolean sealed) {
        NativeJava obj = new NativeJava();
        scope = sp;
        obj.activatePrototypeMap(MAX_ID);
        obj.setPrototype(getObjectPrototype(scope));
        obj.setParentScope(scope);
        if (sealed) {
            obj.sealObject();
        }
        scope = sp;
        ScriptableObject.defineProperty(scope, TO_STRING_TAG, obj, ScriptableObject.DONTENUM);
    }

    public static Class<?> type(String typeName) throws ClassNotFoundException {
        if (typeName.endsWith("[]")) {
            return arrayType(typeName);
        }

        return nonArrayType(typeName);
    }

    private static Class<?> nonArrayType(String typeName) throws ClassNotFoundException {
        Class<?> primitiveClass = PrimitiveType.getClassByTypeName(typeName);
        if (primitiveClass != null) {
            return primitiveClass;
        }

        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            // If it is a subclass, the class separator is changed to "$" instead of "."
            StringBuilder sb = new StringBuilder(typeName);
            int index = sb.lastIndexOf(".");
            if (index == -1) {
                throw e;
            }
            sb.setCharAt(index, '$');
            try {
                return Class.forName(sb.toString());
            } catch (ClassNotFoundException e2) {
                throw e2;
            }
        }
    }

    private static Class<?> arrayType(String typeName) throws ClassNotFoundException {
        return Array.newInstance(type(typeName.substring(0, typeName.length() - 2)), 0).getClass();
    }

    public static boolean isJavaMethod(Object obj) {
        return obj instanceof Method;
    }

    public static boolean isJavaObject(Object obj) {
        return obj != null && (!(obj instanceof ScriptableObject));
    }

    public static boolean isScriptObject(Object obj) {
        return obj instanceof ScriptableObject;
    }

    @Override
    public String getClassName() {
        return TO_STRING_TAG;
    }

    @Override
    protected void initPrototypeId(int id) {
        if (id <= MAX_ID) {
            String name;
            int arity;
            switch (id) {
                case Id_toSource -> {
                    arity = 0;
                    name = "toSource";
                }
                case Id_extend -> {
                    arity = 2;
                    name = "extend";
                }
                case Id_from -> {
                    arity = 1;
                    name = "from";
                }
                case Id_isJavaFunction -> {
                    arity = 1;
                    name = "isJavaFunction";
                }
                case Id_isJavaMethod -> {
                    arity = 1;
                    name = "isJavaMethod";
                }
                case Id_isJavaObject -> {
                    arity = 1;
                    name = "isJavaObject";
                }
                case Id_isScriptObject -> {
                    arity = 1;
                    name = "isScriptObject";
                }
                case Id_isType -> {
                    arity = 1;
                    name = "isType";
                }
                case Id_synchronized -> {
                    arity = 2;
                    name = "synchronized";
                }
                case Id_to -> {
                    arity = 2;
                    name = "to";
                }
                case Id_type -> {
                    arity = 1;
                    name = "type";
                }
                case Id_typeName -> {
                    arity = 1;
                    name = "typeName";
                }
                case Id__super -> {
                    arity = 1;
                    name = "_super";
                }
                default -> throw new IllegalStateException(String.valueOf(id));
            }
            initPrototypeMethod(TO_STRING_TAG, id, name, arity);
        } else {
            throw new IllegalStateException(String.valueOf(id));
        }
    }

    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        if (!f.hasTag(TO_STRING_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }

        int methodId = f.methodId();

        switch (methodId) {
            case Id_toSource:
                return (Object) TO_STRING_TAG;

            case Id_isJavaMethod: {
                return isJavaMethod(args[0]);
            }

            case Id_isJavaObject: {
                return isJavaObject(args[0]);
            }

            case Id_isScriptObject: {
                return isScriptObject(args[0]);
            }

            case Id_type: {
                try {
                    WrapFactory wrapFactory = new WrapFactory();
                    String className = ScriptRuntime.toString(args, 0);
                    Class<?> type = type(className);
                    return wrapFactory.wrapJavaClass(Context.getCurrentContext(), scope, type);
                } catch (ClassNotFoundException e) {
                    return null;
                }
            }

            default:
                throw new IllegalStateException(String.valueOf(methodId));
        }
    }

    @Override
    protected int findPrototypeId(String name) {
        int id;
        switch (name) {
            case "toSource":
                id = Id_toSource;
                break;
            case "extend":
                id = Id_extend;
                break;
            case "from":
                id = Id_from;
                break;
            case "isJavaFunction":
                id = Id_isJavaFunction;
                break;
            case "isJavaMethod":
                id = Id_isJavaMethod;
                break;
            case "isJavaObject":
                id = Id_isJavaObject;
                break;
            case "isScriptObject":
                id = Id_isScriptObject;
                break;
            case "isType":
                id = Id_isType;
                break;
            case "synchronized":
                id = Id_synchronized;
                break;
            case "to":
                id = Id_to;
                break;
            case "type":
                id = Id_type;
                break;
            case "typeName":
                id = Id_typeName;
                break;
            case "_super":
                id = Id__super;
                break;
            default:
                id = 0;
                break;
        }
        return id;
    }

    private static final int Id_toSource = 1,
            Id_extend = 2,
            Id_from = 3,
            Id_isJavaFunction = 4,
            Id_isJavaMethod = 5,
            Id_isJavaObject = 6,
            Id_isScriptObject = 7,
            Id_isType = 8,
            Id_synchronized = 9,
            Id_to = 10,
            Id_type = 11,
            Id_typeName = 12,
            Id__super = 13,
            MAX_ID = 13;

}
