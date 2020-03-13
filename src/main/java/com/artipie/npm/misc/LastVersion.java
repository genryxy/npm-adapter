/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.artipie.npm.misc;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.json.JsonObject;

/**
 * LastVersion.
 *
 * @since 0.3
 * @checkstyle IllegalTokenCheck (500 lines)
 * @checkstyle ParameterNameCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (500 lines)
 * @checkstyle FinalLocalVariableCheck (500 lines)
 * @checkstyle AvoidDuplicateLiterals (500 lines)
 */
public final class LastVersion {

    /**
     * Versions.
     */
    private final JsonObject versions;

    /**
     * Ctor.
     *
     * @param versions Versions.
     */
    public LastVersion(final JsonObject versions) {
        this.versions = versions;
    }

    /**
     * Gets that last version.
     *
     * @return Last version
     */
    public String value() {
        return new ArrayList<>(
            this.versions.keySet()
        ).stream()
            .sorted((v1, v2) -> -1 * compareVersions(v1, v2))
            .collect(Collectors.toList()).get(0);
    }

    /**
     * Compares two versions.
     *
     * @param v1 Version 1
     * @param v2 Version 2
     * @return Value {@code 0} if {@code v1 == v2};
     *  a value less than {@code 0} if {@code v1 < v2}; and
     *  a value greater than {@code 0} if {@code v1 > v2}
     */
    private static int compareVersions(final String v1, final String v2) {
        final String delimiter = "\\.";
        final String[] component1 = v1.split(delimiter);
        final String[] component2 = v2.split(delimiter);
        final int length = Math.min(component1.length, component2.length);
        int result;
        for (int index = 0; index < length; index++) {
            result = Integer.valueOf(component1[index])
                .compareTo(Integer.parseInt(component2[index]));
            if (result != 0) {
                break;
            }
        }
        result = Integer.compare(component1.length, component2.length);
        return result;
    }
}
