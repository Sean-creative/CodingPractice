import {TypeReturn} from "./TypeReturn.js";

const getType = v => {
    let k = typeof v;
    if(k === "object"){
        if (v === null) k = "null";
        else if (v instanceof Array) k = "array";
    }
    if(types[k]) return types[k].type(k, v);
    else throw `invalid type of :${v}`;
};
const types = {}, addType =(k, type, stringify, closer, parser)=>k.split(",").forEach(k=>types[k] = {type, stringify, closer, parser});

//addType이란 함수를 만들어 놓고, 하나씩 등록하는 것
addType("string", k=>k, ((rString = /(")/g)=>v=>`"${v.replace(rString, "\\$1")}"`)());
addType("bigint", _=>"number",v=>v.toString());
addType("number,boolean", k=>k,v=>v.toString());
addType("null,symbol,function", k=>k, _=>"null");
addType("undefined", k=>k, (v, stack, acc, idx, type, target)=>type === "object" ? null : "null");
addType("array", k=>k,
    (v, stack, acc, idx, type, target)=>{
        stack.push({target, idx:idx + 1, type});
        return TypeReturn.get("[", -1, "array", v);
    },
    acc=>`${acc}]`
);
addType("object", _=>"object",
    (v, stack, acc, idx, type, target)=>{
        stack.push({target, idx:idx + 1, type});
        return TypeReturn.get("{", -1, "object", Object.entries(v));
    },
    acc=>`${acc}}`
);

const getStringify=v=>types[getType(v)].stringify;
const getCloser=v=>types[v].closer;


export {getType, getCloser, getStringify};