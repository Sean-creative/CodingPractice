package com.slab.admin.ctrl.api.product

import com.slab.admin.ctrl.api.AdminReq
import ein.core.value.eValue
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/*
서비스1 - 상품수정 준비
[요청값]
{
    "data":{
        "baseList":[
            {"key":"memo", "catR":1, "title":"상품명", "memo":"", "defaultValue":"", "data":"", "cntMin":1, "cntLength":0, "itemValue":""},
            {"key":"stock", "catR":4, "title":"재고량", "memo":"", "defaultValue":"", "data":"", "cntMin":1, "cntLength":0, "itemValue":""},
            {"key":"isactive", "catR":3, "title":"활성여부", "memo":"", "defaultValue":"", "data":{"tmpl":"radio", "data":[["활성",true], ["비활성",false]]}, "cntMin":1, "cntLength":0, "itemValue":""},
            {"key":"cat", "catR":3, "title":"카테고리", "memo":"", "defaultValue":"", "data":{"tmpl":"check", "data":[["Fruit",false], ["Sale",false], ["Hit",false]]}, "cntMin":1, "cntLength":0, "itemValue":""}
        ],
        "attrList":[
            {"r":2, "catR":1, "title":"상품명", "memo":"", "defaultValue":"", "data":"", "cntmin":1, "cntlength":0, "itemValue":""},
            {"r":3, "catR":1, "title":"이미지", "memo":"", "defaultValue":"", "data":"", "cntmin":1, "cntlength":9, "itemValue":""},
            {"r":4, "catR":1, "title":"썸네일", "memo":"", "defaultValue":"", "data":"", "cntmin":1, "cntlength":0, "itemValue":""},
            {"r":5, "catR":1, "title":"정가", "memo":"", "defaultValue":"", "data":"", "cntmin":1, "cntlength":0, "itemValue":""},
            {"r":6, "catR":1, "title":"할인가", "memo":"", "defaultValue":"", "data":"", "cntmin":0, "cntlength":1, "itemValue":""}
        ]
    }
}
 */
@Controller
class CtlApiProductE{
    @PostMapping("/api/product/e")
    fun action(req:HttpServletRequest){
        val R = AdminReq(req)
        R.ver("1")
    }

    @Profile("!real")
    @PostMapping("/api/product/e/json")
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
            "title":"Apple",
            "stockCnt":100,
            "isTemp":false,
            "limitCnt":5,
            "isActive":[
                  {value: 0, title: "active", checked: true},
                  {value: 1, title: "inactive", checked: false}
            ],
            "vendorList":[
                {
                    "r":1,
                    "title":"Samsung",
                    "selected":true
                },                
                {
                    "r":2,
                    "title":"LG",
                    "selected":false
                }
            ],
            "categoryList":[
                {
                    "r":1,
                    "title":"Fruits",
                    "cnt":10,
                    "isActive":true,
                    "selected":false,
                    "subList":[
                        {
                            "r":2,
                            "title":"Orange",
                            "cnt":3,
                            "isActive":true,
                            "selected":false
                        },
                        {
                            "r":3,
                            "title":"Berry",
                            "cnt":4,
                            "isActive":true,
                            "selected":false
                        }
                    ]
                },
                {
                    "r":4,
                    "title":"Supplements",
                    "cnt":1,
                    "isActive":true,
                    "selected":true,
                    "subList":[
                        {
                            "r":5,
                            "title":"Pills",
                            "cnt":1,
                            "isActive":true,
                            "selected":true
                        },
                        {
                            "r":6,
                            "title":"Capsules",
                            "cnt":0,
                            "isActive":false,
                            "selected":false
                        }
                    ]
                }
            ],
            "price":"99999999",
            "originPrice":"999999999",
            "thumbnail":"",
            "imageList":["","","","",""],
            "desc":"",
            "descImgList":["","","","","","",""],
            "ship":[ 
                 {value: 0, title: "기본 배송 정보", checked: true},
                 {value: 1, title: "직접 입력", checked: false}
            ],
            "volume":20,
            "weight":20,
            "horizontal":20,
            "vertical":20,
            "depth":20,
            "temperatureList":[
                {
                    "value":0,
                    "title":"창곱 보관 온도",
                    "selected":true
                },
                {
                    "value":1,
                    "title":"냉동",
                    "selected":false
                },
                {
                    "value":2,
                    "title":"냉장",
                    "selected":false
                },
                {
                    "value":3,
                    "title":"상온",
                    "selected":false
                },
                {
                    "value":4,
                    "title":"실온",
                    "selected":false
                }
            ]
        }
    }""").stringify()
}