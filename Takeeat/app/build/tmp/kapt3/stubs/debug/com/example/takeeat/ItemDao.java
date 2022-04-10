package com.example.takeeat;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\b\u0010\u0006\u001a\u00020\u0003H\'J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\'J!\u0010\t\u001a\u00020\u00032\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u000b\"\u00020\u0005H\'\u00a2\u0006\u0002\u0010\fJ!\u0010\r\u001a\u00020\u00032\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u000b\"\u00020\u0005H\'\u00a2\u0006\u0002\u0010\f\u00a8\u0006\u000e"}, d2 = {"Lcom/example/takeeat/ItemDao;", "", "delete", "", "shoppingListItem", "Lcom/example/takeeat/ShoppingListItem;", "deleteAll", "getAll", "", "insertItem", "shoppinglistitem", "", "([Lcom/example/takeeat/ShoppingListItem;)V", "updateItem", "app_debug"})
public abstract interface ItemDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM shoppinglistitem")
    public abstract java.util.List<com.example.takeeat.ShoppingListItem> getAll();
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    public abstract void insertItem(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListItem... shoppinglistitem);
    
    @androidx.room.Delete()
    public abstract void delete(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListItem shoppingListItem);
    
    @androidx.room.Query(value = "DELETE FROM shoppinglistitem")
    public abstract void deleteAll();
    
    @androidx.room.Update()
    public abstract void updateItem(@org.jetbrains.annotations.NotNull()
    com.example.takeeat.ShoppingListItem... shoppinglistitem);
}