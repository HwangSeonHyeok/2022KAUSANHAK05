package com.example.takeeat.ui.refrigerator;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR&\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u0006%"}, d2 = {"Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorAdapter;", "getAdapter", "()Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorAdapter;", "setAdapter", "(Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorAdapter;)V", "binding", "Lcom/example/takeeat/databinding/ActivityAddrefrigeratorBinding;", "getBinding", "()Lcom/example/takeeat/databinding/ActivityAddrefrigeratorBinding;", "setBinding", "(Lcom/example/takeeat/databinding/ActivityAddrefrigeratorBinding;)V", "data", "Landroidx/lifecycle/MutableLiveData;", "Ljava/util/ArrayList;", "Lcom/example/takeeat/ui/refrigerator/RefItem;", "getData", "()Landroidx/lifecycle/MutableLiveData;", "setData", "(Landroidx/lifecycle/MutableLiveData;)V", "viewmodel", "Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorViewModel;", "getViewmodel", "()Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorViewModel;", "setViewmodel", "(Lcom/example/takeeat/ui/refrigerator/AddRefrigeratorViewModel;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "app_debug"})
public final class AddRefrigeratorActivity extends androidx.appcompat.app.AppCompatActivity {
    public com.example.takeeat.databinding.ActivityAddrefrigeratorBinding binding;
    public com.example.takeeat.ui.refrigerator.AddRefrigeratorViewModel viewmodel;
    public com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> data;
    
    public AddRefrigeratorActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.databinding.ActivityAddrefrigeratorBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.databinding.ActivityAddrefrigeratorBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.ui.refrigerator.AddRefrigeratorViewModel getViewmodel() {
        return null;
    }
    
    public final void setViewmodel(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ui.refrigerator.AddRefrigeratorViewModel p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter getAdapter() {
        return null;
    }
    
    public final void setAdapter(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> getData() {
        return null;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.ArrayList<com.example.takeeat.ui.refrigerator.RefItem>> p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
}