package com.neathorium.thorium.core.constants.systemidentity;

import com.neathorium.thorium.core.namespaces.systemidentity.ConcreteSystemIdentityFunctions;

public abstract class ConcreteSystemIdentityConstants {
    public static final String HOST_NAME = ConcreteSystemIdentityFunctions.getHostName();
    public static final String HOST_ADDRESS = ConcreteSystemIdentityFunctions.isOSX() ? ConcreteSystemIdentityFunctions.getOSXHostAddress() : ConcreteSystemIdentityFunctions.getHostAddress();
}
