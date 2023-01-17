package com.adobe.aem.guides.wknd.core.utils;

import java.util.Locale;
import java.util.Optional;

import com.day.cq.search.Predicate;
import com.day.cq.search.eval.AbstractPredicateEvaluator;
import com.day.cq.search.eval.EvaluationContext;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(factory = "com.day.cq.search.eval.PredicateEvaluator/equalsIgnoreCase")
public class CustomQueryPredicate extends AbstractPredicateEvaluator {

    private static final Logger log = LoggerFactory.getLogger(CustomQueryPredicate.class);

    static final String PREDICATE_PROPERTY = "property";
    static final String PREDICATE_VALUE = "value";
    static final String PREDICATE_LOCALE = "locale";
    static final String PREDICATE_OPERATION = "operation";

    @Override
    public String getXPathExpression(Predicate predicate, EvaluationContext context) {
        log.debug("Evaluating predicate: {}", predicate);
        String property = predicate.get(PREDICATE_PROPERTY);
        Locale locale = Optional.ofNullable(predicate.get(PREDICATE_LOCALE)).map(lt -> Locale.forLanguageTag(lt))
                .orElse(Locale.getDefault());
        String value = predicate.get(PREDICATE_VALUE).toLowerCase(locale).replace("'", "''");
        String query = String.format("fn:lower-case(@%s)='%s'", property, value);
        log.debug("Generated query: {}", query);
        return query;
    }
}