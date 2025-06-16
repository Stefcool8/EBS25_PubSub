package org.pub_sub.generator.schema;

import java.util.List;

public class SchemaBuilder {
    public static Schema build() {
        return new Schema(List.of(
                SchemaFields.STATION,
                SchemaFields.CITY,
                SchemaFields.TEMP,
                SchemaFields.RAIN,
                SchemaFields.WIND,
                SchemaFields.DIRECTION,
                SchemaFields.DATE
        ));
    }
}
