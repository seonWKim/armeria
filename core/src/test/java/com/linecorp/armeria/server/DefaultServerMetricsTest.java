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

package com.linecorp.armeria.server;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.SessionProtocol;
import com.linecorp.armeria.testing.junit5.server.ServerExtension;

class DefaultServerMetricsTest {

    @RegisterExtension
    static ServerExtension server = new ServerExtension() {
        @Override
        protected void configure(ServerBuilder sb) throws Exception {
            sb.http(0);
            sb.service("/", (ctx, req) -> HttpResponse.of("ok"));
        }
    };

    @Test
    void http1Request() {
        SessionProtocol sessionProtocol = SessionProtocol.H1C;
        final AggregatedHttpResponse res = WebClient.builder(sessionProtocol, server.httpEndpoint())
                                                    .build().blocking().get("/");
        assertThat(res.status().code()).isEqualTo(200);


        // test
    }

    @ParameterizedTest
    @EnumSource(value = SessionProtocol.class, names = { "HTTP", "H2C" })
    void http2StreamingRequest(SessionProtocol sessionProtocol) {
        WebClient.builder(sessionProtocol, server.httpEndpoint()).build().get("/").aggregate()
                 .thenRun(() -> {
                     // test
                 })
                 .join();
    }

    @ParameterizedTest
    @EnumSource(value = SessionProtocol.class, names = { "HTTP", "H2C" })
    void http2NonStreamingRequest(SessionProtocol sessionProtocol) {
        final AggregatedHttpResponse res = WebClient.builder(sessionProtocol, server.httpEndpoint())
                                                    .build().blocking().get("/");
        assertThat(res.status().code()).isEqualTo(200);
        // test
    }

    private static class CustomServerMetrics implements ServerMetrics {

        private ServerMetrics delegate = new DefaultServerMetrics();
        public long increasePendingRequestCount;
        public long decreasePendingRequestCount;

        @Override
        public long pendingRequests() {
            return delegate.pendingRequests();
        }

        @Override
        public void increasePendingRequests() {
            increasePendingRequestCount++;
            delegate.increasePendingRequests();
        }

        @Override
        public void decreasePendingRequests() {
            decreasePendingRequestCount++;
            delegate.decreasePendingRequests();
            assertThat(delegate.pendingRequests()).isNotNegative();
        }

        void reset() {
            this.delegate = new DefaultServerMetrics();
            this.increasePendingRequestCount = 0;
            this.decreasePendingRequestCount = 0;
        }
    }
}
