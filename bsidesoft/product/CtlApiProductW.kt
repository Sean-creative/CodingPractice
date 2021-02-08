package com.slab.admin.ctrl.api.product

import com.slab.admin.ctrl.api.AdminErr
import com.slab.admin.ctrl.api.AdminReq
import com.slab.admin.ctrl.api.Common
import ein.core.value.eValue
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/*
req
{}

res
{}
*/

@Controller
class CtlApiProductW{
    @PostMapping("/api/product/w")
    fun action(req: HttpServletRequest){
        val R = AdminReq(req)
        R.ver("1")
        R.moveToMainIfIsLoginInfoChanged()

        val vendorList = R.vendorList() ?: return R.err(AdminErr.PRODUCT_W_01)
        R.response{
            v(
                "vendorList" to vendorList.map{mapOf("r" to it.rowid, "title" to it.title, "selected" to false)},
                "activeList" to Common.activeList(),
                "shipList" to Common.shipList(),
                "shipDefault" to AdminReq.ATTR.SHIPPING_TYPE.defaultValue,
                "temperatureList" to Common.temperatureList()
            )
        }
    }

    @Profile("!real")
    @PostMapping("/api/product/w/json")
    @ResponseBody
    fun json(req:HttpServletRequest) = eValue.json("""{
        "data":{
            "vendorList":[
                {"r":1, "title":"CJ", "selected":false},{"r":2, "title":"BHC", "selected":false}

            ],            

            "activeList":[
                  {value: "0", title: "active", checked: true},
                  {value: "1", title: "inactive", checked: false}
            ],
            "shipList":[
                {value: "0", title: "기본 배송 정보", checked: true},
                {value: "1", title: "직접 입력", checked: false}
            ],
            "shipDefault":"Singapore deliveries are managed by our logistics partner “DEARCUS Logistics Singapore.”<br><br>We offer FREE SHIPPING for all orders above ${'$'}120.00.<br>Flat shipping fee ${'$'}20.00 will be charged for all orders under ${'$'}120.00.<br><br>You will receive a SMS text message as soon as delivery has been scheduled.",
            "temperatureList":[
                {
                    "value":"0",
                    "title":"창고 보관 온도",
                    "selected":true
                },
                {
                    "value":"1",
                    "title":"냉동(1)",
                    "selected":false
                },
                {
                    "value":"1",
                    "title":"냉장(2)",
                    "selected":false
                },
                {
                    "value":"3",
                    "title":"상온(3)",
                    "selected":false
                },
                {
                    "value":"4",
                    "title":"실온(4)",
                    "selected":false
                }
            ]
        }
    }""").stringify()
}