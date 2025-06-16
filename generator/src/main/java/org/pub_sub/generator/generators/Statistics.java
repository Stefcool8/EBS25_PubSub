package org.pub_sub.generator.generators;

import org.pub_sub.generator.schema.SchemaField;

import java.util.Map;

public record Statistics(Map<SchemaField, Integer> fieldsFrequencies,
                         Map<SchemaField, Integer> equalOperatorFrequencies, long totalTimeInMillis,
                         long totalRecords) {
}
