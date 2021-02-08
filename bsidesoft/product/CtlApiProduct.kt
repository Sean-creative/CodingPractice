package com.slab.admin.ctrl.api.product

import com.slab.admin.ctrl.api.AdminArg
import com.slab.admin.ctrl.api.AdminReq
import com.slab.admin.ctrl.api.Common
import com.slab.admin.ctrl.api.product.data.CategoryDepth
import com.slab.admin.ctrl.api.product.data.Product
import com.slab.pms6.data.PMSTag
import ein.core.core.eA
import ein.core.core.err
import ein.core.value.eValue
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/*
req
filter
    - 클라에서 선택한 순서대로 보내줘야함.
    - 초기값 {}
    - 활성여부, 카테고리, 제조사 {"active":"0","cat":10,"vendor":0}
{
    "filter":{
        "title":"",
        "cat":10,
        "stock":"t",
        "vendor":0,
        "active":0
    },
    "sort":"",
    "p":1
}

res
data>filter - req 에서 보내준 순서대로 값에 해당하는 이름을 보내줌
*/

@Controller
class CtlApiProduct{
    @PostMapping("/api/product")
    fun action(req: HttpServletRequest){
        val R = AdminReq(req)
        R.ver("1")
        R.moveToMainIfIsLoginInfoChanged()

        val args = R.request(
            AdminArg("filter", AdminArg.Type.Jo, "common/json"),
            AdminArg("sort", AdminArg.Type.S, "product/sort"),
            AdminArg("p", AdminArg.Type.L, "common/page")
        ).let{(err, arg)->
            if(err != null || arg == null) return R.response("formError", err)
            arg!!
        }

        val p = args.l("p")
        val sort = args.s("sort")
        val resFilter = mutableMapOf<String,String>()

        var title = ""
        var stock = ""
        var active = ""
        var vendorRowid = 0L
        var catRowid = 0L

        args.jo("filter").forEach{
            val v = "${it.value.v}"
            if(it.key in eA["title", "active", "stock", "vendor", "cat"]){
                when(it.key){
                    "title"->{
                        title = v
                        resFilter[it.key] = title
                    }
                    "stock"-> stock = v
                    "active"-> active = v
                    "vendor"-> vendorRowid = v.toLong()
                    "cat"-> catRowid = v.toLong()
                }
            }else return R.response("formError", mapOf("msg" to "filter key error:${it.key}"))
        }

        if(stock.isNotBlank() && stock !in Common.ProductStock.values().map{ it.value }) return R.response("formError", mapOf("msg" to "filter stock error:${stock}"))
        var isActive = 2
        if(active.isNotBlank()){
            if(active !in Common.Active.values().map{ it.value }) return R.response("formError", mapOf("msg" to "filter active error:${active}"))
            isActive = active.toInt()
        }

        //venderRowid, tagRowid에 해당하는 Tag Item 정보 가져와서 타이틀 response에 저장
        val tagIds = mutableListOf<String>()
        var vendorTagId = ""
        if(vendorRowid != 0L){
            vendorTagId = R.db.s("vendor/tagId", "", "vendor_rowid" to vendorRowid)
            if(vendorTagId.isBlank()) return R.response("formError", mapOf("msg" to "filter vendor rowid error:$vendorRowid"))
            tagIds.add(vendorTagId)
        }
        var cat1TagId = ""
        var cat2TagId = ""
        if(catRowid != 0L){
            val depth = R.db.queryModel("cat/get/depth", CategoryDepth, "cat_rowid" to catRowid)
                    ?: return R.response("formError", mapOf("msg" to "filter cat rowid error"))
            depth[0].also{
                if(it.depth == 1){
                    cat1TagId = it.tagId1
                    tagIds.add(cat1TagId)
                }else{
                    cat1TagId = it.tagId1
                    cat2TagId = it.tagId2
                    tagIds.add(cat1TagId)
                    tagIds.add(cat2TagId)
                }
            }
        }
        R.pms.tagList(R.env.mallId, AdminReq.ATTR.TITLE.rowid, *tagIds.toTypedArray()).also{ tagList ->
            if(tagIds.size != tagList.size) err("태그 정보 가져오기 실패:${tagIds.size}=${tagList.size}")
            var vendorTag = ""
            var category1Tag = ""
            var category2Tag = ""
            tagList.forEach{
                if(vendorTagId == it.tagId) vendorTag = it.tagTitle
                if(cat2TagId.isBlank()){
                    if(cat1TagId == it.tagId) category1Tag = it.tagTitle
                }else{
                    if(cat2TagId == it.tagId) category2Tag = it.tagTitle
                }
            }
            if(vendorTag.isNotBlank()) resFilter["vendor"] = vendorTag
            if(category1Tag.isNotBlank() && category2Tag.isNotBlank()) resFilter["cat"] = "$${Common.cdataLnGet(category1Tag, R.ln)} - $${Common.cdataLnGet(category2Tag, R.ln)}"
            else if(category1Tag.isNotBlank()) resFilter["cat"] = "${Common.cdataLnGet(category1Tag, R.ln)}(All)"
        }
        
        //리스트 가져오기
        class ProductProduct constructor(val p1:Product, val p2:AdminReq.Product, val vendor:PMSTag)
        val list = R.db.queryModel("product/list", Product, "vendor_rowid" to vendorRowid, "isactive" to isActive)?.let{ productList->
            val tagList = R.tagList(productList.map{ it.vendorTagId }.distinct())
            R.productList(productList.map{ it.productId }).let{ itemList->
                if(productList.size != itemList.size) err("상품 정보 가져오기 실패:${productList.size}=${itemList.size}")
                productList.map{ p1->
                    val p2 = itemList.find{ p2-> p1.productId == p2.productId } ?: err("상품 정보 가져오기 실패:${p1.productId}")
                    val tag = tagList.find{ tag-> p1.vendorTagId == tag.tagId } ?: err("제조사의 정보 가져오기 실패:${p1.productId}")
                    ProductProduct(p1, p2, tag)
                }
            }
        } ?: listOf()


        R.response{
            v(
                "filter" to resFilter,
                "search" to mapOf(
                    "title" to title,
                    "vendorList" to R.vendorList(),
                    "activeList" to Common.activeList("", true),
                    "catList" to "",
                    "stockList" to Common.productStockList("", true)
                ),
                "totalCnt" to 0,
                "sortList" to Common.productSortList(sort),
                "list" to list.map{
                    mapOf(
                        "r" to it.p1.productRowid,
                        "thumbnail" to it.p2.thumbnail,
                        "vendor" to it.vendor.tagTitle,
                        "title" to it.p2.title,
                        "isActive" to it.p1.isActive,
                        "stockCnt" to 10,
                        "stockList" to it.p2.stockList,
                        "price" to it.p2.price,
                        "originalPrice" to it.p2.originalPrice,
                        "sale" to it.p2.sale,
                        "catList" to ""
                    )
                }
            )
        }
    }

