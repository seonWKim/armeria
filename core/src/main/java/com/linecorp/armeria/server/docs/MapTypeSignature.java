/*
 * Copyright 2022 LINE Corporation
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

package com.linecorp.armeria.server.docs;

import com.google.common.collect.ImmutableList;

import com.linecorp.armeria.common.annotation.UnstableApi;

/**
 * A map {@link TypeSignature}.
 */
@UnstableApi
public final class MapTypeSignature extends ContainerTypeSignature {

    private final TypeSignature keyTypeSignature;
    private final TypeSignature valueTypeSignature;

    MapTypeSignature(TypeSignature keyTypeSignature, TypeSignature valueTypeSignature) {
        super(TypeSignatureType.MAP, "map", ImmutableList.of(keyTypeSignature, valueTypeSignature));
        this.keyTypeSignature = keyTypeSignature;
        this.valueTypeSignature = valueTypeSignature;
    }

    /**
     * Return the {@link TypeSignature} of the key.
     */
    public TypeSignature keyTypeSignature() {
        return keyTypeSignature;
    }

    /**
     * Return the {@link TypeSignature} of the value.
     */
    public TypeSignature valueTypeSignature() {
        return valueTypeSignature;
    }

    // Use equals() hashCode() in ContainerTypeSignature.
}
