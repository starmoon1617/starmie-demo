/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.github.starmoon1617.starmie.core.util.CommonUtils;
import io.github.starmoon1617.starmie.demo.web.vo.PresetConditionsVo;

/**
 * PresetConditionsVo 自定义JSON序列化
 * 
 * @date 2023-12-29
 * @author Nathan Liao
 */
public class PresetConditionsSerializer extends StdSerializer<PresetConditionsVo> {

    private static final long serialVersionUID = 7696868061607403755L;

    public PresetConditionsSerializer() {
        super(PresetConditionsVo.class);
    }

    @Override
    public void serialize(PresetConditionsVo value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        gen.writeStartObject();
        gen.writeStringField("field", value.getField());
        gen.writeStringField("type", value.getType());
        if (value.getOpt() != null) {
            gen.writeNumberField("opt", value.getOpt().intValue());
        } else {
            gen.writeNullField("opt");
        }
        if (value.getAlias() != null) {
            gen.writeStringField("alias", value.getAlias());
        }
        if (CommonUtils.isNotBlank(value.getValue())) {
            gen.writeStringField("value", value.getValue());
        } else if (!CommonUtils.isEmpty(value.getValues())) {
            gen.writeArrayFieldStart("value");
            for (String v : value.getValues()) {
                gen.writeString(v);
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
    }

}