    @Profile("!real")
    @PostMapping("/api/product/json")
    @ResponseBody
    fun json(req:HttpServletRequest) = eValue.json("""{
        "data":{
            "filter":{
                "title":"",
                "vendor":"",
                "active":"",
                "cat":"",
                "stock":""
            },
            "search":{
                "title":"",
                "vendorList":[
                   {"value":0, "title":"제조사"}
                ],
                "activeList":[
                    {"value":"", "title":"활성화 여부"},
                    {"value":"1", "title":"active"},
                    {"value":"0", "title":"inactive"}                    
                ],
                "catList":[
                    {
                        "value":0,
                        "title":"카테고리",
                        "subList":[]
                    },
                    {
                        "value":12,
                        "title":"Fruits(All)",
                        "subList":[
                            {
                                "value":123,
                                "title":"Fruits"
                            }
                        ]
                    },
                    {
                        "value":123,
                        "title":"Supplements(All)",
                        "subList":[
                            {
                                "value":123,
                                "title":"Fruits"
                            },
                            {
                                "value":123,
                                "title":"Fruits"
                            }
                        ]
                    }
                ]
            },
            "stockList":[
               {"value":"0", "title":"재고상태"},
               {"value":"1", "title":"Out of stock"},
               {"value":"2", "title":"Temporarily out of stock"}
            ]
        },
        "totalCnt":60,
        "sortList":[
            {"value":"0", "title":"등록 최근 순", "selected":true},
            {"value":"1", "title":"등록 오래된 순", "selected":false},
            {"value":"2", "title":"품목 이름 오름 차순", "selected":false},
            {"value":"3", "title":"품목 이름 내림 차순", "selected":false}
        ],
        "list":[                
            {
                "r":1,
                "thumbnail":"",
                "vendor":"CJ",
                "title":"baby Food pouch 200ml Pink",
                "isActive":true,
                "stockCnt":10,
                "stockList":["t","o"],
                "price":"SG $19.00",
                "originalPrice":"SG $25.00",
                "sale":"21",
                "catList":[["Supplements", ["Pills"]], ["Fruits", ["Apple","Grapes"]]]
            },
            {
                "pid":"21591214124",
                "title":"10 Billion Probiotics Digestive Supplement",
                "vendor":"CJ",
                "isActice":false,
                "thumbnail":"",
                "stockCnt":100,                        
                "stockList":["o"],
                "price":"SG 19.00",
                "originalPrice":"SG 25.00",
                "sale":"21",
                "catList":[["Supplements", ["Pills"]], ["Fruits", ["Apple","Grapes"]]]
            }
        ],
        "page":{
            "prev":{"value":1, "disable":true},
            "next":{"value":10, "disable":false},
            "list":[
                {"value":1, "selected":true}
            ]
        }
    }""").stringify()
}