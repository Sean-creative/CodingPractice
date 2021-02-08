package com.slab.admin.ctrl.api.product

import com.slab.admin.ctrl.api.AdminReq
import ein.core.value.eValue
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/*
관리자6 - 상품수정

[요청값]
{
    "r":1,
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

[응답값]
{

}
*/
@Controller
class CtlApiProductEp{
    @PostMapping("/api/product/ep")
    fun action(req:HttpServletRequest){
        val R = AdminReq(req)
        R.ver("1")
    }

    @Profile("!real")
    @PostMapping("/api/product/ep/json")
    @ResponseBody
    fun json(req:HttpServletRequest) = eValue.json("""{
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
            "formError":{
                "title":"품목명을 입력해 주세요.",
                "title2":"200자 이내로 입력해 주세요. (입력 글자 수/200)",
                "stockCnt":"재고량을 입력해 주세요.",
                "stockCnt2":"0~9,999,999 이내로 입력해 주세요.",
                "limitCnt":"품절 기준을 입력해 주세요.",
                "limitCnt2":"0~9,999,999 이내로 입력해 주세요.",
                "price":"판매가를 입력해 주세요.", 
                "price2":"0~9,999,999 이내로 입력해 주세요.",                
                "originPrice":"0~9,999,999 이내로 입력해 주세요.",
                "originPrice2":"판매가보다 낮게 입력해 주세요.",
                "thumbnail":"썸네일을 첨부해 주세요.",
                "image":"이미지를 첨부해 주세요.",
                "info":"배송 정보를 입력해 주세요."
            }
        }
    }""").stringify()
//                "volume":"값을 입력해 주세요.",
//                "horizontal":"값을 입력해 주세요.",
//                "vertical":"값을 입력해 주세요.",
//                "depth":"값을 입력해 주세요.",
//                "temperature":"값을 입력해 주세요."
}