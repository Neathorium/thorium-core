package com.neathorium.thorium.core.records.clipboard;

import com.neathorium.thorium.core.extensions.namespaces.CoreUtilities;
import com.neathorium.thorium.core.extensions.namespaces.NullableFunctions;
import com.neathorium.thorium.core.records.caster.BasicCastData;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.util.Objects;

public class ClipboardData<T> {
    public final BasicCastData<T> castData;
    public final Clipboard clipboard;
    public final ClipboardOwner owner;
    public final DataFlavor flavor;

    public ClipboardData(BasicCastData<T> castData, Clipboard clipboard, ClipboardOwner owner, DataFlavor flavor) {
        this.castData = castData;
        this.clipboard = clipboard;
        this.owner = owner;
        this.flavor = flavor;
    }

    @Override
    public boolean equals(Object o) {
        if (CoreUtilities.isEqual(this, o)) {
            return true;
        }

        if (NullableFunctions.isNull(o) || CoreUtilities.isNotEqual(getClass(), o.getClass())) {
            return false;
        }

        final var that = (ClipboardData<?>) o;
        return (
            CoreUtilities.isEqual(castData, that.castData) &&
            CoreUtilities.isEqual(clipboard, that.clipboard) &&
            CoreUtilities.isEqual(owner, that.owner) &&
            CoreUtilities.isEqual(flavor, that.flavor)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(castData, clipboard, owner, flavor);
    }
}
