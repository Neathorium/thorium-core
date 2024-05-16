package com.neathorium.thorium.core.constants.clipboard;

import com.neathorium.thorium.core.constants.CastDataConstants;
import com.neathorium.thorium.core.records.clipboard.ClipboardData;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.HashMap;
import java.util.Map;

public abstract class ClipboardConstants {
    public static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    public static final Map<String, String> COMMANDS = new HashMap<>();
    public static final ClipboardData<String> CLIPBOARD_DATA = new ClipboardData<>(CastDataConstants.CLIPBOARD_STRING, ClipboardConstants.CLIPBOARD, null, DataFlavor.stringFlavor);
}
