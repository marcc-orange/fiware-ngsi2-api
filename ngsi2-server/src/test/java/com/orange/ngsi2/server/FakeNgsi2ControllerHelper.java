/*
 * Copyright (C) 2016 Orange
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.orange.ngsi2.server;

import com.orange.ngsi2.exception.ConflictingEntitiesException;
import com.orange.ngsi2.model.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.*;

import static com.orange.ngsi2.utility.Utils.*;
import static com.orange.ngsi2.utility.Utils.createListSubscriptionsReference;
import static com.orange.ngsi2.utility.Utils.retrieveRegistrationReference;
import static com.orange.ngsi2.utility.Utils.retrieveSubscriptionReference;

@RestController
@RequestMapping("/v2/i")
public class FakeNgsi2ControllerHelper extends Ngsi2BaseController {

    @Override
    protected Map<String, String> listResources() throws Exception {
        return createListResourcesReference();
    }

    @Override
    protected Paginated<Entity> listEntities(Optional<String> id, Optional<String> type, Optional<String> idPattern, Optional<Integer> limit,
            Optional<Integer> offset, Optional<String> attrs, Optional<String> query, Optional<GeoQuery> geoquery, Optional<Collection<String>> orderBy) throws Exception {
        if (id.isPresent() && id.get().equals("Bcn-Welt")) {
            return new Paginated<>(Collections.singletonList(createEntityBcnWelt()),1, 1, 1);
        }
        return new Paginated<>(createListEntitiesConflictingReference(), 2, 2, 2);
    }

    @Override
    protected void createEntity(Entity entity){ }

    @Override
    protected Entity retrieveEntity(String entityId, Optional<String> attrs) throws ConflictingEntitiesException {
        if (entityId.equals("Bcn-Welt")) {
            return createEntityBcnWelt();
        }
        throw new ConflictingEntitiesException("Boe-Idearium", "GET /v2/entities?id=Boe-Idearium&attrs=temperature");
    }

    @Override
    protected void updateOrAppendEntity(String entityId, Map<String, Attribute> attributes){ }

    @Override
    protected void updateExistingEntityAttributes(String entityId, Map<String, Attribute> attributes){ }

    @Override
    protected void replaceAllEntityAttributes(String entityId, Map<String, Attribute> attributes){ }

    @Override
    protected void removeEntity(String entityId){ }

    @Override
    protected Paginated<EntityType> retrieveEntityTypes(Optional<Integer> offset, Optional<Integer> limit, boolean count) {
        return createEntityTypesRoom();
    }

    @Override
    protected EntityType retrieveEntityType(String entityType) {
        return createEntityTypeRoom();
    }

    @Override
    protected Attribute retrieveAttributeByEntityId(String entityId, String attrName, Optional<String> type) throws ConflictingEntitiesException {
        if (entityId.equals("Bcn-Welt")) {
            return createTemperatureEntityBcnWelt();
        }
        throw new ConflictingEntitiesException("Boe-Idearium", "GET /v2/entities/Boe-Idearium/attrs/temperature?type=");
    }

    @Override
    protected void updateAttributeByEntityId(String entityId, String attrName, Optional<String> type, Attribute attribute) throws ConflictingEntitiesException {
        if (!entityId.equals("Bcn-Welt")) {
            throw new ConflictingEntitiesException("Boe-Idearium", "PUT /v2/entities/Boe-Idearium/attrs/temperature?type=");
        }
    }

    @Override
    protected void removeAttributeByEntityId(String entityId, String attrName, Optional<String> type) throws ConflictingEntitiesException {
        if (!entityId.equals("Bcn-Welt")) {
            throw new ConflictingEntitiesException("Boe-Idearium", "DELETE /v2/entities/Boe-Idearium/attrs/temperature?type=");
        }
    }

    @Override
    protected Object retrieveAttributeValue(String entityId, String attrName, Optional<String> type) {
        if (attrName.equals("temperature")) {
            return new Float(25.0);
        } else if (attrName.equals("on")) {
            return true;
        } else if (attrName.equals("color")) {
            return null;
        } else if (attrName.equals("hello")) {
            return "hello, world";
        } else {
            return createValueReference();
        }
    }

    @Override
    protected void updateAttributeValue(String entityId, String attrName, Optional<String> type, Object value) throws ConflictingEntitiesException {
        if (!entityId.equals("Bcn-Welt")) {
            throw new ConflictingEntitiesException("Boe-Idearium", "PUT /v2/entities/Boe-Idearium/attrs/temperature/value?type=");
        }
    }

    @Override
    protected List<Registration> listRegistrations() {
        try {
            return createListRegistrationsReference();
        } catch (MalformedURLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    protected void createRegistration(Registration registration){ }

    @Override
    protected Registration retrieveRegistration(String registrationId) {
        try {
            return retrieveRegistrationReference();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    protected void updateRegistration(String registrationId, Registration registration){
    }

    @Override
    protected void removeRegistration(String registrationId){
    }

    @Override
    protected Paginated<Subscription> listSubscriptions( Optional<Integer> limit, Optional<Integer> offset) {
        try {
            return new Paginated<>(createListSubscriptionsReference(), 2, 2, 1);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    protected void createSubscription(Subscription subscription){ }

    @Override
    protected Subscription retrieveSubscription(String subscriptionId) {
        try {
            return retrieveSubscriptionReference();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    protected void updateSubscription(String subscriptionId, Subscription subscription){
    }

    @Override
    protected void removeSubscription(String subscriptionId){
    }
}
