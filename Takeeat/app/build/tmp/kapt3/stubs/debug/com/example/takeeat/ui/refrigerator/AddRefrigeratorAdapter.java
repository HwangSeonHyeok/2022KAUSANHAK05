package com.example.takeeat.ui.refrigerator;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0015B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u0007\u00a8\u0006\u0016"}, d2 = {"Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorAdapter$ViewHolder;", "data", "Landroidx/lifecycle/LiveData;", "Ljava/util/ArrayList;", "Lcom/example/takeeat/ui/refrigerator/RefItem;", "(Landroidx/lifecycle/LiveData;)V", "getData", "()Landroidx/lifecycle/LiveData;", "setData", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
public final class AddRefrigeratorAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> data;
    
    public AddRefrigeratorAdapter(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> data) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> getData() {
        return null;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter.ViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\"\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u001c\u001a\u00020\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u000f\"\u0004\b&\u0010\u0011R\u001a\u0010\'\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u000f\"\u0004\b)\u0010\u0011\u00a8\u0006*"}, d2 = {"Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "parent", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "calendar", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "getCalendar", "()Ljava/util/Calendar;", "setCalendar", "(Ljava/util/Calendar;)V", "date", "", "getDate", "()I", "setDate", "(I)V", "itemAmountEdit", "Landroid/widget/EditText;", "getItemAmountEdit", "()Landroid/widget/EditText;", "itemEXPText", "Landroid/widget/TextView;", "getItemEXPText", "()Landroid/widget/TextView;", "itemNameEdit", "getItemNameEdit", "itemTagButton", "Landroid/widget/Button;", "getItemTagButton", "()Landroid/widget/Button;", "itemUnitSpinner", "Landroid/widget/Spinner;", "getItemUnitSpinner", "()Landroid/widget/Spinner;", "month", "getMonth", "setMonth", "year", "getYear", "setYear", "app_debug"})
    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.EditText itemNameEdit = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.Button itemTagButton = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView itemEXPText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.EditText itemAmountEdit = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.Spinner itemUnitSpinner = null;
        private java.util.Calendar calendar;
        private int year;
        private int month;
        private int date;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.EditText getItemNameEdit() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.Button getItemTagButton() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getItemEXPText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.EditText getItemAmountEdit() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.Spinner getItemUnitSpinner() {
            return null;
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
    }
}