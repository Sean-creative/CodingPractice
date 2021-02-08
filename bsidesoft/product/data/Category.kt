package com.slab.admin.ctrl.api.product.data

import ein.core.model.eModel
import ein.core.model.eModelFactory
import java.sql.Timestamp

//class Category:eModel(){
//    companion object:eModelFactory<Category>{
//        override fun invoke() = Category()
//    }
//    val catRowid by kv("cat_rowid", 0L)
//    val tagId1 by kv("tag_id1", "")
//    val tagId2 by kv("tag_id2", "")
//    val isActive by kv("isactive", true){
//        when(it){
//            is Boolean->it
//            is Int->it == 1
//            else->false
//        }
//    }
//    val cnt by kv("cnt", 0L)
//}