import {getStringify, getType} from "./getType.js";
import {TypeReturn} from "./TypeReturn.js";

const init =v=>({isPrimitive:!"array,object".includes(getType(v)), ...accumulate(v, [], "", 0, "array", [v])});
const accumulate =(v, stack, acc, idx, type, target)=>{
    if (!"array,object".includes(type)) throw `invalid type:${type}`;
    const isArray = type === "array";
    const targetV = isArray ? v : v[1];
    let r = getStringify(targetV)(targetV, stack, acc, idx, type, target);
    if(r === null) r = TypeReturn.get(acc, idx, type, target);
    else{
        if (!(r instanceof TypeReturn)) r = TypeReturn.get(r, idx, type, target);
        r.acc = `${acc}${idx > 0 ? "," : ""}${isArray ? "" : `"${v[0]}":`}${r.acc}`;
    }
    return r;
};
export {accumulate, init};