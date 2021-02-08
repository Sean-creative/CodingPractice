const PRODUCT = TestRequest.generate({
    product: TestRequest.get(
        _ => ({
            "p":1,
            "pid":"",
            "title":"",
            "catType":"0",
            "catList":[{"r":1, "list":[0]}],
            "sort":"0",
            "vender":0,
            "outofstock":"0",
            "isActive":true
        }),
        v => undefined,
        (origin, req, v) =>{
            let result = true;
            Object.entries(origin).every(([k, val])=>{
                if(k in req) return true
                else result = TEST.subKeyTest("data.formError."+k,
                    it =>it ==="Invalid value")(v);
            });
            return result;
        },
        (req, v) => {
            const key = req["key"];
            switch (key){
                case "case 4" : return TEST.subKeyTest("data.formError.pid",it => it === "invalid value.")(v);
                case "case 5" : return TEST.subKeyTest("data.formError.pid",it => it === "20자 이내로 입력해 주세요. (@cnt@/20)")(v);
                case "case 7" : return TEST.subKeyTest("data.formError.title",it => it)(v)
                case "case 8" : return TEST.subKeyTest("data.formError.title",it => it === "200자 이내로 입력해 주세요. (@cnt@/200)")(v)
                case "case 10" : return TEST.subKeyTest("data.formError.catType",it => it)(v)
                case "case 11" : return TEST.subKeyTest("data.formError.catType",it => it)(v)
                case "case 13" : return TEST.subKeyTest("data.formError.catList",it => it)(v)
                case "case 14" : return TEST.subKeyTest("data.formError.catType",it => it)(v)
                case "case 15" : return TEST.subKeyTest("data.formError.catType",it => it === "카테고리를 선택해 주세요.")(v)
                case "case 16" : return TEST.subKeyTest("data.formError.catList",it => it)(v);
                case "case 17" : return TEST.subKeyTest("data.formError.catList",it => it)(v);

                default: return false
            }
        }
    ),
    productw: TestRequest.get(
        _ => ({
            "r":1,
            "key":"clear"
        }),
        v => undefined,
        (origin, req, v) =>{
            let result = true;
            Object.entries(origin).every(([k, val])=>{
                if(k in req) return true;
                else result = TEST.subKeyTest("data.formError."+k,
                    it =>it ==="Invalid value")(v);
            });
            return result;
        },
        (req, v) => {
            const key = req["key"];
            switch (key){
                case "clear" : return (
                    TEST.subKeyTest("data.vendorList", it => {
                        if(it.length>0) {
                            for(const vendor of it) {
                                if(!(typeof vendor.r == 'number' &&
                                    !vendor.title &&
                                    typeof vendor.selected == 'boolean')) return false;
                            } return true;
                        } return false;
                    })(v) &&
                    TEST.subKeyTest("data.activeList", it => {
                        return clearCheck(it, ["value", "string"],["title", "string"],["checked", "boolean"]) && it[0].value === "0" && it[0].checked === true && it[0].title === "active" && it[1].value === "1" && it[1].checked === false && it[1].title === "inactive"
                    })(v) &&
                    TEST.subKeyTest("data.shipList", it => {
                        return clearCheck(it, ["value", "string"],["title", "string"],["checked", "boolean"]) && it[0].value === "0" && it[0].checked === true && it[0].title === "기본 배송 정보" && it[1].value === "1" && it[1].checked === false && it[1].title === "직접 입력"
                    })(v) &&
                    TEST.subKeyTest("data.shipDefault", it => typeof it === "string")(v)
                    // &&
                    // TEST.subKeyTest("data.temperatureList", it => {
                    //     return (it.length === 5 &&
                    //         it[0].value === 0 &&
                    //         it[0].title === "창고 보관 온도" &&
                    //         typeof it[0].selected === "true" &&
                    //         it[1].value === 1 &&
                    //         it[1].title === "냉동(1)" &&
                    //         typeof it[1].selected === "boolean" &&
                    //         it[2].value === 2 &&
                    //         it[2].title === "냉장(2)" &&
                    //         typeof it[2].selected === "boolean" &&
                    //         it[3].value === 3 &&
                    //         it[3].title === "상온(3)" &&
                    //         typeof it[3].selected === "boolean" &&
                    //         it[4].value === 0 &&
                    //         it[4].title === "실온(4)" &&
                    //         typeof it[4].selected === "boolean")
                    // })(v)
                );
                default: return false;
            }
        }
    ),
    productwp: TestRequest.get(
        _ => ({
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
            "temperature":"0",
            "key":"clear"
        }),
        v => undefined,
        (origin, req, v) =>{
            let result = true;
            Object.entries(origin).every(([k, val])=>{
                if(k in req) return true
                else result = TEST.subKeyTest("data.formError."+k,
                    it =>it ==="Invalid value")(v);
            });
            return result;
        },
        (req, v) => {
            const key = req["key"];
            const title = req["title"];
            switch (key){
                case "error" : return TEST.subKeyTest("error",it => it)(v) || TEST.subKeyTest("data.formError",it => it)(v);
                case "case 5" : return TEST.subKeyTest("data.formError.title",it => it === "품목명을 입력해 주세요.")(v);
                case "case 6" : return TEST.subKeyTest("data.formError.title",it => it === `200자 이내로 입력해 주세요. (${title.length}/200)`)(v);
                case "case 8" : return TEST.subKeyTest("data.formError.stockCnt",it => it === "재고량을 입력해 주세요.")(v);
                case "case 9" : return TEST.subKeyTest("data.formError.stockCnt",it => it === "9,999,999.99 이내로 입력해 주세요.")(v);
                case "case 13" : return TEST.subKeyTest("data.formError.limitCnt",it => it === "품절 기준을 입력해 주세요.")(v);
                case "case 14" : return TEST.subKeyTest("data.formError.limitCnt",it => it === "9,999,999.99 이내로 입력해 주세요.")(v);
                case "case 18" : return TEST.subKeyTest("data.formError.vendorR",it => it === "제조사를 입력해 주세요.")(v);
                case "case 22" : return TEST.subKeyTest("data.formError.price",it => it === "판매가를 입력해 주세요.")(v);
                case "case 23" : return TEST.subKeyTest("data.formError.price",it => it === "9,999,999.99 이내로 입력해 주세요.")(v);
                case "case 27" : return TEST.subKeyTest("data.formError.originPrice",it => it === "9,999,999.99 이내로 입력해 주세요.")(v);
                case "case 28" : return TEST.subKeyTest("data.formError.originPrice",it => it === "판매가보다 낮은 가격은 입력할 수 없습니다.")(v);
                case "case 31" : return TEST.subKeyTest("data.formError.thumbnail",it => it === "썸네일을 첨부해 주세요.")(v);
                case "case 35" : return TEST.subKeyTest("data.formError.image",it => it === "이미지를 첨부해 주세요.")(v);
                case "case 49" : return TEST.subKeyTest("data.formError.info",it => it === "배송 정보를 입력해 주세요.")(v);

                case "clear" : return !TEST.subKeyTest("error",it => it)(v) && TEST.subKeyTest("data",it => it && it.formError === undefined)(v);
                default: return false


            }
        }
    ),
    producte: TestRequest.get(
        _ => ({
            "r":1,
            "key":"clear"
        }),
        v => undefined,
        (origin, req, v) =>{
            let result = true;
            Object.entries(origin).every(([k, val])=>{
                if(k in req) return true
                else result = TEST.subKeyTest("data.formError."+k,
                        it =>it ==="Invalid value")(v);
            });
            return result;
        },
        (req, v) => {
            const key = req["key"];
            switch (key){
                case "case 4" : return TEST.subKeyTest("error",it => it)(v) || TEST.subKeyTest("data.formError",it => it)(v);
                case "case 5" : return TEST.subKeyTest("error",it => it)(v) || TEST.subKeyTest("data.formError",it => it)(v);
                case "clear" : return (
                    TEST.subKeyTest("data", it => {
                        return clearCheck(it, ["title", "string"],
                            ["stockCnt", "number"], ["isTemp", "boolean"],
                            ["limitCnt", "number"], ["depth", "number"],
                            ["price", "string"], ["originPrice", "string"],
                            ["thumbnail", "string"], ["desc", "string"],
                            ["volume", "number"], ["weight", "number"],
                            ["horizontal", "number"], ["vertical", "number"],)
                    })(v) &&
                    TEST.subKeyTest("data.imageList", it => {
                        if(it.length>0) {
                            for(const image of it) {
                                if(!(typeof image === "string")) return false
                            } return true
                        } else return false
                    })(v) &&
                    TEST.subKeyTest("data.descImgList", it => {
                        if(it.length>0) {
                            for(const descImg of it) {
                                if(!(typeof descImg === "string")) return false
                            } return true
                        } else return true
                    })(v) &&
                    TEST.subKeyTest("data.isActive", it => {
                       return (it.length === 2 &&
                           it[0].value === 0 &&
                           it[0].title === "active" &&
                           typeof it[0].checked === 'boolean' &&
                           it[1].value === 1 &&
                           it[1].title === "inactive" &&
                           typeof it[1].checked === 'boolean'
                       )
                    }) &&
                    TEST.subKeyTest("data.ship", it => {
                        return (it.length === 2 &&
                            it[0].value === 0 &&
                            it[0].title === "기본값 사용" &&
                            typeof it[0].checked === 'boolean' &&
                            it[1].value === 1 &&
                            it[2].title === "직접 입력" &&
                            typeof it[1].checked === 'boolean'
                        )
                    })(v) &&
                    TEST.subKeyTest("data.temperatureList", it => {
                        return (it.length === 1 &&
                                it[0].value === 0 &&
                                it[0].title === "상온" &&
                                typeof it[0].selected === "boolean")
                    })(v) &&
                    TEST.subKeyTest("data.vendorList", it => {
                        if(it.length>0) {
                            for(const vendor of it) {
                                if(!(typeof vendor.r === 'number' &&
                                !vendor.title &&
                                typeof vendor.selected === 'boolean')) return false
                            } return true
                        } return false
                    })(v) &&
                    TEST.subKeyTest("data.categoryList", it => {
                        if(it.length>0) {
                            for(const category of it) {
                                if(!clearCheck(category, ["r", "number"],
                                    ["title", "string"],["cnt", "number"],
                                    ["isActive", "boolean"],["selected", "boolean"])) return false
                                if(category.subList.length>0){
                                    for(const subCat of category.subList) {
                                        if(!clearCheck(subCat, ["r", "number"],
                                            ["title", "string"],["cnt", "number"],
                                            ["isActive", "boolean"],["selected", "boolean"])) return false
                                    }
                                }
                            } return true
                        } return true
                    })(v)
                );
                case "check" :
                    TEST.subKeyTest("data", it => {
                        return equalCheck(it, req,"title", "stockCnt", "isTemp",
                            "limitCnt", "depth", "price", "originPrice", "thumbnail", "desc",
                            "volume", "weight", "horizontal", "vertical")
                    })(v) &&
                    TEST.subKeyTest("data.imageList", it => {
                        const reqImageList = req["imageList"]
                        if(it.length>0) {
                            for(const image of it) {
                                if(reqImageList.every(it => it !== image)) return false
                            } return true
                        } else return false
                    })(v) &&
                    TEST.subKeyTest("data.descImgList", it => {
                        const reqImageList = req["descImgList"]
                        if(it.length>0) {
                            for(const descImg of it) {
                                if(reqImageList.every(it => it !== descImg)) return false
                            } return true
                        } else return true
                    })(v) &&
                    TEST.subKeyTest("data.isActive", it => {
                        const isActive = req["isActive"]
                        return (it.length === 2 &&
                            it[0].value === 0 &&
                            it[0].title === (isActive===true) &&
                            typeof it[0].checked === 'boolean' &&
                            it[1].value === 1 &&
                            it[1].title === "inactive" &&
                            typeof it[1].checked === (isActive===false)
                        )
                    }) &&
                    TEST.subKeyTest("data.ship", it => {
                        const ship = req["ship"]
                        return (it.length === 2 &&
                            it[0].value === 0 &&
                            it[0].title === "기본값 사용" &&
                            typeof it[0].checked === 'boolean' &&
                            it[1].value === 1 &&
                            it[2].title === "직접 입력" &&
                            typeof it[1].checked === 'boolean'
                        )
                    })(v) &&
                    TEST.subKeyTest("data.temperatureList", it => {
                        const temperature = req["temperature"]
                        return (it.length === 1 &&
                            it[0].value === 0 &&
                            it[0].title === "상온" &&
                            typeof it[0].selected === (it[0].value === temperature))
                    })(v) &&
                    TEST.subKeyTest("data.vendorList", it => {
                        const vendorR = req["vendorR"]
                        if(it.length>0) {
                            for(const vendor of it) {
                                if(!(typeof vendor.r === 'number' &&
                                    !vendor.title &&
                                    typeof vendor.selected === (vendor.r === vendorR))) return false
                            } return true
                        } return false
                    })(v) &&
                    TEST.subKeyTest("data.categoryList", it => {
                        if(it.length>0) {
                            for(const category of it) {
                                if(!clearCheck(category, ["r", "number"],
                                    ["title", "string"],["cnt", "number"],
                                    ["isActive", "boolean"],["selected", "boolean"])) return false
                                if(category.subList.length>0){
                                    for(const subCat of category.subList) {
                                        if(!clearCheck(subCat, ["r", "number"],
                                            ["title", "string"],["cnt", "number"],
                                            ["isActive", "boolean"],["selected", "boolean"])) return false
                                    }
                                }
                            } return true
                        } return true
                    })(v);
                default: return false
            }
        }
    ),
    productep: TestRequest.get(
        _ => ({
            "r":1,
            "title":"product@random@",
            "stockCnt":1,
            "isTemp":false,
            "limitCnt":50,
            "isActive":true,
            "vendorR":1,
            "categoryR": [1],
            "price":"999",
            "originPrice":"1000",
            "thumbNail":"a",
            "imageList":["b","c"],
            "desc":"",
            "descImgList":[],
            "ship": {
                "selected" : 0,
                "info":""
            },
            "volume":1,
            "weight":1,
            "horizontal":1,
            "vertical":1,
            "depth":1,
            "temperature":0,
            "key":"clear"
        }),
        v => undefined,
        (origin, req, v) =>{
            let result = true;
            Object.entries(origin).every(([k, val])=>{
                if(k in req) return true
                else result = TEST.subKeyTest("data.formError."+k,
                    it =>it ==="Invalid value")(v);
            });
            return result;
        },
        (req, v) => {
            const key = req["key"];
            const title = req["title"];
            switch (key){
                case "error" : return TEST.subKeyTest("error",it => it)(v) || TEST.subKeyTest("data.formError",it => it)(v);
                case "case 4" : return TEST.subKeyTest("data.formError.title",it => it === "품목명을 입력해 주세요.")(v);
                case "case 5" : return TEST.subKeyTest("data.formError.title",it => it === `200자 이내로 입력해 주세요. (${title.length}/200)`)(v);
                case "case 8" : return TEST.subKeyTest("data.formError.stockCnt",it => it === "재고량을 입력해 주세요.")(v);
                case "case 9" : return TEST.subKeyTest("data.formError.stockCnt",it => it === "0~9,999,999 이내로 입력해 주세요.")(v);
                case "case 14" : return TEST.subKeyTest("data.formError.limitCnt",it => it === "품절 기준을 입력해 주세요.")(v);
                case "case 15" : return TEST.subKeyTest("data.formError.limitCnt",it => it === "0~9,999,999 이내로 입력해 주세요.")(v);
                case "case 28" : return TEST.subKeyTest("data.formError.price",it => it === "판매가를 입력해 주세요.")(v);
                case "case 29" : return TEST.subKeyTest("data.formError.price",it => it === "0~9,999,999 이내로 입력해 주세요.")(v);
                case "case 32" : return TEST.subKeyTest("data.formError.originPrice",it => it === "0~9,999,999 이내로 입력해 주세요.")(v);
                case "case 33" : return TEST.subKeyTest("data.formError.originPrice",it => it === "판매가보다 낮게 입력해 주세요.")(v);
                case "case 35" : return TEST.subKeyTest("data.formError.thumbnail",it => it === "썸네일을 첨부해 주세요.")(v);
                case "case 37" : return TEST.subKeyTest("data.formError.image",it => it === "이미지를 첨부해 주세요.")(v);
                case "case 47" : return TEST.subKeyTest("data.formError.info",it => it === "배송 정보를 입력해 주세요.")(v);
                case "case 50" : return TEST.subKeyTest("data.formError.volume",it => it === "값을 입력해 주세요.")(v);
                case "case 53" : return TEST.subKeyTest("data.formError.weight",it => it === "값을 입력해 주세요.")(v);
                case "case 56" : return TEST.subKeyTest("data.formError.horizontal",it => it === "값을 입력해 주세요.")(v);
                case "case 59" : return TEST.subKeyTest("data.formError.vertical",it => it === "값을 입력해 주세요.")(v);
                case "case 62" : return TEST.subKeyTest("data.formError.depth",it => it === "값을 입력해 주세요.")(v);
                case "case 65" : return TEST.subKeyTest("data.formError.temperature",it => it === "값을 입력해 주세요.")(v);
                case "clear" : return !TEST.subKeyTest("error",it => it)(v) && TEST.subKeyTest("data",it => it && it.formError === undefined)(v);
                default: return false
            }
        }
    )
});



function clearCheck(it, ...requirement){
    return Object.entries(it).every(([k,_])=>{
        for(const [key, type] of requirement){
            if(typeof it[key] !== type) {
                console.log("key", key, ", type", typeof it[key])
                return false
            }
        }
        return true;
    });
}

function equalCheck(it, req, ...requirement){
    return Object.entries(it).every(([k,_])=>{
        for(const key of requirement){
            if(it[key] === req[key]) {
                console.log("key", key, ", value", it[key])
                return false
            }
        }
        return true;
    });
}

function getCdataLn(cdata,key){
    const obj = cdata[key.split(".")[1]+"ln"];
    const country = obj["@default"];
    console.log(obj[country]);
    return obj[country];
}