package com.kloia.configuration;

public class CustomContext {

    static ThreadLocal<RequestScopedAttributes> value = ThreadLocal.withInitial(RequestScopedAttributes::new);

    public static void set(RequestScopedAttributes value) {
        CustomContext.value.set(value);
    }

    public static RequestScopedAttributes get() {
        return value.get();
    }

    public static void remove() {
        value.remove();
    }


}
