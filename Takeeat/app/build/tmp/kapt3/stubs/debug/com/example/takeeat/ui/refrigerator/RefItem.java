package com.example.takeeat.ui.refrigerator;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\nJ\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JJ\u0010!\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&H\u00d6\u0003J\t\u0010\'\u001a\u00020\bH\u00d6\u0001J\t\u0010(\u001a\u00020\u0003H\u00d6\u0001R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017\u00a8\u0006)"}, d2 = {"Lcom/example/takeeat/ui/refrigerator/RefItem;", "Ljava/io/Serializable;", "itemname", "", "itemtag", "itemexp", "Ljava/util/Date;", "itemamount", "", "itemunit", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/lang/Integer;Ljava/lang/String;)V", "getItemamount", "()Ljava/lang/Integer;", "setItemamount", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getItemexp", "()Ljava/util/Date;", "setItemexp", "(Ljava/util/Date;)V", "getItemname", "()Ljava/lang/String;", "setItemname", "(Ljava/lang/String;)V", "getItemtag", "setItemtag", "getItemunit", "setItemunit", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/lang/Integer;Ljava/lang/String;)Lcom/example/takeeat/ui/refrigerator/RefItem;", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
public final class RefItem implements java.io.Serializable {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String itemname;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String itemtag;
    @org.jetbrains.annotations.Nullable()
    private java.util.Date itemexp;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer itemamount;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String itemunit;
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.ui.refrigerator.RefItem copy(@org.jetbrains.annotations.Nullable()
    java.lang.String itemname, @org.jetbrains.annotations.Nullable()
    java.lang.String itemtag, @org.jetbrains.annotations.Nullable()
    java.util.Date itemexp, @org.jetbrains.annotations.Nullable()
    java.lang.Integer itemamount, @org.jetbrains.annotations.Nullable()
    java.lang.String itemunit) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public RefItem(@org.jetbrains.annotations.Nullable()
    java.lang.String itemname, @org.jetbrains.annotations.Nullable()
    java.lang.String itemtag, @org.jetbrains.annotations.Nullable()
    java.util.Date itemexp, @org.jetbrains.annotations.Nullable()
    java.lang.Integer itemamount, @org.jetbrains.annotations.Nullable()
    java.lang.String itemunit) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getItemname() {
        return null;
    }
    
    public final void setItemname(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getItemtag() {
        return null;
    }
    
    public final void setItemtag(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date getItemexp() {
        return null;
    }
    
    public final void setItemexp(@org.jetbrains.annotations.Nullable()
    java.util.Date p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getItemamount() {
        return null;
    }
    
    public final void setItemamount(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getItemunit() {
        return null;
    }
    
    public final void setItemunit(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
}