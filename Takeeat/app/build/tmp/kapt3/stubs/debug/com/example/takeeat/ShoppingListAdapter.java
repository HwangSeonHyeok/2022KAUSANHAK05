package com.example.takeeat;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0015B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u0007\u00a8\u0006\u0016"}, d2 = {"Lcom/example/takeeat/ShoppingListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/takeeat/ShoppingListAdapter$ViewHolder;", "data", "Landroidx/lifecycle/LiveData;", "Ljava/util/ArrayList;", "Lcom/example/takeeat/ShoppingListItem;", "(Landroidx/lifecycle/LiveData;)V", "getData", "()Landroidx/lifecycle/LiveData;", "setData", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
public final class ShoppingListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.takeeat.ShoppingListAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> data;
    
    public ShoppingListAdapter(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> data) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> getData() {
        return null;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<java.util.ArrayList<com.example.takeeat.ShoppingListItem>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.takeeat.ShoppingListAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\f\u00a8\u0006\u0015"}, d2 = {"Lcom/example/takeeat/ShoppingListAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "parent", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "itemCountText", "Landroid/widget/TextView;", "getItemCountText", "()Landroid/widget/TextView;", "itemMinusButton", "Landroidx/appcompat/widget/AppCompatButton;", "getItemMinusButton", "()Landroidx/appcompat/widget/AppCompatButton;", "itemNameEdit", "Landroid/widget/EditText;", "getItemNameEdit", "()Landroid/widget/EditText;", "itemNameText", "getItemNameText", "itemPlusButton", "getItemPlusButton", "app_debug"})
    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView itemNameText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.EditText itemNameEdit = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.appcompat.widget.AppCompatButton itemMinusButton = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView itemCountText = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.appcompat.widget.AppCompatButton itemPlusButton = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getItemNameText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.EditText getItemNameEdit() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.appcompat.widget.AppCompatButton getItemMinusButton() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getItemCountText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.appcompat.widget.AppCompatButton getItemPlusButton() {
            return null;
        }
    }
}