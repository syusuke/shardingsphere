/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.governance.core.state;

import com.google.common.eventbus.Subscribe;
import org.apache.shardingsphere.governance.core.event.model.lock.GlobalLockAddedEvent;
import org.apache.shardingsphere.infra.eventbus.ShardingSphereEventBus;
import org.apache.shardingsphere.infra.state.StateContext;
import org.apache.shardingsphere.infra.state.StateEvent;
import org.apache.shardingsphere.infra.state.StateType;

import java.util.Optional;

/**
 * Governed state machine.
 */
public final class GovernedStateContext {
    
    /**
     * Start up governed state machine.
     */
    public static void startUp() {
        ShardingSphereEventBus.getInstance().register(new GovernedStateContext());
    }
    
    /**
     * Switch state.
     *
     * @param event state event
     */
    @Subscribe
    public void switchState(final StateEvent event) {
        StateContext.switchState(event);
    }
    
    /**
     * Lock instance after global lock added.
     *
     * @param event global lock added event
     */
    @Subscribe
    public void lock(final GlobalLockAddedEvent event) {
        if (Optional.of(event).isPresent()) {
            StateContext.switchState(new StateEvent(StateType.LOCK, true));
        }
    }
    
    /**
     * Unlock instance.
     */
    public static void unlock() {
        StateContext.switchState(new StateEvent(StateType.LOCK, false));
    }
}
