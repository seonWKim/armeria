/*
 * Copyright 2023 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.armeria.common;

import java.util.function.Function;

/**
 * A builder implementation for Text {@link HeadersSanitizer}.
 */
public final class TextHeadersSanitizerBuilder extends AbstractHeadersSanitizerBuilder<String> {

    @Override
    public TextHeadersSanitizerBuilder maskingHeaders(CharSequence... headers) {
        return (TextHeadersSanitizerBuilder) super.maskingHeaders(headers);
    }

    @Override
    public TextHeadersSanitizerBuilder maskingHeaders(Iterable<? extends CharSequence> headers) {
        return (TextHeadersSanitizerBuilder) super.maskingHeaders(headers);
    }

    @Override
    public TextHeadersSanitizerBuilder maskingFunction(Function<String, String> maskingFunction) {
        return (TextHeadersSanitizerBuilder) super.maskingFunction(maskingFunction);
    }

    /**
     * Returns a newly created text {@link HeadersSanitizer} based on the properties of this builder.
     */
    public HeadersSanitizer<String> build() {
        return new TextHeadersSanitizer(maskingHeaders(), maskingFunction());
    }
}
