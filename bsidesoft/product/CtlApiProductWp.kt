package com.slab.admin.ctrl.api.product

import com.slab.admin.ctrl.api.AdminArg
import com.slab.admin.ctrl.api.AdminErr
import com.slab.admin.ctrl.api.AdminReq
import com.slab.admin.ctrl.api.Common
import ein.core.value.eJsonArray
import ein.core.value.eJsonObject
import ein.core.value.eLong
import ein.core.value.eValue
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/*
req
{
    "title":"product@random@",
    "stockCnt":1,
    "isTemp":false,
    "limitCnt":50,
    "isActive":"0",
    "vendorR":1,
    "price":"999",
    "originPrice":"1000",
    "thumbNail":"https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4arX/image/TrbRquy4TZ1rvMVYBYaIXp0cTxo.jpg",
    "imageList":["https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4arX/image/TrbRquy4TZ1rvMVYBYaIXp0cTxo.jpg"],
    "desc":"",
    "descImgList":["https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/4arX/image/TrbRquy4TZ1rvMVYBYaIXp0cTxo.jpg"],
    "ship":"0",
    "info":"asdf",
    "volume":1,
    "weight":1,
    "horizontal":1,
    "vertical":1,
    "depth":1,
    "temperature":"0"
}

res
{}
*/







@Controller
class CtlApiProductWp {
    @PostMapping("/api/product/wp")
    fun action(req: HttpServletRequest) {
        val R = AdminReq(req)
        R.ver("1")
        R.moveToMainIfIsLoginInfoChanged()

        val args = R.request(
            AdminArg("title", AdminArg.Type.S, "product/title"){ msg, v ->
                val cnt = "$v".length
                msg.replace("@cnt@", "$cnt")
            },
            AdminArg("stockCnt", AdminArg.Type.L, "product/stockCnt"),
            AdminArg("isTemp", AdminArg.Type.B, "common/boolean"),
            AdminArg("limitCnt", AdminArg.Type.L, "product/limitCnt"),
            AdminArg("isActive", AdminArg.Type.S, "common/active"),
            AdminArg("vendorR", AdminArg.Type.L, "product/vendorR"),
            AdminArg("price", AdminArg.Type.S, "product/price"),
            AdminArg("originPrice", AdminArg.Type.S, "product/originPrice"),
            AdminArg("thumbnail", AdminArg.Type.S, "product/thumbnail"),
            AdminArg("imageList", AdminArg.Type.Ja, "product/imageList"),
            AdminArg("desc", AdminArg.Type.S, "product/desc"),
            AdminArg("descImgList", AdminArg.Type.Ja, "product/descImgList"),
            AdminArg("ship", AdminArg.Type.S, "common/string"),
            AdminArg("info", AdminArg.Type.S, "product/info"),
            AdminArg("volume", AdminArg.Type.L),
            AdminArg("weight", AdminArg.Type.L),
            AdminArg("horizontal", AdminArg.Type.L),
            AdminArg("vertical", AdminArg.Type.L),
            AdminArg("depth", AdminArg.Type.L),
            AdminArg("temperature", AdminArg.Type.S)
        ).let{(err, arg)->
            if(err != null || arg == null) return R.response("formError", err)
            arg!!
        }
        //ship value 확인
        val shipList = Common.shipList(args.s("ship"))
        if(shipList.find{ it["selected"] == true } == null) return R.response("formError", mapOf("ship" to "Invalid ship value."))

        //vendor rowid 확인
        val vendorRowid = args.l("vendorR")
        if(R.db.i("vendor/isExsit", 0, "vendor_rowid" to vendorRowid) == 0) return R.response("formError", mapOf("catList" to "Invalid vender rowid."))

        val itemValue = eJsonObject()
        R.productAttrList.forEach{
            when(it){
                AdminReq.ATTR.IMAGES,AdminReq.ATTR.DESCRIPTION_IMAGES-> R.itemValueCdata(itemValue, it, args.ja(it.reqKey))
                AdminReq.ATTR.VOLUME,AdminReq.ATTR.WEIGHT,AdminReq.ATTR.HORIZONTAL,AdminReq.ATTR.VERTICAL,AdminReq.ATTR.DEPTH,AdminReq.ATTR.TEMPERATURE-> args[it.reqKey]?.also{ v-> R.itemValueCdata(itemValue, it, v) }
                AdminReq.ATTR.SHIPPING_TYPE-> itemValue.s(it.key(), args.s(it.reqKey))
                AdminReq.ATTR.SHIPPING-> if(args.s(it.reqKey) == shipList[1]["value"]) R.itemValueCdata(itemValue, it, args.s(it.reqKey))
                else-> R.itemValueCdata(itemValue, it, args.s(it.reqKey))
            }
        }
        val title = args.s("title")
        val isActive = args.s("isActive") == "1"
        val stockCnt = args.l("stockCnt")
        val isTemp = args.b("isTemp")
        val limitCnt = args.l("limitCnt")
        try{
            var productRowid = 0L
            R.db.tx{
                R.sms.productAndStockAdd(R.env.mallId, R.itemTitle(title), isActive, itemValue, stockCnt, limitCnt, "", isTemp){productId->
                    productRowid = l("product/add", 0L, "product_id" to productId, "vendor_rowid" to vendorRowid, "isactive" to if (isActive) 1 else 0)
                    productRowid != 0L
                }
                productRowid != 0L
            }
            if(R.isTest) R.response("r", productRowid)
        }catch(e:Throwable){
            e.printStackTrace()
            R.err(AdminErr.PRODUCT_WP_01)
        }
    }

    @Profile("!real")
    @PostMapping("/api/product/wp/json")
    @ResponseBody
    fun json(req: HttpServletRequest) = eValue.json("""{
           "error":[
            {
                "code":"",
                "method":"move_to_login",
                "message":""
            },
            {
                "code":"",
                "method":"move_to_main",
                "message":""
            }
        ],
        "data":{
            "formError": {
               "title":"품목명을 입력해 주세요.",
               "title2":"200자 이내로 입력해 주세요. (입력 글자 수/200)",
               "stockCnt":"재고량을 입력해 주세요.",
               "stockCnt2":"9,999,999.99 이내로 입력해 주세요.",
               "limitCnt":"품절 기준을 입력해 주세요.",
               "limitCnt2":"9,999,999.99 이내로 입력해 주세요.",
               "vendorR":"제조사를 입력해 주세요.",                                    
               "price":"판매가를 입력해 주세요.",
               "price2":"9,999,999.99 이내로 입력해 주세요.",
               "originPrice":"9,999,999.99 이내로 입력해 주세요.",
               "originPrice2":"판매가보다 낮은 가격은 입력할 수 없습니다.",               
               "thumbnail":"썸네일을 첨부해 주세요.",
               "imageList":"이미지를 첨부해 주세요.",
               "info":"배송 정보를 입력해 주세요."
            }
        }
    }""").stringify()
}