/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.github.starmoon1617.starmie.demo.web.vo.PresetConditionsVo;

/**
 * 
 * 
 * @date 2023-12-29
 * @author Nathan Liao
 */
public class PresetConditionsDeserializer extends StdDeserializer<PresetConditionsVo> {

    private static final long serialVersionUID = 5521778654723750685L;

    public PresetConditionsDeserializer() {
        super(PresetConditionsDeserializer.class);
    }

    @Override
    public PresetConditionsVo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        PresetConditionsVo pcv = new PresetConditionsVo();
        JsonNode node = p.getCodec().readTree(p);

        JsonNode alias = node.get("alias");
        if (alias != null) {
            pcv.setAlias(alias.asText());
        }
        JsonNode type = node.get("type");
        if (type != null) {
            pcv.setType(type.asText());
        }
        JsonNode field = node.get("field");
        if (field != null) {
            pcv.setField(field.asText());
        }
        JsonNode opt = node.get("opt");
        if (opt != null) {
            pcv.setOpt(opt.asInt());
        }
        JsonNode value = node.get("value");
        if (value != null) {
            if (value.isArray()) {
                final List<String> vs = new ArrayList<>();
                ArrayNode.class.cast(value).forEach(n -> vs.add(n.asText()));
                pcv.setValues(vs);
            } else {
                pcv.setValue(value.asText());
            }
        }

        return pcv;
    }

}
