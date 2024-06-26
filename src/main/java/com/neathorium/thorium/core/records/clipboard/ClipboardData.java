package com.neathorium.thorium.core.records.clipboard;

import com.neathorium.thorium.core.records.caster.CastData;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;

public record ClipboardData<T> (CastData<T, T> CAST_DATA, Clipboard CLIPBOARD, ClipboardOwner OWNER, DataFlavor FLAVOR) {}
