package com.slab.admin.ctrl.api.product.data

import ein.core.model.eModel
import ein.core.model.eModelFactory
import java.sql.Timestamp

class Product:eModel(){
    companion object:eModelFactory<Product>{
        override fun invoke() = Product()
    }
    val productRowid by kv("product_rowid", 0L)
    val productId by kv("product_id", "")
    val vendorTagId by kv("tag_id", "")
    val isActive by kv("isactive", true){
        when(it){
            is Boolean->it
            is Int->it == 1
            else->false
        }
    }
    val regdate by kv("regdate", Timestamp.valueOf("0000-01-01 00:00:00.0"))
}