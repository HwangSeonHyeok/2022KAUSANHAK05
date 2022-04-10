package com.example.takeeat;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0012\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/example/takeeat/ShoppingListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/takeeat/ShoppingListAdapter;", "getAdapter", "()Lcom/example/takeeat/ShoppingListAdapter;", "setAdapter", "(Lcom/example/takeeat/ShoppingListAdapter;)V", "binding", "Lcom/example/takeeat/databinding/ActivityShoppinglistBinding;", "db", "Lcom/example/takeeat/AppDatabase;", "getDb", "()Lcom/example/takeeat/AppDatabase;", "setDb", "(Lcom/example/takeeat/AppDatabase;)V", "viewmodel", "Lcom/example/takeeat/ShoppingListViewModel;", "getViewmodel", "()Lcom/example/takeeat/ShoppingListViewModel;", "setViewmodel", "(Lcom/example/takeeat/ShoppingListViewModel;)V", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "app_debug"})
public final class ShoppingListActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.takeeat.databinding.ActivityShoppinglistBinding binding;
    public com.example.takeeat.ShoppingListViewModel viewmodel;
    public com.example.takeeat.ShoppingListAdapter adapter;
    public com.example.takeeat.AppDatabase db;
    
    public ShoppingListActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.ShoppingListViewModel getViewmodel() {
        return null;
    }
    
    public final void setViewmodel(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListViewModel p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.ShoppingListAdapter getAdapter() {
        return null;
    }
    
    public final void setAdapter(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListAdapter p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.takeeat.AppDatabase getDb() {
        return null;
    }
    
    public final void setDb(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.AppDatabase p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
}