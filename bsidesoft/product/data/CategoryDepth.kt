package com.slab.admin.ctrl.api.product.data

import ein.core.model.eModel
import ein.core.model.eModelFactory
import java.sql.Timestamp

class CategoryDepth:eModel(){
    companion object:eModelFactory<CategoryDepth>{
        override fun invoke() = CategoryDepth()
    }
    val depth by kv("depth", 0)
    val tagId1 by kv("tag_id1", "")
    val tagId2 by kv("tag_id2", "")
}