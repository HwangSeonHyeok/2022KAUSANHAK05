package com.example.takeeat.ui.refrigerator;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u000e\u0010&\u001a\u00020\u001f2\u0006\u0010\'\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\"\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0011\u00a8\u0006("}, d2 = {"Lcom/example/takeeat/ui/refrigerator/RefItemDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/takeeat/databinding/ActivityRefitemdetailBinding;", "calendar", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "getCalendar", "()Ljava/util/Calendar;", "setCalendar", "(Ljava/util/Calendar;)V", "date", "", "getDate", "()I", "setDate", "(I)V", "month", "getMonth", "setMonth", "refItem", "Lcom/example/takeeat/ui/refrigerator/RefItem;", "getRefItem", "()Lcom/example/takeeat/ui/refrigerator/RefItem;", "setRefItem", "(Lcom/example/takeeat/ui/refrigerator/RefItem;)V", "year", "getYear", "setYear", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "updateUI", "itemData", "app_debug"})
public final class RefItemDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.takeeat.databinding.ActivityRefitemdetailBinding binding;
    public com.example.takeeat.ui.refrigerator.RefItem refItem;
    private java.util.Calendar calendar;
    private int year;
    private int month;
    private int date;
    
    public RefItemDetailActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.ui.refrigerator.RefItem getRefItem() {
        return null;
    }
    
    public final void setRefItem(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ui.refrigerator.RefItem p0) {
    }
    
    public final java.util.Calendar getCalendar() {
        return null;
    }
    
    public final void setCalendar(java.util.Calendar p0) {
    }
    
    public final int getYear() {
        return 0;
    }
    
    public final void setYear(int p0) {
    }
    
    public final int getMonth() {
        return 0;
    }
    
    public final void setMonth(int p0) {
    }
    
    public final int getDate() {
        return 0;
    }
    
    public final void setDate(int p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void updateUI(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ui.refrigerator.RefItem itemData) {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
}