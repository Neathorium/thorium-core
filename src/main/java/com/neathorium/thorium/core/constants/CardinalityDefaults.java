package com.neathorium.thorium.core.constants;

import com.neathorium.thorium.core.records.CardinalityData;

public abstract class CardinalityDefaults {
    public static final CardinalityData any = new CardinalityData(false, true, false);
    public static final CardinalityData all = new CardinalityData(true, false, true);
    public static final CardinalityData none = new CardinalityData(true, false, false);
}
