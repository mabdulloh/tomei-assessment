package org.tomei.assessment.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ProductUtil {

    public String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    public String determineStatus(Integer productId) {
        if (productId % 2 == 0) {
            return "IN_STOCK";
        } else {
            return "OUT_OF_STOCK";
        }
    }
}
