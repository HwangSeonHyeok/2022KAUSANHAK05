package com.example.takeeat;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006J\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0006R0\u0010\u0003\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u00070\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001a"}, d2 = {"Lcom/example/takeeat/ShoppingListViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "liveData", "Landroidx/lifecycle/MutableLiveData;", "Ljava/util/ArrayList;", "Lcom/example/takeeat/ShoppingListItem;", "Lkotlin/collections/ArrayList;", "getLiveData", "()Landroidx/lifecycle/MutableLiveData;", "setLiveData", "(Landroidx/lifecycle/MutableLiveData;)V", "shoppingListItemData", "getShoppingListItemData", "()Ljava/util/ArrayList;", "setShoppingListItemData", "(Ljava/util/ArrayList;)V", "addData", "", "shoppingListItem", "deleteData", "at", "", "getCount", "updateData", "index", "app_debug"})
public final class ShoppingListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> liveData;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.example.takeeat.ShoppingListItem> shoppingListItemData;
    
    public ShoppingListViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> getLiveData() {
        return null;
    }
    
    public final void setLiveData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.example.takeeat.ShoppingListItem> getShoppingListItemData() {
        return null;
    }
    
    public final void setShoppingListItemData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.example.takeeat.ShoppingListItem> p0) {
    }
    
    public final void addData(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListItem shoppingListItem) {
    }
    
    public final void deleteData(int at) {
    }
    
    public final void updateData(int index, @org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListItem shoppingListItem) {
    }
    
    public final int getCount() {
        return 0;
    }
}